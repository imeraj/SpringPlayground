package com.rest.restdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.restdemo.model.Bookmark;

import java.util.Collection;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
