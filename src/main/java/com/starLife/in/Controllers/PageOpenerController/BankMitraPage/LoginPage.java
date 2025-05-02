package com.starLife.in.Controllers.PageOpenerController.BankMitraPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginPage {
  
  
  @GetMapping("/bankmitraLogin")
  public String baniMitraLogin( Model m) {


     m.addAttribute("title", "Bank Mitra : Login");


      return "BankMitraServices/login";
  }
  


  
}
