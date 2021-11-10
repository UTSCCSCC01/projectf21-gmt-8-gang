package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.ConversionRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversionRequestRepository extends MongoRepository<ConversionRequest, String> {
//    ConversionRequest findConversionRequestById(String conversionRequestId);
    List<ConversionRequest> findConversionRequestByUsername(String username);
//    Optional<ConversionRequest> findById(String id);
    List<ConversionRequest> findConversionRequestByUsernameAndIsDone(String username, Boolean isDone);
    Boolean existsByUsername(String username);

    Optional<ConversionRequest> findById(ObjectId objectId);
}
