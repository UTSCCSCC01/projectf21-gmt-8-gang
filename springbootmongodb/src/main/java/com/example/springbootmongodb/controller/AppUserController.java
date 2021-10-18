package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.response.AppUserResponse;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.response.AuthenticationResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = appUserRepository.findByUserName(username);
        return ResponseEntity.ok(new AppUserResponse(appUser.getUserName(), appUser.getRole(), appUser.getProfileInfo(), appUser.getBalance()));
    }

    @GetMapping("/balance")
    public Long getBalance() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser appUser = appUserRepository.findByUserName(username);
        return appUser.getBalance();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfileInfo(@RequestBody String string) throws JSONException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            JSONObject jsonItem = new JSONObject(string);
            String profile = jsonItem.getString("profileInfo");
            try {
                AppUser appUser = appUserRepository.findByUserName(username);
                appUser.setProfileInfo(profile);
                appUserRepository.save(appUser);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponse("SAVE_ERROR", "Error during saving data for  " + username +
                                "might need to check ur formatting! (or backend code)"));
            }
            return ResponseEntity.ok(new AuthenticationResponse("SUCCESS", "Successful registration for " + username));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("SAVE_ERROR", "Error during receiving data for  " + username +
                            "might need to check ur formatting! (or backend code)"));
        }
    }

    @PutMapping("/balance")
    public ResponseEntity<?> updateBalance(@RequestBody String string) throws JSONException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            JSONObject jsonItem = new JSONObject(string);
            String balanceString = jsonItem.getString("balance");
            Long balance = Long.parseLong(balanceString);
            try {
                AppUser appUser = appUserRepository.findByUserName(username);
                appUser.setBalance(balance);
                appUserRepository.save(appUser);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthenticationResponse("SAVE_ERROR", "Error during saving data for  " + username +
                                "might need to check ur formatting! (or backend code)"));
            }
            return ResponseEntity.ok(new AuthenticationResponse("SUCCESS", "Successful registration for " + username));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("SAVE_ERROR", "Error during receiving data for  " + username +
                            "might need to check ur formatting! (or backend code)"));
        }
    }
}





