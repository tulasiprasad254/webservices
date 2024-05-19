package com.techies.requests;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

public class TailorRequestModelAttribute {
	
	private String name;
    private String email;
    private String mobile;
    private String password;
    private String address;
    private String confirmpassword;
    private MultipartFile adharimage;
    private MultipartFile selfiimage;
    
	
	public TailorRequestModelAttribute(String name, String email, String mobile, String password, String address,
			String confirmpassword, MultipartFile adharimage, MultipartFile selfiimage) {
		super();
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.address = address;
		this.confirmpassword = confirmpassword;
		this.adharimage = adharimage;
		this.selfiimage = selfiimage;
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
	 * @return the adharimage
	 */
	public MultipartFile getAdharimage() {
		return adharimage;
	}
	/**
	 * @param adharimage the adharimage to set
	 */
	public void setAdharimage(MultipartFile adharimage) {
		this.adharimage = adharimage;
	}
	/**
	 * @return the selfiimage
	 */
	public MultipartFile getSelfiimage() {
		return selfiimage;
	}
	/**
	 * @param selfiimage the selfiimage to set
	 */
	public void setSelfiimage(MultipartFile selfiimage) {
		this.selfiimage = selfiimage;
	}
	/**
	 * @return the confirmpassword
	 */
	public String getConfirmpassword() {
		return confirmpassword;
	}
	/**
	 * @param confirmpassword the confirmpassword to set
	 */
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, adharimage, confirmpassword, email, mobile, name, password, selfiimage);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TailorRequestModelAttribute other = (TailorRequestModelAttribute) obj;
		return Objects.equals(address, other.address) && Objects.equals(adharimage, other.adharimage)
				&& Objects.equals(confirmpassword, other.confirmpassword) && Objects.equals(email, other.email)
				&& Objects.equals(mobile, other.mobile) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(selfiimage, other.selfiimage);
	}
	@Override
	public String toString() {
		return "TailorRequestModelAttribute [name=" + name + ", email=" + email + ", mobile=" + mobile + ", password="
				+ password + ", address=" + address + ", confirmpassword=" + confirmpassword + ", adharimage="
				+ adharimage + ", selfiimage=" + selfiimage + "]";
	}
	
    

}
