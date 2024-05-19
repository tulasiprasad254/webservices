package com.techies.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.techies.dtlr.controller.TailorCustomerController;
import com.techies.dtlr.entity.Customer;
import com.techies.dtlr.entity.TailorCustomer;
import com.techies.dtlr.repository.CustomerRepository;
import com.techies.dtlr.repository.TailorCustomerRepository;
import com.techies.integration.AdminCustomerTailorOrders;
import com.techies.integration.ChangePassword;
import com.techies.integration.CustomerOrdersPlacement;
import com.techies.requests.LoginRequest;
import com.techies.requests.TailorRequestModelAttribute;
import com.techies.requests.UserRequest;
import com.techies.responses.SucessResponse;

@Component
public class MobileProcessRequest {

	private static final Logger logger = LoggerFactory.getLogger(MobileProcessRequest.class);
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TailorCustomerRepository tailorCustomerRespository;
	
	@Value("${upload.dir}") // This is the directory where uploaded files will be saved, configured in
	private String uploadDir;
	
	@Value("${login.otp}") 
	private String loginOTP;
	
	 @Autowired
	 private ChangePassword changePassword;
	 
	 @Autowired
	 private CustomerOrdersPlacement customerOrdersPlacement;
	 
	 @Autowired
	 private AdminCustomerTailorOrders adminCustomerTailorOrders;
	
	@Autowired
	private CustomerRepository customer;

	public HashMap requestRegisterMap(UserRequest request) {
		HashMap inputMap = new HashMap();
		HashMap outputMap = new HashMap();
		try {
			//inserting user request to hashmap
			inputMap.put("Name", request.getName());
			inputMap.put("Email", request.getEmail());
			inputMap.put("Mobile", request.getPhone());
			inputMap.put("Password", passwordEncoder.encode(request.getPassword()));
			//Getting the multi part objects
			MultipartFile adharImage = request.getAdharImage();
			MultipartFile addressProofImage = request.getAddressProofImage();
			//Validation on Input Parameters
			HashMap validationMap = validationRegister(inputMap);
			if (!validationMap.containsKey("ResultMessage")) {
				return validationMap;
			}
			String adharImagePath = saveImage(adharImage);
			String addressProofImagePath = saveImage(addressProofImage);

			inputMap.put("AdharImage", adharImagePath);
			inputMap.put("AddressProofImage", addressProofImagePath);

			processInsertionRegister(inputMap);
			outputMap.put("ResultMessage", "Sucessfully Registered");
			outputMap.put("Code", "100");
			

		} catch (Exception e) {
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		return outputMap;
	}

	private void processInsertionRegister(HashMap inputMap) throws Exception {
		
		Customer customer = new Customer();
		customer.setCname(getMapValue(inputMap,"Name"));
		customer.setEmail(getMapValue(inputMap,"Email"));
		customer.setMobile(getMapValue(inputMap,"Mobile"));
		customer.setPinNo(getMapValue(inputMap,"Password"));
		customer.setRstatus("1");
		customer.setCustExtn1(getMapValue(inputMap,"AdharImage"));
		customer.setCustproof(getMapValue(inputMap,"AddressProofImage"));
		customer.setCustCat("TL");
		customerRepository.save(customer);

	}

	
	private HashMap validationRegister(HashMap inputMap) {
		HashMap outputMap = new HashMap();
		Iterator itertor =inputMap.entrySet().iterator();
		while(itertor.hasNext())
		{
			Map.Entry map =  (Entry) itertor.next();
			String key = (String) map.getKey();
			String value = (String) map.getValue();
			if(value == null || value.length()<1)
			{
				outputMap.put("ErrorMessage", ""+key+" is NULL.Try with value.");
				outputMap.put("Code", "105");
				return outputMap;
			}
			
		}
		
		outputMap.put("ResultMessage", "Sucessfully Registered");
		return outputMap;
	}

	private String saveImage(MultipartFile image) throws IOException {
		// Generate a unique file name using UUID
		String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();

		// Resolve the directory path where the image will be saved
		Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
		if (!Files.exists(directory)) {
			Files.createDirectories(directory);
		}

		// Resolve the file path
		Path filePath = directory.resolve(fileName).normalize();

		// Save the file to the specified path
		Files.copy(image.getInputStream(), filePath);

		// Return the relative path to the saved file
		return image.getOriginalFilename().toString();
	}
	
	
	
	
	public HashMap requestRegisterCustomerMap(UserRequest request) {
			
			HashMap inputMap = new HashMap();
			HashMap outputMap = new HashMap();
			try {
				//inserting user request to hashmap
				inputMap.put("Name", request.getName());
				inputMap.put("Email", request.getEmail());
				inputMap.put("Mobile", request.getPhone());
				inputMap.put("Password", passwordEncoder.encode(request.getPassword()));
				//Getting the multi part objects
				MultipartFile adharImage = request.getAdharImage();
				MultipartFile addressProofImage = request.getAddressProofImage();
				//Validation on Input Parameters
				HashMap validationMap = validationRegister(inputMap);
				if (!validationMap.containsKey("ResultMessage")) {
					return validationMap;
				}
				

				processInsertionCustomerRegister(inputMap);
				outputMap.put("ResultMessage", "Sucessfully Registered");
				outputMap.put("Code", "100");
				

			} catch (Exception e) {
				outputMap.put("ErrorMessage", "Failed to Register");
				outputMap.put("Code", "101");
				
			}
			return outputMap;	
		
	}
	
	private void processInsertionCustomerRegister(HashMap inputMap) throws Exception {
		
		Customer customer = new Customer();
		customer.setCname(getMapValue(inputMap,"Name"));
		customer.setEmail(getMapValue(inputMap,"Email"));
		customer.setMobile(getMapValue(inputMap,"Mobile"));
		customer.setPinNo(getMapValue(inputMap,"Password"));
		customer.setRstatus("1");
		customer.setCustCat("CL");
		customerRepository.save(customer);

	}

	public HashMap requestLoginMap(LoginRequest request) {
		
		HashMap inputMap = new HashMap();
		HashMap outputMap = new HashMap();
		try {
			//inserting user request to hashmap
			inputMap.put("Mobile", request.getUser());
			inputMap.put("passcode", request.getPassword());
			HashMap validationMap = validationRegister(inputMap);
			System.out.println("inputMap:"+inputMap);
			if (!validationMap.containsKey("ResultMessage")) {
				return validationMap;
			}
			
			Customer customer = new Customer();
			//customer.setMobile(getMapValue(inputMap, "Mobile"));
			customer = customerRepository.findByMobile(getMapValue(inputMap, "Mobile"));
			if(customer == null)
			{
				outputMap.put("ErrorMessage", "Failed to Login.Customer Not Found");
				outputMap.put("Code", "105");
				return outputMap;
			}
			String passCode = customer.getPinNo();
			
			if(!passwordEncoder.matches(request.getPassword(), passCode))
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
	
	}
	
	public HashMap requestRegisterTailorCustomerMap(Map<String,Object> request) {
		
		HashMap inputMap = new HashMap();
		HashMap outputMap = new HashMap();
		try {
			
			return processInsertionTailorCustomerRegister(request,outputMap);
			/*
			 * //inserting user request to hashmap inputMap.put("Name", request.getName());
			 * inputMap.put("Email", request.getEmail()); inputMap.put("Mobile",
			 * request.getPhone()); inputMap.put("Password",
			 * passwordEncoder.encode(request.getPassword())); //Getting the multi part
			 * objects MultipartFile adharImage = request.getAdharImage(); MultipartFile
			 * addressProofImage = request.getAddressProofImage(); //Validation on Input
			 * Parameters HashMap validationMap = validationRegister(inputMap); if
			 * (!validationMap.containsKey("ResultMessage")) { return validationMap; }
			 * 
			 * 
			 * processInsertionCustomerRegister(inputMap); outputMap.put("ResultMessage",
			 * "Sucessfully Registered"); outputMap.put("Code", "100");
			 */
			

		} catch (Exception e) {
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		return outputMap;	
	
}

	private HashMap<String,Object> processInsertionTailorCustomerRegister(Map<String, Object> request, HashMap outputMap) {
		try
		{
			TailorCustomer tailorCustomer = new TailorCustomer();
			tailorCustomer.setName(getMapValue(request,"Name"));
			tailorCustomer.setBlockedFlag(false);
			tailorCustomer.setPassword(passwordEncoder.encode(getMapValue(request,"Password")));
			tailorCustomer.setEmail(getMapValue(request,"Email"));
			//tailorCustomer.setUsername(getMapValue(request,"Password"));
			tailorCustomer.setRstatus("1");
			tailorCustomer.setMadeAt(LocalDateTime.now());
			tailorCustomer.setCustCat("cust");
			tailorCustomer.setMobile(getMapValue(request,"Mobile"));
			tailorCustomer.setAddress(getMapValue(request,"Address"));			
			tailorCustomerRespository.save(tailorCustomer);
			outputMap.put("ResultMessage", "Regitered Sucessfull");
			
		}catch (Exception e) {
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		return outputMap;
		
	}
	
	
	public HashMap requestLoginMap(Map<String,Object> request) {
		
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
			
			if(!loginOTP.equalsIgnoreCase("Y") && !passwordEncoder.matches(getMapValue(request, "Password"), passCode))
			{
				outputMap.put("ErrorMessage", "Authentication Failure");
				outputMap.put("Code", "99");
				return outputMap;
			}
			
			if(loginOTP.equalsIgnoreCase("Y"))
			{
				String OTP = customer.getCity();
				String enteredOTP = getMapValue(request, "OTP");
				if(!OTP.equalsIgnoreCase(enteredOTP))
				{
					outputMap.put("ErrorMessage", "Authentication Failure.OTP validation failed.");
					outputMap.put("Code", "99");
					return outputMap;
				}
			}
			
			//processInsertionCustomerRegister(inputMap);
			outputMap.put("ResultMessage", "Authenticated Sucessfull");
			outputMap.put("cat", ""+customer.getCustCat());
			outputMap.put("Code", "100");
			

		} catch (Exception e) {
			e.printStackTrace();
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		return outputMap;	
	
	}
	
	private String getMapValue(Map<String, Object> request,String key) throws Exception
	{
			if(request.containsKey(key))
			{
				return (String)request.get(key);
			}
			return "";
		
	}

	public HashMap<String,Object> requestRegisterTailorMap(TailorRequestModelAttribute tailorRequestModelAttribute) {
		
		HashMap inputMap = new HashMap();
		HashMap outputMap = new HashMap();
		try {
			logger.info("requestRegisterTailorMap:");
			inputMap.put("Name", tailorRequestModelAttribute.getName());
			inputMap.put("Email", tailorRequestModelAttribute.getEmail());
			inputMap.put("Mobile", tailorRequestModelAttribute.getMobile());
			inputMap.put("Password", passwordEncoder.encode(tailorRequestModelAttribute.getPassword()));
			inputMap.put("Address", tailorRequestModelAttribute.getAddress());	
			//Getting the multi part objects
			MultipartFile adharImage = tailorRequestModelAttribute.getAdharimage();
			MultipartFile selfeeImage = tailorRequestModelAttribute.getSelfiimage();
			//Validation on Input Parameters
			HashMap validationMap = validationRegister(inputMap);
			if (!validationMap.containsKey("ResultMessage")) {
				return validationMap;
			}
			String adharImagePath = saveImage(adharImage);
			String addressProofImagePath = saveImage(selfeeImage);

			inputMap.put("AdharImage", adharImagePath);
			inputMap.put("AddressProofImage", addressProofImagePath);
			logger.info("requestRegisterTailorMap:"+inputMap);
			
			
			return processInsertionTailorRegister(inputMap,outputMap);
			

		} catch (Exception e) {
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		return outputMap;	
	}
	
	private HashMap<String,Object> processInsertionTailorRegister(HashMap inputMap, HashMap outputMap) {
		try
		{
			TailorCustomer tailorCustomer = new TailorCustomer();
			tailorCustomer.setName(getMapValue(inputMap,"Name"));
			tailorCustomer.setBlockedFlag(false);
			tailorCustomer.setPassword(passwordEncoder.encode(getMapValue(inputMap,"Password")));
			tailorCustomer.setEmail(getMapValue(inputMap,"Email"));
			tailorCustomer.setAddress(getMapValue(inputMap,"Address"));	
			tailorCustomer.setExt1(getMapValue(inputMap,"AdharImage"));
			tailorCustomer.setExt2(getMapValue(inputMap,"AddressProofImage"));//tailorCustomer.setUsername(getMapValue(request,"Password"));
			tailorCustomer.setRstatus("1");
			tailorCustomer.setMadeAt(LocalDateTime.now());
			tailorCustomer.setCustCat("tlr");
			tailorCustomer.setMobile(getMapValue(inputMap,"Mobile"));
			logger.info("Inserted Records:"+tailorCustomer.toString());
			tailorCustomerRespository.save(tailorCustomer);
			outputMap.put("ResultMessage", "Regitered Sucessfull");
			
		}catch (Exception e) {
			outputMap.put("ErrorMessage", "Failed to Register");
			outputMap.put("Code", "101");
			
		}
		logger.info("requestRegisterTailorMap:"+outputMap);
		
		return outputMap;
		
	}
	
	public HashMap changePasswordMap(Map<String,Object> request) {
		HashMap outputMap = new HashMap<>();
		try
		{
			return changePassword.changePassword(request);
		}catch (Exception e) {
			outputMap.put("ErrorMessage", "Unable to process Request.Some technical issue.Try again");
			e.printStackTrace();
		}
		return outputMap;
	}
	
	public HashMap sendOTPMap(Map<String,Object> request) {
		HashMap outputMap = new HashMap<>();
		try
		{
			return changePassword.sendOTP(request);
		}catch (Exception e) {
			outputMap.put("ErrorMessage", "Unable to process Request.Some technical issue.Try again");
			e.printStackTrace();
		}
		return outputMap;
	}
	
	public HashMap customerOrder(Map<String,Object> request)
	{
		logger.info("customerOrder:"+request.toString());
		HashMap outputMap = new HashMap<>();
		try
		{
			TailorCustomer customer = new TailorCustomer();
			customer = tailorCustomerRespository.findByMobile(getMapValue(request, "Mobile"));//UserName(getMapValue(inputMap, "username"));
			if(customer == null)
			{
				outputMap.put("ErrorMessage", "Failed to place order.Customer Not Found");
				outputMap.put("Code", "105");
				return outputMap;
			}
			logger.info("customer:"+customer.toString());
			request.put("CustomerObject", customer);
			return customerOrdersPlacement.customerOrderinsert(request);
		}catch (Exception e) {
			outputMap.put("ErrorMessage", "Unable to process Request.Some technical issue.Try again");
			e.printStackTrace();
		}
		return outputMap;
	}
	
	/*public HashMap adminCusttailOrders()
	{
		logger.info("adminCusttailOrders:");
		HashMap outputMap = new HashMap<>();
		try
		{
			
			logger.info("customer:"+customer.toString());
			return adminCustomerTailorOrders.adminCustTailOrders();
		}catch (Exception e) {
			outputMap.put("ErrorMessage", "Unable to process Request.Some technical issue.Try again");
			e.printStackTrace();
		}
		return outputMap;
	}*/
	
	
	
    public Map<String, Object> adminCustTailOrders() { 
		logger.info("adminCustTailOrders called");
    	Map<String, Object> outputMap = new HashMap<>();
    	try {
    		outputMap = adminCustomerTailorOrders.adminCustTailOrders();
        } catch (Exception e) {
        	logger.error("Error in adminCustTailOrders:", e);
        	outputMap.put("ErrorMessage", "Unable to process request. Some technical issue. Try again.");
        }
    	return outputMap;
	}

	/*
	 * public Map<String, Object> adminAllTailors() {
	 * logger.info("adminAllTailors called"); Map<String, Object> outputMap = new
	 * HashMap<>(); try { outputMap = adminCustomerTailorOrders.adminAllTailors(); }
	 * catch (Exception e) { logger.error("Error in adminAllTailors:", e);
	 * outputMap.put("ErrorMessage",
	 * "Unable to process request. Some technical issue. Try again."); } return
	 * outputMap; }
	 */

	public Map<String, Object> adminAllTailors() {
		logger.info("adminAllTailors called");
    	Map<String, Object> outputMap = new HashMap<>();
    	try {
    		outputMap = adminCustomerTailorOrders.adminAllTailors();
        } catch (Exception e) {
        	logger.error("Error in adminAllTailors:", e);
        	outputMap.put("ErrorMessage", "Unable to process request. Some technical issue. Try again.");
        }
    	return outputMap;
	}

	public Map<String, Object> tailorAssign(Map<String, Object> userRequest) {
		logger.info("tailorAssign called");
    	Map<String, Object> outputMap = new HashMap<>();
    	try {
    		outputMap = adminCustomerTailorOrders.tailorAssign(userRequest);
        } catch (Exception e) {
        	logger.error("Error in tailorAssign:", e);
        	outputMap.put("ErrorMessage", "Unable to process request. Some technical issue. Try again.");
        }
		return outputMap;
    	
	}
	
		 
}
