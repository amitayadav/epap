package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "notification_codes", uniqueConstraints = @UniqueConstraint(columnNames = "notification_meaning"))
public class NotificationCodes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Short notificationCode;
	private String notificationMeaning;

	public NotificationCodes() {
	}

	public NotificationCodes(Short notificationCode) {
		this.notificationCode = notificationCode;
	}

	public NotificationCodes(String notificationMeaning) {
		this.notificationMeaning = notificationMeaning;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "notification_code", unique = true, nullable = false)
	public Short getNotificationCode() {
		return this.notificationCode;
	}

	public void setNotificationCode(Short notificationCode) {
		this.notificationCode = notificationCode;
	}

	@Column(name = "notification_meaning", unique = true, nullable = false, length = 10)
	public String getNotificationMeaning() {
		return this.notificationMeaning;
	}

	public void setNotificationMeaning(String notificationMeaning) {
		this.notificationMeaning = notificationMeaning;
	}

}