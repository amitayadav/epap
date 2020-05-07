package com.auction.model.bean;

import java.util.Date;
import com.auction.model.entity.RatingsLog;
public class RatingsLogBean {
	
	private Integer logId;
	private AccountProfileBean accountProfileByRatorId;
	private AccountProfileBean accountProfileByRatedId;
	private DailyAuctionsBean   dailyAuctionsBean;
	private Short ratingValue;
	private String comments;
	private Date ratingTimestamp;
	private Float rating;
	private Integer numOfRatings;
	private Integer ratingTotal;

public RatingsLogBean() {
		
	}
	
public RatingsLogBean(float rating,int ratingTotal, int numOfRatings) {
		this.rating=rating;
		this.ratingTotal=ratingTotal;
		this.numOfRatings=numOfRatings;
	
	}

public RatingsLogBean(int logId) {
		this.logId =logId;
	}
	
	
public RatingsLogBean(RatingsLog entity) {
	if (null != entity) {
		this.logId =entity.getLogId();
		this.accountProfileByRatorId = new AccountProfileBean(entity.getAccountProfileByRatorId());
		this.accountProfileByRatorId = new AccountProfileBean(entity.getAccountProfileByRatedId());
		this.dailyAuctionsBean = new DailyAuctionsBean(entity.getDailyAuctions());
		this.ratingValue = entity.getRatingValue();
		this.comments = entity.getComments();
		this.ratingTimestamp = entity.getRatingTimestamp();
		
	}
}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public AccountProfileBean getAccountProfileByRatorId() {
		return accountProfileByRatorId;
	}

	public void setAccountProfileByRatorId(AccountProfileBean accountProfileByRatorId) {
		this.accountProfileByRatorId = accountProfileByRatorId;
	}

	public AccountProfileBean getAccountProfileByRatedId() {
		return accountProfileByRatedId;
	}

	public void setAccountProfileByRatedId(AccountProfileBean accountProfileByRatedId) {
		this.accountProfileByRatedId = accountProfileByRatedId;
	}

	public DailyAuctionsBean getDailyAuctionsBean() {
		return dailyAuctionsBean;
	}

	public void setDailyAuctionsBean(DailyAuctionsBean dailyAuctionsBean) {
		this.dailyAuctionsBean = dailyAuctionsBean;
	}
	
	public Short getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(Short ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getRatingTimestamp() {
		return ratingTimestamp;
	}

	public void setRatingTimestamp(Date ratingTimestamp) {
		this.ratingTimestamp = ratingTimestamp;
	}
	
	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getNumOfRatings() {
		return numOfRatings;
	}

	public void setNumOfRatings(Integer numOfRatings) {
		this.numOfRatings = numOfRatings;
	}

	public Integer getRatingTotal() {
		return ratingTotal;
	}

	public void setRatingTotal(Integer ratingTotal) {
		this.ratingTotal = ratingTotal;
	}

	
	
}
