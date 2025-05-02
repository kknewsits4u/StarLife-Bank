package com.starLife.in.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starLife.in.Entity.Attachment;
import com.starLife.in.repository.AttachmentGlobal;

@Service
public class FileUploadService {

  @Autowired
  private AttachmentGlobal attachmentGlobal;

    public List<Attachment> getAllImages() {
        return attachmentGlobal.findAll();
    }
  
}
