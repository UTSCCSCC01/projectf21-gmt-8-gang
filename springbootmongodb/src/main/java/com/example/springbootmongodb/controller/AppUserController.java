package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.model.AppUserResponse;
import com.example.springbootmongodb.model.AuthenticationResponse;
import com.example.springbootmongodb.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepos;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser a = appUserRepos.findByUserName(username);
        return ResponseEntity.ok(new AppUserResponse(a.getUserName(), a.getRole(), a.getProfileInfo(), a.getBalance()));
    }

    @GetMapping("/balance")
    public Long getBalance() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = appUserRepos.findByUserName(username);
        return appUser.getBalance();
    }




}
