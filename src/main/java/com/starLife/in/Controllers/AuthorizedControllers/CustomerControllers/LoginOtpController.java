package com.starLife.in.Controllers.AuthorizedControllers.CustomerControllers;

import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.OtpException;
import com.starLife.in.helper.Message;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.OtpService;

import jakarta.servlet.http.HttpSession;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginOtpController {

  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private OtpService otpservice;

  @PostMapping("/proceedOtp")
  public String postMethodName(Principal p, Model m, HttpSession session, @RequestParam("d1") String d1,
      @RequestParam("d2") String d2, @RequestParam("d3") String d3, @RequestParam("d4") String d4) throws OtpException {

    String enter_otp = d1 + d2 + d3 + d4;

    Customer customer = this.customerRepo.getUserByUserName(p.getName());

    // saved otp

    String saved_otp = customer.getSavedOtp();

    // match otp

    Boolean verifyOtp = this.otpservice.verifyOtp(enter_otp, saved_otp);

    if (verifyOtp) {

      customer.setIsVerified(true);

      this.customerRepo.save(customer);

      return "redirect:/customer/customerDashboard/homepage";
    } else {
      if (customer.getOtpAttept() <= 3) {
        return "LoginOtpPage";
      }

      customer.setIsVerified(false);
      this.customerRepo.save(customer);
      return "redirect:/logout";
    }

  }

  // resend otp

  @GetMapping("/resend-otp")
  public String resendOtp() {

    return "redirect:/home/otppage";

  }

}
