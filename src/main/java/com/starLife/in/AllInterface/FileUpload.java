package com.starLife.in.AllInterface;

import org.springframework.web.multipart.MultipartFile;

import com.starLife.in.Entity.Attachment;

public interface FileUpload {
 
  public Boolean uploadFile(MultipartFile file, Attachment attachment);
  
}
