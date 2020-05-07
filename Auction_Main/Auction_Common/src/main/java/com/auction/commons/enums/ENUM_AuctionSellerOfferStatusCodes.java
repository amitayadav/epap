package com.auction.commons.enums;

import java.util.ArrayList;
import java.util.List;

public enum ENUM_AuctionSellerOfferStatusCodes {

	OPEN((short) 1, "Open"),
	RUNNING((short) 2, "Running"),
	SETTLING((short) 3, "Settling"),
	FINISHED((short) 4, "Finished"),
	CANCELLED((short) 5, "Cancelled"),
	EXPIRED((short) 6, "Expired");

	private short status;
	private String desc;

	ENUM_AuctionSellerOfferStatusCodes(short status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public short getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return desc;
	}

	public static String getDesc(short status) {

		for (ENUM_AuctionSellerOfferStatusCodes codes : ENUM_AuctionSellerOfferStatusCodes.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}

	public static boolean isCancled(Short status) {
		if (null == status) {
			return false;
		} else {
			return (status == ENUM_AuctionSellerOfferStatusCodes.CANCELLED.getStatus());
		}
	}

	public static Short[] getDailyAuctionOfferStatus() {
		return new Short[] { ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus(), ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(),
				ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus() };
	}

	public static Short[] getAllAuctionOfferStatus() {
		List<Short> codeList = new ArrayList<Short>(0);
		for (ENUM_AuctionSellerOfferStatusCodes codes : ENUM_AuctionSellerOfferStatusCodes.values()) {
			codeList.add(codes.getStatus());

		}
		return codeList.toArray(new Short[0]);
	}

	public static boolean isOfferOpenOrRunning(short status) {
		if (status == ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus() || status == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()) {
			return true;
		}
		return false;
	}
}