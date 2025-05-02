package com.starLife.in.helper;

public class MoneyTransferHelper {
  
  private String recipientAcc;
  private String recipientName;
  private String amount;
  private String atmPin;

  public String getRecipientAcc() {
    return recipientAcc;
  }
  public void setRecipientAcc(String recipientAcc) {
    this.recipientAcc = recipientAcc;
  }
  public String getRecipientName() {
    return recipientName;
  }
  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }
  public String getAmount() {
    return amount;
  }
  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getAtmPin() {
    return atmPin;
  }
  public void setAtmPin(String atmPin) {
    this.atmPin = atmPin;
  }



  public MoneyTransferHelper(String recipientAcc, String recipientName, String amount , String atmPin) {
    this.recipientAcc = recipientAcc;
    this.recipientName = recipientName;
    this.amount = amount;
    this.atmPin = atmPin;
  }


  public MoneyTransferHelper() {
  }




}
