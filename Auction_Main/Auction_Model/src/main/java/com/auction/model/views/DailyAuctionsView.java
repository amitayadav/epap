package com.auction.model.views;

import com.auction.commons.constant.CommonConstants;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.entity.DailyAuctions;

public class DailyAuctionsView {
	private Integer dailyAuctionsId;
	private Long beginLong;
	private String beginDate;
	private String beginTime;
	private Long endLong;
	private String endDate;
	private String endTime;
	private Short auctionDuration;
	private String productGroupName;
	private String productName;
	private String productTypeName;
	private Short auctionStatusCode;
	private String auctionStatusCodeMeaning;
	private String productDescription;
	private String containerSpecs;
	private Boolean auctionSellers = new Boolean(false);
	private Boolean auctionBuyers = new Boolean(false);

	public DailyAuctionsView() {

	}

	public DailyAuctionsView(DailyAuctionsBean bean) {
		if (null != bean && null != bean.getDailyAuctionsId()) {
			this.dailyAuctionsId = bean.getDailyAuctionsId();
			this.beginLong = bean.getBeginTime().getTime();
			this.beginDate = CommonConstants.UI_SIMPLE_DATE_FORMAT.format(bean.getBeginTime());
			this.beginTime = CommonConstants.UI_AUCTION_SIMPLE_TIME_FORMAT.format(bean.getBeginTime());
			this.endLong = bean.getEndTime().getTime();
			this.endDate = CommonConstants.UI_SIMPLE_DATE_FORMAT.format(bean.getEndTime());
			this.endTime = CommonConstants.UI_AUCTION_SIMPLE_TIME_FORMAT.format(bean.getEndTime());
			this.auctionDuration = bean.getAuctionDuration();
			this.productGroupName = bean.getProductCatalogBean().getProductGroupName();
			this.productName = bean.getProductCatalogBean().getProductName();
			this.productTypeName = bean.getProductCatalogBean().getProductTypeName();
			this.auctionStatusCode = bean.getAuctionStatusCodesBean().getAuctionStatusCode();
			this.auctionStatusCodeMeaning = bean.getAuctionStatusCodesBean().getAuctionStatusCodeMeaning();
			this.productDescription = bean.getProductCatalogBean().getProductDescription();
			this.containerSpecs = bean.getProductCatalogBean().getContainerSpecs();
		}
	}

	public DailyAuctionsView(DailyAuctions entity) {

		if (null != entity && null != entity.getDailyAuctionsId()) {
			this.dailyAuctionsId = entity.getDailyAuctionsId();
			this.beginLong = entity.getBeginTime().getTime();
			this.beginDate = CommonConstants.UI_SIMPLE_DATE_FORMAT_WITH_DAYNAME.format(entity.getBeginTime());
			this.beginTime = CommonConstants.UI_AUCTION_SIMPLE_TIME_FORMAT.format(entity.getBeginTime());
			this.endLong = entity.getEndTime().getTime();
			this.endDate = CommonConstants.UI_SIMPLE_DATE_FORMAT.format(entity.getEndTime());
			this.endTime = CommonConstants.UI_AUCTION_SIMPLE_TIME_FORMAT.format(entity.getEndTime());
			this.auctionDuration = entity.getAuctionDuration();
			this.productGroupName = entity.getProductCatalog().getProductGroupName();
			this.productName = entity.getProductCatalog().getProductName();
			this.productTypeName = entity.getProductCatalog().getProductTypeName();
			this.auctionStatusCode = entity.getAuctionStatusCodes().getAuctionStatusCode();
			this.auctionStatusCodeMeaning = entity.getAuctionStatusCodes().getAuctionStatusCodeMeaning();
			this.productDescription = entity.getProductCatalog().getProductDescription();
			this.containerSpecs = entity.getProductCatalog().getContainerSpecs();
		}
	}

	public Integer getDailyAuctionsId() {
		return this.dailyAuctionsId;
	}

	public void setDailyAuctionsId(Integer dailyAuctionsId) {
		this.dailyAuctionsId = dailyAuctionsId;
	}

	public Long getBeginLong() {
		return beginLong;
	}

	public void setBeginLong(Long beginLong) {
		this.beginLong = beginLong;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndLong() {
		return endLong;
	}

	public void setEndLong(Long endLong) {
		this.endLong = endLong;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Short getAuctionDuration() {
		return this.auctionDuration;
	}

	public void setAuctionDuration(Short auctionDuration) {
		this.auctionDuration = auctionDuration;
	}

	public String getProductGroupName() {
		return this.productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeName() {
		return this.productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Short getAuctionStatusCode() {
		return this.auctionStatusCode;
	}

	public void setAuctionStatusCode(Short auctionStatusCode) {
		this.auctionStatusCode = auctionStatusCode;
	}

	public String getAuctionStatusCodeMeaning() {
		return this.auctionStatusCodeMeaning;
	}

	public void setAuctionStatusCodeMeaning(String auctionStatusCodeMeaning) {
		this.auctionStatusCodeMeaning = auctionStatusCodeMeaning;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getContainerSpecs() {
		return containerSpecs;
	}

	public void setContainerSpecs(String containerSpecs) {
		this.containerSpecs = containerSpecs;
	}

	public Boolean getAuctionSellers() {
		return auctionSellers;
	}

	public void setAuctionSellers(Boolean auctionSellers) {
		this.auctionSellers = auctionSellers;
	}

	public Boolean getAuctionBuyers() {
		return auctionBuyers;
	}

	public void setAuctionBuyers(Boolean auctionBuyers) {
		this.auctionBuyers = auctionBuyers;
	}

}