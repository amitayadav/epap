package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.ShippersRegistrationInfo;

public class ShippersRegistrationInfoBean {

	private Integer accountId;
	private Date driverLicenseExpirationDate;
	private Integer truckModelYear;
	private String truckType;

	public ShippersRegistrationInfoBean() {

	}

	public ShippersRegistrationInfoBean(ShippersRegistrationInfo entity) {
		this.accountId = entity.getAccountId();
		this.driverLicenseExpirationDate = entity.getDriverLicenseExpirationDate();
		this.truckModelYear = entity.getTruckModelYear();
		this.truckType = entity.getTruckType();
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Date getDriverLicenseExpirationDate() {
		return driverLicenseExpirationDate;
	}

	public void setDriverLicenseExpirationDate(Date driverLicenseExpirationDate) {
		this.driverLicenseExpirationDate = driverLicenseExpirationDate;
	}

	public Integer getTruckModelYear() {
		return truckModelYear;
	}

	public void setTruckModelYear(Integer truckModelYear) {
		this.truckModelYear = truckModelYear;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

}