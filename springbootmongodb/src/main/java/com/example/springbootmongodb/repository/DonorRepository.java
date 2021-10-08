package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.Donor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository extends MongoRepository<Donor, ObjectId> {
    Donor findByUsername(String username);
    Boolean existsByUsername(String username);
}