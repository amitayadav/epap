package com.auction.model.views;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseInvoicesView {
	private Integer transactionId;
	private String transactionCreation;
	private String item; 
	private String seller;
	private String buyer;
	private String buyerShipper;
	private Float unitPrice;
	private Integer quantity;
	private BigDecimal amount;
	//private BigDecimal subtotal;
	private Float commission;
	private Float shipping;
	private Float vat;
	//private Float others;
	private BigDecimal totalDue;
	private String purchaseInvoicesNo;
	
	
	

	public PurchaseInvoicesView() {
		
	}

public PurchaseInvoicesView(Integer transactionId, String transactionCreation) {
	this.transactionCreation = transactionCreation;
	this.transactionId=transactionId;
	}

public PurchaseInvoicesView(Integer transactionId, String transactionCreation, String item,String seller, String buyer,
		String buyerShipper,  Float unitPrice, Integer quantity, BigDecimal amount,
		Float commission, Float shipping, Float vat, BigDecimal totalDue,String purchaseInvoicesNo) {
	super();
	this.transactionId = transactionId;
	this.transactionCreation = transactionCreation;
	this.item = item;
	this.seller = seller;
	this.buyer = buyer;
	this.buyerShipper = buyerShipper;
	this.unitPrice = unitPrice;
	this.quantity = quantity;
	this.amount = amount;
	//this.subtotal = subtotal;
	this.commission = commission;
	this.shipping = shipping;
	this.vat = vat;
	//this.others = others;
	this.totalDue = totalDue;
	this.purchaseInvoicesNo =purchaseInvoicesNo;
}
	
	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionCreation() {
		return transactionCreation;
	}

	public void setTransactionCreation(String transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getBuyerShipper() {
		return buyerShipper;
	}

	public void setBuyerShipper(String buyerShipper) {
		this.buyerShipper = buyerShipper;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/*public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}*/

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public Float getShipping() {
		return shipping;
	}

	public void setShipping(Float shipping) {
		this.shipping = shipping;
	}

	public Float getVat() {
		return vat;
	}

	public void setVat(Float vat) {
		this.vat = vat;
	}

	/*public Float getOthers() {
		return others;
	}

	public void setOthers(Float others) {
		this.others = others;
	}*/

	public BigDecimal getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(BigDecimal totalDue) {
		this.totalDue = totalDue;
	}
	public String getPurchaseInvoicesNo() {
		return purchaseInvoicesNo;
	}

	public void setPurchaseInvoicesNo(String purchaseInvoicesNo) {
		this.purchaseInvoicesNo = purchaseInvoicesNo;
	}
}
