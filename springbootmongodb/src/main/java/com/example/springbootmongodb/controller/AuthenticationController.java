package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.model.AuthenticationRequest;
import com.example.springbootmongodb.model.AuthenticationResponse;
import com.example.springbootmongodb.model.Donor;
import com.example.springbootmongodb.repository.DonorRepository;
import com.example.springbootmongodb.service.DonorService;
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
    private DonorRepository donorRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DonorService donorService;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/register")
    private ResponseEntity<?> registerDonor(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String role = authenticationRequest.getRole();
        if(donorRepository.existsByUsername(username)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new AuthenticationResponse("DUPLICATE_USERNAME","Username already exists, failed to register"));
        }
        Donor donor = new Donor();
        donor.setUsername(username);
        donor.setPassword(password);
        donor.setRole(role);
        try {
            donorRepository.save(donor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("SAVE_ERROR","Error during client Authentication " + username));
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

        UserDetails loadedUser = donorService.loadUserByUsername(username);

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

        return ResponseEntity.ok(new AuthenticationResponse(role, generatedToken));


    }

    // getName is just for testing if jwt is working!
    @GetMapping("/getName")
    private String getName() {
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "name is: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
