package com.auction.model.entity;

import java.sql.Blob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "message_center")
public class MessageCenter implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer messageId;
	private AccountProfile accountProfileByFromAccountId;
	private AccountProfile accountProfileByToAccountId;
	private String message;
	private Date messageTimestamp;
	private Blob attachment;
	private String attachmentType;
	private String reserved;

	public MessageCenter() {
	}

	public MessageCenter(AccountProfile accountProfileByFromAccountId, AccountProfile accountProfileByToAccountId, String message, Date messageTimestamp, Blob attachment) {
		this.accountProfileByFromAccountId = accountProfileByFromAccountId;
		this.accountProfileByToAccountId = accountProfileByToAccountId;
		this.message = message;
		this.messageTimestamp = messageTimestamp;
		this.attachment = attachment;
	}

	public MessageCenter(AccountProfile accountProfileByFromAccountId, AccountProfile accountProfileByToAccountId, String message, Date messageTimestamp, String attachmentType,
			String reserved, Blob attachment) {
		this.accountProfileByFromAccountId = accountProfileByFromAccountId;
		this.accountProfileByToAccountId = accountProfileByToAccountId;
		this.message = message;
		this.messageTimestamp = messageTimestamp;
		this.attachmentType = attachmentType;
		this.attachment = attachment;
		this.reserved = reserved;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "message_id", unique = true, nullable = false)
	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_account_id", nullable = false)
	public AccountProfile getAccountProfileByFromAccountId() {
		return this.accountProfileByFromAccountId;
	}

	public void setAccountProfileByFromAccountId(AccountProfile accountProfileByFromAccountId) {
		this.accountProfileByFromAccountId = accountProfileByFromAccountId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_account_id", nullable = false)
	public AccountProfile getAccountProfileByToAccountId() {
		return this.accountProfileByToAccountId;
	}

	public void setAccountProfileByToAccountId(AccountProfile accountProfileByToAccountId) {
		this.accountProfileByToAccountId = accountProfileByToAccountId;
	}

	@Column(name = "message", nullable = false)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "message_timestamp", nullable = false, length = 19)
	public Date getMessageTimestamp() {
		return this.messageTimestamp;
	}

	public void setMessageTimestamp(Date messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}

	@Column(name = "attachment")
	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}

	@Column(name = "attachment_type", length = 100)
	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	@Column(name = "reserved", length = 20)
	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
