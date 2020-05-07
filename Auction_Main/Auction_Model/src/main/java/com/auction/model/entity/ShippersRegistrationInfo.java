package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "shippers_registration_info")
public class ShippersRegistrationInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountId;
	private AccountProfile accountProfile;
	private Date driverLicenseExpirationDate;
	private Integer truckModelYear;
	private String truckType;

	public ShippersRegistrationInfo() {
	}

	public ShippersRegistrationInfo(Integer accountId) {
		this.accountId = accountId;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "accountProfile"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "account_id", unique = true, nullable = false)
	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "driver_license_expiration_date", nullable = false, length = 19)
	public Date getDriverLicenseExpirationDate() {
		return this.driverLicenseExpirationDate;
	}

	public void setDriverLicenseExpirationDate(Date driverLicenseExpirationDate) {
		this.driverLicenseExpirationDate = driverLicenseExpirationDate;
	}

	@Column(name = "truck_model_year", nullable = false)
	public Integer getTruckModelYear() {
		return this.truckModelYear;
	}

	public void setTruckModelYear(Integer truckModelYear) {
		this.truckModelYear = truckModelYear;
	}

	@Column(name = "truck_type", nullable = false, length = 50)
	public String getTruckType() {
		return this.truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}

}