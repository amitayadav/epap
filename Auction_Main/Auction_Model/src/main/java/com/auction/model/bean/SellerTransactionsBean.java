package com.auction.model.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.auction.model.entity.SellerTransactions;

public class SellerTransactionsBean {

	private Integer transactionId;
	private AccountProfileBean accountProfileBean;
	private AuctionBuyersBean auctionBuyersBean;
	private Integer sellQuantity;
	private Float sellPrice;
	private BigDecimal grossSale;
	private Float royaltyPercentage;
	private BigDecimal royaltyAmount;
	private Float vatPercentage;
	private BigDecimal vatAmount;
	private BigDecimal netSales;
	private Date transactionCreation;
	private Float sellerShippingCharge;
	private Integer reserved1;
	private String reserved2;

	public SellerTransactionsBean() {
	}

	public SellerTransactionsBean(SellerTransactions entity) {
		if (null != entity) {
			this.transactionId = entity.getTransactionId();
			this.accountProfileBean = new AccountProfileBean(entity.getAccountProfile());
			this.auctionBuyersBean = new AuctionBuyersBean(entity.getAuctionBuyers());
			this.sellQuantity = entity.getSellQuantity();
			this.sellPrice = entity.getSellPrice();
			this.grossSale = entity.getGrossSale();
			this.royaltyPercentage = entity.getRoyaltyPercentage();
			this.royaltyAmount = entity.getRoyaltyAmount();
			this.vatPercentage = entity.getVatPercentage();
			this.vatAmount = entity.getVatAmount();
			this.netSales = entity.getNetSales();
			this.transactionCreation = entity.getTransactionCreation();
			this.sellerShippingCharge = entity.getSellerShippingCharge();
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

	public Integer getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Integer sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public BigDecimal getGrossSale() {
		return grossSale;
	}

	public void setGrossSale(BigDecimal grossSale) {
		this.grossSale = grossSale;
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

	public BigDecimal getNetSales() {
		return netSales;
	}

	public void setNetSales(BigDecimal netSales) {
		this.netSales = netSales;
	}

	public Date getTransactionCreation() {
		return transactionCreation;
	}

	public void setTransactionCreation(Date transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

	public Float getSellerShippingCharge() {
		return sellerShippingCharge;
	}

	public void setSellerShippingCharge(Float sellerShippingCharge) {
		this.sellerShippingCharge = sellerShippingCharge;
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