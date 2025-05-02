package com.starLife.in.services_implementation;

import org.springframework.stereotype.Service;

import com.starLife.in.Entity.BankMitra;
import com.starLife.in.service.BankMitraService;
@Service
public class BankMitraServiceImpl  implements BankMitraService {

  @Override
  public BankMitra findBankMitraById(int id) {
    
    throw new UnsupportedOperationException("Unimplemented method 'findBankMitraById'");
  }

  @Override
  public BankMitra findBankMitraByJwt(String jwt) {
   
    throw new UnsupportedOperationException("Unimplemented method 'findBankMitraByJwt'");
  }

  @Override
  public BankMitra findBankMitraByEmail(String email) {
    
    throw new UnsupportedOperationException("Unimplemented method 'findBankMitraByEmail'");
  }
  
}
