package com.rest.restdemo.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkCreationDTO {
	@NotNull
	private String uri;
	
	@NotNull
	private String description;
	
	@JsonIgnore
	private final LocalDateTime createdAt = LocalDateTime.now();
	
	@JsonIgnore
    private final LocalDateTime updatedAt = LocalDateTime.now();
}
