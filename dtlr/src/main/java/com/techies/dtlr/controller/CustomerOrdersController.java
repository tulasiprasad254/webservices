package com.techies.dtlr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techies.utils.MobileProcessRequest;

@RestController
@RequestMapping("/custorders")
public class CustomerOrdersController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerOrdersController.class);
	
	private final  MobileProcessRequest mobileProcessRequest;
	
	public CustomerOrdersController(MobileProcessRequest mobileProcessRequest) {
        this.mobileProcessRequest = mobileProcessRequest;
    }
	
	@PostMapping("/orderreq")
    public Map<Object,Object> customerorder(@RequestBody Map<String,Object> userRequest){ 
		logger.info("registerUser:"+userRequest.toString());
    	HashMap outputMap = new HashMap();
    	try
    	{
    		outputMap	= mobileProcessRequest.customerOrder(userRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
	}

}
