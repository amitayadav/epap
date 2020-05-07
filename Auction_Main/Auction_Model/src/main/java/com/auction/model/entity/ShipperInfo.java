package com.auction.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "shipper_info")
public class ShipperInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private Integer shipperId;
	private AccountProfile accountProfile;
	private String infoLine1;
	private String infoLine2;
	
	
	public ShipperInfo() {
		
	}
		public ShipperInfo(Integer shipperId) {
			this.shipperId =shipperId;
		}
	
		@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "accountProfile"))
		@Id
		@Column(name = "shipper_id", unique = true, nullable = false)
	public Integer getShipperId() {
		return shipperId;
	}
	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public AccountProfile getAccountProfile() {
		return accountProfile;
	}
	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}
	@Column(name = "info_line1", nullable = false)
	public String getInfoLine1() {
		return infoLine1;
	}
	public void setInfoLine1(String infoLine1) {
		this.infoLine1 = infoLine1;
	}
	@Column(name = "info_line2")
	public String getInfoLine2() {
		return infoLine2;
	}
	public void setInfoLine2(String infoLine2) {
		this.infoLine2 = infoLine2;
	}
	
}
