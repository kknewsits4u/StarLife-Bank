package com.starLife.in.starLifeConfig.BankMitraConfiguration;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.starLife.in.Entity.BankMitra;

public class BankMitraInfo implements UserDetails {

  private BankMitra bankmitra;

  	
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;


  public BankMitraInfo(BankMitra bankMitra){
    this.bankmitra = bankMitra;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_"+ bankmitra.getRole()));
  }

  @Override
  public String getPassword() {
    return bankmitra.getPassword();
  }

  @Override
  public String getUsername() {
    return bankmitra.getEmail();
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }


  @Override
  public boolean isAccountNonLocked() {
    return true;
  }


  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }


  @Override
  public boolean isEnabled() {
    return true;
  }




  
  
}
