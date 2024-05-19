package com.techies.integration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techies.dtlr.entity.CustomerOrders;
import com.techies.dtlr.entity.TailorCustomer;
import com.techies.dtlr.repository.CustomerOrdersRepository;
import com.techies.dtlr.repository.TailorCustomerRepository;
import com.techies.utils.StringUtils;

@Component
public class AdminCustomerTailorOrders {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminCustomerTailorOrders.class);
    
    @Autowired
    private CustomerOrdersRepository customerOrdersRepository;
    
    @Autowired
    private TailorCustomerRepository tailorCustomerRepository;
    
    @Autowired
	private SMSGateWay sendGateWay;
    
    public Map<String, Object> adminCustTailOrders() {
        Map<String, Object> outputMap = new HashMap<>();
        try {
            logger.info("Fetching all customer tailor orders.");
            
            List<CustomerOrders> allOrders = customerOrdersRepository.findAll();
            
            outputMap.put("ResultMessage", allOrders);
            outputMap.put("Code", "100");
            logger.info("Orders fetched successfully: {}", outputMap);
        } catch (Exception e) {
            logger.error("Error fetching customer tailor orders.", e);
            outputMap.put("ErrorMessage", "Failed to fetch orders. Please try again later.");
            outputMap.put("Code", "105");
        }
        return outputMap;
    }

	public Map<String, Object> adminAllTailors() {
		Map<String, Object> outputMap = new HashMap<>();
        try {
            logger.info("Fetching all Tailors tailor.");
            
            List<TailorCustomer> allOrders = tailorCustomerRepository.findByCustCat("tlr");
            
            outputMap.put("ResultMessage", allOrders);
            outputMap.put("Code", "100");
            logger.info("Orders fetched successfully: {}", outputMap);
        } catch (Exception e) {
            logger.error("Error fetching customer tailor orders.", e);
            outputMap.put("ErrorMessage", "Failed to fetch orders. Please try again later.");
            outputMap.put("Code", "105");
        }
        return outputMap;
	}

	public Map<String, Object> tailorAssign(Map<String, Object> userRequest) {
		Map<String, Object> outputMap = new HashMap<>();
        try {
            logger.info("Fetching all Tailors tailor.");
            
            String orderId = StringUtils.getMapValue(userRequest, "orderid");
            String tailorId = StringUtils.getMapValue(userRequest, "tailorid");
            String tailorMobile = StringUtils.getMapValue(userRequest, "tailormobile");
            String tailorname = StringUtils.getMapValue(userRequest, "tailorname");
            
            
           CustomerOrders customerOrders = customerOrdersRepository.findByOrderid(orderId);
           if(customerOrders == null)
           {
        	   outputMap.put("ErrorMessage", "No records Found OrderId");
               outputMap.put("Code", "100"); 
               return outputMap;
           }
           customerOrders.setTailorid(tailorId);
          // customerOrders.setMobile(tailorMobile);
           customerOrders.setTailorname(tailorname);
           customerOrders.setTlrssigstatus("Assigned");
           String verficode = StringUtils.generateRandomPin();
           customerOrders.setVerficationcode(verficode);
           customerOrders.setExt1(tailorMobile);//tailor Mobile
           customerOrdersRepository.updateCustomerOrdersByOrderId(orderId, customerOrders);
          // sendGateWay.sendOTP(customerOrders.getMobile(),verficode);
            //List<TailorCustomer> allOrders = tailorCustomerRepository.findByCustCat("tlr");
            
            outputMap.put("ResultMessage", "Assigned Sucessfull.OTP sent to customer");
            outputMap.put("Code", "100");
            logger.info("Orders fetched successfully: {}", outputMap);
        } catch (Exception e) {
            logger.error("Error fetching customer tailor orders.", e);
            outputMap.put("ErrorMessage", "Failed to Assign tailor. Please try again later.");
            outputMap.put("Code", "105");
        }
        return outputMap;
	}
}


/*package com.techies.integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techies.dtlr.entity.CustomerOrders;
import com.techies.dtlr.entity.TailorCustomer;
import com.techies.dtlr.repository.CustomerOrdersRepository;
import com.techies.utils.StringUtils;

@Component
public class AdminCustomerTailorOrders {
private static final Logger logger = LoggerFactory.getLogger(AdminCustomerTailorOrders.class);
	
	@Autowired
	private CustomerOrdersRepository customerOrdersRespository;
	
	
	
	public HashMap<String, Object> adminCustTailOrders(){
		HashMap outputMap = new HashMap<>();
		try
		{
			logger.info("adminCustTailOrders:");
			//HashMap resultMap = validationOrderRequest(request);
			//if(resultMap.containsKey("ErrorMessage"))
			//	return resultMap;
			
			//TailorCustomer customerObject = (TailorCustomer) request.get("CustomerObject");
			
			
			List<CustomerOrders> allorders = customerOrdersRespository.findAll();
			
			outputMap.put("ResultMessage", allorders);
			outputMap.put("Code", "100");
			logger.info("customerOrderinsert:"+outputMap.toString());
			return outputMap;
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			outputMap.put("ErrorMessage", "Failed to Order.try again later");
			outputMap.put("Code", "105");
		}
		return outputMap;
		
	}

}*/
