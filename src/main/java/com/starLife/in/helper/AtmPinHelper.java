package com.starLife.in.helper;

import org.springframework.stereotype.Component;

@Component
public class AtmPinHelper {

   private String atmpin1;
   private String atmpin2;
   private String atmpin3;
   private String atmpin4;

   private String newPin;
   private String ConfirmPin;
   

   public String getNewPin() {
      return newPin;
   }

   public void setNewPin(String newPin) {
      this.newPin = newPin;
   }

   public String getConfirmPin() {
      return ConfirmPin;
   }

   public void setConfirmPin(String confirmPin) {
      ConfirmPin = confirmPin;
   }

   public String getAtmpin1() {
      return atmpin1;
   }

   public void setAtmpin1(String atmpin1) {
      this.atmpin1 = atmpin1;
   }

   public String getAtmpin2() {
      return atmpin2;
   }

   public void setAtmpin2(String atmpin2) {
      this.atmpin2 = atmpin2;
   }

   public String getAtmpin3() {
      return atmpin3;
   }

   public void setAtmpin3(String atmpin3) {
      this.atmpin3 = atmpin3;
   }

   public String getAtmpin4() {
      return atmpin4;
   }

   public void setAtmpin4(String atmpin4) {
      this.atmpin4 = atmpin4;
   }


}
