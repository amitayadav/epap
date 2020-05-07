package com.auction.model.bean;

import com.auction.model.entity.AuctionSettings;

public class AuctionSettingsBean {

	private Integer auctionSettingsId;
	private CurrencyCodesBean currencyCodesBean;
	private Float vatBuyers=0.0f;
	private Float vatSellers=0.0f;
	public AuctionSettingsBean() {
	}

	public AuctionSettingsBean(Integer auctionSettingsId) {
		this.auctionSettingsId = auctionSettingsId;
	}

	public AuctionSettingsBean(AuctionSettings entity) {

		if (null != entity) {
			this.auctionSettingsId = entity.getAuctionSettingsId();
			this.currencyCodesBean = new CurrencyCodesBean(entity.getCurrencyCodes());
			this.vatBuyers = entity.getVatBuyers();
			this.vatSellers = entity.getVatSellers();
		}
	}

	public Integer getAuctionSettingsId() {
		return auctionSettingsId;
	}

	public void setAuctionSettingsId(Integer auctionSettingsId) {
		this.auctionSettingsId = auctionSettingsId;
	}

	public CurrencyCodesBean getCurrencyCodesBean() {
		return currencyCodesBean;
	}

	public void setCurrencyCodesBean(CurrencyCodesBean currencyCodesBean) {
		this.currencyCodesBean = currencyCodesBean;
	}

	public Float getVatBuyers() {
		return vatBuyers;
	}

	public void setVatBuyers(Float vatBuyers) {
		this.vatBuyers = vatBuyers;
	}

	public Float getVatSellers() {
		return vatSellers;
	}

	public void setVatSellers(Float vatSellers) {
		this.vatSellers = vatSellers;
	}
}
