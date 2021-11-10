package com.example.springbootmongodb.controller;


import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.model.ConversionRequest;
import com.example.springbootmongodb.repository.AccountRepository;
import com.example.springbootmongodb.repository.ConversionRequestRepository;
import com.example.springbootmongodb.request.ConversionRequestRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class ConversionRequestController {

    @Autowired
    private ConversionRequestRepository conversionRequestRepository;

    @Autowired
    private AccountRepository accountRepository;

    // given email and amount, save a conversion request in db
    @PostMapping("/conversionRequest")
    private ResponseEntity<?> createConversionRequest(@RequestBody ConversionRequestRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!accountRepository.existsByUsername(username)) {
            System.out.println("username not registered");
            return new ResponseEntity<>("username not registered", HttpStatus.BAD_REQUEST);
        }

        String email = request.getEmail();
        Long amount = request.getAmount();

        try {
            ConversionRequest a = new ConversionRequest(username, email, amount, false);
            conversionRequestRepository.save(a);
        } catch (Exception e) {
            System.out.println("error on saving conversion request");
            return new ResponseEntity<>("error on saving conversion request", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        System.out.println("successfully created conversion request");
        return new ResponseEntity<>("successfully created conversion request", HttpStatus.OK);

    }

    @GetMapping("/allConversionRequests")
    private ResponseEntity<?> getAllConversionRequests(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<?> conversionRequests = new ArrayList<>();
        try {
            conversionRequests = conversionRequestRepository.findConversionRequestByUsername(username);
            Collections.reverse(conversionRequests);
        } catch (Exception e) {
            System.out.println("error on getting list of conversion request");
            return new ResponseEntity<>("error on getting list of conversion request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("successfully get conversion requests");
        return new ResponseEntity<>(conversionRequests, HttpStatus.OK);
    }

    @GetMapping("/allPendingConversionRequests")
    private ResponseEntity<?> getAllPendingConversionRequests(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<?> conversionRequests = new ArrayList<>();
        try {
            conversionRequests = conversionRequestRepository.findConversionRequestByUsernameAndIsDone(username, false);
            Collections.reverse(conversionRequests);
        } catch (Exception e) {
            System.out.println("error on getting list of conversion request");
            return new ResponseEntity<>("error on getting list of conversion request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("successfully get conversion requests");
        return new ResponseEntity<>(conversionRequests, HttpStatus.OK);
    }
}
