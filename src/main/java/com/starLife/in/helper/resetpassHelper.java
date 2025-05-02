package com.starLife.in.helper;

public class resetpassHelper {

  
  private String newpassword;
  private String cnfmPass;


  public String getNewpassword() {
    return newpassword;
  }
  public void setNewpassword(String newpassword) {
    this.newpassword = newpassword;
  }
  public String getCnfmPass() {
    return cnfmPass;
  }
  public void setCnfmPass(String cnfmPass) {
    this.cnfmPass = cnfmPass;
  }
  public resetpassHelper(String newpassword, String cnfmPass) {
    this.newpassword = newpassword;
    this.cnfmPass = cnfmPass;
  }
  @Override
  public String toString() {
    return "resetpassHelper [newpassword=" + newpassword + ", cnfmPass=" + cnfmPass + "]";
  }
  public resetpassHelper() {
    super();
  }
  

}
