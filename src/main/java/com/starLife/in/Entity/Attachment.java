package com.starLife.in.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Attachment{
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private int Aid;


    private String filename;
    private String duration;
    private String detail;
    private String user;

    
    public int getAid() {
      return Aid;
    }
    public void setAid(int aid) {
      Aid = aid;
    }
    public String getFilename() {
      return filename;
    }
    public void setFilename(String filename) {
      this.filename = filename;
    }
    public String getDuration() {
      return duration;
    }
    public void setDuration(String duration) {
      this.duration = duration;
    }
    public String getDetail() {
      return detail;
    }
    public void setDetail(String detail) {
      this.detail = detail;
    }
    public String getUser() {
      return user;
    }
    public void setUser(String user) {
      this.user = user;
    }
    public Attachment(int aid, String filename, String duration, String detail, String user) {
      Aid = aid;
      this.filename = filename;
      this.duration = duration;
      this.detail = detail;
      this.user = user;
    }
    public Attachment() {
    }
    @Override
    public String toString() {
      return "Attachment [Aid=" + Aid + ", filename=" + filename + ", duration=" + duration + ", detail=" + detail
          + ", user=" + user + "]";
    }



    
    
}