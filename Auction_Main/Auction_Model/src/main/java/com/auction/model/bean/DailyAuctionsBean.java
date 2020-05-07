package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.DailyAuctions;

public class DailyAuctionsBean {

	private Integer dailyAuctionsId;
	private AccountProfileBean accountProfileByCancelledbyIdBean;
	private AccountProfileBean accountProfileByCreatedbyIdBean;
	private AccountProfileBean accountProfileByUpdatedbyIdBean;
	private AuctionSettingsBean auctionSettingsBean;
	private AuctionStatusCodesBean auctionStatusCodesBean;
	private ProductCatalogBean productCatalogBean;
	private Date beginTime;
	private Date endTime;
	private Short auctionDuration;
	private Date createdOn;
	private Date updatedOn;
	private Date canceledOn;
	private Integer reserved1;
	private String reserved2;

	public DailyAuctionsBean() {
	}
	
	public DailyAuctionsBean(Integer dailyAuctionsId) {
		this.dailyAuctionsId = dailyAuctionsId;
	}

	public DailyAuctionsBean(DailyAuctions entity) {

		if (null != entity) {

			this.dailyAuctionsId = entity.getDailyAuctionsId();
			this.accountProfileByCancelledbyIdBean = new AccountProfileBean(entity.getAccountProfileByCancelledbyId());
			this.accountProfileByCreatedbyIdBean = new AccountProfileBean(entity.getAccountProfileByCreatedbyId());
			this.accountProfileByUpdatedbyIdBean = new AccountProfileBean(entity.getAccountProfileByUpdatedbyId());
			this.auctionSettingsBean = new AuctionSettingsBean(entity.getAuctionSettings());
			this.auctionStatusCodesBean = new AuctionStatusCodesBean(entity.getAuctionStatusCodes());
			if(null != entity.getProductCatalog()) {
				this.productCatalogBean = new ProductCatalogBean(entity.getProductCatalog());
			}
			this.beginTime = entity.getBeginTime();
			this.endTime = entity.getEndTime();
			this.auctionDuration = entity.getAuctionDuration();
			this.createdOn = entity.getCreatedOn();
			this.updatedOn = entity.getUpdatedOn();
			this.canceledOn = entity.getCanceledOn();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();

			/*final long ONE_MINUTE_IN_MILLIS = 60000;
			long curTimeInMs = this.beginTime.getTime();
			Date afterAddingMins = new Date(
					curTimeInMs + (Integer.parseInt((this.auctionDuration.intValue() + "")) * ONE_MINUTE_IN_MILLIS));

			this.endTime = afterAddingMins;*/
		}
	}

	public Integer getDailyAuctionsId() {
		return dailyAuctionsId;
	}

	public void setDailyAuctionsId(Integer dailyAuctionsId) {
		this.dailyAuctionsId = dailyAuctionsId;
	}

	public AccountProfileBean getAccountProfileByCancelledbyIdBean() {
		return accountProfileByCancelledbyIdBean;
	}

	public void setAccountProfileByCancelledbyIdBean(AccountProfileBean accountProfileByCancelledbyIdBean) {
		this.accountProfileByCancelledbyIdBean = accountProfileByCancelledbyIdBean;
	}

	public AccountProfileBean getAccountProfileByCreatedbyIdBean() {
		return accountProfileByCreatedbyIdBean;
	}

	public void setAccountProfileByCreatedbyIdBean(AccountProfileBean accountProfileByCreatedbyIdBean) {
		this.accountProfileByCreatedbyIdBean = accountProfileByCreatedbyIdBean;
	}

	public AccountProfileBean getAccountProfileByUpdatedbyIdBean() {
		return accountProfileByUpdatedbyIdBean;
	}

	public void setAccountProfileByUpdatedbyIdBean(AccountProfileBean accountProfileByUpdatedbyIdBean) {
		this.accountProfileByUpdatedbyIdBean = accountProfileByUpdatedbyIdBean;
	}

	public AuctionSettingsBean getAuctionSettingsBean() {
		return auctionSettingsBean;
	}

	public void setAuctionSettingsBean(AuctionSettingsBean auctionSettingsBean) {
		this.auctionSettingsBean = auctionSettingsBean;
	}

	public AuctionStatusCodesBean getAuctionStatusCodesBean() {
		return auctionStatusCodesBean;
	}

	public void setAuctionStatusCodesBean(AuctionStatusCodesBean auctionStatusCodesBean) {
		this.auctionStatusCodesBean = auctionStatusCodesBean;
	}

	public ProductCatalogBean getProductCatalogBean() {
		return productCatalogBean;
	}

	public void setProductCatalogBean(ProductCatalogBean productCatalogBean) {
		this.productCatalogBean = productCatalogBean;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Short getAuctionDuration() {
		return auctionDuration;
	}

	public void setAuctionDuration(Short auctionDuration) {
		this.auctionDuration = auctionDuration;
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

	public Date getCanceledOn() {
		return canceledOn;
	}

	public void setCanceledOn(Date canceledOn) {
		this.canceledOn = canceledOn;
	}

	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
