package com.techies.dtlr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.techies.dtlr.entity.CustomerOrders;
import com.techies.dtlr.entity.TailorCustomer;


public interface CustomerOrdersRepository extends JpaRepository<CustomerOrders, Long>{

	// Update whole record based on id
  /* @Modifying
    @Transactional
    @Query("UPDATE customer_orders c SET c = :updatecustomerorders WHERE c.orderid = :order_id")
    void updateCustomerOrdersByOrderId(@Param("order_id") String orderid, @Param("updatecustomerorders") CustomerOrders updatecustomerorders);
   */
	@Modifying
    @Transactional
    @Query("UPDATE CustomerOrders c SET c.name = :#{#updateCustomerOrders.name}, " +
            "c.mobile = :#{#updateCustomerOrders.mobile}, " +
            "c.address = :#{#updateCustomerOrders.address}, " +
            "c.amountpaid = :#{#updateCustomerOrders.amountpaid}, " +
            "c.amountpaidtlrloc = :#{#updateCustomerOrders.amountpaidtlrloc}, " +
            "c.tailorid = :#{#updateCustomerOrders.tailorid}, " +
            "c.tailorname = :#{#updateCustomerOrders.tailorname}, " +
            "c.verficationcode = :#{#updateCustomerOrders.verficationcode}, " +
            "c.tlrssigstatus = :#{#updateCustomerOrders.tlrssigstatus}, " +
            "c.ext1 = :#{#updateCustomerOrders.ext1}, " +
            "c.ext2 = :#{#updateCustomerOrders.ext2}, " +
            "c.ext3 = :#{#updateCustomerOrders.ext3}, " +
            "c.ext4 = :#{#updateCustomerOrders.ext4}, " +
            "c.ext5 = :#{#updateCustomerOrders.ext5}, " +
            "c.ext6 = :#{#updateCustomerOrders.ext6}, " +
            "c.ext7 = :#{#updateCustomerOrders.ext7}, " +
            "c.madeat = :#{#updateCustomerOrders.madeat}, " +
            "c.updatedat = :#{#updateCustomerOrders.updatedat} " +
            "WHERE c.orderid = :order_id")
    void updateCustomerOrdersByOrderId(@Param("order_id") String orderId,  @Param("updateCustomerOrders") CustomerOrders updateCustomerOrders);
     
    CustomerOrders findByOrderid(String orderid);
}
