package com.rest.restdemo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Account implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	
//	@OneToMany(mappedBy = "account")
//	private Set<Bookmark> bookmarks = new HashSet<>();
//	
	private Account() {}
	
	public Account(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
