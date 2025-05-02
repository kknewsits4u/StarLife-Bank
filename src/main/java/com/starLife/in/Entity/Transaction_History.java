package com.starLife.in.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="trans_history")
public class Transaction_History {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String trans_id;
  private String amount;
  private String balance;
  private LocalDate  trans_date;
  private String status;
   
  @ManyToOne
	private Customer cust;



  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTrans_id() {
    return trans_id;
  }

  public void setTrans_id(String trans_id) {
    this.trans_id = trans_id;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public LocalDate getTrans_date() {
    return trans_date;
  }

  public void setTrans_date(LocalDate trans_date) {
    this.trans_date = trans_date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Customer getCust() {
    return cust;
  }

  public void setCust(Customer cust) {
    this.cust = cust;
  }

  public Transaction_History(int id, String trans_id, String amount, String balance, LocalDate trans_date, String status,
      Customer cust) {
    this.id = id;
    this.trans_id = trans_id;
    this.amount = amount;
    this.balance = balance;
    this.trans_date = trans_date;
    this.status = status;
    this.cust = cust;
  }

  public Transaction_History() {
  }

  @Override
  public String toString() {
    return "Transaction_History [id=" + id + ", trans_id=" + trans_id + ", amount=" + amount + ", balance=" + balance
        + ", trans_date=" + trans_date + ", status=" + status + ", cust=" + cust + "]";
  }

  


}
