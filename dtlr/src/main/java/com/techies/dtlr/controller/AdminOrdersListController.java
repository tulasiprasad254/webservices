package com.techies.dtlr.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techies.utils.MobileProcessRequest;

@RestController
@RequestMapping("/admincusttailorders")
public class AdminOrdersListController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerOrdersController.class);

	private final  MobileProcessRequest mobileProcessRequest;
	
	public AdminOrdersListController(MobileProcessRequest mobileProcessRequest) {
        this.mobileProcessRequest = mobileProcessRequest;
    }
	
	/*@GetMapping("/orders")
    public Map<Object,Object> adminCustTailOrders(){ 
		logger.info("adminCustTailOrders:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		outputMap	= mobileProcessRequest.adminCustTailOrders();
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
	}*/
	 @GetMapping("/orders")
	    public Map<String, Object> adminCustTailOrders() {
	        logger.info("adminCustTailOrders called");
	        Map<String, Object> outputMap = new HashMap<>();
	        try {
	            outputMap = mobileProcessRequest.adminCustTailOrders();
	        } catch (Exception e) {
	            logger.error("Error in adminCustTailOrders:", e);
	            outputMap.put("ErrorMessage", "Unable to process request. Some technical issue. Try again.");
	        }
	        return outputMap;
	    }
	 
	 @GetMapping("/getAlltailors")
	 public Map<String, Object> adminAlltailors() {
	        logger.info("adminAlltailors called");
	        Map<String, Object> outputMap = new HashMap<>();
	        try {
	            outputMap = mobileProcessRequest.adminAllTailors();
	        } catch (Exception e) {
	            logger.error("Error in adminAlltailors:", e);
	            outputMap.put("ErrorMessage", "Unable to process request. Some technical issue. Try again.");
	        }
	        return outputMap;
	    }
	 
	 @PostMapping("/assignment")
	    public Map<String, Object> tailorAssign(@RequestBody Map<String,Object> userRequest){ 
			logger.info("tailorAssign:"+userRequest.toString());
			 Map<String, Object> outputMap = new HashMap();
	    	try
	    	{
	    		outputMap	= mobileProcessRequest.tailorAssign(userRequest);
	    		
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	outputMap.put("ErroMessage", "Exception");
	        }
	    	return outputMap;
		}
}
