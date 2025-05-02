package com.starLife.in.starLifeConfig.CustomerConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.starLife.in.Entity.Customer;

import com.starLife.in.repository.CustomerRepository;

@Component
@Primary
public class CustomCustomerDetails implements UserDetailsService {
    
	@Autowired private CustomerRepository customerRepo;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
   
   Customer customer = customerRepo.getUserByUserName(username);

    if (customer == null) {
      throw new UsernameNotFoundException("User not found: " + username);
    }

   return new CustomerInfoDetails(customer);

}

}