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
@Table(name = "auction_settings")
public class AuctionSettings implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer auctionSettingsId;
	private CurrencyCodes currencyCodes;
	private Float vatBuyers;
	private Float vatSellers;

	public AuctionSettings() {
	}

	public AuctionSettings(Integer auctionSettingsId) {
		this.auctionSettingsId = auctionSettingsId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auction_settings_id", unique = true, nullable = false)
	public Integer getAuctionSettingsId() {
		return this.auctionSettingsId;
	}

	public void setAuctionSettingsId(Integer auctionSettingsId) {
		this.auctionSettingsId = auctionSettingsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_code", nullable = false)
	public CurrencyCodes getCurrencyCodes() {
		return this.currencyCodes;
	}

	public void setCurrencyCodes(CurrencyCodes currencyCodes) {
		this.currencyCodes = currencyCodes;
	}
	@Column(name = "vat_buyers", nullable = false, precision = 4)
	public Float getVatBuyers() {
		return vatBuyers;
	}

	public void setVatBuyers(Float vatBuyers) {
		this.vatBuyers = vatBuyers;
	}
	
	@Column(name = "vat_sellers", nullable = false, precision = 4)
	public Float getVatSellers() {
		return vatSellers;
	}

	public void setVatSellers(Float vatSellers) {
		this.vatSellers = vatSellers;
	}

	
	 

}
