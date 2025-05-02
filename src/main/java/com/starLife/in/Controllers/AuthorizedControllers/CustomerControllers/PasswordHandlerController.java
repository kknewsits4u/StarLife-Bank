package com.starLife.in.Controllers.AuthorizedControllers.CustomerControllers;

import java.security.Principal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.starLife.in.Entity.Customer;
import com.starLife.in.helper.Message;
import com.starLife.in.helper.SigninHelper;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.EmailService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/home")
public class PasswordHandlerController {

  @Autowired
  private CustomerRepository custRepo;

  @Autowired
  private EmailService emailSEndService;

  // Home forget password page handler ................................

  @GetMapping("/forgetPasswordPage")
  public String openForgetPasswordPage(Model m, HttpSession session) {
    m.addAttribute("title", "Forgotten password");
    session.invalidate();
    return "forgetPassword";
  }

  // reset password page handler ....................................

  @GetMapping("/resetPassword")
  public String passwordResetPageOpen(Model m) {
    m.addAttribute("title", "Reset password !!");
    return "NewPasswordSetUp";
  }

  @PostMapping("/forgetPasswordPage/getOtp")
  public String forgetPassword(@ModelAttribute Customer customer, Principal p, HttpSession session, Model m) {

    // check valid user .....

    String userid = customer.getUserId();

    session.setAttribute("userid", userid);

    Customer cust = this.custRepo.getUserByUserName(userid);

    Random rd = new Random();

    try {

      int min = 100000;
      int max = 999999;

      int ottp = rd.nextInt(max - min + 1) + min;

      String myOtp = Integer.toString(ottp);

      String message = "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP for reset password</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"
          + myOtp + "</h1></div>";

      String subject = "ONE TIME PASSWORD";

      if (cust != null) {
        String email = cust.getcEmail();
        session.setAttribute("email", email);
        session.setAttribute("myotp", myOtp);
        Message messagee = new Message("alert-success", "OTP send successfully");
        session.setAttribute("messagee", messagee);
        this.emailSEndService.sendmaill(email, message, subject);
        m.addAttribute("userid", userid);
        m.addAttribute("title", "Forgot Password");
        return "forgetPassword";
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    Message messagee = new Message("alert-danger", "Invalid userid");
    session.setAttribute("messagee", messagee);
    m.addAttribute("userid", userid);
    m.addAttribute("title", "Forgot Password");

    return "forgetPassword";

  }

  // verify otp here ......................

  @PostMapping("/forgetPasswordPage/verify_otp")
  public String verifyOtp(@ModelAttribute SigninHelper signinHelper, HttpSession session, Model m) {

    String savedOtp = (String) session.getAttribute("myotp");

    String currOtp = signinHelper.getNewOtp();

    String email = (String) session.getAttribute("email");

    Customer customer = this.custRepo.findByEmail(email);

    boolean flag = false;

    if (customer != null) {

      if (savedOtp.equals(currOtp)) {

        flag = true;

        session.setAttribute("messagee", new Message("alert-success", "OTP verified successfully !!! "));
      } else {
        session.setAttribute("messagee", new Message("alert-danger", "Invalid OTP ! Please enter valid otp "));
      }
    } else {
      session.setAttribute("messagee", new Message("alert-danger", "User not found !! please enter valid userid"));
    }

    // otp verification confirmation

    session.setAttribute("flag", flag);

    // set attribute for input otp

    m.addAttribute("myotp", currOtp);

    // fetch userid from session

    String userid = (String) session.getAttribute("userid");

    m.addAttribute("userid", userid);

    return "forgetPassword";
  }

  // method for reset password page ...........

  @GetMapping("/forgetPasswordPage/resetpass")
  public String getResetPage(HttpSession session) {

    boolean flag = (boolean) session.getAttribute("flag");

    if (!flag) {
      return "forgetPassword";
    }

    return "NewPasswordSetUp";
  }

}