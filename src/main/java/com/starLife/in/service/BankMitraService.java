package com.starLife.in.service;

import com.starLife.in.Entity.BankMitra;

public interface BankMitraService {

// method for find bank mitra by id 

public BankMitra findBankMitraById(int id);

// method for find bank mitra by jwt
public BankMitra findBankMitraByJwt(String jwt);

// method for find bank mitra by email
public BankMitra findBankMitraByEmail(String email);
}
