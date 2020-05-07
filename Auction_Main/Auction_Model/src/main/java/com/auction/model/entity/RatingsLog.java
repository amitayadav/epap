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
@Table(name = "ratings_log")
public class RatingsLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer logId;
	private AccountProfile accountProfileByRatorId;
	private AccountProfile accountProfileByRatedId;
	private DailyAuctions   dailyAuctions;
	private Short ratingValue;
	private String comments;
	private Date ratingTimestamp;

	public RatingsLog() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "log_id", unique = true, nullable = false)
	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rator_id", nullable = false)
	public AccountProfile getAccountProfileByRatorId() {
		return this.accountProfileByRatorId;
	}

	public void setAccountProfileByRatorId(AccountProfile accountProfileByRatorId) {
		this.accountProfileByRatorId = accountProfileByRatorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rated_id", nullable = false)
	public AccountProfile getAccountProfileByRatedId() {
		return this.accountProfileByRatedId;
	}

	public void setAccountProfileByRatedId(AccountProfile accountProfileByRatedId) {
		this.accountProfileByRatedId = accountProfileByRatedId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "daily_auctions_id", nullable = false)
	public DailyAuctions getDailyAuctions() {
		return dailyAuctions;
	}

	public void setDailyAuctions(DailyAuctions dailyAuctions) {
		this.dailyAuctions = dailyAuctions;
	}
	
	@Column(name = "rating_value", nullable = true)
	public Short getRatingValue() {
		return this.ratingValue;
	}

	public void setRatingValue(Short ratingValue) {
		this.ratingValue = ratingValue;
	}

	@Column(name = "comments", nullable = true)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rating_timestamp", nullable = false, length = 19)
	public Date getRatingTimestamp() {
		return this.ratingTimestamp;
	}

	public void setRatingTimestamp(Date ratingTimestamp) {
		this.ratingTimestamp = ratingTimestamp;
	}

}
