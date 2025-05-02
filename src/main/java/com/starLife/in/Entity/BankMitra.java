package com.starLife.in.Entity;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BankMitra{
  
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;

  private String fullname;
  private String email;
  private String phoneno;
  private String panno;
  private String addharno;
  private String accountno;
  private String fathername;
  private String mothername;
  private String bimage;
  private String password;
  private LocalDateTime createdAt;
  private String role;


  public String getFullname() {
    return fullname;
  }
  public void setFullname(String fullname) {
    this.fullname = fullname;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPhoneno() {
    return phoneno;
  }
  public void setPhoneno(String phoneno) {
    this.phoneno = phoneno;
  }
  public String getPanno() {
    return panno;
  }
  public void setPanno(String panno) {
    this.panno = panno;
  }
  public String getAddharno() {
    return addharno;
  }
  public void setAddharno(String addharno) {
    this.addharno = addharno;
  }
  public String getAccountno() {
    return accountno;
  }
  public void setAccountno(String accountno) {
    this.accountno = accountno;
  }
  public String getFathername() {
    return fathername;
  }
  public void setFathername(String fathername) {
    this.fathername = fathername;
  }
  public String getMothername() {
    return mothername;
  }
  public void setMothername(String mothername) {
    this.mothername = mothername;
  }
  public String getBimage() {
    return bimage;
  }
  public void setBimage(String bimage) {
    this.bimage = bimage;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
  
  
  
  public BankMitra() {
  }


 
  public BankMitra(int id, String fullname, String email, String phoneno, String panno, String addharno,
      String accountno, String fathername, String mothername, String bimage, String password, LocalDateTime createdAt,
      String role) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.phoneno = phoneno;
    this.panno = panno;
    this.addharno = addharno;
    this.accountno = accountno;
    this.fathername = fathername;
    this.mothername = mothername;
    this.bimage = bimage;
    this.password = password;
    this.createdAt = createdAt;
    this.role = role;
  }
  @Override
  public String toString() {
    return "BankMitra [id=" + id + ", fullname=" + fullname + ", email=" + email + ", phoneno=" + phoneno + ", panno="
        + panno + ", addharno=" + addharno + ", accountno=" + accountno + ", fathername=" + fathername + ", mothername="
        + mothername + ", bimage=" + bimage + ", password=" + password + ", createdAt=" + createdAt + ", role=" + role
        + "]";
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }





}
