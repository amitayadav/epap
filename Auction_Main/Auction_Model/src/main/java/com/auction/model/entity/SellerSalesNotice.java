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
@Table(name = "sales_notice")
public class SellerSalesNotice implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer salesNoticeId;
	private SellerTransactions sellerTransactions;
	private String noticeNo;
	private Date noticeCreation;
	private String sellerPublicName;
	private String buyerPublicName;
	private String sellerShipperPublicName;

	public SellerSalesNotice() {

	}

	public SellerSalesNotice(Integer salesNoticeId) {
		this.salesNoticeId = salesNoticeId;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sales_notice_id", unique = true, nullable = false)
	public Integer getSalesNoticeId() {
		return salesNoticeId;
	}

	public void setSalesNoticeId(Integer salesNoticeId) {
		this.salesNoticeId = salesNoticeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id", nullable = false)
	public SellerTransactions getSellerTransactions() {
		return sellerTransactions;
	}

	public void setSellerTransactions(SellerTransactions sellerTransactions) {
		this.sellerTransactions = sellerTransactions;
	}

	@Column(name = "notice_no")
	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notice_creation", nullable = false, length = 19)
	public Date getNoticeCreation() {
		return noticeCreation;
	}

	public void setNoticeCreation(Date noticeCreation) {
		this.noticeCreation = noticeCreation;
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

	@Column(name = "seller_shipper_public_name")
	public String getSellerShipperPublicName() {
		return sellerShipperPublicName;
	}

	public void setSellerShipperPublicName(String sellerShipperPublicName) {
		this.sellerShipperPublicName = sellerShipperPublicName;
	}
}
