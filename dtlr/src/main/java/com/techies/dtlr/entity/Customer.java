package com.techies.dtlr.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "CNAME")
    private String cname;

    @Column(name = "VAL_ACC")
    private String valAcc;

    @Column(name = "NATIONAL_ID")
    private String nationalId;

    @Column(name = "PASSPORT")
    private String passport;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ID_SUBMITTED")
    private boolean idSubmitted;

    @Column(name = "ID_VERIFIED")
    private boolean idVerified;

    @Column(name = "PIN_NO")
    private String pinNo;

    @Column(name = "TERMS_SIGNED")
    private boolean termsSigned;

    @Column(name = "PREF_LANG")
    private String prefLang;

    @Column(name = "INSTITUTION_ID")
    private String institutionId;

    @Column(name = "MADE_AT")
    private LocalDateTime madeAt;

    @Column(name = "CHECKED_AT")
    private LocalDateTime checkedAt;

    @Column(name = "RSTATUS")
    private String rstatus;

    @Column(name = "BLOCKED_FLAG")
    private boolean blockedFlag;

    @Column(name = "CUST_CAT")
    private String custCat;

    @Column(name = "TPIN_NO")
    private String tpinNo;

    @Column(name = "AUTH_FAIL_PIN")
    private int authFailPin;

    @Column(name = "AUTH_FAIL_TPIN")
    private int authFailTpin;
    
    @Column(name = "CUST_IMAGE")
    private String custimage;
    
    @Column(name = "CUST_PROOF")
    private String custproof;

    @Column(name = "CUST_EXTN1")
    private String custExtn1;

    @Column(name = "CUST_EXTN2")
    private String custExtn2;

    @Column(name = "CUST_EXTN3")
    private String custExtn3;

    @Column(name = "CUST_EXTN4")
    private String custExtn4;

    @Column(name = "CUST_EXTN5")
    private String custExtn5;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the valAcc
	 */
	public String getValAcc() {
		return valAcc;
	}

	/**
	 * @param valAcc the valAcc to set
	 */
	public void setValAcc(String valAcc) {
		this.valAcc = valAcc;
	}

	/**
	 * @return the nationalId
	 */
	public String getNationalId() {
		return nationalId;
	}

	/**
	 * @param nationalId the nationalId to set
	 */
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	/**
	 * @return the passport
	 */
	public String getPassport() {
		return passport;
	}

	/**
	 * @param passport the passport to set
	 */
	public void setPassport(String passport) {
		this.passport = passport;
	}

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the idSubmitted
	 */
	public boolean isIdSubmitted() {
		return idSubmitted;
	}

	/**
	 * @param idSubmitted the idSubmitted to set
	 */
	public void setIdSubmitted(boolean idSubmitted) {
		this.idSubmitted = idSubmitted;
	}

	/**
	 * @return the idVerified
	 */
	public boolean isIdVerified() {
		return idVerified;
	}

	/**
	 * @param idVerified the idVerified to set
	 */
	public void setIdVerified(boolean idVerified) {
		this.idVerified = idVerified;
	}

	/**
	 * @return the pinNo
	 */
	public String getPinNo() {
		return pinNo;
	}

	/**
	 * @param pinNo the pinNo to set
	 */
	public void setPinNo(String pinNo) {
		this.pinNo = pinNo;
	}

	/**
	 * @return the termsSigned
	 */
	public boolean isTermsSigned() {
		return termsSigned;
	}

	/**
	 * @param termsSigned the termsSigned to set
	 */
	public void setTermsSigned(boolean termsSigned) {
		this.termsSigned = termsSigned;
	}

	/**
	 * @return the prefLang
	 */
	public String getPrefLang() {
		return prefLang;
	}

	/**
	 * @param prefLang the prefLang to set
	 */
	public void setPrefLang(String prefLang) {
		this.prefLang = prefLang;
	}

	/**
	 * @return the institutionId
	 */
	public String getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the madeAt
	 */
	public LocalDateTime getMadeAt() {
		return madeAt;
	}

	/**
	 * @param madeAt the madeAt to set
	 */
	public void setMadeAt(LocalDateTime madeAt) {
		this.madeAt = madeAt;
	}

	/**
	 * @return the checkedAt
	 */
	public LocalDateTime getCheckedAt() {
		return checkedAt;
	}

	/**
	 * @param checkedAt the checkedAt to set
	 */
	public void setCheckedAt(LocalDateTime checkedAt) {
		this.checkedAt = checkedAt;
	}

	/**
	 * @return the rstatus
	 */
	public String getRstatus() {
		return rstatus;
	}

	/**
	 * @param rstatus the rstatus to set
	 */
	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	/**
	 * @return the blockedFlag
	 */
	public boolean isBlockedFlag() {
		return blockedFlag;
	}

	/**
	 * @param blockedFlag the blockedFlag to set
	 */
	public void setBlockedFlag(boolean blockedFlag) {
		this.blockedFlag = blockedFlag;
	}

	/**
	 * @return the custCat
	 */
	public String getCustCat() {
		return custCat;
	}

	/**
	 * @param custCat the custCat to set
	 */
	public void setCustCat(String custCat) {
		this.custCat = custCat;
	}

	/**
	 * @return the tpinNo
	 */
	public String getTpinNo() {
		return tpinNo;
	}

	/**
	 * @param tpinNo the tpinNo to set
	 */
	public void setTpinNo(String tpinNo) {
		this.tpinNo = tpinNo;
	}

	/**
	 * @return the authFailPin
	 */
	public int getAuthFailPin() {
		return authFailPin;
	}

	/**
	 * @param authFailPin the authFailPin to set
	 */
	public void setAuthFailPin(int authFailPin) {
		this.authFailPin = authFailPin;
	}

	/**
	 * @return the authFailTpin
	 */
	public int getAuthFailTpin() {
		return authFailTpin;
	}

	/**
	 * @param authFailTpin the authFailTpin to set
	 */
	public void setAuthFailTpin(int authFailTpin) {
		this.authFailTpin = authFailTpin;
	}

	/**
	 * @return the custimage
	 */
	public String getCustimage() {
		return custimage;
	}

	/**
	 * @param custimage the custimage to set
	 */
	public void setCustimage(String custimage) {
		this.custimage = custimage;
	}

	/**
	 * @return the custproof
	 */
	public String getCustproof() {
		return custproof;
	}

	/**
	 * @param custproof the custproof to set
	 */
	public void setCustproof(String custproof) {
		this.custproof = custproof;
	}

	/**
	 * @return the custExtn1
	 */
	public String getCustExtn1() {
		return custExtn1;
	}

	/**
	 * @param custExtn1 the custExtn1 to set
	 */
	public void setCustExtn1(String custExtn1) {
		this.custExtn1 = custExtn1;
	}

	/**
	 * @return the custExtn2
	 */
	public String getCustExtn2() {
		return custExtn2;
	}

	/**
	 * @param custExtn2 the custExtn2 to set
	 */
	public void setCustExtn2(String custExtn2) {
		this.custExtn2 = custExtn2;
	}

	/**
	 * @return the custExtn3
	 */
	public String getCustExtn3() {
		return custExtn3;
	}

	/**
	 * @param custExtn3 the custExtn3 to set
	 */
	public void setCustExtn3(String custExtn3) {
		this.custExtn3 = custExtn3;
	}

	/**
	 * @return the custExtn4
	 */
	public String getCustExtn4() {
		return custExtn4;
	}

	/**
	 * @param custExtn4 the custExtn4 to set
	 */
	public void setCustExtn4(String custExtn4) {
		this.custExtn4 = custExtn4;
	}

	/**
	 * @return the custExtn5
	 */
	public String getCustExtn5() {
		return custExtn5;
	}

	/**
	 * @param custExtn5 the custExtn5 to set
	 */
	public void setCustExtn5(String custExtn5) {
		this.custExtn5 = custExtn5;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authFailPin, authFailTpin, blockedFlag, checkedAt, cif, cname, custCat, custExtn1,
				custExtn2, custExtn3, custExtn4, custExtn5, custimage, custproof, email, id, idSubmitted, idVerified,
				institutionId, madeAt, mobile, nationalId, nationality, passport, pinNo, prefLang, rstatus, termsSigned,
				tpinNo, valAcc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return authFailPin == other.authFailPin && authFailTpin == other.authFailTpin
				&& blockedFlag == other.blockedFlag && Objects.equals(checkedAt, other.checkedAt)
				&& Objects.equals(cif, other.cif) && Objects.equals(cname, other.cname)
				&& Objects.equals(custCat, other.custCat) && Objects.equals(custExtn1, other.custExtn1)
				&& Objects.equals(custExtn2, other.custExtn2) && Objects.equals(custExtn3, other.custExtn3)
				&& Objects.equals(custExtn4, other.custExtn4) && Objects.equals(custExtn5, other.custExtn5)
				&& Objects.equals(custimage, other.custimage) && Objects.equals(custproof, other.custproof)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& idSubmitted == other.idSubmitted && idVerified == other.idVerified
				&& Objects.equals(institutionId, other.institutionId) && Objects.equals(madeAt, other.madeAt)
				&& Objects.equals(mobile, other.mobile) && Objects.equals(nationalId, other.nationalId)
				&& Objects.equals(nationality, other.nationality) && Objects.equals(passport, other.passport)
				&& Objects.equals(pinNo, other.pinNo) && Objects.equals(prefLang, other.prefLang)
				&& Objects.equals(rstatus, other.rstatus) && termsSigned == other.termsSigned
				&& Objects.equals(tpinNo, other.tpinNo) && Objects.equals(valAcc, other.valAcc);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", cif=" + cif + ", cname=" + cname + ", valAcc=" + valAcc + ", nationalId="
				+ nationalId + ", passport=" + passport + ", nationality=" + nationality + ", mobile=" + mobile
				+ ", email=" + email + ", idSubmitted=" + idSubmitted + ", idVerified=" + idVerified + ", pinNo="
				+ pinNo + ", termsSigned=" + termsSigned + ", prefLang=" + prefLang + ", institutionId=" + institutionId
				+ ", madeAt=" + madeAt + ", checkedAt=" + checkedAt + ", rstatus=" + rstatus + ", blockedFlag="
				+ blockedFlag + ", custCat=" + custCat + ", tpinNo=" + tpinNo + ", authFailPin=" + authFailPin
				+ ", authFailTpin=" + authFailTpin + ", custimage=" + custimage + ", custproof=" + custproof
				+ ", custExtn1=" + custExtn1 + ", custExtn2=" + custExtn2 + ", custExtn3=" + custExtn3 + ", custExtn4="
				+ custExtn4 + ", custExtn5=" + custExtn5 + "]";
	}

    
}

