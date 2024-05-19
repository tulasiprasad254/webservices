package com.techies.dtlr.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tailor_orders")
public class TailorOrders {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
	@Column(name = "customer_order_id")
	private String customerorderid;
	@Column(name = "tlr_order_id")
	private String tlrorderid;
	@Column(name = "total_cost")
	private String totalcost;
	@Column(name = "amount_paid")
	private String amountpaid;
	@Column(name = "rstatus")
	private String rstatus;
	@Column(name = "curr_app_status")
	private String currappstatus;
	@Column(name = "made_at")
    private LocalDateTime madeat;
    @Column(name = "updated_at")
    private LocalDateTime updatedat;
    @Column(name = "ext1")
    private String ext1;
    @Column(name = "ext2")
    private String ext2;
    @Column(name = "ext3")
    private String ext3;
    @Column(name = "ext4")
    private String ext4;
    @Column(name = "ext5")
    private String ext5;
    @Column(name = "ext6")
    private String ext6;
    @Column(name = "ext7")
    private String ext7;
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
	 * @return the customerorderid
	 */
	public String getCustomerorderid() {
		return customerorderid;
	}
	/**
	 * @param customerorderid the customerorderid to set
	 */
	public void setCustomerorderid(String customerorderid) {
		this.customerorderid = customerorderid;
	}
	/**
	 * @return the tlrorderid
	 */
	public String getTlrorderid() {
		return tlrorderid;
	}
	/**
	 * @param tlrorderid the tlrorderid to set
	 */
	public void setTlrorderid(String tlrorderid) {
		this.tlrorderid = tlrorderid;
	}
	/**
	 * @return the totalcost
	 */
	public String getTotalcost() {
		return totalcost;
	}
	/**
	 * @param totalcost the totalcost to set
	 */
	public void setTotalcost(String totalcost) {
		this.totalcost = totalcost;
	}
	/**
	 * @return the amountpaid
	 */
	public String getAmountpaid() {
		return amountpaid;
	}
	/**
	 * @param amountpaid the amountpaid to set
	 */
	public void setAmountpaid(String amountpaid) {
		this.amountpaid = amountpaid;
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
	 * @return the currappstatus
	 */
	public String getCurrappstatus() {
		return currappstatus;
	}
	/**
	 * @param currappstatus the currappstatus to set
	 */
	public void setCurrappstatus(String currappstatus) {
		this.currappstatus = currappstatus;
	}
	/**
	 * @return the madeat
	 */
	public LocalDateTime getMadeat() {
		return madeat;
	}
	/**
	 * @param madeat the madeat to set
	 */
	public void setMadeat(LocalDateTime madeat) {
		this.madeat = madeat;
	}
	/**
	 * @return the updatedat
	 */
	public LocalDateTime getUpdatedat() {
		return updatedat;
	}
	/**
	 * @param updatedat the updatedat to set
	 */
	public void setUpdatedat(LocalDateTime updatedat) {
		this.updatedat = updatedat;
	}
	/**
	 * @return the ext1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * @param ext1 the ext1 to set
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * @return the ext2
	 */
	public String getExt2() {
		return ext2;
	}
	/**
	 * @param ext2 the ext2 to set
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * @return the ext3
	 */
	public String getExt3() {
		return ext3;
	}
	/**
	 * @param ext3 the ext3 to set
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	/**
	 * @return the ext4
	 */
	public String getExt4() {
		return ext4;
	}
	/**
	 * @param ext4 the ext4 to set
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	/**
	 * @return the ext5
	 */
	public String getExt5() {
		return ext5;
	}
	/**
	 * @param ext5 the ext5 to set
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	/**
	 * @return the ext6
	 */
	public String getExt6() {
		return ext6;
	}
	/**
	 * @param ext6 the ext6 to set
	 */
	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}
	/**
	 * @return the ext7
	 */
	public String getExt7() {
		return ext7;
	}
	/**
	 * @param ext7 the ext7 to set
	 */
	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}
	@Override
	public String toString() {
		return "TailorOrders [id=" + id + ", customerorderid=" + customerorderid + ", tlrorderid=" + tlrorderid
				+ ", totalcost=" + totalcost + ", amountpaid=" + amountpaid + ", rstatus=" + rstatus
				+ ", currappstatus=" + currappstatus + ", madeat=" + madeat + ", updatedat=" + updatedat + ", ext1="
				+ ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + ", ext4=" + ext4 + ", ext5=" + ext5 + ", ext6=" + ext6
				+ ", ext7=" + ext7 + "]";
	}

}
