package com.starLife.in.Controllers.AccountOpenController;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.starLife.in.Entity.Customer;
import com.starLife.in.helper.Message;
import com.starLife.in.service.AccountOpenService;
import com.starLife.in.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OpenAccountController {
  
  
	@Autowired
	private 	EmailService	emailService;

	@Autowired
	private AccountOpenService accountOpenService;
	
	@PostMapping("/createAccount/{type}")
	public String createAcc(@ModelAttribute Customer customer ,Model m, Principal p, HttpSession session, @RequestParam("filename") MultipartFile file ,  @PathVariable("type" ) String type ) throws IOException {
			
    System.out.println("customer  details  : "+customer);
		
			try {	

				if(customer.getCpassword().equals(customer.getConfiemPassword())){

        System.out.println("running   here   ");

				Customer savedCustomer = accountOpenService.openAccount(customer , file, m);

				System.out.println("saved user : "+savedCustomer);

				if(savedCustomer != null){
        	
      //  if account is open then send email 

      String message = 
			"<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>-:Your userid ! Don't disclose to others   :-</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+savedCustomer.getUserId()+"</h1><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>-:Your account no.  :-</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+savedCustomer.getAccountNo()+"</h1></div>";
			
			emailService.sendmaill(savedCustomer.getcEmail(), message , "Account Opening Alert");

			m.addAttribute("customer",savedCustomer);

      System.out.println("saved customer  : "+savedCustomer);
		 	}
	   	}else{

				m.addAttribute("title","Create new account");

				// m.addAttribute("alert","Account craetes successfully");
		
				Message alert = new Message("alert-danger","Password and Confirm password did not match !!! ");
		
				session.setAttribute("alert", alert);
		
				return "redirect:/bank_mitra/accountOpen";

			}
		
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("error occured : "+e);
			}

			if( type.equals("self")){

				m.addAttribute("title","Create new account");
				m.addAttribute( "jwt"  , m.getAttribute("token"));

			
        Message alertt = new Message("alert-success","Account Has been created successfully");
          
        session.setAttribute("alertt", alertt);
       
				return "redirect:/home/Open_new_accountt";

			}
		
		m.addAttribute("title","Create new account");

		m.addAttribute("alert","Account created successfully");

    Message alert = new Message("alert-success","Account Has been created successfully");

	  session.setAttribute("alert", alert);

		return "redirect:/bank_mitra/accountOpen";
}


}
