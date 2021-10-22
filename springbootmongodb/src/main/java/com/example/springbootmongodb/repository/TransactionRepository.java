package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Transaction findTransactionById(String transactionId);
    List<Transaction> findTransactionByReceiver(String receiver);
    List<Transaction> findTransactionBySender(String sender);

}
