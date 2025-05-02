package com.starLife.in.services_implementation;

import org.springframework.stereotype.Service;

import com.starLife.in.Entity.Customer;
import com.starLife.in.service.AccountVerified;


@Service
public class AccountVerifiedImple   implements AccountVerified{

  @Override
  public Boolean isVerified(Customer customer) {
    
     if( customer.getIsVerified() == true){
      return true;
     }
    
    return false;
  }
  
}
