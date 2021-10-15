package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.model.Transaction;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppUserRepository appUserRepository;


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
            appUser.setBalance(appUser.getBalance() - amount);

            //increase balance for receiver
            appUser = appUserRepository.findByUserName(receiver);
            appUser.setBalance(appUser.getBalance() + amount);

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
    public ResponseEntity<?> getTransactionBySender(@RequestBody String string) {
        Transaction transaction;
        try {
            JSONObject jsonItem = new JSONObject(string);
            String sender = jsonItem.getString("username");
            try {
                transaction = transactionRepository.findTransactionBySender(sender);
            } catch (Exception e) {
                return new ResponseEntity<>("Error during  finding transaction. Please check formatting", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during  finding transaction. Please check formatting", HttpStatus.BAD_REQUEST);
        }
    }

    // get transaction by receiver username
    @GetMapping("/transaction/receiver")
    public ResponseEntity<?> getTransactionByReceiver(@RequestBody String string) {
        Transaction transaction;
        try {
            JSONObject jsonItem = new JSONObject(string);
            String receiver = jsonItem.getString("username");
            try {
                transaction = transactionRepository.findTransactionByReceiver(receiver);
            } catch (Exception e) {
                return new ResponseEntity<>("Error during  finding transaction. Please check formatting", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during  finding transaction. Please check formatting", HttpStatus.BAD_REQUEST);
        }
    }
}

