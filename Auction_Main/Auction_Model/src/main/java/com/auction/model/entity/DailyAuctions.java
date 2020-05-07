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
@Table(name = "daily_auctions")
public class DailyAuctions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer dailyAuctionsId;
	private AccountProfile accountProfileByCancelledbyId;
	private AccountProfile accountProfileByCreatedbyId;
	private AccountProfile accountProfileByUpdatedbyId;
	private AuctionSettings auctionSettings;
	private AuctionStatusCodes auctionStatusCodes;
	private ProductCatalog productCatalog;
	private Date beginTime;
	private Date endTime;
	private Short auctionDuration;
	private Date createdOn;
	private Date updatedOn;
	private Date canceledOn;
	private Integer reserved1;
	private String reserved2;

	public DailyAuctions() {
	}

	public DailyAuctions(Integer dailyAuctionsId) {
		this.dailyAuctionsId = dailyAuctionsId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "daily_auctions_id", unique = true, nullable = false)
	public Integer getDailyAuctionsId() {
		return this.dailyAuctionsId;
	}

	public void setDailyAuctionsId(Integer dailyAuctionsId) {
		this.dailyAuctionsId = dailyAuctionsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cancelledby_id")
	public AccountProfile getAccountProfileByCancelledbyId() {
		return this.accountProfileByCancelledbyId;
	}

	public void setAccountProfileByCancelledbyId(AccountProfile accountProfileByCancelledbyId) {
		this.accountProfileByCancelledbyId = accountProfileByCancelledbyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdby_id", nullable = false)
	public AccountProfile getAccountProfileByCreatedbyId() {
		return this.accountProfileByCreatedbyId;
	}

	public void setAccountProfileByCreatedbyId(AccountProfile accountProfileByCreatedbyId) {
		this.accountProfileByCreatedbyId = accountProfileByCreatedbyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updatedby_id")
	public AccountProfile getAccountProfileByUpdatedbyId() {
		return this.accountProfileByUpdatedbyId;
	}

	public void setAccountProfileByUpdatedbyId(AccountProfile accountProfileByUpdatedbyId) {
		this.accountProfileByUpdatedbyId = accountProfileByUpdatedbyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_settings_id", nullable = false)
	public AuctionSettings getAuctionSettings() {
		return this.auctionSettings;
	}

	public void setAuctionSettings(AuctionSettings auctionSettings) {
		this.auctionSettings = auctionSettings;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_status_code", nullable = false)
	public AuctionStatusCodes getAuctionStatusCodes() {
		return this.auctionStatusCodes;
	}

	public void setAuctionStatusCodes(AuctionStatusCodes auctionStatusCodes) {
		this.auctionStatusCodes = auctionStatusCodes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	public ProductCatalog getProductCatalog() {
		return this.productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begin_time", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, length = 19)
	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "auction_duration", nullable = false, precision = 4)
	public Short getAuctionDuration() {
		return this.auctionDuration;
	}

	public void setAuctionDuration(Short auctionDuration) {
		this.auctionDuration = auctionDuration;
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
	@Column(name = "canceled_on", length = 19)
	public Date getCanceledOn() {
		return this.canceledOn;
	}

	public void setCanceledOn(Date canceledOn) {
		this.canceledOn = canceledOn;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2", length = 50)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

}