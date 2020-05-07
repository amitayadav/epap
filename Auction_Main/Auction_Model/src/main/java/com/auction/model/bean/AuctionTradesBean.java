package com.auction.model.bean;

import com.auction.model.entity.AuctionTrades;

public class AuctionTradesBean {

	private AuctionTradesIdBean id = new AuctionTradesIdBean();
	private Integer soldQuantity;
	private Float soldPrice;

	public AuctionTradesBean() {

	}

	public AuctionTradesBean(AuctionTrades entity) {
		if (null != entity) {
			this.id = new AuctionTradesIdBean(entity.getId());
			this.soldQuantity = entity.getSoldQuantity();
			this.soldPrice = entity.getSoldPrice();
		}

	}

	public AuctionTradesIdBean getId() {
		return id;
	}

	public void setId(AuctionTradesIdBean id) {
		this.id = id;
	}

	public Integer getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public Float getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(Float soldPrice) {
		this.soldPrice = soldPrice;
	}

}