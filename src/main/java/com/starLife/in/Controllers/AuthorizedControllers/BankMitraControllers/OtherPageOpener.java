package com.starLife.in.Controllers.AuthorizedControllers.BankMitraControllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.starLife.in.Entity.BankMitra;
import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.OtpException;
import com.starLife.in.helper.AdharNumberProceesorHelper;
import com.starLife.in.helper.Message;
import com.starLife.in.helper.OtpHelper;
import com.starLife.in.repository.BankMitraRepository;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.service.EmailService;
import com.starLife.in.service.KycService;
import com.starLife.in.service.OtpService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/bank_mitra")
public class OtherPageOpener {

  @Autowired
  private BankMitraRepository bankmitraRepo;

  @Autowired private CustomerRepository customerRepo;

  @Autowired private EmailService emailService;

  @Autowired private OtpService otpService;

  @Autowired private KycService kycService;
   
  @GetMapping("/bankmitraUpdate")
  public String bankmitrauPdate(Model m, Principal p, HttpSession session) {


   m.addAttribute("title", "Bank Mitra: Details Update ");

  //  find email .................................
     String username = p.getName();
    
     BankMitra bankmitra  = this.bankmitraRepo.findByEmail(username);
     
     session.setAttribute("bankmitra1", bankmitra);

     return "BankMitraServices/bankMitraDetailUpdate";
  }
  

//   image update view page opener ................

  @GetMapping("/updateImageHandler")
  public String getUpdateImage(Model m, Principal p, HttpSession session) {

   m.addAttribute("title", "Bank Mitra: Details Image ");

   String username = p.getName();
    
   BankMitra bankmitra  = this.bankmitraRepo.findByEmail(username);
   
   session.setAttribute("bankmitra1", bankmitra);

      return "BankMitraServices/UpdateImage.html";
  }
  

  //  update image processing ...........................................

  @PostMapping("/updateBankMitraImage")
  public String updateBankMitraProfileImage(@ModelAttribute BankMitra bankmitra, @RequestParam("filename") MultipartFile file, HttpSession session , Model m, Principal p) throws IOException {

    String username = p.getName();
    
    BankMitra currentBankMitra = this.bankmitraRepo.findByEmail(username);

    if(file == null){

      currentBankMitra.setBimage(currentBankMitra.getBimage());

    }
    else{

    //  get original filename 

    String savedImage = file.getOriginalFilename();

    //  GET file 

  File savedfile = new ClassPathResource("static/image").getFile();

  Path path = (Path) Paths.get(savedfile.getAbsolutePath()+File.separator+savedImage);

  Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);

     currentBankMitra.setBimage(savedImage);

    }


     BankMitra updatedBankMitra= this.bankmitraRepo.save(currentBankMitra);

     System.out.println("Updated bankmitra : "+updatedBankMitra);

 
 
     System.out.println("Current logged in bank mitra : "+currentBankMitra);
     session.setAttribute("bankmitra", currentBankMitra);

      return "redirect:/bank_mitra/dashboard";
  }
  

  //  update bank mitra update details ...............................................
    
  @PostMapping("/updateDetails")
  public String postMethodName(@ModelAttribute BankMitra bankmitra, HttpSession session , Model m, Principal p) {

    String username = p.getName();

    BankMitra currentBankMitra = this.bankmitraRepo.findByEmail(username);

    currentBankMitra.setFullname( bankmitra.getFullname());
    currentBankMitra.setFathername( bankmitra.getFathername());
    currentBankMitra.setMothername( bankmitra.getMothername());
    currentBankMitra.setPhoneno( bankmitra.getPhoneno());
    currentBankMitra.setPanno( bankmitra.getPanno());
    currentBankMitra.setEmail( bankmitra.getEmail());

     BankMitra updatedBankMitra= this.bankmitraRepo.save(currentBankMitra);

     System.out.println("Updated bankmitra : "+updatedBankMitra);


      return "redirect:/bank_mitra/bankmitraUpdate";
  }
  


  //  do byc through bank mitra ......................................................

  @PostMapping("/doKyc/otp")
  public String doKyc(@ModelAttribute AdharNumberProceesorHelper  adharHelp , Model m , Principal p , HttpSession session) throws OtpException {
      
  
    String username = p.getName();

    //  find user by username 

    Customer currentCustomer = this.customerRepo.findByEmail(username);

    // adhar construct ..........

    String adhar1 = adharHelp.getAdharpart1();
    String adhar2 = adharHelp.getAdharpart2();
    String adhar3 = adharHelp.getAdharpart3();

    String aadharNo = adhar1+adhar2+adhar3;

    if(currentCustomer.getcAadhar().equals(aadharNo)){

     
    // generate otp ..........

     String otp = this.otpService.generateOtp();

    // send email for otp 
    

    String message = 
    "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP for KYC Update </h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+otp+"</h1></div>";


    this.emailService.sendmaill(currentCustomer.getcEmail(), message, "OTP for KYC update !!!!! ");

    Message sendmessage = new Message("alert-success","Otp send successfully!!!");
    session.setAttribute("otpProcess", sendmessage);
    session.setAttribute("otpSendDone", sendmessage);
    

    currentCustomer.setSavedOtp(otp);
    this.customerRepo.save(currentCustomer);

    return "redirect:/bank_mitra/ekyc";


    }

    Message sendmessage = new Message("alert-danger","Some error occured, Please check your adhar no.  !!!");
    session.setAttribute("otpProcess", sendmessage);
  
    
    return "redirect:/bank_mitra/ekyc";


  }
  
  //  customer kyc handler ..........................................

  @PostMapping("/doKyc/process")
  public String postMethodName(@ModelAttribute OtpHelper  otpHelper , Model m , Principal p , HttpSession session) throws OtpException {
      
  
    String username = p.getName();

    //  find user by username 

    Customer currentCustomer = this.customerRepo.findByEmail(username);

    // otp construct ..........
   
    String otp1 = otpHelper.getOtp1();
    String otp2 = otpHelper.getOtp2();
    String otp3 = otpHelper.getOtp3();
    String otp4 = otpHelper.getOtp4();
    String otp5 = otpHelper.getOtp5();
    String otp6 = otpHelper.getOtp6();
    

    String otp = otp1+otp2+otp3+otp4+otp5+otp6;

    System.out.println("Saved otp : "+currentCustomer.getSavedOtp());
    System.out.println("enter user otp : "+otp);

    if(currentCustomer.getSavedOtp().equals(otp)){

     
    // generate do kyc  ..........
    
    Boolean kyc = this.kycService.makeKyc(currentCustomer, otp);

    // send email for otp 
    if( kyc){

    String newMessage = "Kyc Done Success Fully !!!!!";


    String message = 
    "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP for KYC Update </h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+newMessage+"</h1></div>";


    this.emailService.sendmaill(currentCustomer.getcEmail(), message, "Message for KYC update !!!!! ");

    Message sendmessage = new Message("alert-success","Kyc has been done successfully!!!");
    session.setAttribute("otpProcess", sendmessage);
    session.removeAttribute("otpSendDone");

    currentCustomer.setSavedOtp("null");
    this.customerRepo.save(currentCustomer);

    return "redirect:/bank_mitra/ekyc";

    }else{


      Message sendmessage = new Message("alert-danger","Some error occured !!! ");
      session.setAttribute("otpProcess", sendmessage);
      session.removeAttribute("otpSendDone");
  
      return "redirect:/bank_mitra/ekyc";

    }

  }

    Message sendmessage = new Message("alert-danger","OTP did not match, Please check and re-enter !!!");

    session.setAttribute("otpProcess", sendmessage);
    session.setAttribute("otpSendDone", sendmessage);
    
    return "redirect:/bank_mitra/ekyc";


  }
  
// handler for pan card link process...................................

@GetMapping("/open_pan_card_link")
public String getpancard_link_page(Model m , Principal p, HttpSession session) {

  m.addAttribute("title", "Bank Mitra : Customer services ");
  
  session.setAttribute("open_pan", "open_pan");
  session.removeAttribute("open_atm");
  session.removeAttribute("open_npci");
  session.removeAttribute("generate_otp");
  session.removeAttribute("message");

  return "/BankMitraServices/customer_services";
}


// pan card otp handler ...................................


@PostMapping("/pan_card_link_otp")
public String getMethodName(Model m , Principal p, HttpSession session, @ModelAttribute Customer customer) throws OtpException {

   String email = p.getName();
   
   Customer currentCustomer = this.customerRepo.findByEmail(email);

   String panNumber = customer.getPanCard();
   String aadharno =customer.getcAadhar();

  System.out.println("pannumber : "+panNumber);
  System.out.println("adhar number  : "+aadharno);
  System.out.println("currentCustomer  : "+currentCustomer);


   if( aadharno.equals(currentCustomer.getcAadhar()) ){


   String otp = this.otpService.generateOtp();

   System.out.println("otp : "+otp);
   
   String message = 
   "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP for KYC Update </h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+otp+"</h1></div>";


   this.emailService.sendmaill(currentCustomer.getcEmail(), message, "Message for KYC update !!!!! ");

   session.setAttribute("pannumberr", panNumber);
   currentCustomer.setSavedOtp(otp);
   this.customerRepo.save(currentCustomer);

   session.setAttribute("open_pan", "open_pan");
   session.setAttribute("generate_otp", "generate_otp");

   Message newmwssage = new Message("alert-success", "OTP has been sent successfully !!!");
   session.setAttribute("message", newmwssage);
   m.addAttribute("title", "Bank Mitra : Customer services ");
   return "/BankMitraServices/customer_services";

   }


    Message newmwssage = new Message("alert-danger", "Some error occured !!!");
    session.setAttribute("message", newmwssage);
    m.addAttribute("title", "Bank Mitra : Customer services ");
    session.setAttribute("open_pan", "open_pan");
 
    return "/BankMitraServices/customer_services";

}


@PostMapping("/pan_card_link_proceed")
public String linkPan(Model m , Principal p, HttpSession session, @ModelAttribute OtpHelper otpHelper) {
    

  String email = p.getName();
   
  Customer currentCustomer = this.customerRepo.findByEmail(email);
  
  if(currentCustomer.getPanCard().isEmpty()){

  String otp1 = otpHelper.getOtp1();
  String otp2 = otpHelper.getOtp2();
  String otp3 = otpHelper.getOtp3();
  String otp4 = otpHelper.getOtp4();
  String otp5 = otpHelper.getOtp5();
  String otp6 = otpHelper.getOtp6();
  

  String otp = otp1+otp2+otp3+otp4+otp5+otp6;

 
  if(currentCustomer.getSavedOtp().equals(otp)){


    session.setAttribute("open_pan", "open_pan");
    session.removeAttribute("generate_otp");

    Message newmwssage = new Message("alert-success", "Pan Card has been linked successfully !!!");
    session.setAttribute("message", newmwssage);

    String pancardno = (String) session.getAttribute("pannumberr");
    currentCustomer.setPanCard(pancardno);
    this.customerRepo.save(currentCustomer);
 
    return "/BankMitraServices/customer_services";
    
  }
    
  currentCustomer.setPanCard("null");
  this.customerRepo.save(currentCustomer);
  Message newmwssage = new Message("alert-danger", "Wrong otp entered ");
  session.setAttribute("message", newmwssage);
  session.setAttribute("open_pan", "open_pan");
  session.setAttribute("generate_otp", "generate_otp");

  return "/BankMitraServices/customer_services";

}
session.setAttribute("open_pan", "open_pan");
session.removeAttribute("generate_otp");

Message newmwssage = new Message("alert-success", "Pan Card  already linked !!!");
session.setAttribute("message", newmwssage);

return "/BankMitraServices/customer_services";
}


@GetMapping("/open_atm_card_apply_link")
public String getapply_aadhar_card_link_page(Model m , Principal p, HttpSession session) {

  m.addAttribute("title", "Bank Mitra : Customer services ");

  session.setAttribute("open_atm", "open_atm");
  session.removeAttribute("open_pan");
  session.removeAttribute("open_npci");
  session.removeAttribute("message");

    return "/BankMitraServices/customer_services";
}


// apply 
@GetMapping("/open_npci_enable_link")
public String get_npci_link_page(Model m , Principal p, HttpSession session) {

  m.addAttribute("title", "Bank Mitra : Customer services ");

  session.setAttribute("open_npci", "open_npci");
  session.removeAttribute("open_atm");
  session.removeAttribute("open_pan");
  session.removeAttribute("message");


    return "/BankMitraServices/customer_services";
}


@PostMapping("/make_npci_link_otp")
public String doNpciOtp(Model m , Principal p, HttpSession session, @ModelAttribute Customer customer) throws OtpException {

   String email = p.getName();
   
   Customer currentCustomer = this.customerRepo.findByEmail(email);

   if(currentCustomer.getNpcistatus() == null){


   String accountNumber = customer.getAccountNo();
   String aadharno = customer.getcAadhar();

   System.out.println("current acc : "+accountNumber);
   System.out.println("current aadhar : "+aadharno);
   System.out.println("saved acc : "+currentCustomer.getAccountNo());
   System.out.println("saved adhar : "+currentCustomer.getcAadhar());

   if( accountNumber.equals(currentCustomer.getAccountNo()) && aadharno.equals(currentCustomer.getcAadhar()) ){


   String otp = this.otpService.generateOtp();


   
   String message = 
   "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP for NPCI Link </h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"+otp+"</h1></div>";


   this.emailService.sendmaill(currentCustomer.getcEmail(), message, "Message for NPCI activate !!!!! ");

   currentCustomer.setSavedOtp(otp);
   this.customerRepo.save(currentCustomer);

   session.setAttribute("open_npci", "open_npci");
   session.setAttribute("generate_otp1", "generate_otp1");
   session.removeAttribute("generate_otp");
   Message newmwssage = new Message("alert-success", "OTP has been sent successfully !!!");
   session.setAttribute("message", newmwssage);

   return "/BankMitraServices/customer_services";

   }

   Message newmwssage = new Message("alert-danger", "Some error occured !!!");
   session.setAttribute("message", newmwssage);
   session.setAttribute("open_npci", "open_npci");
   session.removeAttribute("generate_otp1");
   return "/BankMitraServices/customer_services";
  }

  Message newmwssage = new Message("alert-success", "NPCI already done !!!");
  session.setAttribute("message", newmwssage);
  session.removeAttribute("generate_otp1");

  return "/BankMitraServices/customer_services";

}


@PostMapping("/make_npci_link_process")
public String proceedNpci(Model m , Principal p, HttpSession session, @ModelAttribute OtpHelper otpHelper) {
    

  String email = p.getName();
   
  Customer currentCustomer = this.customerRepo.findByEmail(email);

  

  

  String otp1 = otpHelper.getOtp1();
  String otp2 = otpHelper.getOtp2();
  String otp3 = otpHelper.getOtp3();
  String otp4 = otpHelper.getOtp4();
  String otp5 = otpHelper.getOtp5();
  String otp6 = otpHelper.getOtp6();
  

  String otp = otp1+otp2+otp3+otp4+otp5+otp6;

 
  if(currentCustomer.getSavedOtp().equals(otp)){

    currentCustomer.setSavedOtp("null");
    currentCustomer.setNpcistatus("done");
    this.customerRepo.save(currentCustomer);

    session.setAttribute("open_npci", "open_npci");
    session.removeAttribute("generate_otp");
    Message newmwssage = new Message("alert-success", "NPCI has been activated successfully !!!");

    session.setAttribute("message", newmwssage);
    return "/BankMitraServices/customer_services";
    
  }
    

  Message newmwssage = new Message("alert-danger", "Wrong otp entered ");
  session.setAttribute("message", newmwssage);
  session.setAttribute("open_pan", "open_pan");
  session.setAttribute("generate_otp", "generate_otp");

  return "/BankMitraServices/customer_services";

}


}
