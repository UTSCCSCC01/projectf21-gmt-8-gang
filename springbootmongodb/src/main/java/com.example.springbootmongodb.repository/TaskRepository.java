package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Role, ObjectId>{

}
