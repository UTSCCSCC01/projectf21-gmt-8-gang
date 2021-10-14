package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.DonationGoal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationGoalRepository extends MongoRepository<DonationGoal, String> {
    Boolean existsByOwner(String username);
    DonationGoal findByOwner(String username);
}