package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comments")
public class Comments implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer commentId;
	private AccountProfile accountProfileByAccountId;
	private AccountProfile accountProfileByCreatedBy;
	private AccountProfile accountProfileByUpdatedBy;
	private String commentText;
	private Date createdTimestamp;
	private String feedback;
	private Date updatedTimestamp;
	private String commentStatus;
	private String reserved;

	public Comments() {
	}

	public Comments(Integer commentId) {
		this.commentId = commentId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "comment_id", unique = true, nullable = false)
	public Integer getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_account_id", nullable = false)
	public AccountProfile getAccountProfileByAccountId() {
		return this.accountProfileByAccountId;
	}

	public void setAccountProfileByAccountId(AccountProfile accountProfileByAccountId) {
		this.accountProfileByAccountId = accountProfileByAccountId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	public AccountProfile getAccountProfileByCreatedBy() {
		return this.accountProfileByCreatedBy;
	}

	public void setAccountProfileByCreatedBy(AccountProfile accountProfileByCreatedBy) {
		this.accountProfileByCreatedBy = accountProfileByCreatedBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	public AccountProfile getAccountProfileByUpdatedBy() {
		return this.accountProfileByUpdatedBy;
	}

	public void setAccountProfileByUpdatedBy(AccountProfile accountProfileByUpdatedBy) {
		this.accountProfileByUpdatedBy = accountProfileByUpdatedBy;
	}

	@Column(name = "comment_text", nullable = false)
	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_timestamp", nullable = false, length = 19)
	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Column(name = "feedback")
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_timestamp", length = 19)
	public Date getUpdatedTimestamp() {
		return this.updatedTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	@Column(name = "comment_status", nullable = false, length = 15)
	public String getCommentStatus() {
		return this.commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	@Column(name = "reserved", length = 50)
	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
