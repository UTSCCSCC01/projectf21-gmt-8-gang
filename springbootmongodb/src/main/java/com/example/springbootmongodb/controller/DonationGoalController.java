package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.*;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.DonationGoalRepository;
import com.example.springbootmongodb.request.AuthenticationRequest;
import com.example.springbootmongodb.request.DonationGoalRequest;
import com.example.springbootmongodb.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DonationGoalController  {

    @Autowired
    private DonationGoalRepository donationGoalRepos;
    @Autowired
    private AppUserRepository appUserRepos;

    @PostMapping("/donationGoal")
    private ResponseEntity<?> getGoalInfo(@RequestBody DonationGoalRequest goalRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser a = appUserRepos.findByUserName(username);
        
        String title = goalRequest.getTitle();
        String owner = a.getUserName();
        String description = goalRequest.getDescription();
        Long goal = goalRequest.getGoal();
        Long current = goalRequest.getCurrent();

        // check if there are duplicate usernames
        if(donationGoalRepos.existsByOwner(owner)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new AuthenticationResponse("USERNAME_HAS_GOAL","Username already has a donation goal." +
                            " failed to create new goal"));
        }

        // creating and saving donation goal for authentication
        try {
            DonationGoal dgoal = new DonationGoal();
            dgoal.setTitle(title);
            dgoal.setOwner(owner);
            dgoal.setDescription(description);
            dgoal.setGoal(goal);
            dgoal.setCurrent(current);

            donationGoalRepos.save(dgoal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("SAVE_ERROR","Error during saving data for  " + owner +
                            "might need to check ur formatting! (or backend code)"));
        }

        return ResponseEntity.ok(new AuthenticationResponse("SUCCESS","Successful goal creation for "
                + owner));
    }

    // Access: All
    // returns all donation goals
    //   200: num of goals > 0
    //   404: num of goals == 0
    @GetMapping("/allDonationGoals")
    private ResponseEntity<?> getAllGoals() {
        List<DonationGoal> donationGoals = donationGoalRepos.findAll();
        if (donationGoals.size() == 0) {
            return new ResponseEntity<>("no donation goals available", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(donationGoals, HttpStatus.OK);
    }

    // Access: All
    // returns donation goal of one homeless given username
    @GetMapping("/donationGoal")
    private ResponseEntity<?> getGoal(@RequestBody AuthenticationRequest authenticationRequest) {
        DonationGoal donationGoal;
        try {
            // we only need username, other fields ignored
            String username = authenticationRequest.getUsername();
            donationGoal = donationGoalRepos.findByOwner(username);
        } catch (Exception e) {
            return new ResponseEntity<>("error on getting username / finding this dude", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(donationGoal, HttpStatus.OK);
    }


}
