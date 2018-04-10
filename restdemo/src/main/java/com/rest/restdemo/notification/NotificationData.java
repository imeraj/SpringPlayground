package com.rest.restdemo.notification;

import java.io.Serializable;

public class NotificationData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object payload;
	private NotificationType type;
	
	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}
}
