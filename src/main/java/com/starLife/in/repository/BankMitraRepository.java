package com.starLife.in.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.starLife.in.Entity.BankMitra;

@Repository
public interface BankMitraRepository extends JpaRepository<BankMitra , Integer> {


 @Query("select a from BankMitra as a WHERE a.email=:email")
 public BankMitra findByEmail(@Param("email")  String email);


}
