package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.AuctionRequest;

public class AuctionRequestBean {

	private Integer requestId;
	private AccountProfileBean accountProfileByCreatedBy;
	private AccountProfileBean accountProfileByFeedbackBy;
	private AccountProfileBean accountProfileBySellerId;
	private AccountProfileBean accountProfileByUpdatedBy;
	private ProductCatalogBean productCatalogBean;
	private Short status;
	private String sellerComment;
	private String feedback;
	private Date createdOn;
	private Date updatedOn;
	private Date feedbackOn;

	public AuctionRequestBean() {
	}

	public AuctionRequestBean(AuctionRequest entity) {

		if (null != entity) {
			this.requestId = entity.getRequestId();
			this.accountProfileByCreatedBy = new AccountProfileBean(entity.getAccountProfileByCreatedBy());
			this.accountProfileByFeedbackBy = new AccountProfileBean(entity.getAccountProfileByFeedbackBy());
			this.accountProfileBySellerId = new AccountProfileBean(entity.getAccountProfileBySellerId());
			this.accountProfileByUpdatedBy = new AccountProfileBean(entity.getAccountProfileByUpdatedBy());
			this.productCatalogBean = new ProductCatalogBean(entity.getProductCatalog());
			this.status = entity.getStatus();
			this.sellerComment = entity.getSellerComment();
			this.feedback = entity.getFeedback();
			this.createdOn = entity.getCreatedOn();
			this.updatedOn = entity.getUpdatedOn();
			this.feedbackOn = entity.getFeedbackOn();
		}

	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public AccountProfileBean getAccountProfileByCreatedBy() {
		return accountProfileByCreatedBy;
	}

	public void setAccountProfileByCreatedBy(AccountProfileBean accountProfileByCreatedBy) {
		this.accountProfileByCreatedBy = accountProfileByCreatedBy;
	}

	public AccountProfileBean getAccountProfileByFeedbackBy() {
		return accountProfileByFeedbackBy;
	}

	public void setAccountProfileByFeedbackBy(AccountProfileBean accountProfileByFeedbackBy) {
		this.accountProfileByFeedbackBy = accountProfileByFeedbackBy;
	}

	public AccountProfileBean getAccountProfileBySellerId() {
		return accountProfileBySellerId;
	}

	public void setAccountProfileBySellerId(AccountProfileBean accountProfileBySellerId) {
		this.accountProfileBySellerId = accountProfileBySellerId;
	}

	public AccountProfileBean getAccountProfileByUpdatedBy() {
		return accountProfileByUpdatedBy;
	}

	public void setAccountProfileByUpdatedBy(AccountProfileBean accountProfileByUpdatedBy) {
		this.accountProfileByUpdatedBy = accountProfileByUpdatedBy;
	}

	public ProductCatalogBean getProductCatalogBean() {
		return productCatalogBean;
	}

	public void setProductCatalogBean(ProductCatalogBean productCatalogBean) {
		this.productCatalogBean = productCatalogBean;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getSellerComment() {
		return sellerComment;
	}

	public void setSellerComment(String sellerComment) {
		this.sellerComment = sellerComment;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Date getFeedbackOn() {
		return feedbackOn;
	}

	public void setFeedbackOn(Date feedbackOn) {
		this.feedbackOn = feedbackOn;
	}

}
