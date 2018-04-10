package com.rest.restdemo.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
	private final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);
	
	@Autowired
	private NotificationService notificationService;
	
    public void receiveMessage(NotificationData data) {
        try {
        	notificationService.sendNotification(data); 
        } catch (Exception e) {
        	logger.info("Exception caught: " + e.getMessage());
        }   
    }
}
