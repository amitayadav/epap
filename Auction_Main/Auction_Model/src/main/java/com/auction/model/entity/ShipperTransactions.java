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
@Table(name = "shipper_transactions")
public class ShipperTransactions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer transactionId;
	private AccountLocations accountLocationsByShippingToLocation;
	private AccountLocations accountLocationsByShippingFromLocation;
	private AccountProfile accountProfile;
	private AuctionBuyers auctionBuyers;
	private BigDecimal grossRevenue;
	private Float royaltyPercentage;
	private BigDecimal royaltyAmount;
	private Float vatPercentage;
	private BigDecimal vatAmount;
	private BigDecimal netRevenue;
	private Date transactionCreation;

	public ShipperTransactions() {
	}

	public ShipperTransactions(Integer transactionId) {
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
	@JoinColumn(name = "shipping_to_location", nullable = false)
	public AccountLocations getAccountLocationsByShippingToLocation() {
		return this.accountLocationsByShippingToLocation;
	}

	public void setAccountLocationsByShippingToLocation(AccountLocations accountLocationsByShippingToLocation) {
		this.accountLocationsByShippingToLocation = accountLocationsByShippingToLocation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipping_from_location", nullable = false)
	public AccountLocations getAccountLocationsByShippingFromLocation() {
		return this.accountLocationsByShippingFromLocation;
	}

	public void setAccountLocationsByShippingFromLocation(AccountLocations accountLocationsByShippingFromLocation) {
		this.accountLocationsByShippingFromLocation = accountLocationsByShippingFromLocation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipper_id", nullable = false)
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

	@Column(name = "gross_revenue", nullable = false, precision = 10)
	public BigDecimal getGrossRevenue() {
		return this.grossRevenue;
	}

	public void setGrossRevenue(BigDecimal grossRevenue) {
		this.grossRevenue = grossRevenue;
	}

	@Column(name = "royalty_percentage", nullable = false, precision = 4)
	public Float getRoyaltyPercentage() {
		return this.royaltyPercentage;
	}

	public void setRoyaltyPercentage(Float royaltyPercentage) {
		this.royaltyPercentage = royaltyPercentage;
	}

	@Column(name = "royalty_amount", nullable = false, precision = 10)
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

	@Column(name = "net_revenue", nullable = false, precision = 10)
	public BigDecimal getNetRevenue() {
		return this.netRevenue;
	}

	public void setNetRevenue(BigDecimal netRevenue) {
		this.netRevenue = netRevenue;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transaction_creation", nullable = false, length = 19)
	public Date getTransactionCreation() {
		return this.transactionCreation;
	}

	public void setTransactionCreation(Date transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

}