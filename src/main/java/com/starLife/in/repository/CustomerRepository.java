package com.starLife.in.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.starLife.in.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
  	
	@Query("select u from Customer u where u.userId = :userId")
	public Customer getUserByUserName(@Param("userId") String userId);
    
	@Query("select u from Customer u where u.cEmail = :cEmail")
	public Customer findByEmail(@Param("cEmail") String email);



	@Query("select u from Customer u where u.accountNo = :accountNo")
  public Customer findByAccountNo( @Param("accountNo")    String accountNo);
  

}
