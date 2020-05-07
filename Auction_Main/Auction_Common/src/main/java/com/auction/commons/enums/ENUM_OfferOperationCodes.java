package com.auction.commons.enums;

public enum ENUM_OfferOperationCodes {

	PLACED((short) 1, "Placed"),
	UPDATED((short) 2, "Updated"),
	SETTLING((short) 3, "Settling"),
	EXECUTED((short) 4, "Executed"), 
	REMAINED_QTY((short) 5, "Remained Quantity"), 
	EXPIRED((short) 6, "Expired"), 
	CANCELLED((short) 7, "Cancelled");
	/*,ANCELLED_BY_MANAGEMENT((short) 7, "Cancelled By Management");*/

	private short status;
	private String desc;

	ENUM_OfferOperationCodes(short status, String desc) {
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
		for (ENUM_OfferOperationCodes codes : ENUM_OfferOperationCodes.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}

}