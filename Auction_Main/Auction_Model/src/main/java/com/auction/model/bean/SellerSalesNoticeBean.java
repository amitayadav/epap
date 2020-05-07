package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.SellerSalesNotice;

public class SellerSalesNoticeBean {
	private Integer salesNoticeId;
	private SellerTransactionsBean sellerTransactionsBean;
	private String noticeNo;
	private Date noticeCreation;
	private String sellerPublicName;
	private String buyerPublicName;
	private String sellerShipperPublicName;

	public SellerSalesNoticeBean() {

	}

	public SellerSalesNoticeBean(SellerSalesNotice entity) {
		if (null != entity) {
			this.salesNoticeId = entity.getSalesNoticeId();
			this.sellerTransactionsBean = new SellerTransactionsBean(entity.getSellerTransactions());
			this.noticeNo = entity.getNoticeNo();
			this.noticeCreation = entity.getNoticeCreation();
			this.sellerPublicName = entity.getSellerPublicName();
			this.buyerPublicName = entity.getBuyerPublicName();
			this.sellerShipperPublicName = entity.getSellerShipperPublicName();

		}
	}

	public Integer getSalesNoticeId() {
		return salesNoticeId;
	}

	public void setSalesNoticeId(Integer salesNoticeId) {
		this.salesNoticeId = salesNoticeId;
	}

	public SellerTransactionsBean getSellerTransactionsBean() {
		return sellerTransactionsBean;
	}

	public void setSellerTransactionsBean(SellerTransactionsBean sellerTransactionsBean) {
		this.sellerTransactionsBean = sellerTransactionsBean;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public Date getNoticeCreation() {
		return noticeCreation;
	}

	public void setNoticeCreation(Date noticeCreation) {
		this.noticeCreation = noticeCreation;
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

	public String getSellerShipperPublicName() {
		return sellerShipperPublicName;
	}

	public void setSellerShipperPublicName(String sellerShipperPublicName) {
		this.sellerShipperPublicName = sellerShipperPublicName;
	}
}
