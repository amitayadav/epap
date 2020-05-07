package com.auction.model.views;

import java.math.BigDecimal;

public class SellerSalesNoticesView {
	private String seller;
	private String buyer;
	private String sellerShipper;
	private Integer transactionId;
	private String item;
	private String transactionCreation;
	private Float unitPrice;
	private Integer quantity;
	private BigDecimal amount;
	private Float commission;
	private Float shipping;
	private Float vat;
	private BigDecimal totalDue;
	private String salesNoticesNo;

	public SellerSalesNoticesView() {

	}

	public SellerSalesNoticesView(Integer transactionId, String transactionCreation) {
		this.transactionCreation = transactionCreation;
		this.transactionId = transactionId;
	}

	public SellerSalesNoticesView(String seller, String buyer, String sellerShipper, Integer transactionId, String item, String transactionCreation, Float unitPrice,
			Integer quantity, BigDecimal amount, Float commission, Float shipping, Float vat, BigDecimal totalDue, String salesNoticesNo) {
		super();
		this.seller = seller;
		this.buyer = buyer;
		this.sellerShipper = sellerShipper;
		this.transactionId = transactionId;
		this.item = item;
		this.transactionCreation = transactionCreation;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.amount = amount;
		this.commission = commission;
		this.shipping = shipping;
		this.vat = vat;
		this.totalDue = totalDue;
		this.salesNoticesNo = salesNoticesNo;
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

	public String getSellerShipper() {
		return sellerShipper;
	}

	public void setSellerShipper(String sellerShipper) {
		this.sellerShipper = sellerShipper;
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

	public BigDecimal getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(BigDecimal totalDue) {
		this.totalDue = totalDue;
	}

	public String getSalesNoticesNo() {
		return salesNoticesNo;
	}

	public void setSalesNoticesNo(String salesNoticesNo) {
		this.salesNoticesNo = salesNoticesNo;
	}
}
