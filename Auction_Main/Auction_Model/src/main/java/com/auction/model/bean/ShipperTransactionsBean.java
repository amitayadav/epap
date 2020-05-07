package com.auction.model.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.auction.model.entity.ShipperTransactions;

public class ShipperTransactionsBean {
	private Integer transactionId;
	private AccountLocationsBean accountLocationsByShippingToLocation;
	private AccountLocationsBean accountLocationsByShippingFromLocation;
	private AccountProfileBean accountProfile;
	private AuctionBuyersBean auctionBuyers;
	private BigDecimal grossRevenue;
	private Float royaltyPercentage;
	private BigDecimal royaltyAmount;
	private Float vatPercentage;
	private BigDecimal vatAmount;
	private BigDecimal netRevenue;
	private Date transactionCreation;

	public ShipperTransactionsBean() {

	}

	public ShipperTransactionsBean(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public ShipperTransactionsBean(ShipperTransactions entity) {
		if (null != entity) {
			this.transactionId = entity.getTransactionId();
			this.accountLocationsByShippingToLocation = new AccountLocationsBean(
					entity.getAccountLocationsByShippingToLocation());
			this.accountLocationsByShippingFromLocation = new AccountLocationsBean(
					entity.getAccountLocationsByShippingFromLocation());
			this.accountProfile = new AccountProfileBean(entity.getAccountProfile());
			this.auctionBuyers = new AuctionBuyersBean(entity.getAuctionBuyers());
			this.grossRevenue = entity.getGrossRevenue();
			this.royaltyPercentage = entity.getRoyaltyPercentage();
			this.royaltyAmount = entity.getRoyaltyAmount();
			this.vatPercentage = entity.getVatPercentage();
			this.vatAmount = entity.getVatAmount();
			this.netRevenue = entity.getNetRevenue();
			this.transactionCreation = entity.getTransactionCreation();

		}
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public AccountLocationsBean getAccountLocationsByShippingToLocation() {
		return accountLocationsByShippingToLocation;
	}

	public void setAccountLocationsByShippingToLocation(AccountLocationsBean accountLocationsByShippingToLocation) {
		this.accountLocationsByShippingToLocation = accountLocationsByShippingToLocation;
	}

	public AccountLocationsBean getAccountLocationsByShippingFromLocation() {
		return accountLocationsByShippingFromLocation;
	}

	public void setAccountLocationsByShippingFromLocation(AccountLocationsBean accountLocationsByShippingFromLocation) {
		this.accountLocationsByShippingFromLocation = accountLocationsByShippingFromLocation;
	}

	public AccountProfileBean getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(AccountProfileBean accountProfile) {
		this.accountProfile = accountProfile;
	}

	public AuctionBuyersBean getAuctionBuyers() {
		return auctionBuyers;
	}

	public void setAuctionBuyers(AuctionBuyersBean auctionBuyers) {
		this.auctionBuyers = auctionBuyers;
	}

	public BigDecimal getGrossRevenue() {
		return grossRevenue;
	}

	public void setGrossRevenue(BigDecimal grossRevenue) {
		this.grossRevenue = grossRevenue;
	}

	public Float getRoyaltyPercentage() {
		return royaltyPercentage;
	}

	public void setRoyaltyPercentage(Float royaltyPercentage) {
		this.royaltyPercentage = royaltyPercentage;
	}

	public BigDecimal getRoyaltyAmount() {
		return royaltyAmount;
	}

	public void setRoyaltyAmount(BigDecimal royaltyAmount) {
		this.royaltyAmount = royaltyAmount;
	}

	public Float getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(Float vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public BigDecimal getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetRevenue() {
		return netRevenue;
	}

	public void setNetRevenue(BigDecimal netRevenue) {
		this.netRevenue = netRevenue;
	}

	public Date getTransactionCreation() {
		return transactionCreation;
	}

	public void setTransactionCreation(Date transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

}
