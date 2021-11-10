package com.example.springbootmongodb.controller;


import com.example.springbootmongodb.model.ConversionRequest;
import com.example.springbootmongodb.repository.AccountRepository;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.ConversionRequestRepository;
import com.example.springbootmongodb.request.ConversionRequestRequest;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
public class ConversionRequestController {

    @Autowired
    private ConversionRequestRepository conversionRequestRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    // given email and amount, save a conversion request in db
    @PostMapping("/conversion-request")
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

    // get all conversion requests a specific logged-in merchant made
    @GetMapping("/conversion-requests/merchant")
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

    // get all pending conversion requests a specific logged-in merchant made
    @GetMapping("/pending/conversion-requests/merchant")
    private ResponseEntity<?> getAllPendingConversionRequests() {
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

    // update isDone of conversion request specified by ObjectId from false to true
    @PutMapping("/conversion-request")
    private ResponseEntity<?> updateConversionRequest(@RequestBody String request) {

        // making sure the user has admin access
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            if (!appUserRepository.findByUserName(username).getRole().equals("BEING_SEEN")) {
                System.out.println("you are not being seen so you have no access");
                return new ResponseEntity<>("you are not being seen so you have no access", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println("Exception on getting current user in AppUser");
            return new ResponseEntity<>("Exception on getting current user in AppUser", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // get conversion request from db, update, and save
        Optional<ConversionRequest> conversionRequest;
        try {
            JSONObject jsonItem = new JSONObject(request);
            String stringRequestId = jsonItem.getString("requestId");
            // convert to ObjectId from String
            ObjectId requestId = new ObjectId(stringRequestId);
            try {
                conversionRequest = conversionRequestRepository.findById(requestId);
                if (conversionRequest.isPresent()) {
                    if (conversionRequest.get().getIsDone() == true) {
                        System.out.println("this conversion request is already done");
                        return new ResponseEntity<>("this conversion request is already done", HttpStatus.NOT_FOUND);
                    }
                    ConversionRequest newConversionRequest = conversionRequest.get();
                    newConversionRequest.setIsDone(true);
                    conversionRequestRepository.save(newConversionRequest);
                } else {
                    System.out.println("can't find this conversion request");
                    return new ResponseEntity<>("can't find this conversion request", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                System.out.println("Exception on finding conversion request or saving it in repo");
                return new ResponseEntity<>("Exception on finding conversion request or saving it in repo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            System.out.println("successfully updated conversion request");
            return new ResponseEntity<>("successfully updated conversion request", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during this conversion request. Please check formatting", HttpStatus.BAD_REQUEST);
        }
    }

    // get all conversion requests regardless of username
    @GetMapping("/conversion-requests")
    private ResponseEntity<?> getConversionRequest() {
        List<ConversionRequest> conversionRequests = new ArrayList<>();
        try {
            conversionRequests = conversionRequestRepository.findAll();
            Collections.reverse(conversionRequests);
        } catch (Exception e) {
            return new ResponseEntity<>("Error on getting conversion requests", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(conversionRequests, HttpStatus.OK);
    }


}
