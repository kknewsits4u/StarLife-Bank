package com.starLife.in.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.starLife.in.Entity.Transaction_History;

@Repository
public interface Trans_historyRepository  extends JpaRepository<Transaction_History,Integer>{

  @Query("select t from  Transaction_History t WHERE  t.cust.Cid = :Cid AND t.trans_date BETWEEN :startDate AND :endDate ORDER BY t.trans_date DESC  ")
  public List<Transaction_History> findByCustomerCidAndTransDateBetween(@Param("Cid") int Cid, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


  
} 