package com.auction.commons.enums;

public enum ENUM_AuctionRequestStatus {

	REJECTED((short) 1,"Rejected"), RQUESTED((short) 2,"Requested"), ONHOLD((short) 3,"On Hold"), APPROVED((short) 4,"Approved");
	private short status;
	private String desc;

	ENUM_AuctionRequestStatus(short status,String desc) {
		this.status = status;
		this.desc = desc;
	}

	public short getStatus() {
		return status;
	}
	public String getDesc() {
		return desc;
	}
}
