package com.starLife.in.ExceptionHandler;

public class AdminException extends Exception {

  private int code ;
  private String message;
  
   public AdminException(int code, String message){
    super();
    this.code = code;
    this.message = message;
  }

}
