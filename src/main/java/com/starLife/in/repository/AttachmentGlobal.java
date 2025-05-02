package com.starLife.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starLife.in.Entity.Attachment;

@Repository
public interface AttachmentGlobal  extends JpaRepository<Attachment, Integer> {
  
}
