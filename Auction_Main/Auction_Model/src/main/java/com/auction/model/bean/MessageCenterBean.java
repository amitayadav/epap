package com.auction.model.bean;

import java.sql.Blob;
import java.util.Date;

import com.auction.model.entity.MessageCenter;

public class MessageCenterBean {

	private Integer messageId;
	private AccountProfileBean accountProfileByFromAccountId;
	private AccountProfileBean accountProfileByToAccountId;
	private String message;
	private Date messageTimestamp;
	private Blob attachment;
	private String attachmentType;
	private String reserved;

	public MessageCenterBean() {

	}

	public MessageCenterBean(Integer messageId) {
		this.messageId = messageId;
	}

	public MessageCenterBean(MessageCenter entity) {
		if (null != entity) {
			this.messageId = entity.getMessageId();
			this.accountProfileByFromAccountId = new AccountProfileBean(entity.getAccountProfileByFromAccountId());
			this.accountProfileByToAccountId = new AccountProfileBean(entity.getAccountProfileByToAccountId());
			this.message = entity.getMessage();
			this.messageTimestamp = entity.getMessageTimestamp();
			this.attachment = entity.getAttachment();
			this.attachmentType = entity.getAttachmentType();
			this.reserved = entity.getReserved();
		}
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public AccountProfileBean getAccountProfileByFromAccountId() {
		return accountProfileByFromAccountId;
	}

	public void setAccountProfileByFromAccountId(AccountProfileBean accountProfileByFromAccountId) {
		this.accountProfileByFromAccountId = accountProfileByFromAccountId;
	}

	public AccountProfileBean getAccountProfileByToAccountId() {
		return accountProfileByToAccountId;
	}

	public void setAccountProfileByToAccountId(AccountProfileBean accountProfileByToAccountId) {
		this.accountProfileByToAccountId = accountProfileByToAccountId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageTimestamp() {
		return messageTimestamp;
	}

	public void setMessageTimestamp(Date messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}

	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
