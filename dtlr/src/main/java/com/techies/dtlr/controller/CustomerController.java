package com.techies.dtlr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.techies.dtlr.repository.CustomerRepository;
import com.techies.requests.LoginRequest;
import com.techies.requests.UserRequest;
import com.techies.responses.SucessResponse;
import com.techies.utils.MobileProcessRequest;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private final  MobileProcessRequest mobileProcessRequest;
	
	
    public CustomerController(MobileProcessRequest mobileProcessRequest) {
        this.mobileProcessRequest = mobileProcessRequest;
    }
	
   

    @Value("${upload.dir}") // This is the directory where uploaded files will be saved, configured in application.properties
    private String uploadDir;
    
    @PostMapping("/tailorregister")
    public Map<Object,Object> registerUser(@ModelAttribute UserRequest userRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("registerUser:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		outputMap	= mobileProcessRequest.requestRegisterMap(userRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
    
    @PostMapping("/custregister")
    public Map<Object,Object> registerCustUser(@ModelAttribute UserRequest userRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("registerUser:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		outputMap	= mobileProcessRequest.requestRegisterCustomerMap(userRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
    
    @PostMapping("/login")
    public Map<Object,Object> loginUser(@RequestBody LoginRequest loginRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("login:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		System.out.println(loginRequest.getUser());
    		outputMap	= mobileProcessRequest.requestLoginMap(loginRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
   
    
}
