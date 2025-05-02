package com.starLife.in.Controllers.PageOpenerController.BankMitraPage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.starLife.in.Entity.BankMitra;
import com.starLife.in.helper.Message;
import com.starLife.in.repository.BankMitraRepository;
import com.starLife.in.service.EmailService;
import com.starLife.in.service.PasswordGeneratorService;
import jakarta.servlet.http.HttpSession;


@Controller
public class RegisterController {

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Autowired
  private PasswordGeneratorService passwordGeneratorService;


  @Autowired
  private BankMitraRepository bankMitraRepository;


  @Autowired
  private EmailService emailService;


  //  handler for new bank mitra register .........

  @PostMapping("/doregister")
  public String postMethodName(@ModelAttribute BankMitra bankmitra,@RequestParam("filename") MultipartFile file , Principal p , Model m , HttpSession session ) {

    try {

      // String email = bankmitra.getEmail();
      String email = bankmitra.getEmail();

      BankMitra existUser = this.bankMitraRepository.findByEmail(email);

      System.out.println("existing user = "+ existUser);

      if(existUser == null){

      // image handling.............
      if(file.isEmpty()){
        bankmitra.setBimage("profile.png");
      }
      else{

        // get filename

        String savedImage = file.getOriginalFilename();

        // get File 

        File newfile = new ClassPathResource("static/image").getFile();

        // get path

        Path path = (Path) Paths.get(newfile.getAbsolutePath()+File.separator+savedImage);

        // copy image 

        Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);

        bankmitra.setBimage(savedImage);
      }
       
        //  passwordGenerator.............

        String name = bankmitra.getFullname();
        String phone = bankmitra.getPhoneno();
        String fathername = bankmitra.getFathername();

        String password = passwordGeneratorService.generate(name, phone, fathername);

        bankmitra.setPassword(encoder.encode(password));
        bankmitra.setCreatedAt(LocalDateTime.now());
        // bankmitra.setRole("ROLE_BANKMITRA");
        bankmitra.setRole("BANKMITRA");
        
        BankMitra save = bankMitraRepository.save(bankmitra);
        
        if( save != null){

          // send email ............................................

          String message = 
          "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+"Hello Mr. "+save.getFullname()+"</h1><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>-:Your password  :-</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+password+"</h1><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>-:Application Status  :-</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+"PENDING"+"</h1></div>";
          
         
          emailService.sendmaill(save.getEmail(), message , "New Bank Mitra Registration Process");
        }


        System.out.println("bank mitra : " + save);

        return "BankMitraServices/login";
      }
    
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    System.out.println("bank mitra : "+bankmitra);
    m.addAttribute("title", "Bank Mitra : Registration");
    Message message = new Message("alert-danger", "Email is already registered with another user !!!");
    session.setAttribute("register",   message);


    return "BankMitraServices/register";
    
    
  }
  
}

