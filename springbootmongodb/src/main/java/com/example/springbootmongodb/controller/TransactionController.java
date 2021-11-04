package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.model.Transaction;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Transactional
    @PutMapping("/transaction")
    public ResponseEntity<?> insertTransaction(@RequestBody String string) {
        AppUser appUser;
        try {
            JSONObject jsonItem = new JSONObject(string);
            String sender = jsonItem.getString("sender");
            String receiver = jsonItem.getString("receiver");
            Long amount = Long.parseLong(jsonItem.getString("amount"));
            String comment = jsonItem.getString("comment");

            //decrease balance for sender
            appUser = appUserRepository.findByUserName(sender);
            Long currentAmount = appUser.getBalance();
//            if (currentAmount - amount < 0) {
//                return new ResponseEntity<>("money can't be less than 0", HttpStatus.NOT_FOUND);
//            }
            appUser.setBalance(currentAmount - amount);
            appUserRepository.save(appUser);

            //increase balance for receiver
            appUser = appUserRepository.findByUserName(receiver);
            currentAmount = appUser.getBalance();
            appUser.setBalance(currentAmount + amount);
            appUserRepository.save(appUser);

            //create transaction record
            Transaction transaction = new Transaction();
            transaction.setSender(sender);
            transaction.setReceiver(receiver);
            transaction.setAmount(amount);
            transaction.setComment(comment);
            transactionRepository.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during transaction. Please check formatting", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successful transaction made",HttpStatus.OK);
    }

    // get transaction by sender username
    @GetMapping("/transaction/sender")
    public ResponseEntity<?> getTransactionBySender(@RequestParam("username") String username) {
        Transaction transaction;
        List<?> transactions = new ArrayList<>();
        try {
            String sender = username;
            try {
                transactions = transactionRepository.findTransactionBySender(sender);
            } catch (Exception e) {
                return new ResponseEntity<>("Error during  finding transaction. Please check formatting", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during  finding transaction. Please check formatting", HttpStatus.BAD_REQUEST);
        }
    }

    // get transaction by receiver username
    @GetMapping("/transaction/receiver")
    public ResponseEntity<?> getTransactionByReceiver(@RequestParam("username") String username) {
        List<?> transactions = new ArrayList<>();
        try {
            String receiver = username;
            try {
                transactions = transactionRepository.findTransactionByReceiver(receiver);
            } catch (Exception e) {
                return new ResponseEntity<>("Error during  finding transaction. Transaction not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during  finding transaction. This is bad request.", HttpStatus.BAD_REQUEST);
        }
    }
}

