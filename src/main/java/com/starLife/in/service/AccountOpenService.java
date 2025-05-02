package com.starLife.in.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.CustomerException;

import io.jsonwebtoken.io.IOException;

public interface AccountOpenService {
   
  public Customer openAccount(Customer customer , MultipartFile image, Model m) throws CustomerException , IOException;

}
