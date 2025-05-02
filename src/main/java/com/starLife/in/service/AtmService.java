package com.starLife.in.service;

import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.OtpException;

public interface AtmService  {

  public Boolean generateAtm(Customer cust);
  public Boolean updateAtmPin(Customer cust , String newpin);
  public String generatePin() throws OtpException;
  
}
