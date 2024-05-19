package com.techies.dtlr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.techies.dtlr.entity.Items;
import com.techies.dtlr.repository.ItemRepository;
import com.techies.requests.UserRequest;
import com.techies.responses.ItemsResponse;

@RestController
@RequestMapping("/getproducts")
public class ItemsController {
	
	  @Autowired
	  private ItemRepository itemRepository;
	  
		@Value("${url.image}") // This is the directory where uploaded files will be saved, configured in
		private String urlImage;
	  
	 	@GetMapping("/list")
	    public ResponseEntity<Map<String, List<ItemsResponse>>> getProductList() {
	        Map<String, List<ItemsResponse>> response = new HashMap<>();
	      //  List<Map<String, String>> productList = new ArrayList<>();

	        // First product
	      /*  Map<String, String> product1 = new HashMap<>();
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
	        productList.add(product2);*/
	       List<Items> items = itemRepository.findAll();
			/*
			 * private String name; private String imageUrl; private String description;
			 * private String amount;
			 */
	      // String urlImageURL = urlImage+"/";
	       List<ItemsResponse> itemsResponse = new ArrayList<>();;
	       for (Items items2 : items) {
	    	   ItemsResponse object  = new ItemsResponse();
	    	   object.setName(items2.getName());
	    	   object.setDescription(items2.getDescription());
	    	   object.setImageUrl(urlImage+"/"+items2.getExt1());
	    	   object.setAmount(items2.getExt2());
	    	   itemsResponse.add(object);
	    	   
	       }
	       response.put("ProductList", itemsResponse);
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
}
