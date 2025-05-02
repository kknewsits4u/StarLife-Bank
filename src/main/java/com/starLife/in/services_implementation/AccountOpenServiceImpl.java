package com.starLife.in.services_implementation;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.CustomerException;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.AccountOpenService;
import com.starLife.in.starLifeConfig.CustomerConfiguration.JwtProvider;



@Service
public class AccountOpenServiceImpl implements AccountOpenService {

	@Autowired
	private BCryptPasswordEncoder bcptp;

	@Autowired private JwtProvider jwtProvider;

	@Autowired
	private CustomerRepository customerRepo;

  @Override
  public Customer openAccount(Customer customer, MultipartFile image, Model m) throws CustomerException {
    
    
    try{


      if(image.isEmpty()) {
				   customer.setcImage("profile.png");       
		   }
       else
       {
		    	
		    	String fileurr = image.getOriginalFilename();
		    	 		    	 
		    	customer.setcImage(fileurr);	
				
		    	
		    	File saveFile = new ClassPathResource("static/image").getFile();
		    	 
		    	Path path = (Path)Paths.get(saveFile.getAbsolutePath()+File.separator+image.getOriginalFilename());

					System.out.println("Your image path is : "+path);
		    	 
		    	long copy = Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

					System.out.println("IMAGE CONTENT : "+ copy);

				  
		      }
			 
			    String email = customer.getcEmail();
			    String password =customer.getCpassword();
			 
			    Customer cstmr = this.customerRepo.findByEmail(email);
			 
			    if(cstmr != null) {
			   	  throw new Exception("Email id already used in another account");
			    }
			 
			 int min1=10000;
			 int max1=99999;
			 int min=100000;
			 int max=999999;
			 
			 Random rd = new Random();
			 
			 int acco = rd.nextInt(max - min + 1)+ min;	
			 
			 int userI=rd.nextInt(max1 - min1 + 1)+ min1;	
			 
			 String AccountNo="1545451070"+Integer.toString(acco);
			 
			 String userIdd="SLB"+Integer.toString(userI);

    

			 String balance = "500";
			 
			 customer.setAccountNo(AccountNo);
			 customer.setDate(new Date());
			 customer.setUserId(userIdd);
			 customer.setRole("CUSTOMER");
			 customer.setBalance(balance);
			 customer.setCpassword(this.bcptp.encode(password));

			

			Authentication authentication = new UsernamePasswordAuthenticationToken(userIdd, password);
      SecurityContextHolder.getContext().setAuthentication(authentication);

			// request for generate jwt token 
      String jwt = jwtProvider.generateToken(authentication);

			m.addAttribute("token", jwt);
      
			// login otp attept

			customer.setOtpAttept(0);
      customer.setIsVerified(false);
      customer.setIsLoggedIn(false);
			// save details into database.......
			
			Customer ctmr = this.customerRepo.save(customer);

      return ctmr;

      }
      catch(Exception e){
        e.printStackTrace();
      }
			
			return null;
  }
  
}
