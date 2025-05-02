package com.starLife.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.starLife.in.Entity.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin , Integer> {


 @Query("select a from Admin as a WHERE a.email=:email")
 public Admin findByEmail(@Param("email")  String email);


}