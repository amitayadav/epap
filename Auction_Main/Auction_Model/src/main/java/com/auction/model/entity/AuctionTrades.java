package com.auction.model.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "auction_trades")
@AssociationOverrides({ @AssociationOverride(name = "id.logTimestamp", joinColumns = @JoinColumn(name = "log_timestamp")),
		@AssociationOverride(name = "id.productCatalog", joinColumns = @JoinColumn(name = "product_id")) })
public class AuctionTrades implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private AuctionTradesId id = new AuctionTradesId();
	private Integer soldQuantity;
	private Float soldPrice;

	public AuctionTrades() {
	}

	public AuctionTrades(AuctionTradesId id) {
		super();
		this.id = id;
	}

	@EmbeddedId
	public AuctionTradesId getId() {
		return this.id;
	}

	public void setId(AuctionTradesId id) {
		this.id = id;
	}

	@Column(name = "sold_quantity", nullable = false)
	public Integer getSoldQuantity() {
		return this.soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	@Column(name = "sold_price", nullable = false, precision = 6)
	public Float getSoldPrice() {
		return this.soldPrice;
	}

	public void setSoldPrice(Float soldPrice) {
		this.soldPrice = soldPrice;
	}

	@Override
	public String toString() {
		return "AuctionTrades [id=" + id + ", soldQuantity=" + soldQuantity + ", soldPrice=" + soldPrice + "]";
	}
	

}