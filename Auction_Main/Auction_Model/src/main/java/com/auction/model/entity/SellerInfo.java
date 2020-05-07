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
@Table(name = "seller_info")
public class SellerInfo implements java.io.Serializable {

	private static final long serialVersionUID = 3285218037879069041L;
	private Integer sellerId;
	private AccountProfile accountProfile;
	private String infoLine1;
	private String infoLine2;

	public SellerInfo() {
	}

	public SellerInfo(Integer sellerId) {
		this.sellerId = sellerId;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "accountProfile"))
	@Id
	@Column(name = "seller_id", unique = true, nullable = false)
	public Integer getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@Column(name = "info_line1", nullable = false)
	public String getInfoLine1() {
		return this.infoLine1;
	}

	public void setInfoLine1(String infoLine1) {
		this.infoLine1 = infoLine1;
	}

	@Column(name = "info_line2")
	public String getInfoLine2() {
		return this.infoLine2;
	}

	public void setInfoLine2(String infoLine2) {
		this.infoLine2 = infoLine2;
	}

}