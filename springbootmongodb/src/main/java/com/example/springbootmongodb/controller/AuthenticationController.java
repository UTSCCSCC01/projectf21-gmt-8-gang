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
import org.springframework.security.core.userdetails.UserDetails;
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
        Donor donor = new Donor();
        donor.setUsername(username);
        donor.setPassword(password);
        donor.setRole(role);
        try {
            donorRepository.save(donor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("Error during client Authentication " + username));
        }
        return ResponseEntity.ok(new AuthenticationResponse("Successful registration for " + username));
    }

    @PostMapping("/auth")
    private ResponseEntity<?> authenticateDonor(@RequestBody AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        String role = authenticationRequest.getRole();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("Error during client Authentication " + username));
        }

        UserDetails loadedUser = donorService.loadUserByUsername(username);

        if (!loadedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + role))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthenticationResponse("Error during client Authentication " + username));
        }

        String generatedToken = jwtUtils.generateToken(loadedUser);

        return ResponseEntity.ok(new AuthenticationResponse("Successful! Token:"+generatedToken));


    }

}
