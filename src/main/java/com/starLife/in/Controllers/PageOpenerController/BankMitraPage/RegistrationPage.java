package com.starLife.in.Controllers.PageOpenerController.BankMitraPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpSession;

@Controller
public class RegistrationPage {
  
  @GetMapping("/bank_mitra_register")
  public String baniMitraLogin( Model m , HttpSession session ) {

    m.addAttribute("title", "Bank Mitra :  Registration");

    session.removeAttribute("register");
    
    return "BankMitraServices/register";
  }
}
