package com.auction.commons.enums;

import java.util.ArrayList;
import java.util.List;

public enum ENUM_AuctionBuyerBidStatusCodes {

	OPEN((short) 1, "Open"),
	RUNNING((short) 2, "Running"),
	SETTLING((short) 3, "Settling"),
	FINISHED((short) 4, "Finished"),
	CANCELLED((short) 5, "Cancelled"),
	EXPIRED((short) 6, "Expired"),
	INPROGRESS((short) 7 , "In Progress");

	private short status;
	private String desc;

	ENUM_AuctionBuyerBidStatusCodes(short status, String desc) {
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

		for (ENUM_AuctionBuyerBidStatusCodes codes : ENUM_AuctionBuyerBidStatusCodes.values()) {
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
			return (status == ENUM_AuctionBuyerBidStatusCodes.CANCELLED.getStatus());
		}
	}

	public static Short[] getDailyAuctionBidStatus() {
		return new Short[] { ENUM_AuctionBuyerBidStatusCodes.OPEN.getStatus(), ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(),
				ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus() };
	}

	public static Short[] getAllAuctionBidStatus() {
		List<Short> codeList = new ArrayList<Short>(0);
		for (ENUM_AuctionBuyerBidStatusCodes codes : ENUM_AuctionBuyerBidStatusCodes.values()) {
			codeList.add(codes.getStatus());
		}
		return codeList.toArray(new Short[0]);
	}
}