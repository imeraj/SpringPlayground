package com.rest.restdemo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rest.restdemo.model.Account;
import com.rest.restdemo.repository.AccountRepository;

import static java.util.Collections.emptyList;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = accountRepository.findByUsername(username);
        
        if (user.isPresent()) {
        	return new User(user.get().getUsername(), user.get().getPassword(), emptyList());
        } else {
        	throw new UsernameNotFoundException(username);
        }
    }
}
        
