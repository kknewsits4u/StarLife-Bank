package com.starLife.in.starLifeConfig.BankMitraConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.starLife.in.Entity.BankMitra;
import com.starLife.in.repository.BankMitraRepository;


@Component
public class CustomBankMitraDetailsService  implements UserDetailsService {


  @Autowired private BankMitraRepository bankMitraRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   
     BankMitra bankmitra = this.bankMitraRepo.findByEmail(username);

     System.out.println("bank mitra with login request  : "+ bankmitra);

     if( bankmitra == null){
      throw new UsernameNotFoundException("Bank Mitra Not Found !!!! ");
     }


     return new BankMitraInfo(bankmitra);
  }
  
}
