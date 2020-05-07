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
@Table(name = "seller_transactions")
public class SellerTransactions implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer transactionId;
	private AccountProfile accountProfile;
	private AuctionBuyers auctionBuyers;
	private Integer sellQuantity;
	private Float sellPrice;
	private BigDecimal grossSale;
	private Float royaltyPercentage;
	private BigDecimal royaltyAmount;
	private Float vatPercentage;
	private BigDecimal vatAmount;
	private Float sellerShippingCharge;
	private BigDecimal netSales;
	private Date transactionCreation;
	private Integer reserved1;
	private String reserved2;

	public SellerTransactions() {
	}

	public SellerTransactions(Integer transactionId) {
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
	@JoinColumn(name = "seller_id", nullable = false)
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

	@Column(name = "sell_quantity", nullable = false)
	public Integer getSellQuantity() {
		return this.sellQuantity;
	}

	public void setSellQuantity(Integer sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	@Column(name = "sell_price", nullable = false, precision = 6)
	public Float getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	@Column(name = "gross_sale", nullable = false, precision = 10)
	public BigDecimal getGrossSale() {
		return this.grossSale;
	}

	public void setGrossSale(BigDecimal grossSale) {
		this.grossSale = grossSale;
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

	@Column(name = "seller_shipping_charge", nullable = false, precision = 6)
	public Float getSellerShippingCharge() {
		return this.sellerShippingCharge;
	}

	public void setSellerShippingCharge(Float sellerShippingCharge) {
		this.sellerShippingCharge = sellerShippingCharge;
	}
	
	@Column(name = "net_sales", nullable = false, precision = 10)
	public BigDecimal getNetSales() {
		return this.netSales;
	}

	public void setNetSales(BigDecimal netSales) {
		this.netSales = netSales;
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