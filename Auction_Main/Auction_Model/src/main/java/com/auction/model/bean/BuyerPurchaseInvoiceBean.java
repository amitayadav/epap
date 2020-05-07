package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.BuyerPurchaseInvoice;

public class BuyerPurchaseInvoiceBean {
	private Integer PurchaseInvoiceId;
	private BuyerTransactionsBean buyerTransactionsBean;
	private String invoiceNo;
	private Date invoiceCreation;
	private String sellerPublicName;
	private String buyerPublicName;
	private String buyerShipperPublicName;

	public BuyerPurchaseInvoiceBean() {

	}

	public BuyerPurchaseInvoiceBean(BuyerPurchaseInvoice entity) {
		if (null != entity) {
			this.PurchaseInvoiceId = entity.getPurchaseInvoiceId();
			this.buyerTransactionsBean = new BuyerTransactionsBean(entity.getBuyerTransactions());
			this.invoiceNo = entity.getInvoiceNo();
			this.invoiceCreation = entity.getInvoiceCreation();
			this.sellerPublicName = entity.getSellerPublicName();
			this.buyerPublicName = entity.getBuyerPublicName();
			this.buyerShipperPublicName = entity.getBuyerPublicName();

		}
	}

	public Integer getPurchaseInvoiceId() {
		return PurchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(Integer purchaseInvoiceId) {
		PurchaseInvoiceId = purchaseInvoiceId;
	}

	public BuyerTransactionsBean getBuyerTransactionsBean() {
		return buyerTransactionsBean;
	}

	public void setBuyerTransactionsBean(BuyerTransactionsBean buyerTransactionsBean) {
		this.buyerTransactionsBean = buyerTransactionsBean;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceCreation() {
		return invoiceCreation;
	}

	public void setInvoiceCreation(Date invoiceCreation) {
		this.invoiceCreation = invoiceCreation;
	}

	public String getSellerPublicName() {
		return sellerPublicName;
	}

	public void setSellerPublicName(String sellerPublicName) {
		this.sellerPublicName = sellerPublicName;
	}

	public String getBuyerPublicName() {
		return buyerPublicName;
	}

	public void setBuyerPublicName(String buyerPublicName) {
		this.buyerPublicName = buyerPublicName;
	}

	public String getBuyerShipperPublicName() {
		return buyerShipperPublicName;
	}

	public void setBuyerShipperPublicName(String buyerShipperPublicName) {
		this.buyerShipperPublicName = buyerShipperPublicName;
	}

}
