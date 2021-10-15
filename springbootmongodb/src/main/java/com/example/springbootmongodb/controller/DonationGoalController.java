package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.*;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.DonationGoalRepository;
import com.example.springbootmongodb.request.DonationGoalRequest;
import com.example.springbootmongodb.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DonationGoalController  {

    @Autowired
    private DonationGoalRepository donationGoalRepos;
    @Autowired
    private AppUserRepository appUserRepos;

    // CRUDing donationGoal
    // Access: Homeless
    //   200: succeed
    //   400: cant find user / user has goal / user isn't homeless
    @PostMapping("/donationGoal")
    private ResponseEntity<?> createGoal(@RequestBody DonationGoalRequest goalRequest) {
        AppUser a;
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            a = appUserRepos.findByUserName(username);
        } catch (Exception e) {
            return new ResponseEntity<>("cant find this user", HttpStatus.BAD_REQUEST);
        }

        if (!a.getRole().equals("HOMELESS")) {
            return new ResponseEntity<>("ur not a homeless youth so you cannot create donation goals", HttpStatus.BAD_REQUEST);
        }

        
        String title = goalRequest.getTitle();
        String owner = a.getUserName();
        String description = goalRequest.getDescription();
        Long goal = goalRequest.getGoal();
        Long current = goalRequest.getCurrent();

        // check if there are duplicate usernames
        if(donationGoalRepos.existsByUsername(owner)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new AuthenticationResponse("USERNAME_HAS_GOAL","Username already has a donation goal." +
                            " failed to create new goal"));
        }

        // creating and saving donation goal for authentication
        try {
            DonationGoal dgoal = new DonationGoal();
            dgoal.setTitle(title);
            dgoal.setUsername(owner);
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
    // returns donation goal of one homeless given username
    @GetMapping("/donationGoal")
    private ResponseEntity<?> readGoal(@RequestBody DonationGoalRequest donationGoalRequest) {
        DonationGoal donationGoal;
        try {
            String username = donationGoalRequest.getUsername();
            donationGoal = donationGoalRepos.findByUsername(username);
        } catch (Exception e) {
            return new ResponseEntity<>("error on getting username / finding this dude", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(donationGoal, HttpStatus.OK);
    }

    // Access: the user him/herself
    // Updates donation goal (add the fields you want to update in the json object you're sending)
    @PutMapping("/donationGoal")
    private ResponseEntity<?> updateGoal(@RequestBody DonationGoalRequest goalRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DonationGoal donationGoal;
        try {
            donationGoal = donationGoalRepos.findByUsername(username);
        } catch (Exception e) {
            return new ResponseEntity<>("username not in donation goal repo", HttpStatus.NOT_FOUND);
        }


        if (goalRequest.getTitle() != null) {
            donationGoal.setTitle(goalRequest.getTitle());
        }
        if (goalRequest.getDescription() != null) {
            donationGoal.setDescription(goalRequest.getDescription());
        }
        if (goalRequest.getGoal() != null) {
            donationGoal.setGoal(goalRequest.getGoal());
        }
        if (goalRequest.getCurrent() != null) {
            donationGoal.setCurrent(goalRequest.getCurrent());
        }

        donationGoalRepos.save(donationGoal);
        return new ResponseEntity<>("donation goal saved!", HttpStatus.OK);
    }

    // Access: the user him/herself
    // deletes donation goal corresponding to the user
//    @DeleteMapping("/donationGoal")
//    private ResponseEntity<?> deleteGoal() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        DonationGoal donationGoal;
//        try {
//            donationGoal = donationGoalRepos.findByUsername(username);
//        } catch (Exception e) {
//            return new ResponseEntity<>("username not in donation goal repo", HttpStatus.NOT_FOUND);
//        }
//
//        donationGoalRepos
//
//    }

    // Access: All
    // returns all donation goals
    //   200: num of goals > 0
    //   404: num of goals == 0
    @GetMapping("/allDonationGoals")
    private ResponseEntity<?> readAllGoals() {
        List<DonationGoal> donationGoals = donationGoalRepos.findAll();
        if (donationGoals.size() == 0) {
            return new ResponseEntity<>("no donation goals available", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(donationGoals, HttpStatus.OK);
    }

}
