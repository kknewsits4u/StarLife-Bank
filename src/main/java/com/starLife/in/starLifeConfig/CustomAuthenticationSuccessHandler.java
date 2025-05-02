package com.starLife.in.starLifeConfig;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler  {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

      if (roles.contains("ROLE_CUSTOMER")) {
          response.sendRedirect("/home/otppage"); // Redirect Customers
      } else if (roles.contains("ROLE_BANKMITRA")) {
          response.sendRedirect("/bank_mitra/dashboard"); // Redirect Bank Mitras
      } else if (roles.contains("ROLE_ADMIN")) {
          response.sendRedirect("/admin/dashboard"); // Redirect Bank Mitras
      } else {
        System.out.println("Customer not found  "+roles);
          response.sendRedirect("/home"); // Default Page
      }
  }
  
}
