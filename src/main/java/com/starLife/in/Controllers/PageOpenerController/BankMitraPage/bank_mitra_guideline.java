package com.starLife.in.Controllers.PageOpenerController.BankMitraPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class bank_mitra_guideline {
  


  @GetMapping("/bank_mitra_guideline_page")
  public String requestMethodName(Model m) {

  m.addAttribute("title", "Bank Mitra : Guide Line");

      return "/bank_mitra_guideline";
  }
  
}
