package com.starLife.in.Controllers.PageOpenerController.CustomerPages;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.OtpException;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.EmailService;
import com.starLife.in.service.OtpService;

// import com.starLife.in.starLifeConfig.CustomCustomerDetails;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class MainController {


 @Autowired private CustomerRepository customerRepo;

  @Autowired private OtpService otpService;

  @Autowired private EmailService emailservice;

  // Home page handler ................................

	@GetMapping("")
	public String home(Model m) {
		m.addAttribute("title","Star Life : Home page");
		return "newHome";
	}
	

	@GetMapping("/otppage")
	public String newLoginForm(Model m, Principal p) throws OtpException {
		m.addAttribute("title","Star Life : Home page");

		Customer customer = this.customerRepo.getUserByUserName(p.getName());

		if(customer.getOtpAttept() <= 3){

		

    String email = customer.getcEmail();

   // generate otp 
    String otp = this.otpService.generateOtp();

    // save into database 
    customer.setSavedOtp(otp);
		customer.setOtpAttept(customer.getOtpAttept()+1);
    Customer isSaved = this.customerRepo.save(customer);

    if(isSaved != null){
        //   send email

    String otpmessage = 
		"<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP </h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+otp+"</h1></div>";

        String subject ="OTP for login proceed ";

        this.emailservice.sendmaill(email, otpmessage, subject);
    }

		   return "LoginOtpPage";
	  }
  
			customer.setOtpAttept(0);
			customer.setIsVerified(false);
			this.customerRepo.save(customer);
			return "redirect:/logout";
	     
	}
	

	// Home account open page handler ................................
		
	@GetMapping("/Open_new_accountt")
	public String OpenAccount(Model m, HttpSession session) {
		m.addAttribute("title","Create new account");

		session.removeAttribute("alert");
		
		return "Open_new_account";
	}
	 

// handler for get customer page support ..........

@GetMapping("/Customer_Support")
public String getCustSuppPage(Model m,HttpSession  session){

  
	m.addAttribute("session", session);

   return "customerSupport";
}



// handler for cardService ..............

@GetMapping("/cardService")
public String openCardServicePage(Model m , Principal p){
  m.addAttribute("title", "Star Bank : Card service");
	return "cardService";
}


// for incomplete work access page ..........................................

@GetMapping("/incomplete_task_page")
public String getIncomplete(Model m){
  m.addAttribute("title", "Incomplete work page");

	return "incompleteAlert";
}


/// download image here.......................................................

@GetMapping("/download")
public ResponseEntity<?> DOWNLOADfILE(@RequestParam MultipartFile file){
	return new ResponseEntity<>("Upload failed ", HttpStatus.INTERNAL_SERVER_ERROR);
}




}
