package com.starLife.in.Controllers.PageOpenerController.CustomerPages;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.starLife.in.Entity.Customer;
import com.starLife.in.repository.CustomerRepository;


@Controller
public class logoutController {
  
   @Autowired private CustomerRepository customerRepo;

   @GetMapping("/home/logoutsuccess")
    public String logout() {

        return "redirect:/signin"; 
    }


}
