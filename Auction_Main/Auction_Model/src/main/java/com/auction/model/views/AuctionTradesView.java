package com.auction.model.views;

import com.auction.commons.constant.CommonConstants;
import com.auction.model.bean.AuctionTradesBean;
import com.auction.model.entity.AuctionTrades;

public class AuctionTradesView {

	private String tradeTime;
	private String tradeDate;
	private String product;
	private Integer soldQuantity;
	private Float soldPrice;

	public AuctionTradesView() {
	}

	public AuctionTradesView(AuctionTradesBean bean) {
		if (null != bean && null != bean.getId()) {
			this.tradeDate = CommonConstants.UI_SIMPLE_DATE_FORMAT.format(bean.getId().getLogTimestamp());
			this.tradeTime = CommonConstants.UI_SIMPLE_TIME_FORMAT.format(bean.getId().getLogTimestamp());
			this.soldQuantity = bean.getSoldQuantity();
			this.soldPrice = bean.getSoldPrice();
			this.product = bean.getId().getProductCatalogBean().getProductGroupName() + ","
					+ bean.getId().getProductCatalogBean().getProductName() + ","
					+ bean.getId().getProductCatalogBean();

		}
	}

	public AuctionTradesView(AuctionTrades entity) {
		if (null != entity) {
			this.tradeDate = CommonConstants.UI_SIMPLE_DATE_FORMAT.format(entity.getId().getLogTimestamp());
			this.tradeTime = CommonConstants.UI_SIMPLE_TIME_FORMAT.format(entity.getId().getLogTimestamp());
			this.soldQuantity = entity.getSoldQuantity();
			this.soldPrice = entity.getSoldPrice();
			this.product = entity.getId().getProductCatalog().getProductGroupName() + ","
					+ entity.getId().getProductCatalog().getProductName() + ","
					+ entity.getId().getProductCatalog().getProductTypeName();
		}
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
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