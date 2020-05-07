package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seller_offer_info")
public class SellerOfferInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer sellerOfferInfoId;
	private AccountProfile accountProfile;
	private ProductCatalog productCatalog;
	private String infoLine1;
	private String infoLine2;

	public SellerOfferInfo() {
	}

	public SellerOfferInfo(Integer sellerOfferInfoId) {
		this.sellerOfferInfoId = sellerOfferInfoId;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "seller_offer_info_id", unique = true, nullable = false)
	public Integer getSellerOfferInfoId() {
		return this.sellerOfferInfoId;
	}

	public void setSellerOfferInfoId(Integer sellerOfferInfoId) {
		this.sellerOfferInfoId = sellerOfferInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	public ProductCatalog getProductCatalog() {
		return this.productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
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
