package com.techies.dtlr.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import com.techies.dtlr.entity.TailorCustomer;



public interface TailorCustomerRepository extends JpaRepository<TailorCustomer, Long>{
	
		/*	TailorCustomer save(TailorCustomer customer);
			// Update the whole record of a customer by ID
		    @Modifying
		    @Transactional
		    @Query("UPDATE tailorcustomer c SET c = :customer WHERE c.id = :id")
		    void updateTailorCustomerById(@Param("customer") TailorCustomer customer, @Param("id") Long id);
		    // Delete a customer record by email and return the ID
		    void deleteById(Long id);
		    List<TailorCustomer> findAll();	    
		    @Query("SELECT c FROM tailorcustomer c WHERE c.email = :email")
		    List<TailorCustomer> findByEmail(@Param("email") String email);
		    List<TailorCustomer> findAll(Specification<TailorCustomer> spec);
		    @Query("SELECT c FROM tailorcustomer c WHERE c.mobile = :mobile")
		    TailorCustomer findByMobile(@Param("mobile") String mobile);
		    @Query("SELECT c FROM tailorcustomer c WHERE c.username = :username")
		    TailorCustomer findByUserName(@Param("username") String username);*/

	// Update whole record based on id
    @Modifying
    @Transactional
    @Query("UPDATE TailorCustomer c SET c = :updatedCustomer WHERE c.id = :id")
    void updateTailorCustomerById(@Param("id") Long id, @Param("updatedCustomer") TailorCustomer updatedCustomer);
    
    
    // Find the record based on email
    TailorCustomer findByEmail(String email);
    
    // Find the record based on mobile
    TailorCustomer findByMobile(String mobile);
    
    //Fid the records based on custcat
    List<TailorCustomer> findByCustCat(String custCat);


	
    
    
}
