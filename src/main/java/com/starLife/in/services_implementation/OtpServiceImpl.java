package com.starLife.in.services_implementation;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.starLife.in.ExceptionHandler.OtpException;
import com.starLife.in.service.OtpService;


@Service
public class OtpServiceImpl  implements OtpService {

  @Override
  public String generateOtp() throws OtpException {
  
    int max = 9999;
    int min = 1000;

    Random rd = new Random();
    int otp = rd.nextInt((max - min) + 1) + min; // Ensures a 6-digit number

    return String.valueOf(otp);
    
  }



  @Override
  public Boolean verifyOtp(String otp1, String otp2) throws OtpException {
  
      try {
        
        if(otp1.equals(otp2)){
          return true;
        }
      } catch (Exception e) {
          throw new OtpException(500, "OTP does not matched !!! ");
      }
    return false;
  }
  
}
