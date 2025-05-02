package com.starLife.in.Controllers.AuthorizedControllers.BankMitraControllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.starLife.in.Entity.BankMitra;
import com.starLife.in.repository.BankMitraRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bank_mitra")
public class BankMitraController {

  @Autowired  private BankMitraRepository bankmitraRepo;

  @GetMapping("/dashboard")
  public String getBanlMitraHomePage(Model m , Principal p, HttpSession session ){


    String username = p.getName();

    BankMitra bankmitra = this.bankmitraRepo.findByEmail(username);


    System.out.println("Current logged in bank mitra : "+bankmitra);
    session.setAttribute("bankmitra", bankmitra);
  
   m.addAttribute("title", "Bank Mitra Home Page ");
    return "/BankMitraServices/BankMitraDashboard";
  }

  @GetMapping("/accountOpen")
  public String newAccountOpen(Model m , Principal p ){
   m.addAttribute("title", "Bank Mitra : Account open service ");
    return "/BankMitraServices/openNewAccount";

  }

  @GetMapping("/ekyc")
  public String ekyc(Model m , Principal p ){
   m.addAttribute("title", "Bank Mitra : Kyc Service ");
    return "/BankMitraServices/ekyc";

  }

  @GetMapping("/depositMoney")
  public String depositMoney(Model m , Principal p ){
   m.addAttribute("title", "Bank Mitra : Deposit Money");
    return "/BankMitraServices/depositMoney";

  }

  @GetMapping("/cutomer_service")
  public String insurence(Model m , Principal p, HttpSession session ){
   m.addAttribute("title", "Bank Mitra : Customer services ");
   session.setAttribute("open_pan", "open_pan");
    return "/BankMitraServices/customer_services";
  }

  @GetMapping("/withdrawl")
  public String withdrawl(Model m , Principal p ){
   m.addAttribute("title", "Bank Mitra : Withdrawl Money ");
    return "/BankMitraServices/withdrawl";

  }

  @GetMapping("/moneyTransfer")
  public String moneyTransfer(Model m , Principal p ){
   m.addAttribute("title", "Bank Mitra : Money Transfer");
    return "/BankMitraServices/moneyTransfer";

  }

  
}
