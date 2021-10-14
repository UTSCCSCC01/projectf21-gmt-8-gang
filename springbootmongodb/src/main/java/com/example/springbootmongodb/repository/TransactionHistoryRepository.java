package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.TransactionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, String> {
    TransactionHistory findHistoryById(String transactionId);
    List<TransactionHistory> findHistoryBySender(String sender);
    List<TransactionHistory> findHistoryByReceiver(String receiver);

}
