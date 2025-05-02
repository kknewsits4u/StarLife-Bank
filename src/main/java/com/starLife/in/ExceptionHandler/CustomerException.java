package com.starLife.in.ExceptionHandler;


public class CustomerException extends Exception {

  private int code ;
  private String message;
  
   public CustomerException(int code, String message){
    super();
    this.code = code;
    this.message = message;
  }

}
