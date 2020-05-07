package com.auction.model.bean;

import com.auction.model.entity.NotificationCodes;

public class NotificationCodesBean {

	private Short notificationCode;
	private String notificationMeaning;
	
	
	public NotificationCodesBean() {
	}
	
	public NotificationCodesBean(NotificationCodes entity) {
		if(null != entity) {
			this.notificationCode = entity.getNotificationCode();
			this.notificationMeaning = entity.getNotificationMeaning();
		}
	}

	public NotificationCodesBean(Short notificationCode) {
		this.notificationCode = notificationCode;
	}

	public Short getNotificationCode() {
		return notificationCode;
	}

	public void setNotificationCode(Short notificationCode) {
		this.notificationCode = notificationCode;
	}

	public String getNotificationMeaning() {
		return notificationMeaning;
	}

	public void setNotificationMeaning(String notificationMeaning) {
		this.notificationMeaning = notificationMeaning;
	}
	
}
