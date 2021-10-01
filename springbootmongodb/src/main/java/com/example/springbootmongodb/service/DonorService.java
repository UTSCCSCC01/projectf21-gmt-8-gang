package com.example.springbootmongodb.service;

import com.example.springbootmongodb.model.Donor;
import com.example.springbootmongodb.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DonorService implements UserDetailsService {
    @Autowired
    private DonorRepository donorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Donor foundedDonor = donorRepository.findByUsername(username);
        if (foundedDonor == null)
            return null;

        String name = foundedDonor.getUsername();
        String password = foundedDonor.getPassword();

        return new User(name, password, new ArrayList<>());
    }
}
