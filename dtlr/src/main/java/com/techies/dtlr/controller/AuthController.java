package com.techies.dtlr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.techies.dtlr.entity.User;
import com.techies.dtlr.repository.UserRepository;
import com.techies.requests.UserRequest;
import com.techies.responses.SucessResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${upload.dir}") // This is the directory where uploaded files will be saved, configured in application.properties
    private String uploadDir;

    
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password) {
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warning message");
        logger.error("Error message");

        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid email or password!";
        }
    }
    
    
   // public ResponseEntity<SucessResponse> registerUser((@RequestParam("file") MultipartFile file,@RequestBody UserRequest userRequest) {
    @PostMapping("/tailorregister")
    public ResponseEntity<SucessResponse> registerUser(@ModelAttribute UserRequest userRequest){ 
    	//String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	logger.info("registerUser:");
    	String name = userRequest.getName();
        String email = userRequest.getEmail();
        String phone = userRequest.getPhone();
        String password = userRequest.getPassword();
        
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setPassword(passwordEncoder.encode(password));
        
        
        MultipartFile adharImage = userRequest.getAdharImage();
        MultipartFile addressProofImage = userRequest.getAddressProofImage();
       
        try {
        	// Save file to the server
			/*
			 * if (file.isEmpty()) { SucessResponse response = new
			 * SucessResponse("Registration successful","105"); return new
			 * ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); }
			 

            // Create directory if it doesn't exist
            Path directoryPath = Paths.get(uploadDir);
            Files.createDirectories(directoryPath);

            // Resolve file path
            Path filePath = directoryPath.resolve(fileName);

            // Copy file to the target location
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            */
            // Save adharImage and addressProofImage to file system
            String adharImagePath = saveImage(adharImage);
            String addressProofImagePath = saveImage(addressProofImage);

            // Set paths to the user object
            newUser.setAdharImage(adharImagePath);
            newUser.setAddressImage(addressProofImagePath);

            // Register user
            //User registeredUser =  
            		userRepository.save(newUser);
            SucessResponse response = new SucessResponse("Registration successful","200");
            return new ResponseEntity<>(response, HttpStatus.OK);
           // return "Registration Sucessfull";//new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IOException e) {
        	 SucessResponse response = new SucessResponse("Registration successful","101");
             return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            //return "";//new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
    // Other controller methods...

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
    
    
}
