package com.rest.restdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.restdemo.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
