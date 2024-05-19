package com.techies.dtlr.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tailorcustomer")
public class TailorCustomer {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	@Column(name = "NAME")
	 	private String name;
	 	@Column(name = "EMAIL",unique = true, nullable = false)
	 	private String email;
	 	@Column(name = "USER_NAME")
	 	private String username;
	 	@Column(name = "PASSWORD")
	 	private String password;
	 	@Column(name = "ADDRESS")
	 	private String address;
	 	@Column(name = "CITY")
	 	private String city;
	 	@Column(name = "ZIPCODE")
	 	private String zipcode;
	 	@Column(name = "MADE_AT")
	    private LocalDateTime madeAt;
	 	@Column(name = "BLOCKED_FLAG")
	 	private boolean blockedFlag;
	 	@Column(name = "CUST_CAT")
	 	private String custCat;
	 	@Column(name = "EXT1")
	 	private String ext1;
	 	@Column(name = "EXT2")
	 	private String ext2;
	 	@Column(name = "RSTATUS")
	 	private String rstatus;
	 	@Column(name = "MOBILE")
	 	private String mobile;
	 	
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
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}
		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
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
		 * @return the city
		 */
		public String getCity() {
			return city;
		}
		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}
		/**
		 * @return the zipcode
		 */
		public String getZipcode() {
			return zipcode;
		}
		/**
		 * @param zipcode the zipcode to set
		 */
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
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
	 	
}
