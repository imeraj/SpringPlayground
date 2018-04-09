package com.rest.restdemo.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rest.restdemo.model.Account;

@Service
public class NotificationService {
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	public void sendNotificatoin(Account account) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("meraj.enigma@gmail.com");
		mail.setFrom("noreply@restdemo.org");
		mail.setSubject("Bookmark App (Spring Boot)");
		mail.setText("Bookmark added for user - " + account.getUsername());
		javaMailSender.send(mail);
	}
	
}
