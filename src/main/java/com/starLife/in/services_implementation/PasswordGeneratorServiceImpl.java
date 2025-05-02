package com.starLife.in.services_implementation;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.starLife.in.ExceptionHandler.PasswordGenerateException;
import com.starLife.in.service.PasswordGeneratorService;


@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService{

  @Override
  public String generate(String name, String phoneno, String fathername) throws PasswordGenerateException {
  
    // generate random number ................

    Random rd = new Random();

    int num;
    int min = 1000;
    int max = 9999;
    num=rd.nextInt(max + min -1) + max ;
    // name substring.....

    String namePart1 =name.substring(0, 1).toUpperCase();
    String namePart2 =name.substring(1, 3);
    String phonePart = phoneno.substring(4, 7);
    String fathernamePart = fathername.substring(0,2);
    String namePart =namePart1+namePart2;

    String password = namePart+num+phonePart+fathernamePart;

    System.out.println(password);

    return password;
  }
  
}
