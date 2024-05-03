package com.techies.responses;

public class SucessResponse {
	
	private String message;
	
	private String responsecode;
	
	
	public SucessResponse(String message, String responsecode) {
		super();
		this.message = message;
		this.responsecode = responsecode;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the responsecode
	 */
	public String getResponsecode() {
		return responsecode;
	}
	/**
	 * @param responsecode the responsecode to set
	 */
	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}
	

}
