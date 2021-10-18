package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.AppUser;
import com.example.springbootmongodb.request.AuthenticationRequest;
import com.example.springbootmongodb.response.AuthenticationResponse;
import com.example.springbootmongodb.model.Account;
import com.example.springbootmongodb.repository.AppUserRepository;
import com.example.springbootmongodb.repository.AccountRepository;
import com.example.springbootmongodb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.springbootmongodb.utils.JwtUtils;

@RestController
public class AuthenticationController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/register")
    private ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String role = authenticationRequest.getRole();

        // check if there are duplicate usernames
        if(accountRepository.existsByUsername(username)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(new AuthenticationResponse("DUPLICATE_USERNAME","Username already exists, failed to register"));
        }

        // creating and saving donor(user) for authentication
        // also creating and saving AppUser for profiles
        try {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setRole(role);

            AppUser appUser = new AppUser();
            appUser.setUserName(username);
            appUser.setRole(role);
            appUser.setBalance(0L);
            appUser.setProfileInfo("");

            accountRepository.save(account);
            appUserRepository.save(appUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("SAVE_ERROR","Error during saving data for  " + username +
                            "might need to check ur formatting! (or backend code)"));
        }

        return ResponseEntity.ok(new AuthenticationResponse("SUCCESS","Successful registration for " + username));
    }

    @PostMapping("/auth")
    private ResponseEntity<?> authenticateDonor(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("AUTH_ERROR","Error during client Authentication " + username));
        }

        UserDetails loadedUser = accountService.loadUserByUsername(username);

        String role = "";
        if (loadedUser.getAuthorities().contains(new SimpleGrantedAuthority(("ROLE_DONOR")))) {
            role = "ROLE_DONOR";
        } else if (loadedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_HOMELESS"))) {
            role = "ROLE_HOMELESS";
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("ROLE_DNE","Error on Identifying role for " + username));
        }

        String generatedToken = jwtUtils.generateToken(loadedUser);

        return ResponseEntity.ok(new AuthenticationResponse(role, "Bearer " + generatedToken));
    }

    // getName is just for testing if jwt is working!
    @GetMapping("/getName")
    private String getName() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "name is: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
