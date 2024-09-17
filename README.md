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
------------------------------------------------------------------------------
FEB ISSUE
To implement the described requirements, we will design a solution that:

Backs up records from the CZ_LN_BILLING_STMT_TXN table into two backup tables: CZ_LN_BILLING_STMT_TXN_H and CZ_LN_BILLING_STMT_TXN_H_N.
Ensures that the CZ_LN_BILLING_STMT_TXN_H_N table contains only one month's records using the DATE_RUN column.
Compares the CZ_LN_BILLING_STMT_TXN table with CZ_LN_BILLING_STMT_TXN_H_N using the transaction reference column (TRANSACTION_REF) to prevent duplicates.
Removes duplicates from the CZ_LN_BILLING_STMT_TXN table if records exist in both tables.
Step-by-Step Process:
1. Back Up Records
This procedure will back up records from the CZ_LN_BILLING_STMT_TXN table to the two backup tables (CZ_LN_BILLING_STMT_TXN_H and CZ_LN_BILLING_STMT_TXN_H_N).

SQL Code:
sql
Copy code
CREATE OR REPLACE PROCEDURE CZ_AP_BATCH_EXT_LOAN_BILLING AS
BEGIN
    -- Backup records to CZ_LN_BILLING_STMT_TXN_H
    INSERT INTO CZ_LN_BILLING_STMT_TXN_H (TRANSACTION_REF, DATE_RUN, ...)
    SELECT TRANSACTION_REF, DATE_RUN, ...
    FROM CZ_LN_BILLING_STMT_TXN;

    -- Backup records to CZ_LN_BILLING_STMT_TXN_H_N
    INSERT INTO CZ_LN_BILLING_STMT_TXN_H_N (TRANSACTION_REF, DATE_RUN, ...)
    SELECT TRANSACTION_REF, DATE_RUN, ...
    FROM CZ_LN_BILLING_STMT_TXN;

    COMMIT;
END;
/
This will insert all records from the CZ_LN_BILLING_STMT_TXN table into both backup tables (CZ_LN_BILLING_STMT_TXN_H and CZ_LN_BILLING_STMT_TXN_H_N).

2. Clean Old Records in CZ_LN_BILLING_STMT_TXN_H_N
Next, we ensure that CZ_LN_BILLING_STMT_TXN_H_N only contains records for the current month by removing older records based on the DATE_RUN column.

SQL Code:
sql
Copy code
CREATE OR REPLACE PROCEDURE CLEAN_OLD_RECORDS_H_N AS
BEGIN
    -- Delete records older than one month in CZ_LN_BILLING_STMT_TXN_H_N
    DELETE FROM CZ_LN_BILLING_STMT_TXN_H_N
    WHERE DATE_RUN < TRUNC(ADD_MONTHS(SYSDATE, -1), 'MM'); -- Only keep current month's records

    COMMIT;
END;
/
This procedure will keep only the records for the current month and remove older records.

3. Compare and Remove Duplicates
Finally, we compare the records between the two tables (CZ_LN_BILLING_STMT_TXN and CZ_LN_BILLING_STMT_TXN_H_N) based on the transaction reference and DATE_RUN to remove duplicates from CZ_LN_BILLING_STMT_TXN.

SQL Code:
sql
Copy code
CREATE OR REPLACE PROCEDURE CHECK_DUPLICATES AS
BEGIN
    -- Remove duplicate records from CZ_LN_BILLING_STMT_TXN where the same transaction exists in CZ_LN_BILLING_STMT_TXN_H_N
    DELETE FROM CZ_LN_BILLING_STMT_TXN
    WHERE EXISTS (
        SELECT 1
        FROM CZ_LN_BILLING_STMT_TXN_H_N h
        WHERE h.TRANSACTION_REF = CZ_LN_BILLING_STMT_TXN.TRANSACTION_REF
        AND h.DATE_RUN = CZ_LN_BILLING_STMT_TXN.DATE_RUN
    );

    COMMIT;
END;
/
This procedure removes duplicates from CZ_LN_BILLING_STMT_TXN by comparing it with CZ_LN_BILLING_STMT_TXN_H_N.

Workflow:
Backup records: Run the CZ_AP_BATCH_EXT_LOAN_BILLING procedure to back up the records from CZ_LN_BILLING_STMT_TXN to the two backup tables.
Clean old records: Run the CLEAN_OLD_RECORDS_H_N procedure to ensure that CZ_LN_BILLING_STMT_TXN_H_N only has records for the current month.
Check and remove duplicates: Run the CHECK_DUPLICATES procedure to remove duplicate records from the CZ_LN_BILLING_STMT_TXN table by comparing it with CZ_LN_BILLING_STMT_TXN_H_N.
Summary:
Backup Process: Records from CZ_LN_BILLING_STMT_TXN are backed up into CZ_LN_BILLING_STMT_TXN_H and CZ_LN_BILLING_STMT_TXN_H_N.
Clean Up: Older records are removed from CZ_LN_BILLING_STMT_TXN_H_N, keeping only the current month’s records.
Duplicate Check: Records in CZ_LN_BILLING_STMT_TXN are compared with the backup table CZ_LN_BILLING_STMT_TXN_H_N, and duplicates are removed.
This ensures a clean, deduplicated set of records for billing statements.


------------------------------------------------------------
CREATE OR REPLACE PROCEDURE CZ_AD_BILL_SAME_DAY (
    PI_BENACH_CODE      VARCHAR2,
    PI_PROCESS_DATE     DATE,
    PI_NEXT_PROCESS_DATE DATE
)
AS
    -- Declare the cursor to fetch the required data
    CURSOR c_bill_data IS
        SELECT A.ACCOUNT_ID,
               A.ACTUAL_EXECUTION_DATE,
               C.NEXT_BILL_GENERATION_DATE
          FROM FLX_LN_ACCT_PAYMENT_INSTR_B A,
               FLX_LN_ACCOUNTS_B B,
               FLX_LN_ACCT_STATE C
         WHERE A.ACCOUNT_ID = B.ACCOUNT_ID
           AND A.ACCOUNT_ID = C.ACCOUNT_ID
           AND A.ACTUAL_EXECUTION_DATE = C.NEXT_BILL_GENERATION_DATE;

    -- Variables to hold the fetched cursor values
    v_account_id               FLX_LN_ACCT_PAYMENT_INSTR_B.ACCOUNT_ID%TYPE;
    v_actual_execution_date    FLX_LN_ACCT_PAYMENT_INSTR_B.ACTUAL_EXECUTION_DATE%TYPE;
    v_next_bill_generation_date FLX_LN_ACCT_STATE.NEXT_BILL_GENERATION_DATE%TYPE;
BEGIN
    -- Open the cursor
    OPEN c_bill_data;
    
    -- Fetch records one by one
    LOOP
        FETCH c_bill_data INTO v_account_id, v_actual_execution_date, v_next_bill_generation_date;
        
        -- Exit the loop when no more rows are found
        EXIT WHEN c_bill_data%NOTFOUND;
        
        -- Insert the fetched data into the target table
        INSERT INTO TARGET_TABLE_NAME (ACCOUNT_ID, ACTUAL_EXECUTION_DATE, NEXT_BILL_GENERATION_DATE, BENACH_CODE, PROCESS_DATE, NEXT_PROCESS_DATE)
        VALUES (v_account_id, v_actual_execution_date, v_next_bill_generation_date, PI_BENACH_CODE, PI_PROCESS_DATE, PI_NEXT_PROCESS_DATE);
    END LOOP;
    
    -- Close the cursor
    CLOSE c_bill_data;

    -- Commit the transaction
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        -- Handle exceptions and rollback if needed
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20001, 'An error occurred while processing the data: ' || SQLERRM);
END CZ_AD_BILL_SAME_DAY;
/

------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE CZ_DELETE_MATCHING_TXN
AS
    -- Declare a cursor to fetch the ACCOUNT_IDs from the target table
    CURSOR c_target_data IS
        SELECT ACCOUNT_ID 
        FROM TARGET_TABLE_NAME;

    -- Variables to hold fetched values
    v_account_id TARGET_TABLE_NAME.ACCOUNT_ID%TYPE;
    v_txn_ref_no CZ_LN_BILLING_STMT_TXN.TXN_REF_NO%TYPE;
    v_txn_ref_no_h CZ_LN_BILLING_STMT_TXN_H.TXN_REF_NO%TYPE;
    
BEGIN
    -- Open the cursor
    OPEN c_target_data;
    
    -- Fetch each ACCOUNT_ID from the target table
    LOOP
        FETCH c_target_data INTO v_account_id;
        
        -- Exit the loop when no more rows are found
        EXIT WHEN c_target_data%NOTFOUND;

        -- For each ACCOUNT_ID, find matching transactions in CZ_LN_BILLING_STMT_TXN
        FOR txn_record IN (SELECT TXN_REF_NO 
                             FROM CZ_LN_BILLING_STMT_TXN 
                            WHERE ACCOUNT_ID = v_account_id)
        LOOP
            -- Store the TXN_REF_NO for comparison
            v_txn_ref_no := txn_record.TXN_REF_NO;

            -- Check if the TXN_REF_NO exists in CZ_LN_BILLING_STMT_TXN_H
            SELECT TXN_REF_NO
              INTO v_txn_ref_no_h
              FROM CZ_LN_BILLING_STMT_TXN_H
             WHERE TXN_REF_NO = v_txn_ref_no;

            -- If a match is found, delete the record from CZ_LN_BILLING_STMT_TXN
            DELETE FROM CZ_LN_BILLING_STMT_TXN
             WHERE TXN_REF_NO = v_txn_ref_no;

        END LOOP;
    END LOOP;

    -- Close the cursor
    CLOSE c_target_data;

    -- Commit the transaction after processing all records
    COMMIT;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- If no matching transaction is found in CZ_LN_BILLING_STMT_TXN_H, continue processing
        NULL;
    WHEN OTHERS THEN
        -- Handle any other errors and rollback
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20002, 'An error occurred while processing transactions: ' || SQLERRM);
END CZ_DELETE_MATCHING_TXN;
/



