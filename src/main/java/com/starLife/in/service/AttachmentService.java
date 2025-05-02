package com.starLife.in.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;



// public interface AttachmentService {

//  public Attachment saveAttachment(MultipartFile file) throws Exception;

//  public byte[] getAttachment(MultipartFile file) throws Exception;
  

// }


@Service
public class AttachmentService {

    @Value("${image.upload.dir}") // Define your upload directory in `application.properties`
    private String uploadDir;

    public Resource getImageFile(String fileName) throws IOException {

        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        
        return new UrlResource(filePath.toUri());
    }
}

