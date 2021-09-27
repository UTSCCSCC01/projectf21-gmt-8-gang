package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.model.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId>{

}
