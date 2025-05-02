package com.starLife.in.service;

import com.starLife.in.Entity.Customer;

public interface KycService {

   public Boolean checkKycStatus(Customer customer , String aadharno);
    public Boolean makeKyc(Customer customer , String adharno);

    
}
