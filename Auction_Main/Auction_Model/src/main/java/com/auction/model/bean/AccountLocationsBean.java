package com.auction.model.bean;

import com.auction.model.entity.AccountLocations;

public class AccountLocationsBean {

	private Integer locationId;
	private AccountProfileBean accountProfileBean;
	private String locationName;
	private Double latitude;
	private Double longitude;
	private Short status;

	public AccountLocationsBean() {
	}

	public AccountLocationsBean(AccountLocations entity) {
		if (null != entity) {
			this.locationId = entity.getLocationId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.locationName = entity.getLocationName();
			this.latitude = entity.getLatitude();
			this.longitude = entity.getLongitude();
			this.status = entity.getStatus();
		}
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}