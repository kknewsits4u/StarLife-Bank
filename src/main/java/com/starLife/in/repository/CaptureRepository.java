package com.starLife.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.starLife.in.Entity.CaptureCode;

public interface CaptureRepository extends JpaRepository<CaptureCode,Integer>{
   
	@Query("select ca from CaptureCode ca WHERE ca.captureNo= :captureNo")
	public CaptureCode findByCaptureno(@Param("captureNo") int captureNo);
}
