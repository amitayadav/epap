package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.Comments;

public class CommentsBean {

	private Integer commentId;
	private AccountProfileBean accountProfileBeanByAccountId;
	private AccountProfileBean accountProfileBeanByCreatedBy;
	private AccountProfileBean accountProfileBeanByUpdatedBy;
	private String commentText;
	private Date createdTimestamp;
	private String feedback;
	private Date updatedTimestamp;
	private String commentStatus;
	private String reserved;

	public CommentsBean() {
	}

	public CommentsBean(Comments entity) {
		if (null != entity) {
			this.commentId = entity.getCommentId();
			this.accountProfileBeanByAccountId = new AccountProfileBean(entity.getAccountProfileByAccountId());
			this.accountProfileBeanByCreatedBy = new AccountProfileBean(entity.getAccountProfileByCreatedBy());
			this.accountProfileBeanByUpdatedBy = new AccountProfileBean(entity.getAccountProfileByUpdatedBy());
			this.commentText = entity.getCommentText();
			this.createdTimestamp = entity.getCreatedTimestamp();
			this.feedback = entity.getFeedback();
			this.updatedTimestamp = entity.getUpdatedTimestamp();
			this.commentStatus = entity.getCommentStatus();
			this.reserved = entity.getReserved();
		}
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public AccountProfileBean getAccountProfileBeanByAccountId() {
		return accountProfileBeanByAccountId;
	}

	public void setAccountProfileBeanByAccountId(AccountProfileBean accountProfileBeanByAccountId) {
		this.accountProfileBeanByAccountId = accountProfileBeanByAccountId;
	}

	public AccountProfileBean getAccountProfileBeanByCreatedBy() {
		return accountProfileBeanByCreatedBy;
	}

	public void setAccountProfileBeanByCreatedBy(AccountProfileBean accountProfileBeanByCreatedBy) {
		this.accountProfileBeanByCreatedBy = accountProfileBeanByCreatedBy;
	}

	public AccountProfileBean getAccountProfileBeanByUpdatedBy() {
		return accountProfileBeanByUpdatedBy;
	}

	public void setAccountProfileBeanByUpdatedBy(AccountProfileBean accountProfileBeanByUpdatedBy) {
		this.accountProfileBeanByUpdatedBy = accountProfileBeanByUpdatedBy;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Date getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}