package com.rest.restdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.restdemo.model.Account;
import com.rest.restdemo.repository.AccountRepository;

@RestController
@RequestMapping("/users")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@PostMapping("/sign-up")
	public void signUp(@RequestBody Account user) {
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		accountRepository.save(user);
	}
}
