package com.example.springbootmongodb.service;

import com.example.springbootmongodb.model.Account;
import com.example.springbootmongodb.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account foundedAccount = accountRepository.findByUsername(username);
        if (foundedAccount == null)
            return null;

        String name = foundedAccount.getUsername();
        String password = foundedAccount.getPassword();
        String role = foundedAccount.getRole();

        ArrayList<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
        roleList.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new User(name, password, roleList);
    }
}
