CREATE OR REPLACE PROCEDURE CZ_AP_AC_POP_DRV_AD_INSTR_DLT(
    PI_BRANCH_GROUP_CODE VARCHAR2,
    PI_PROCESS_DATE DATE,
    PI_NEXT_PROCESS_DATE DATE
) AS 

    V_ACCOUNT_NUMBER VARCHAR2(50);
    V_RETURN_CODE VARCHAR2(10);
    V_TXN_REF_NO VARCHAR2(50);
    V_BRANCH_CODE VARCHAR2(10);
    V_ACCT_TYPE VARCHAR2(10);
    V_ROW_CNT NUMBER;
    V_CFG_FAILURE_CNT NUMBER;
    V_ACT_FAILURE_CNT NUMBER;

    CURSOR ACCOUNT IS 
        SELECT a.ACCOUNT_CODE, a.RETURN_CODE, NULL AS TXN_REF_NO, B.BRANCH_CODE, 'OLO' AS ACCT_TYPE 
        FROM CZ_OBP_FM_NSF_COUNTER_LOGS a, FLX_LN_ACCOUNTS B 
        WHERE a.ACCOUNT_CODE = B.ACCOUNT_ID
          AND a.TRANSACTION_DATE = PI_PROCESS_DATE 
          AND A.STATUS='A'
        UNION
        SELECT a.ACCOUNT_CODE, a.RETURN_CODE, a.TXN_REF_NO, B.BRANCH_CODE, 'OLI' AS ACCT_TYPE 
        FROM CZ_OBP_FM_NSF_COUNTER_LOGS a, FLX_LN_ACCOUNTS B 
        WHERE a.ACCOUNT_CODE = B.ACCOUNT_ID
          AND a.TRANSACTION_DATE = PI_PROCESS_DATE 
          AND A.STATUS='A';

    CURSOR ADDITIONAL_ACCOUNTS IS 
        SELECT ACCOUNT_ID, RETURN_CODE, TXN_REF_NO, BRANCH_CODE, ACCT_TYPE 
        FROM SOME_OTHER_TABLE 
        WHERE PROCESS_DATE = PI_PROCESS_DATE;

BEGIN
    BEGIN
        INSERT INTO CZ_MW_AC_AUTO_DEBIT_INSTRL_DLT_DRV_H
        SELECT * FROM CZ_MW_AC_AUTO_DEBIT_INSTRL_DLT_DRV WHERE DATE_RUN < TO_CHAR(PI_PROCESS_DATE,'YYYYMMDD');
        
        DELETE FROM CZ_MW_AC_AUTO_DEBIT_INSTRL_DLT_DRV WHERE DATE_RUN < TO_CHAR(PI_PROCESS_DATE,'YYYYMMDD');
    
    EXCEPTION
        WHEN OTHERS THEN 
            ORA_RAISEERROR(SQLCODE,'Execution FAILED',$$PLSQL_LINE);
    END;
    
    BEGIN 
        OPEN ACCOUNT;
        V_ROW_CNT := 0;
        LOOP
            BEGIN
                FETCH ACCOUNT INTO V_ACCOUNT_NUMBER, V_RETURN_CODE, V_TXN_REF_NO, V_BRANCH_CODE, V_ACCT_TYPE;
                EXIT WHEN ACCOUNT%NOTFOUND;
                
                SELECT NVL(OLO_OLI_SI_FAILURE_CNTR, -1) 
                INTO V_CFG_FAILURE_CNT 
                FROM CZ_MW_FM_NACHA_RETURN_RL_CFG 
                WHERE SEC_CODE = 'PPD' AND ADDENDA_TYPE_CODE = '99' AND RETURN_CODE = V_RETURN_CODE;
                
                SELECT COUNT(ACCOUNT_CODE) 
                INTO V_ACT_FAILURE_CNT 
                FROM CZ_OBP_FM_NSF_COUNTER_LOGS 
                WHERE TRANSACTION_DATE > ADD_MONTHS(PI_PROCESS_DATE, -12)
                  AND STATUS = 'A'
                  AND RETURN_CODE = V_RETURN_CODE
                  AND ACCOUNT_CODE = V_ACCOUNT_NUMBER;
                
                IF V_ACT_FAILURE_CNT >= V_CFG_FAILURE_CNT THEN
                    V_ROW_CNT := V_ROW_CNT + 1;
                    INSERT INTO CZ_MW_AC_AUTO_DEBIT_INSTRL_DLT_DRV
                    (
                        DATE_RUN,
                        SEQ,
                        BRANCH_CODE,
                        BRANCH_GROUP_CODE,
                        PROCESS_RESULT,
                        ERROR_CODE,
                        ERROR_DESC,
                        ACCOUNT_NUMBER,
                        ACCOUNT_TYPE,
                        TXN_REF_NO
                    ) VALUES (
                        TO_CHAR(PI_PROCESS_DATE, 'YYYYMMDD'),
                        V_ROW_CNT,
                        V_BRANCH_CODE,
                        'BRN_GRP_1',
                        0,
                        NULL,
                        NULL,
                        V_ACCOUNT_NUMBER,
                        V_ACCT_TYPE,
                        V_TXN_REF_NO
                    );
                    
                    IF V_ACCT_TYPE = 'OLO' THEN
                        INSERT INTO CZ_MW_AUTODEBIT_NSF_DELETE
                        (
                            DATE_RUN,
                            ACCT_ID,
                            COMP_PREF_ID,
                            DDA_ACCT_NO,
                            STL_TYPE,
                            ACC_TYPE,
                            EVENT_CODE,
                            AMT_COMP_DIR,
                            AMT_COMP_NAME
                        )
                        SELECT 
                            PI_PROCESS_DATE,
                            a.ACC_ID,
                            a.COMP_PREF_ID,
                            a.DDA_ACCT_NO,
                            a.STL_TYPE,
                            'LN',
                            'LN_DRAWDOWN',
                            'P',
                            'LN_PAYMENT_AMT_EQVLNT' 
                        FROM FLX_ST_COMP_PREF_ITEM_B a, 
                             FLX_LN_ACCT_PAYMENT_INSTR_B b, 
                             FLX_LN_ACCT_PAYMENT_INST_LOG c
                        WHERE a.ACC_ID = V_ACCOUNT_NUMBER
                          AND a.ACC_ID = b.ACCOUNT_ID
                          AND a.ACC_ID = c.ACCOUNT_ID
                          AND c.ACTUAL_EXECUTION_DATE = PI_PROCESS_DATE
                          AND a.COMP_PREF_ID = c.SETTLEMENT_REFERENCE_NUMBER
                          AND c.HISTORY_NO = (
                              SELECT MAX(HISTORY_NO) 
                              FROM FLX_LN_ACCT_PAYMENT_INST_LOG 
                              WHERE ACCOUNT_ID = V_ACCOUNT_NUMBER
                          )
                          AND b.OBJECT_STATUS = 'A';
                    END IF;
                END IF;
                
            EXCEPTION 
                WHEN OTHERS THEN
                    ORA_RAISEERROR(SEC_CODE, 'Execution FAILED', $$PLSQL_LINE);
            END;
        END LOOP;
        CLOSE ACCOUNT;

        -- Processing records from ADDITIONAL_ACCOUNTS cursor
        OPEN ADDITIONAL_ACCOUNTS;
        LOOP
            BEGIN
                FETCH ADDITIONAL_ACCOUNTS INTO V_ACCOUNT_NUMBER, V_RETURN_CODE, V_TXN_REF_NO, V_BRANCH_CODE, V_ACCT_TYPE;
                EXIT WHEN ADDITIONAL_ACCOUNTS%NOTFOUND;
                
                V_ROW_CNT := V_ROW_CNT + 1;
                INSERT INTO CZ_MW_AC_AUTO_DEBIT_INSTRL_DLT_DRV
                (
                    DATE_RUN,
                    SEQ,
                    BRANCH_CODE,
                    BRANCH_GROUP_CODE,
                    PROCESS_RESULT,
                    ERROR_CODE,
                    ERROR_DESC,
                    ACCOUNT_NUMBER,
                    ACCOUNT_TYPE,
                    TXN_REF_NO
                ) VALUES (
                    TO_CHAR(PI_PROCESS_DATE, 'YYYYMMDD'),
                    V_ROW_CNT,
                    V_BRANCH_CODE,
                    'BRN_GRP_1',
                    0,
                    NULL,
                    NULL,
                    V_ACCOUNT_NUMBER,
                    V_ACCT_TYPE,
                    V_TXN_REF_NO
                );
                
                -- Similar logic to handle OLO type records
                IF V_ACCT_TYPE = 'OLO' THEN
                    INSERT INTO CZ_MW_AUTODEBIT_NSF_DELETE
                    (
                        DATE_RUN,
                        ACCT_ID,
                        COMP_PREF_ID,
                        DDA_ACCT_NO,
                        STL_TYPE,
                        ACC_TYPE,
                        EVENT_CODE,
                        AMT_COMP_DIR,
                        AMT_COMP_NAME
                    )
                    SELECT 
                        PI_PROCESS_DATE,
                        a.ACC_ID,
                        a.COMP_PREF_ID,
                        a.DDA_ACCT_NO,
                        a.STL_TYPE,
                        'LN',
                        'LN_DRAWDOWN',
                        'P',
                        'LN_PAYMENT_AMT_EQVLNT' 
                    FROM FLX_ST_COMP_PREF_ITEM_B a, 
                         FLX_LN_ACCT_PAYMENT_INSTR_B b, 
                         FLX_LN_ACCT_PAYMENT_INST_LOG c
                    WHERE a.ACC_ID = V_ACCOUNT_NUMBER
                      AND a.ACC_ID = b.ACCOUNT_ID
                      AND a.ACC_ID = c.ACCOUNT_ID
                      AND c.ACTUAL_EXECUTION_DATE = PI_PROCESS_DATE
                      AND a.COMP_PREF_ID = c.SETTLEMENT_REFERENCE_NUMBER
                      AND c.HISTORY_NO = (
                          SELECT MAX(HISTORY_NO) 
                          FROM FLX_LN_ACCT_PAYMENT_INST_LOG 
                          WHERE ACCOUNT_ID = V_ACCOUNT_NUMBER
                      )
                      AND b.OBJECT_STATUS = 'A';
                END IF;
                
            EXCEPTION 
                WHEN OTHERS THEN
                    ORA_RAISEERROR(SEC_CODE, 'Execution FAILED', $$PLSQL_LINE);
            END;
        END LOOP;
        CLOSE ADDITIONAL_ACCOUNTS;

    EXCEPTION
        WHEN OTHERS THEN
            ORA_RAISEERROR(SEC_CODE, 'Execution FAILED', $$PLSQL_LINE);
    END;
END;
