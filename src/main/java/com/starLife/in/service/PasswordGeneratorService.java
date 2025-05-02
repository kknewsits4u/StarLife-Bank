package com.starLife.in.service;

import com.starLife.in.ExceptionHandler.PasswordGenerateException;

public interface PasswordGeneratorService {

  public String generate(String name , String phoneno, String fathername) throws PasswordGenerateException;
}
