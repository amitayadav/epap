package com.auction.model.views;

import com.auction.model.bean.AccountLocationsBean;
import com.auction.model.entity.AccountLocations;

public class AccountLocationsView {

	private Integer locationId;
	private String locationName;
	private Double latitude;
	private Double longitude;
	private Short status;

	public AccountLocationsView() {
	}

	public AccountLocationsView(AccountLocationsBean bean) {
		this.locationId = bean.getLocationId();
		this.locationName = bean.getLocationName();
		this.latitude = bean.getLatitude();
		this.longitude = bean.getLongitude();
		this.status = bean.getStatus();
	}

	public AccountLocationsView(AccountLocations entity) {
		this.locationId = entity.getLocationId();
		this.locationName = entity.getLocationName();
		this.latitude = entity.getLatitude();
		this.longitude = entity.getLongitude();
		this.status = entity.getStatus();
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
}