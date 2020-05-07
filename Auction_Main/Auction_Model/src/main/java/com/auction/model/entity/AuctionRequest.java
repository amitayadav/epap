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
@Table(name = "auction_request")
public class AuctionRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer requestId;
	private AccountProfile accountProfileByCreatedBy;
	private AccountProfile accountProfileByFeedbackBy;
	private AccountProfile accountProfileBySellerId;
	private AccountProfile accountProfileByUpdatedBy;
	private ProductCatalog productCatalog;
	private Short status;
	private String sellerComment;
	private String feedback;
	private Date createdOn;
	private Date updatedOn;
	private Date feedbackOn;

	public AuctionRequest() {
	}

	public AuctionRequest(Integer requestId) {
		this.requestId = requestId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "request_id", unique = true, nullable = false)
	public Integer getRequestId() {
		return this.requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
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
	@JoinColumn(name = "feedback_by")
	public AccountProfile getAccountProfileByFeedbackBy() {
		return this.accountProfileByFeedbackBy;
	}

	public void setAccountProfileByFeedbackBy(AccountProfile accountProfileByFeedbackBy) {
		this.accountProfileByFeedbackBy = accountProfileByFeedbackBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	public AccountProfile getAccountProfileBySellerId() {
		return this.accountProfileBySellerId;
	}

	public void setAccountProfileBySellerId(AccountProfile accountProfileBySellerId) {
		this.accountProfileBySellerId = accountProfileBySellerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	public AccountProfile getAccountProfileByUpdatedBy() {
		return this.accountProfileByUpdatedBy;
	}

	public void setAccountProfileByUpdatedBy(AccountProfile accountProfileByUpdatedBy) {
		this.accountProfileByUpdatedBy = accountProfileByUpdatedBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	public ProductCatalog getProductCatalog() {
		return this.productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	@Column(name = "status", nullable = false)
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "seller_comment", nullable = false)
	public String getSellerComment() {
		return this.sellerComment;
	}

	public void setSellerComment(String sellerComment) {
		this.sellerComment = sellerComment;
	}

	@Column(name = "feedback")
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "feedback_on", length = 19)
	public Date getFeedbackOn() {
		return this.feedbackOn;
	}

	public void setFeedbackOn(Date feedbackOn) {
		this.feedbackOn = feedbackOn;
	}

}
