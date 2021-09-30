package com.example.springbootmongodb.controller;

import java.util.List;

import com.example.springbootmongodb.repository.TaskRepository;
import com.example.springbootmongodb.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Part of API code was inspired from source code here: https://www.youtube.com/watch?v=GoXgh5bo3jY&list=PLA7e3zmT6XQUjrwAoOHvNu80Axuf-3jft&index=2
// Title: Spring boot and MongoDB
// Author: Bushan SC
// Date: Sep 26th, 2021
@RestController
public class TaskController {

    @Autowired
    private TaskRepository taskRepos;

    @GetMapping("/test")
    public ResponseEntity<?> getAllTasks() {
        List<Role> tasks =  taskRepos.findAll();
        System.out.println(tasks);

        if (tasks.size() >= 0) {
            return new ResponseEntity<List<Role>>(tasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("\"Resource not found\"", HttpStatus.NOT_FOUND);
        }
    }
}
