package com.auction.model.bean;

import java.util.Date;

import com.auction.model.entity.AuctionTradesId;

public class AuctionTradesIdBean {

	private Date logTimestamp;
	private ProductCatalogBean productCatalogBean;

	public AuctionTradesIdBean() {
	}

	public AuctionTradesIdBean(AuctionTradesId entity) {
		if (null != entity) {
			this.logTimestamp = entity.getLogTimestamp();
			this.productCatalogBean = new ProductCatalogBean(entity.getProductCatalog());
		}
	}

	public Date getLogTimestamp() {
		return logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	public ProductCatalogBean getProductCatalogBean() {
		return productCatalogBean;
	}

	public void setProductCatalogBean(ProductCatalogBean productCatalogBean) {
		this.productCatalogBean = productCatalogBean;
	}

}