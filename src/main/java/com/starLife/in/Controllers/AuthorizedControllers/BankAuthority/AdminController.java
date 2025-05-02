package com.starLife.in.Controllers.AuthorizedControllers.BankAuthority;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.starLife.in.Entity.Admin;
import com.starLife.in.Entity.BankMitra;
import com.starLife.in.Entity.Customer;
import com.starLife.in.ExceptionHandler.CustomerException;
import com.starLife.in.repository.AdminRepository;
import com.starLife.in.repository.BankMitraRepository;
import com.starLife.in.repository.CustomerRepository;


@Controller
@RequestMapping("/admin")
public class AdminController {


  @Autowired private AdminRepository adminRepo;
  @Autowired private CustomerRepository customerRepo;
  @Autowired private BankMitraRepository bankmitraRepo;

  @GetMapping("/dashboard")
  public String getAdminDashboard(Model m, Principal p) {


    m.addAttribute("title", "Admin: Dashboard");
    
   
    String email = p.getName();

    // find admin found or not 

     Admin findAdmin = this.adminRepo.findByEmail(email);

     try {

      if(findAdmin != null  ){
             
        // find all  customers

       List<BankMitra> bankmitraRow = this.bankmitraRepo.findAll();
       
       if(bankmitraRow == null){
        throw new CustomerException(404, "Bank Mitra Not Found !!!! ");
       }
       m.addAttribute("totalCountbm", bankmitraRow.size()); 

       List<BankMitra> bankmitra = bankmitraRow.subList(0, Math.min(5, bankmitraRow.size()));
        m.addAttribute("bankmitra", bankmitra);

        List<Customer> customerRow = this.customerRepo.findAll();

        if(customerRow == null){
          throw new CustomerException(404, "Customers Not Found !!!! ");
         }

         m.addAttribute("totalCountc", customerRow.size()); 

         List<Customer> customers = customerRow.subList(0, Math.min(5, customerRow.size()));
        m.addAttribute("customer", customers);

      }

     } catch (Exception e) {
      throw new UsernameNotFoundException(e.getMessage());
     }

      return "/AdminServices/Dashboard";
  }


  @GetMapping("/viewbankmitra/{id}/view")
  public String getBankmitraById(Model m, @PathVariable("id") Integer id) {
    m.addAttribute("title", "Admin: Bank Mitra View");

    //  fetch bank mitra details with id 

    Optional<BankMitra> bankmitraOpt = this.bankmitraRepo.findById(id);

    if (bankmitraOpt.isEmpty()) {
      // handle not found case properly
      throw new RuntimeException("Bank Mitra not found for id: " + id);
  }

      BankMitra bankmitra = bankmitraOpt.get();
      m.addAttribute("bankmitra", bankmitra);

      return "/AdminServices/ViewBankMitra";
  }
  
  @GetMapping("/viewcustomer/{Cid}/view")
  public String getCustomerById(Model m,  @PathVariable("Cid") Integer Cid) {
    m.addAttribute("title", "Admin: Customer View");


     
    Optional<Customer> customerOpt = this.customerRepo.findById(Cid);

    if (customerOpt.isEmpty()) {
      // handle not found case properly
      throw new RuntimeException("Bank Mitra not found for id: " + Cid);
  }

      Customer customers = customerOpt.get();
      m.addAttribute("customer", customers);

      return "/AdminServices/ViewCustomer";
  }


  @GetMapping("/viewallcustomers")
  public String getAllCustomers(Model m , Principal p) {


    String email = p.getName();

    // find admin found or not 

     Admin findAdmin = this.adminRepo.findByEmail(email);
    
     try {

      if(findAdmin != null  ){
             
        // find all  customers

        List<Customer> customersRow = this.customerRepo.findAll();

        List<Customer> customers = customersRow.subList(0, Math.min(5, customersRow.size()));

        m.addAttribute("customer", customers);

       

        m.addAttribute("title", "Admin: Customer Access");

        return "/AdminServices/ViewAllCustomers";

      }
      
     } catch (Exception e) {
      throw new UsernameNotFoundException(e.getMessage());
     }

     return null;

  }
  

  @GetMapping("/viewallbankmitra")
  public String getAllBankMitra(Model m , Principal p) {


    String email = p.getName();

    // find admin found or not 

     Admin findAdmin = this.adminRepo.findByEmail(email);
    
     try {

      if(findAdmin != null  ){
             
        // find all  customers

       List<BankMitra> bankmitraRow = this.bankmitraRepo.findAll();

        if(bankmitraRow == null){
          System.out.println("Bank mitra is nulll ");
        }

        List<BankMitra> bankmitra = bankmitraRow.subList(0, Math.min(5, bankmitraRow.size()));
        m.addAttribute("bankmitra", bankmitra);

        m.addAttribute("title", "bankmitra: Customer Access");
        
        
        return "/AdminServices/ViewAllBankMitra";
      }
      
     } catch (Exception e) {
      throw new UsernameNotFoundException(e.getMessage());
     }

     return null;

  }
  
  //  REMOVE CUSTOMER

  @PostMapping("/customer/{Cid}/remove")
  public String removeCustomer(Model m, Principal p, @PathVariable("Cid") Integer Cid){
   
    System.out.println(" your current id: "+Cid);
    try {

       String email = p.getName();
  
       Admin foundAdmin = this.adminRepo.findByEmail(email);

       if(  foundAdmin != null){
         this.customerRepo.deleteById(Cid);
       }
       
    } catch (Exception e) {
      throw new UsernameNotFoundException("customer not deleted !!! ");
    }

    return "redirect:/admin/dashboard";
    
  }

  @PostMapping("/bankmitra/{id}/remove")
  public String removeBankMitra(Model m, Principal p, @PathVariable("id") Integer id){

    try {

       String email = p.getName();
  
       Admin foundAdmin = this.adminRepo.findByEmail(email);

       if(  foundAdmin != null){
         this.bankmitraRepo.deleteById(id);
       }
   
  
    } catch (Exception e) {
      throw new UsernameNotFoundException("customer not deleted !!! ");
    }

    return "redirect:/admin/dashboard";
    
  }



}
