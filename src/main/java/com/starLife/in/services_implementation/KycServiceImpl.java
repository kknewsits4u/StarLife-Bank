package com.starLife.in.services_implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.starLife.in.Entity.Customer;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.KycService;

@Component
public class KycServiceImpl implements KycService {

  @Autowired private CustomerRepository customerRepo;

  @Override
  public Boolean checkKycStatus(Customer customer, String aadharno) {


    if( customer.getcAadhar() == aadharno){

      if(customer.getEkycstatus() == null){
        return false;
      }
      else{
        return true;
      }

    }
      
    return false;
  }




  @Override
  public Boolean makeKyc(Customer customer, String adharno) {

    if( customer != null){

      customer.setEkycstatus("done");

      this.customerRepo.save(customer);
      return true;
      
    }

     return false;
  }
  
}
