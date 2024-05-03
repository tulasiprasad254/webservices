package com.techies.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.techies.dtlr.entity.Customer;
import com.techies.dtlr.repository.CustomerRepository;
import com.techies.requests.LoginRequest;
import com.techies.requests.UserRequest;
import com.techies.responses.SucessResponse;

@Component
public class MobileProcessRequest {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Value("${upload.dir}") // This is the directory where uploaded files will be saved, configured in
	private String uploadDir;
	
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

	private String getMapValue(HashMap inputMap,String key) throws Exception
	{
			if(inputMap.containsKey(key))
			{
				return (String)inputMap.get(key);
			}
			return "";
		
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
}
