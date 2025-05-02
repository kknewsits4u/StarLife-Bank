package com.starLife.in.Controllers.PageOpenerController.adminPage;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.starLife.in.Entity.Admin;
import com.starLife.in.ExceptionHandler.AdminException;
import com.starLife.in.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;





@Controller
public class Signup {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private AdminRepository adminRepo;


  @GetMapping("/adminsignup")
  public String getMethodName(Model m) {
    m.addAttribute("title", "Admin Signup");

      return "AdminServices/signup";
  }
  
  
  @PostMapping("/adminregister")
  public String getMethodName(@ModelAttribute Admin admin,Principal p, Model m, HttpSession session) throws AdminException {
    
   try {
    

    String password = admin.getPassword();

    admin.setPassword(this.passwordEncoder.encode(password));
    admin.setRole("ADMIN");

    Admin savedAdmin = this.adminRepo.save(admin);

    m.addAttribute("admin", savedAdmin);
    m.addAttribute("title", "Admin Signup");

    return "redirect:/adminLogin";

   } catch (Exception e) {
    throw new AdminException(500, e.getMessage());
   }
     
  }
  

}
