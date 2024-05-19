package com.techies.integration;

import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SMSGateWay {
	
	// Find your Account Sid and Token at console.twilio.com
	  public static final String ACCOUNT_SID = "ACa493dca42873accfac13506000c25b4b";
	  public static final String AUTH_TOKEN = "938632d011860af3cf6840ad8cf7d833";
	  
	  public void sendOTP(String toMobileNumber,String OTP)
	  {
		  try
		  {
		  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		    Message message = Message
		      .creator(        
		    		  new com.twilio.type.PhoneNumber("+91"+toMobileNumber),
		    	      new com.twilio.type.PhoneNumber("+15109240776"),
		    	      OTP+" This is your one-time passcode for Dekho Tailor.The OTP will expires in 5 min"
		      )
		      .create();

		    System.out.println(message.getSid());
		  }catch (Exception e) {
			e.printStackTrace();
		}
	  }

	  /*public static void main(String[] args) {
	    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	    Message message = Message
	      .creator(        
	    		  new com.twilio.type.PhoneNumber("+919491353157"),
	    	      new com.twilio.type.PhoneNumber("+15109240776"),
	        "This is the ship that made the Kessel Run in fourteen parsecs?"
	      )
	      .create();

	    System.out.println(message.getSid());
	  }*/

}
