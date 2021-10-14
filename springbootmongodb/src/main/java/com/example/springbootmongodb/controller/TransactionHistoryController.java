package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.*;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.AccountRepository;
import com.example.springbootmongodb.repository.TransactionHistoryRepository;
import com.example.springbootmongodb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.springbootmongodb.utils.JwtUtils;
@RestController
public class TransactionHistoryController {
    private TransactionHistoryRepository transactionRepository;

    public TransactionHistoryController(TransactionHistoryRepository transactionRepository){
        this.transactionRepository=transactionRepository;
    }
    @PutMapping("/createTransaction")
    public void insertTransactionHistory(@RequestBody TransactionHistory transactionHistory){
        this.transactionRepository.insert(transactionHistory);
    }
}
