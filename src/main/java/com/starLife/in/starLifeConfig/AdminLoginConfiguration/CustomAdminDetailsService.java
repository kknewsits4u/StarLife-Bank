package com.starLife.in.starLifeConfig.AdminLoginConfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.starLife.in.Entity.Admin;
import com.starLife.in.repository.AdminRepository;



@Component
public class CustomAdminDetailsService  implements UserDetailsService {


  @Autowired private AdminRepository adminRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   
    Admin admin = this.adminRepo.findByEmail(username);

     System.out.println("admin with login request  : "+ admin);

     if( admin == null){
      throw new UsernameNotFoundException("Admin Not Found !!!! ");
     }

     return new AdminInfo(admin);
  }
  
}