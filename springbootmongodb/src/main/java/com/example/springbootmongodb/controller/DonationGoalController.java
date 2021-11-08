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
            System.out.println("cant find this user");
            return new ResponseEntity<>("cant find this user", HttpStatus.BAD_REQUEST);
        }

        if (!a.getRole().equals("HOMELESS")) {
            System.out.println("ur not a homeless youth so you cannot create donation goals");
            return new ResponseEntity<>("ur not a homeless youth so you cannot create donation goals", HttpStatus.BAD_REQUEST);
        }

        
        String title = goalRequest.getTitle();
        String owner = a.getUserName();
        String description = goalRequest.getDescription();
        Long goal = goalRequest.getGoal();
        Long current = goalRequest.getCurrent();

        // check if there are duplicate usernames
        if(donationGoalRepos.existsByUsername(owner)){
            System.out.println("Username already has a donation goal." +
                    " failed to create new goal");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
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
            System.out.println("Error during saving data for  " + owner +
                    "might need to check ur formatting! (or backend code)");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("SAVE_ERROR","Error during saving data for  " + owner +
                            "might need to check ur formatting! (or backend code)"));
        }
        System.out.println("created donation goal for " + owner);
        return ResponseEntity.ok(new AuthenticationResponse("SUCCESS","Successful goal creation for "
                + owner));
    }

    // Access: hy
    // returns donation goal of one homeless given username
    @GetMapping("/donationGoal")
    private ResponseEntity<?> readGoal() {
        DonationGoal donationGoal;
        try {
            System.out.println("start donation goal");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!donationGoalRepos.existsByUsername(username)) {
                return new ResponseEntity<>("can't get " + username + "bc this username doesn't exist", HttpStatus.NOT_FOUND);
            }
            donationGoal = donationGoalRepos.findByUsername(username);
        } catch (Exception e) {
            return new ResponseEntity<>("something happend when getting this user" , HttpStatus.BAD_REQUEST);
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
    @DeleteMapping("/donationGoal")
    private ResponseEntity<?> deleteGoal() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!donationGoalRepos.existsByUsername(username)) {
            System.out.println("username doesn't have a donation goal so can't delete");
            return new ResponseEntity<>("username doesn't exist", HttpStatus.NOT_FOUND);
        }
        try {
            donationGoalRepos.deleteByUsername(username);
            System.out.println("deleted donation goal");
        } catch (Exception e) {
            return new ResponseEntity<>("something wrong with delete qwq", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("deleted!", HttpStatus.OK);
    }

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
