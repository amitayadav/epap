package com.auction.commons.enums;

public enum ENUM_BidOperationCodes {

	PLACED((short) 1, "Placed"),
	UPDATED((short) 2, "Updated"),
	SETTLING((short) 3, "Settling"),
	EXECUTED((short) 4, "Executed"),
	REMAINED_QTY((short) 5, "Remained Quantity"),
	EXPIRED((short) 6, "Expired"),
	CANCELLED((short) 7, "Cancelled");
	/*,
	CANCELLED_BY_SELLER((short) 7, "Cancelled By Seller"),
	CANCELLED_BY_MANAGEMENT((short) 8, "Cancelled By Management");*/

	private short status;
	private String desc;

	ENUM_BidOperationCodes(short status, String desc) {
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
		for (ENUM_BidOperationCodes codes : ENUM_BidOperationCodes.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}
	
}