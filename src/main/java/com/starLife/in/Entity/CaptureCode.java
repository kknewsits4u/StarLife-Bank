package com.starLife.in.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="capturecodeTable")
public class CaptureCode {
  @Id
  private int captureNo;
  
  private String captureCode;

public int getCaptureNo() {
	return captureNo;
}

public void setCaptureNo(int captureNo) {
	this.captureNo = captureNo;
}

public String getCaptureCode() {
	return captureCode;
}

public void setCaptureCode(String captureCode) {
	this.captureCode = captureCode;
}

public CaptureCode(int captureNo, String captureCode) {
	super();
	this.captureNo = captureNo;
	this.captureCode = captureCode;
}

public CaptureCode() {
	super();
	
}

@Override
public String toString() {
	return "CaptureCode [captureNo=" + captureNo + ", captureCode=" + captureCode + "]";
}
  
  

}
