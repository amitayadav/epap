package com.auction.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auction_status_codes")
public class AuctionStatusCodes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Short auctionStatusCode;
	private String auctionStatusCodeMeaning;

	public AuctionStatusCodes() {
	}

	public AuctionStatusCodes(Short auctionStatusCode) {
		this.auctionStatusCode = auctionStatusCode;
	}

	public AuctionStatusCodes(String auctionStatusCodeMeaning) {
		this.auctionStatusCodeMeaning = auctionStatusCodeMeaning;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "auction_status_code", unique = true, nullable = false)
	public Short getAuctionStatusCode() {
		return this.auctionStatusCode;
	}

	public void setAuctionStatusCode(Short auctionStatusCode) {
		this.auctionStatusCode = auctionStatusCode;
	}

	@Column(name = "auction_status_code_meaning", nullable = false, length = 10)
	public String getAuctionStatusCodeMeaning() {
		return this.auctionStatusCodeMeaning;
	}

	public void setAuctionStatusCodeMeaning(String auctionStatusCodeMeaning) {
		this.auctionStatusCodeMeaning = auctionStatusCodeMeaning;
	}

}
