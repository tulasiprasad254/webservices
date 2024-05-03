package com.techies.dtlr.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.techies.dtlr.entity.Customer;



public interface CustomerRepository extends JpaRepository<Customer, Long>{
	 Customer save(Customer customer);
	// Update the whole record of a customer by ID
	    @Modifying
	    @Transactional
	    @Query("UPDATE Customer c SET c = :customer WHERE c.id = :customerId")
	    void updateCustomerById(@Param("customer") Customer customer, @Param("customerId") Long customerId);
	    // Delete a customer record by email and return the ID
	    void deleteById(Long id);
	    List<Customer> findAll();	    
	    @Query("SELECT c FROM Customer c WHERE c.email = :email")
	    List<Customer> findByEmail(@Param("email") String email);
	    List<Customer> findAll(Specification<Customer> spec);
	    @Query("SELECT c FROM Customer c WHERE c.mobile = :mobile")
	    Customer findByMobile(@Param("mobile") String mobile);
}
