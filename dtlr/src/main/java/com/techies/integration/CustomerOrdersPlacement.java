package com.techies.integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.techies.dtlr.entity.CustomerOrders;
import com.techies.dtlr.entity.TailorCustomer;
import com.techies.dtlr.repository.CustomerOrdersRepository;
import com.techies.utils.MobileProcessRequest;
import com.techies.utils.StringUtils;

@Component
public class CustomerOrdersPlacement {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerOrdersPlacement.class);
	
	@Autowired
	private CustomerOrdersRepository customerOrdersRespository;
	
	@Autowired
	private CustomerOrders customerOrders;
	
	public HashMap<String, Object> customerOrderinsert(Map<String,Object> request){
		HashMap outputMap = new HashMap<>();
		try
		{
			logger.info("customerOrderinsert:"+request.toString());
			HashMap resultMap = validationOrderRequest(request);
			if(resultMap.containsKey("ErrorMessage"))
				return resultMap;
			
			TailorCustomer customerObject = (TailorCustomer) request.get("CustomerObject");
			
			customerOrders.setMobile(StringUtils.getMapValue(request, "Mobile"));
			customerOrders.setAmountpaid(StringUtils.getMapValue(request, "cost"));
			customerOrders.setAddress(customerObject.getAddress());
			customerOrders.setAmountpaidtlrloc("0");
			customerOrders.setOrderid(StringUtils.generateOrderId());
			customerOrders.setTlrssigstatus("Pending");
			customerOrders.setMadeat(LocalDateTime.now());
			customerOrders.setUpdatedat(LocalDateTime.now());
			customerOrdersRespository.save(customerOrders);
			
			outputMap.put("ResultMessage", "Sucessfully Ordered");
			outputMap.put("Code", "100");
			logger.info("customerOrderinsert:"+outputMap.toString());
			return outputMap;
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			outputMap.put("ErrorMessage", "Failed to Order.try again later");
			outputMap.put("Code", "105");
		}
		return outputMap;
		
	}
	
	private HashMap<String, String> validationOrderRequest(Map<String, Object> inputMap) {
	    logger.info("validationOrderRequest: " + inputMap);
	    HashMap<String, String> outputMap = new HashMap<>();
	    boolean hasError = false;
	    StringBuilder errorMessage = new StringBuilder();
	    
	    for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
	        String key = entry.getKey();
	        logger.info("validationOrderRequest key: " + key);

	        switch (key) {
	            case "Mobile":
	            case "cost":
	                String value = (String) entry.getValue();
	                if (value == null || value.length() < 1) {
	                    hasError = true;
	                    errorMessage.append(key).append(" is NULL or empty. Try with a valid value. ");
	                }
	                break;
	            case "CustomerObject":
	            	break;
	            default:
	                hasError = true;
	                errorMessage.append(key).append(" is not a valid request parameter. ");
	                break;
	        }
	    }

	    if (hasError) {
	        outputMap.put("ErrorMessage", errorMessage.toString().trim());
	        outputMap.put("Code", "105");
	        return outputMap;
	    }

	    logger.info("validationOrderRequest output: " + outputMap);
	    outputMap.put("ResultMessage", "Successfully Registered");
	    return outputMap;
	}
	
	
	/*
	 * private HashMap validationOrderRequest(Map<String,Object> inputMap) {
	 * logger.info("validationOrderRequest:"+inputMap.toString()); HashMap outputMap
	 * = new HashMap(); Iterator itertor =inputMap.entrySet().iterator();
	 * while(itertor.hasNext()) { Map.Entry map = (Entry) itertor.next(); String key
	 * = (String) map.getKey(); logger.info("validationOrderRequest key:"+key);
	 * if(key.equalsIgnoreCase("CustomerObject")) continue; switch(key) { case
	 * "Mobile": case "cost": String value = (String) map.getValue(); if(value ==
	 * null || value.length()<1) { outputMap.put("ErrorMessage",
	 * ""+key+" is NULL.Try with value."); outputMap.put("Code", "105"); return
	 * outputMap; } default: { outputMap.put("ErrorMessage",
	 * ""+key+" is not proper request."); outputMap.put("Code", "105"); return
	 * outputMap; } }
	 * 
	 * } logger.info("validationOrderRequest key:"+outputMap);
	 * outputMap.put("ResultMessage", "Sucessfully Registered"); return outputMap; }
	 */

}
