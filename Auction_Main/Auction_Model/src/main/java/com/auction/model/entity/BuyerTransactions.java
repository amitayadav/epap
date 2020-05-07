package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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
@Table(name = "buyer_transactions")
public class BuyerTransactions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer transactionId;
	private AccountProfile accountProfile;
	private AuctionBuyers auctionBuyers;
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

	public BuyerTransactions() {
	}

	public BuyerTransactions(Integer transactionId) {
		this.transactionId = transactionId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "transaction_id", unique = true, nullable = false)
	public Integer getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	public AccountProfile getAccountProfile() {
		return this.accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_buyers_id", nullable = false)
	public AuctionBuyers getAuctionBuyers() {
		return this.auctionBuyers;
	}

	public void setAuctionBuyers(AuctionBuyers auctionBuyers) {
		this.auctionBuyers = auctionBuyers;
	}

	@Column(name = "buy_quantity", nullable = false)
	public Integer getBuyQuantity() {
		return this.buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	@Column(name = "buy_price", nullable = false, precision = 6)
	public Float getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Column(name = "gross_purchase", nullable = false, precision = 10)
	public BigDecimal getGrossPurchase() {
		return this.grossPurchase;
	}

	public void setGrossPurchase(BigDecimal grossPurchase) {
		this.grossPurchase = grossPurchase;
	}

	@Column(name = "royalty_percentage", nullable = false, precision = 4)
	public Float getRoyaltyPercentage() {
		return this.royaltyPercentage;
	}

	public void setRoyaltyPercentage(Float royaltyPercentage) {
		this.royaltyPercentage = royaltyPercentage;
	}

	@Column(name = "royalty_amount", nullable = false, precision = 9)
	public BigDecimal getRoyaltyAmount() {
		return this.royaltyAmount;
	}

	public void setRoyaltyAmount(BigDecimal royaltyAmount) {
		this.royaltyAmount = royaltyAmount;
	}

	@Column(name = "vat_percentage", nullable = false, precision = 4)
	public Float getVatPercentage() {
		return this.vatPercentage;
	}

	public void setVatPercentage(Float vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	@Column(name = "vat_amount", nullable = false, precision = 10)
	public BigDecimal getVatAmount() {
		return this.vatAmount;
	}

	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	@Column(name = "buyer_shipping_charge", nullable = false, precision = 6)
	public Float getBuyerShippingCharge() {
		return this.buyerShippingCharge;
	}

	public void setBuyerShippingCharge(Float buyerShippingCharge) {
		this.buyerShippingCharge = buyerShippingCharge;
	}

	@Column(name = "net_payment", nullable = false, precision = 10)
	public BigDecimal getNetPayment() {
		return this.netPayment;
	}

	public void setNetPayment(BigDecimal netPayment) {
		this.netPayment = netPayment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_creation", nullable = false, length = 19)
	public Date getTransactionCreation() {
		return this.transactionCreation;
	}

	public void setTransactionCreation(Date transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

	@Column(name = "reserved1")
	public Integer getReserved1() {
		return this.reserved1;
	}

	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}

	@Column(name = "reserved2", length = 30)
	public String getReserved2() {
		return this.reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

}