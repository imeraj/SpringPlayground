package com.rest.restdemo.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rest.restdemo.model.Account;

@Service
public class NotificationServiceImpl implements NotificationService {
	private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationServiceImpl(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	public void sendEmailNotification(Object payload) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("meraj.enigma@gmail.com");
		mail.setFrom("noreply@restdemo.org");
		mail.setSubject("Bookmark App (Spring Boot)");
		mail.setText("Bookmark added for user - " +  ((Account) payload).getUsername());
		javaMailSender.send(mail);
	}

	@Override
	public void sendNotification(NotificationData data) throws Exception {
		if (data.getType() ==  NotificationType.EMAIL) {
			try {
				logger.info("Sending email notification...");
				sendEmailNotification(data.getPayload());
			} catch  (Exception e) {
				logger.info("Exception caught " + e.getMessage());
			}
		}
	}
}
