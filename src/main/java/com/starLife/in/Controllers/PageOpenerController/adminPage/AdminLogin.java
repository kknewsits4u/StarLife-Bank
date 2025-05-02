package com.starLife.in.Controllers.PageOpenerController.adminPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AdminLogin {

  @GetMapping("/adminLogin")
  public String getMethodName(Model m) {

    m.addAttribute("title", "Admin : Login"); 

      return "AdminServices/login";
  }
  
}
