package com.techies.dtlr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techies.requests.LoginRequest;
import com.techies.requests.TailorRequestModelAttribute;
import com.techies.requests.UserRequest;
import com.techies.utils.MobileProcessRequest;

@RestController
@RequestMapping("/Auth")
public class TailorCustomerController {
	private static final Logger logger = LoggerFactory.getLogger(TailorCustomerController.class);
	
	
	private final  MobileProcessRequest mobileProcessRequest;
	
	public TailorCustomerController(MobileProcessRequest mobileProcessRequest) {
        this.mobileProcessRequest = mobileProcessRequest;
    }
	
	@PostMapping("/custregister")
    public Map<Object,Object> registerCustUser(@RequestBody Map<String,Object> userRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("registerUser:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		outputMap	= mobileProcessRequest.requestRegisterTailorCustomerMap(userRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
	
	@PostMapping("/tailorregister")
    public Map<Object,Object> registerTailorUser(@ModelAttribute TailorRequestModelAttribute userRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("registerUser:"+userRequest.toString());
    	HashMap outputMap = new HashMap();
    	try
    	{
    		outputMap	= mobileProcessRequest.requestRegisterTailorMap(userRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
	
	@PostMapping("/login")
    public Map<Object,Object> loginUser(@RequestBody Map<String,Object> loginRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("login:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		//System.out.println(loginRequest.getUser());
    		outputMap	= mobileProcessRequest.requestLoginMap(loginRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
	
	@Value("${upload.dir}") // This is the directory where uploaded files will be saved, configured in
	private String uploadDir;
	
	@GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            // Read the image file from the upload directory
            Path filePath = Paths.get(uploadDir, filename);
            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @Value("${upload.diritem}") // This is the directory where uploaded files will be saved, configured in
	private String uploadItemDir;
    
    @GetMapping("/itemimage/{filename}")
    public ResponseEntity<byte[]> getItemImage(@PathVariable String filename) {
        try {
            // Read the image file from the upload directory
            Path filePath = Paths.get(uploadItemDir, filename);
            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @PostMapping("/changepassword")
    public Map<Object,Object> changePasswordUser(@RequestBody Map<String,Object> changePassWord){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("changepassword:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		//System.out.println(loginRequest.getUser());
    		outputMap	= mobileProcessRequest.changePasswordMap(changePassWord);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
    
    @PostMapping("/sendotp")
    public Map<Object,Object> sendOTPUser(@RequestBody Map<String,Object> otpRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("sendotp:");
    	HashMap outputMap = new HashMap();
    	try
    	{
    		//System.out.println(loginRequest.getUser());
    		outputMap	= mobileProcessRequest.sendOTPMap(otpRequest);
    		
        } catch (Exception e) {
        	e.printStackTrace();
        	outputMap.put("ErroMessage", "Exception");
        }
    	return outputMap;
    }
	
}
