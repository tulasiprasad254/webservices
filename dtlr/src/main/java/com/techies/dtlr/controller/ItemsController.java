package com.techies.dtlr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techies.requests.UserRequest;

@RestController
@RequestMapping("/getproducts")
public class ItemsController {
	 	@GetMapping("/list")
	    public ResponseEntity<Map<String, List<Map<String, String>>>> getProductList() {
	        Map<String, List<Map<String, String>>> response = new HashMap<>();
	        List<Map<String, String>> productList = new ArrayList<>();

	        // First product
	        Map<String, String> product1 = new HashMap<>();
	        product1.put("name", "pspps");
	        product1.put("ProductDescription", "hfkafhkhkksad");
	        product1.put("image", "https://url1");
	        product1.put("amount", "$20");
	        productList.add(product1);

	        // Second product
	        Map<String, String> product2 = new HashMap<>();
	        product2.put("name", "ssddff");
	        product2.put("ProductDescription", "ssssss");
	        product2.put("image", "https://url2");
	        product2.put("amount", "$30");
	        productList.add(product2);

	        response.put("ProductList", productList);
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
}
