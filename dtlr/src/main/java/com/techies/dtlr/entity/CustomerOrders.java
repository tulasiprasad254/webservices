package com.techies.dtlr.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_orders")
public class CustomerOrders {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;	 	
	 	@Column(name = "order_id")
	 	private String orderid;
	 	@Column(name = "name")
	 	private String name;
	 	@Column(name = "mobile")
	 	private String mobile;
	 	@Column(name = "address")
	 	private String address;
	 	@Column(name = "amount_paid")
	 	private String amountpaid;
	 	@Column(name = "amount_paid_tlr_loc")
	 	private String amountpaidtlrloc;
	 	@Column(name = "tailorid")
	 	private String tailorid;
	 	@Column(name = "tailorname")
	 	private String tailorname;
	 	@Column(name = "verficationcode")
	 	private String verficationcode;
	 	@Column(name = "tlrssigstatus")
	 	private String tlrssigstatus;
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
	 	@Column(name = "made_at")
	    private LocalDateTime madeat;
	    @Column(name = "updated_at")
	    private LocalDateTime updatedat;
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
		 * @return the orderid
		 */
		public String getOrderid() {
			return orderid;
		}
		/**
		 * @param orderid the orderid to set
		 */
		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
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
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}
		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
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
		 * @return the amountpaidtlrloc
		 */
		public String getAmountpaidtlrloc() {
			return amountpaidtlrloc;
		}
		/**
		 * @param amountpaidtlrloc the amountpaidtlrloc to set
		 */
		public void setAmountpaidtlrloc(String amountpaidtlrloc) {
			this.amountpaidtlrloc = amountpaidtlrloc;
		}
		/**
		 * @return the tailorid
		 */
		public String getTailorid() {
			return tailorid;
		}
		/**
		 * @param tailorid the tailorid to set
		 */
		public void setTailorid(String tailorid) {
			this.tailorid = tailorid;
		}
		/**
		 * @return the tailorname
		 */
		public String getTailorname() {
			return tailorname;
		}
		/**
		 * @param tailorname the tailorname to set
		 */
		public void setTailorname(String tailorname) {
			this.tailorname = tailorname;
		}
		/**
		 * @return the verficationcode
		 */
		public String getVerficationcode() {
			return verficationcode;
		}
		/**
		 * @param verficationcode the verficationcode to set
		 */
		public void setVerficationcode(String verficationcode) {
			this.verficationcode = verficationcode;
		}
		/**
		 * @return the tlrssigstatus
		 */
		public String getTlrssigstatus() {
			return tlrssigstatus;
		}
		/**
		 * @param tlrssigstatus the tlrssigstatus to set
		 */
		public void setTlrssigstatus(String tlrssigstatus) {
			this.tlrssigstatus = tlrssigstatus;
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
		@Override
		public String toString() {
			return "CustomerOrders [id=" + id + ", orderid=" + orderid + ", name=" + name + ", mobile=" + mobile
					+ ", address=" + address + ", amountpaid=" + amountpaid + ", amountpaidtlrloc=" + amountpaidtlrloc
					+ ", tailorid=" + tailorid + ", tailorname=" + tailorname + ", verficationcode=" + verficationcode
					+ ", tlrssigstatus=" + tlrssigstatus + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3
					+ ", ext4=" + ext4 + ", ext5=" + ext5 + ", ext6=" + ext6 + ", ext7=" + ext7 + ", madeat=" + madeat
					+ ", updatedat=" + updatedat + "]";
		}
		

}
