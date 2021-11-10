package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.model.ConversionRequest;
import com.example.springbootmongodb.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionRequestRepository extends MongoRepository<ConversionRequest, String> {
//    ConversionRequest findConversionRequestById(String conversionRequestId);
    List<ConversionRequest> findConversionRequestByUsername(String username);
    List<ConversionRequest> findConversionRequestByUsernameAndIsDone(String username, Boolean isDone);
    Boolean existsByUsername(String username);
}
