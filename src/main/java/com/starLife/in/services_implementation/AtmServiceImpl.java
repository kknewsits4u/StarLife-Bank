package com.starLife.in.services_implementation;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.OtpException;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.AtmService;
import com.starLife.in.service.OtpService;

@Service
public class AtmServiceImpl  implements AtmService {
  

   @Autowired
   private CustomerRepository  custRepo;


    @Autowired
    private OtpService otpservice;


   Random rd = new Random();


  @Override
  public Boolean generateAtm(Customer cust) {
    
    int max1 =9999;
    int min1 =1000;
    
    String f= Integer.toString(rd.nextInt(max1 - min1 + 1)+ min1);	
    String g= Integer.toString(rd.nextInt(max1 - min1 + 1)+ min1);
    String h= Integer.toString(rd.nextInt(max1 - min1 + 1)+ min1);	
    String i= Integer.toString(rd.nextInt(max1 - min1 + 1)+ min1);		

    String atmnum = f+g+h+i;

    System.out.println(atmnum);
  
    if( cust != null){
        
       String prevAtmNum = cust.getAtmNumber();

       if( prevAtmNum != atmnum ){
         cust.setAtmNumber(atmnum);
         this.custRepo.save(cust);
         return true;
       }
    }

    return false;
  }

  @Override
  public Boolean updateAtmPin(Customer cust, String newpin) {

    Customer custo = this.custRepo.getUserByUserName(cust.getUserId());

    if ( custo != null ){
       custo.setAtmPin(newpin);
       this.custRepo.save(custo);
       return true;
    }
    return false;
  }

  @Override
  public String generatePin() throws OtpException {

    String pin = this.otpservice.generateOtp();
     
    return pin;
  }
  
}
