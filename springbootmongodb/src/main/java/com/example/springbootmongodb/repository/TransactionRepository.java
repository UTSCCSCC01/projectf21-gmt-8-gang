package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Transaction findTransactionById(String transactionId);
    Transaction findTransactionBySender(String sender);
    Transaction findTransactionByReceiver(String receiver);

}
