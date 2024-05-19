package com.techies.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.bytebuddy.utility.RandomString;

public class StringUtils {
	
	public static String getMapValue(Map<String, Object> request,String key) throws Exception
	{
			if(request.containsKey(key))
			{
				return (String)request.get(key);
			}
			return "";
		
	}
	
	public static HashMap validationRegister(HashMap inputMap) {
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
	
	 public static String generateRandomPin()
	    {
		// Generate random 6-digit number
	        int min = 100000; // Minimum 6-digit number
	        int max = 999999; // Maximum 6-digit number
	        Random random = new Random();
	        int randomNumber = random.nextInt(max - min + 1) + min;
	        return ""+randomNumber;
	    }
	 
	 public static String generateOrderId()
	    {
		// Generate random 9-digit number
	        int min = 100000000; // Minimum 9-digit number
	        int max = 999999999; // Maximum 9-digit number
	        Random random = new Random();
	        int randomNumber = random.nextInt(max - min + 1) + min;
	        return ""+randomNumber;
	    }

}
