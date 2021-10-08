package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    Account findByUsername(String username);
    Boolean existsByUsername(String username);
}