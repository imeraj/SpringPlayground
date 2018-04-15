package com.rest.restdemo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Bookmark {
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Account account;
	
	private String uri;
	
	private String description;
	
    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;
	
	private Bookmark() { }
	
	public Bookmark(final Account account, final String uri, final String description) {
        this.uri = uri;
        this.description = description;
        this.account = account;
    }
}
