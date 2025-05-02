package com.starLife.in.ExceptionHandler;

public class OtpException extends Exception {
  public String message;
  public int statusCode;

  public OtpException(int statusCode , String message){
    super();
    this.statusCode = statusCode;
    this.message=message;
  }
}
