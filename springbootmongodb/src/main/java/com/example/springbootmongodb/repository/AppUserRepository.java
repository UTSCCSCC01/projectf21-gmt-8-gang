package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
    AppUser findByUserName(String userName);
}
