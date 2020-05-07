package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "purchase_invoice")
public class BuyerPurchaseInvoice implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer PurchaseInvoiceId;
	private BuyerTransactions buyerTransactions;
	private String invoiceNo;
	private Date invoiceCreation;
	private String sellerPublicName;
	private String buyerPublicName;
	private String buyerShipperPublicName;

	public BuyerPurchaseInvoice() {

	}

	public BuyerPurchaseInvoice(Integer PurchaseInvoiceId) {
		this.PurchaseInvoiceId = PurchaseInvoiceId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "purchase_invoice_id", unique = true, nullable = false)
	public Integer getPurchaseInvoiceId() {
		return PurchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(Integer purchaseInvoiceId) {
		PurchaseInvoiceId = purchaseInvoiceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", nullable = false)
	public BuyerTransactions getBuyerTransactions() {
		return buyerTransactions;
	}

	public void setBuyerTransactions(BuyerTransactions buyerTransactions) {
		this.buyerTransactions = buyerTransactions;
	}

	@Column(name = "invoice_no")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invoice_creation", nullable = false, length = 19)
	public Date getInvoiceCreation() {
		return invoiceCreation;
	}

	public void setInvoiceCreation(Date invoiceCreation) {
		this.invoiceCreation = invoiceCreation;
	}

	@Column(name = "seller_public_name", nullable = false)
	public String getSellerPublicName() {
		return sellerPublicName;
	}

	public void setSellerPublicName(String sellerPublicName) {
		this.sellerPublicName = sellerPublicName;
	}

	@Column(name = "buyer_public_name", nullable = false)
	public String getBuyerPublicName() {
		return buyerPublicName;
	}

	public void setBuyerPublicName(String buyerPublicName) {
		this.buyerPublicName = buyerPublicName;
	}

	@Column(name = "buyer_shipper_public_name")
	public String getBuyerShipperPublicName() {
		return buyerShipperPublicName;
	}

	public void setBuyerShipperPublicName(String buyerShipperPublicName) {
		this.buyerShipperPublicName = buyerShipperPublicName;
	}
}
