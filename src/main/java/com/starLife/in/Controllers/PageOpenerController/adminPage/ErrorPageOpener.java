package com.starLife.in.Controllers.PageOpenerController.adminPage;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ErrorPageOpener {
  
  @GetMapping("/error")
  public String getMethodName(Model m) {
     m.addAttribute("title" , "Star Life Bank : Some error occured !!!");
      return "error";
  }
  


}
