package com.rest.restdemo.controller;

import com.rest.restdemo.model.Account;
import com.rest.restdemo.model.Bookmark;
import com.rest.restdemo.notification.NotificationConsumer;
import com.rest.restdemo.notification.NotificationData;
import com.rest.restdemo.notification.NotificationType;
import com.rest.restdemo.repository.BookmarkRepository;
import com.rest.restdemo.util.DTO;
import com.rest.restdemo.repository.AccountRepository;
import com.rest.restdemo.RestdemoApplication;
import com.rest.restdemo.DTO.BookmarkCreationDTO;
import com.rest.restdemo.exception.UserNotFoundException;

import static org.hamcrest.CoreMatchers.nullValue;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkController {
	private BookmarkRepository bookmarkRepository;
	private AccountRepository accountRepository;
	private Optional<Account> currentUser;
	
	@Autowired 
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public void BookMarkController(BookmarkRepository bookmarkRepository, 
						AccountRepository accountRepository) {
		this.bookmarkRepository = bookmarkRepository;
		this.accountRepository = accountRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<Bookmark> readBookmarks(@PathVariable String userId) {
		this.validateUser(userId);
		return this.bookmarkRepository.findByAccountUsername(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
	Optional<Bookmark> readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
		this.validateUser(userId);
		return this.bookmarkRepository.findById(bookmarkId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String userId, @DTO(BookmarkCreationDTO.class) Bookmark input) {
		this.validateUser(userId);
		return this.accountRepository
				.findByUsername(userId)
				.map(account -> {	
					String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					this.currentUser = this.accountRepository.findByUsername(userName);
						
					if (currentUser.get().getId() == account.getId()) {
						Account user = account;
						input.setAccount(account);
						Bookmark result = bookmarkRepository.save(input);
						
						URI location = ServletUriComponentsBuilder
								.fromCurrentRequest().path("/{id}")
								.buildAndExpand(result.getId()).toUri();

						NotificationData data = new NotificationData();
						data.setPayload(user);
						data.setType(NotificationType.EMAIL);
					
						rabbitTemplate.convertAndSend(RestdemoApplication.topicExchangeName, "event-bus", data);
					
						return ResponseEntity.created(location).build();
					} else {
						return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).build();
					}
				})
				.orElse(ResponseEntity.noContent().build());
	}
	
	private void validateUser(String userId) {
		this.accountRepository.findByUsername(userId).orElseThrow(
				() -> new UserNotFoundException(userId));
	}
}
