package com.starLife.in.Controllers.PageOpenerController.adminPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/home")
public class AboutPageController {
  

  @GetMapping("/about")
  public String getMethodName(Model m) {



  m.addAttribute("title", "Start Life Bank  :About us");


      return "AboutUs";
  }
  
}
