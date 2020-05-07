package com.auction.model.entity;

import java.io.Serializable;
import java.util.Date;

public class AuctionTradeGroupView implements Serializable{
	private Date date;
	private Integer productId;
	private Long quantity;
	private Double price;
	
	public AuctionTradeGroupView(Date date, Integer productId, Long quantity, Double price) {
		this.date = date;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "AuctionTradeGroupView [date=" + date + ", productId=" + productId + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
}
