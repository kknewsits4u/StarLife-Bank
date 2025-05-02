package com.starLife.in.services_implementation;

import org.springframework.stereotype.Service;

import com.starLife.in.service.captureService;

@Service
public class CaptureServiceImpl implements captureService {

  @Override
  public String digitt(int num, int[] arr, String str1, String str2, String str3) {
    
    if( num >= 0 && num <= 9){
        int i = arr[num];
        String str = Integer.toString(i);
//            System.out.print(a);
        return str;
    }
    if(num >= 10 && num <= 35){
        char  b = str1.charAt(num -10);
//            System.out.print(b);
        String str = String.valueOf(b);
        return str;
    }
    if(num >= 36 && num <= 45){
        int c = arr[num - 36];
//            System.out.print(c);
        String str = String.valueOf(c);
        return str;
    }
    if (num >= 46 && num <= 71) {
        char d = str2.charAt(num - 46);
//            System.out.print(d);
        String str = String.valueOf(d);
        return str;
    }
    if(num >= 72 && num <= 77){
        char e = str3.charAt(num - 72);
//            System.out.print(e);
        String str = String.valueOf(e);
        return str;
    }
    if(num >= 78 && num <= 87){
        int f = arr[num - 78];
        String str = Integer.toString(f);
//          System.out.print(a);
      return str;
    }
    
    return null;
};


  
  
}
