package com.auction.model.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.auction.model.entity.BuyerTransactions;

public class BuyerTransactionsBean {

	private Integer transactionId;
	private AccountProfileBean accountProfileBean;
	private AuctionBuyersBean auctionBuyersBean;
	private Integer buyQuantity;
	private Float buyPrice;
	private BigDecimal grossPurchase;
	private Float royaltyPercentage;
	private BigDecimal royaltyAmount;
	private Float vatPercentage;
	private BigDecimal vatAmount;
	private Float buyerShippingCharge;
	private BigDecimal netPayment;
	private Date transactionCreation;
	private Integer reserved1;
	private String reserved2;

	public BuyerTransactionsBean() {
	}

	public BuyerTransactionsBean(BuyerTransactions entity) {
		if (null != entity) {
			this.transactionId = entity.getTransactionId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.auctionBuyersBean = new AuctionBuyersBean(entity.getAuctionBuyers());
			this.buyQuantity = entity.getBuyQuantity();
			this.buyPrice = entity.getBuyPrice();
			this.grossPurchase = entity.getGrossPurchase();
			this.royaltyPercentage = entity.getRoyaltyPercentage();
			this.royaltyAmount = entity.getRoyaltyAmount();
			this.vatPercentage = entity.getVatPercentage();
			this.vatAmount = entity.getVatAmount();
			this.buyerShippingCharge = entity.getBuyerShippingCharge();
			this.netPayment = entity.getNetPayment();
			this.transactionCreation = entity.getTransactionCreation();
			this.reserved1 = entity.getReserved1();
			this.reserved2 = entity.getReserved2();
		}
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public AccountProfileBean getAccountProfileBean() {
		return accountProfileBean;
	}

	public void setAccountProfileBean(AccountProfileBean accountProfileBean) {
		this.accountProfileBean = accountProfileBean;
	}

	public AuctionBuyersBean getAuctionBuyersBean() {
		return auctionBuyersBean;
	}

	public void setAuctionBuyersBean(AuctionBuyersBean auctionBuyersBean) {
		this.auctionBuyersBean = auctionBuyersBean;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getGrossPurchase() {
		return grossPurchase;
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

	public Float getBuyerShippingCharge() {
		return buyerShippingCharge;
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}

	public void setGrossPurchase(BigDecimal grossPurchase) {
		this.grossPurchase = grossPurchase;
	}

	public BigDecimal getNetPayment() {
		return netPayment;
	}

	public void setNetPayment(BigDecimal netPayment) {
		this.netPayment = netPayment;
	}

	public Date getTransactionCreation() {
		return transactionCreation;
	}

	public void setTransactionCreation(Date transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

	public Integer getReserved1() {
		return reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

}