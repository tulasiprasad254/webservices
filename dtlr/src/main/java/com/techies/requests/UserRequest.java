package com.techies.requests;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

public class UserRequest {
    private String name;
    private String email;
    private String phone;
    private MultipartFile adharimage;
    private MultipartFile addressproofimage;
    private String password;
	public UserRequest(String name, String email, String phone, MultipartFile adharimage,
			MultipartFile addressproofimage, String password) {
		
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.adharimage = adharimage;
		this.addressproofimage = addressproofimage;
		this.password = password;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the adharImage
	 */
	public MultipartFile getAdharImage() {
		return adharimage;
	}
	/**
	 * @param adharImage the adharImage to set
	 */
	public void setAdharImage(MultipartFile adharimage) {
		this.adharimage = adharimage;
	}
	/**
	 * @return the addressProofImage
	 */
	public MultipartFile getAddressProofImage() {
		return addressproofimage;
	}
	/**
	 * @param addressProofImage the addressProofImage to set
	 */
	public void setAddressProofImage(MultipartFile addressproofimage) {
		this.addressproofimage = addressproofimage;
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
	@Override
	public int hashCode() {
		return Objects.hash(addressproofimage, adharimage, email, name, password, phone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRequest other = (UserRequest) obj;
		return Objects.equals(addressproofimage, other.addressproofimage)
				&& Objects.equals(adharimage, other.adharimage) && Objects.equals(email, other.email)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone);
	}

    // Getters and Setters
}
