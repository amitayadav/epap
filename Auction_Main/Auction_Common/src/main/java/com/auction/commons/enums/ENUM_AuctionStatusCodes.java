package com.auction.commons.enums;

import java.util.ArrayList;
import java.util.List;

public enum ENUM_AuctionStatusCodes {

	OPEN((short) 1, "Open"),
	RUNNING((short) 2, "Running"),
	SETTLING((short) 3, "Settling"),
	FINISHED((short) 4, "Finished"),
	CANCELLED((short) 5, "Cancelled"),
	EXPIRED((short) 6, "Expired");

	private short status;
	private String desc;

	ENUM_AuctionStatusCodes(short status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public short getStatus() {
		return this.status;
	}

	public String getDesc() {
		return this.desc;
	}

	@Override
	public String toString() {
		return this.desc;
	}

	public static String getDesc(short status) {
		for (ENUM_AuctionStatusCodes codes : ENUM_AuctionStatusCodes.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}

	public static Short[] getAllStatusCodes() {
		List<Short> typeList = new ArrayList<Short>(0);
		for (ENUM_AuctionStatusCodes codes : ENUM_AuctionStatusCodes.values()) {
			typeList.add(codes.getStatus());
		}
		return typeList.toArray(new Short[0]);
	}

	public static Short[] getDailyAuctionStatusList() {
		return new Short[] { ENUM_AuctionStatusCodes.OPEN.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.FINISHED.getStatus() };
	}

	public static boolean isOpenOrRunning(short status) {
		if (status == ENUM_AuctionStatusCodes.OPEN.getStatus() || status == ENUM_AuctionStatusCodes.RUNNING.getStatus()) {
			return true;
		}
		return false;
	}
}