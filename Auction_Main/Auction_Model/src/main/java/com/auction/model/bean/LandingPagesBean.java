package com.auction.model.bean;

import com.auction.model.entity.LandingPages;

public class LandingPagesBean {

	private Short landingPageId;
	private String landingPageName;
	private String landingPageUrl;

	public LandingPagesBean() {
	}

	public LandingPagesBean(LandingPages entity) {
		this.landingPageId = entity.getLandingPageId();
		this.landingPageName = entity.getLandingPageName();
		this.landingPageUrl = entity.getLandingPageUrl();
	}

	public LandingPagesBean(Short landingPageId) {
		this.landingPageId = landingPageId;
	}

	public Short getLandingPageId() {
		return landingPageId;
	}

	public void setLandingPageId(Short landingPageId) {
		this.landingPageId = landingPageId;
	}

	public String getLandingPageName() {
		return landingPageName;
	}

	public void setLandingPageName(String landingPageName) {
		this.landingPageName = landingPageName;
	}

	public String getLandingPageUrl() {
		return landingPageUrl;
	}

	public void setLandingPageUrl(String landingPageUrl) {
		this.landingPageUrl = landingPageUrl;
	}

}
