package com.starLife.in.ExceptionHandler;

public class PasswordGenerateException extends Exception {
  
  public int statusCode;
  public String message;

  public PasswordGenerateException(int statusCode, String message){
    super();
    this.statusCode=statusCode;
    this.message=message;
  }


}
