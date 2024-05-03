package com.techies.requests;

import java.util.Objects;

public class LoginRequest {
	 private String user;
	 private String password;
	public LoginRequest(String user, String password) {
		
		this.user = user;
		this.password = password;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
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
		return Objects.hash(password, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginRequest other = (LoginRequest) obj;
		return Objects.equals(password, other.password) && Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return "LoginRequest [user=" + user + ", password=" + password + ", getUser()=" + getUser() + ", getPassword()="
				+ getPassword() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

}
