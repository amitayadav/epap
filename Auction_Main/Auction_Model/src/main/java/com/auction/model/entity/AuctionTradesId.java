package com.auction.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class AuctionTradesId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Date logTimestamp;
	private ProductCatalog productCatalog;

	public AuctionTradesId() {
	}

	public AuctionTradesId(Date logTimestamp, ProductCatalog productCatalog) {
		this.logTimestamp = logTimestamp;
		this.productCatalog = productCatalog;
	}

	@Column(name = "log_timestamp", nullable = false, length = 19)
	public Date getLogTimestamp() {
		return this.logTimestamp;
	}

	public void setLogTimestamp(Date logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "product_id", nullable = false)
	public ProductCatalog getProductCatalog() {
		return this.productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

}