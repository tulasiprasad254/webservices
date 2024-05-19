package com.techies.integration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techies.dtlr.entity.TailorCustomer;
import com.techies.dtlr.repository.TailorCustomerRepository;
import com.techies.utils.StringUtils;

@Component
public class ChangePassword {
	
	@Autowired
	private TailorCustomerRepository tailorCustomerRespository;
	@Autowired
	private SMSGateWay sendGateWay;
	
	
	
	public HashMap<String, Object> changePassword(Map<String,Object> request)
	{
		HashMap outputMap = new HashMap<>();
		try
		{
			String oldPassword = StringUtils.getMapValue(request, "oldpassword");
			String newPassword = StringUtils.getMapValue(request, "newpassword");
			String confirmPassword = StringUtils.getMapValue(request, "confirmpassword");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return outputMap;
	}
	
	public HashMap<String, Object> sendOTP(Map<String,Object> request)
	{
		HashMap outputMap = new HashMap<>();
		try
		{
			String mobile = StringUtils.getMapValue(request, "Mobile");
			TailorCustomer customer = new TailorCustomer();
			customer = tailorCustomerRespository.findByMobile(StringUtils.getMapValue(request, "Mobile"));//UserName(getMapValue(inputMap, "username"));
			if(customer == null)
			{
				outputMap.put("ErrorMessage", "Failed to Send OTP.Customer Not Found");
				outputMap.put("Code", "105");
				return outputMap;
			}
			String otp = StringUtils.generateRandomPin();
			customer.setCity(otp);
			tailorCustomerRespository.updateTailorCustomerById(customer.getId(), customer);
			//sendGateWay.sendOTP(customer.getMobile(),otp);
			outputMap.put("ResultMessage", "OTP Sent");
			outputMap.put("Code", "100");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			outputMap.put("ErrorMessage", "Failed to sent OTP");
			outputMap.put("Code", "99");
		}
		return outputMap;
	}
	/*
	 
		
		HashMap inputMap = new HashMap();
		HashMap outputMap = new HashMap();
		try {
			
			
			
			TailorCustomer customer = new TailorCustomer();
			//customer.setMobile(getMapValue(inputMap, "Mobile"));
			customer = tailorCustomerRespository.findByMobile(getMapValue(request, "Mobile"));//UserName(getMapValue(inputMap, "username"));
					//
			if(customer == null)
			{
				outputMap.put("ErrorMessage", "Failed to Login.Customer Not Found");
				outputMap.put("Code", "105");
				return outputMap;
			}
			String passCode = customer.getPassword();
			
			if(!passwordEncoder.matches(getMapValue(request, "Password"), passCode))
			{
				outputMap.put("ErrorMessage", "Authentication Failure");
				outputMap.put("Code", "99");
				return outputMap;
			}

			//processInsertionCustomerRegister(inputMap);
			outputMap.put("ResultMessage", "Authenticated Sucessfull");
			outputMap.put("Code", "100");
			

		} catch (Exception e) {
			e.printStackTrace();
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		return outputMap;	
	
	
	 */

}
