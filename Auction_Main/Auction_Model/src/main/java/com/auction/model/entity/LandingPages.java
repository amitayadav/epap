package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "landing_pages")
public class LandingPages implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Short landingPageId;
	private String landingPageName;
	private String landingPageUrl;

	public LandingPages() {
	}

	public LandingPages(Short landingPageId) {
		this.landingPageId = landingPageId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "landing_page_id", unique = true, nullable = false)
	public Short getLandingPageId() {
		return this.landingPageId;
	}

	public void setLandingPageId(Short landingPageId) {
		this.landingPageId = landingPageId;
	}

	@Column(name = "landing_page_name", nullable = false, length = 100)
	public String getLandingPageName() {
		return this.landingPageName;
	}

	public void setLandingPageName(String landingPageName) {
		this.landingPageName = landingPageName;
	}

	@Column(name = "landing_page_url", nullable = false)
	public String getLandingPageUrl() {
		return this.landingPageUrl;
	}

	public void setLandingPageUrl(String landingPageUrl) {
		this.landingPageUrl = landingPageUrl;
	}

}
