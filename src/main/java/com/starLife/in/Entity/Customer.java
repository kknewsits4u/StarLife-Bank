package com.starLife.in.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="customer_details")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Cid;
	@Column( nullable = false )
	private String name;
	@Column( nullable = false )
	private String cEmail;
	@Column( nullable = false )
	private String cPhone;
	@Column( nullable = false )
	private String cAadhar;
	private String cAccType;
	private String cImage;
	@Column( nullable = false )
	private String cFathername;
	private String cMothername;
	private String cNomineename;
	@Column( nullable = false )
	private String cpassword;
	@Transient
	private String confiemPassword;
	private String accountNo;
	private String userId;
	private String role;
	private Date date;
	private String balance;
  private String adharLinkStatus;
	private String npcistatus;
	private String ekycstatus;
	private String atmPin;
	private String atmNumber;
	private String panCard;
	private String savedOtp;
	private Boolean isLoggedIn;
	private Boolean isVerified;
	private Integer otpAttept;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY ,mappedBy="cust")
	private List<Transaction_History>  trans_history = new ArrayList<>();

	public String getPanCard() {
		return panCard;
	}
	public void setPanCard(String panCard) {
		this.panCard = panCard;
	}
 
	public String getAtmPin() {
		return atmPin;
	}
	public void setAtmPin(String atmPin) {
		this.atmPin = atmPin;
	}
	public String getAtmNumber() {
		return atmNumber;
	}
	public void setAtmNumber(String atmNumber) {
		this.atmNumber = atmNumber;
	}

	public int getCid() {
		return Cid;
	}
	public void setCid(int cid) {
		Cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getcEmail() {
		return cEmail;
	}
	public void setcEmail(String cEmail) {
		this.cEmail = cEmail;
	}
	public String getcPhone() {
		return cPhone;
	}
	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}
	public String getcAadhar() {
		return cAadhar;
	}
	public void setcAadhar(String cAadhar) {
		this.cAadhar = cAadhar;
	}
	public String getcAccType() {
		return cAccType;
	}
	public void setcAccType(String cAccType) {
		this.cAccType = cAccType;
	}
	public String getcImage() {
		return cImage;
	}
	public void setcImage(String cImage) {
		this.cImage = cImage;
	}
	public String getcFathername() {
		return cFathername;
	}
	public void setcFathername(String cFathername) {
		this.cFathername = cFathername;
	}
	public String getcMothername() {
		return cMothername;
	}
	public void setcMothername(String cMothername) {
		this.cMothername = cMothername;
	}
	public String getcNomineename() {
		return cNomineename;
	}
	public void setcNomineename(String cNomineename) {
		this.cNomineename = cNomineename;
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAdharLinkStatus() {
		return adharLinkStatus;
	}
	public void setAdharLinkStatus(String adharLinkStatus) {
		this.adharLinkStatus = adharLinkStatus;
	}

	public String getNpcistatus() {
		return npcistatus;
	}
	public void setNpcistatus(String npcistatus) {
		this.npcistatus = npcistatus;
	}
	public String getEkycstatus() {
		return ekycstatus;
	}
	public void setEkycstatus(String ekycstatus) {
		this.ekycstatus = ekycstatus;
	}
	public List<Transaction_History> getTrans_history() {
		return trans_history;
	}
	public void setTrans_history(List<Transaction_History> trans_history) {
		this.trans_history = trans_history;
	}
 

	public String getSavedOtp() {
		return savedOtp;
	}
	public void setSavedOtp(String savedOtp) {
		this.savedOtp = savedOtp;
	}
	public String getConfiemPassword() {
		return confiemPassword;
	}
	public void setConfiemPassword(String confiemPassword) {
		this.confiemPassword = confiemPassword;
	}
	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}
	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public Boolean getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}
	public Integer getOtpAttept() {
		return otpAttept;
	}
	public void setOtpAttept(Integer otpAttept) {
		this.otpAttept = otpAttept;
	}
	
	public Customer() {
	}
	public Customer(int cid, String name, String cEmail, String cPhone, String cAadhar, String cAccType, String cImage,
			String cFathername, String cMothername, String cNomineename, String cpassword, String confiemPassword,
			String accountNo, String userId, String role, Date date, String balance, String adharLinkStatus,
			String npcistatus, String ekycstatus, String atmPin, String atmNumber, String panCard, String savedOtp,
			Boolean isLoggedIn, Boolean isVerified, Integer otpAttept, List<Transaction_History> trans_history) {
		Cid = cid;
		this.name = name;
		this.cEmail = cEmail;
		this.cPhone = cPhone;
		this.cAadhar = cAadhar;
		this.cAccType = cAccType;
		this.cImage = cImage;
		this.cFathername = cFathername;
		this.cMothername = cMothername;
		this.cNomineename = cNomineename;
		this.cpassword = cpassword;
		this.confiemPassword = confiemPassword;
		this.accountNo = accountNo;
		this.userId = userId;
		this.role = role;
		this.date = date;
		this.balance = balance;
		this.adharLinkStatus = adharLinkStatus;
		this.npcistatus = npcistatus;
		this.ekycstatus = ekycstatus;
		this.atmPin = atmPin;
		this.atmNumber = atmNumber;
		this.panCard = panCard;
		this.savedOtp = savedOtp;
		this.isLoggedIn = isLoggedIn;
		this.isVerified = isVerified;
		this.otpAttept = otpAttept;
		this.trans_history = trans_history;
	}
  

	

	
	

}
