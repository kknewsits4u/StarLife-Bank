package com.starLife.in.Controllers.AuthorizedControllers.CustomerControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.starLife.in.Entity.CaptureCode;
import com.starLife.in.Entity.Customer;
import com.starLife.in.helper.Message;
import com.starLife.in.helper.resetpassHelper;
import com.starLife.in.repository.CaptureRepository;
import com.starLife.in.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class resetPasswordController {

  @Autowired
  private CaptureRepository captureRepository;

  @Autowired
  private BCryptPasswordEncoder bcptp;

  @Autowired
  private CustomerRepository custRepo;

  @PostMapping("/home/forgetPasswordPage/resetpassProcess")
  public String processResetPassAction(@ModelAttribute resetpassHelper rstpassHlpr, Model m, HttpSession session) {

    String newPass = rstpassHlpr.getNewpassword();
    String cnfmPass = rstpassHlpr.getCnfmPass();

    if (newPass.equals(cnfmPass)) {

      String email = (String) session.getAttribute("email");

      Customer cust = this.custRepo.findByEmail(email);

      if (cust != null) {
        session.setAttribute("message1", new Message("alert-success", "Password update successfully"));
        // String password =cust.getCpassword();
        // this.custRepo.delete(cust);
        cust.setCpassword(this.bcptp.encode(newPass));
        CaptureCode capture2 = this.captureRepository.findByCaptureno(1);
        String capture = capture2.getCaptureCode();
        m.addAttribute("capture", capture);
        this.custRepo.save(cust);
      } else {
        session.setAttribute("message1", new Message("alert-danger", "Some error occured ! please try again "));
        return "NewPasswordSetUp";
      }
      return "login";

    }
    m.addAttribute("cnfmPass", cnfmPass);
    m.addAttribute("newPass", newPass);
    session.setAttribute("message1", new Message("alert-danger", "New password and confirm password are not the same"));

    return "NewPasswordSetUp";
  }
}
