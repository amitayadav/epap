package com.auction.commons.enums;

public enum ENUM_NotificationCodes {

	SMS_ONLY((short)1), EMAIL_ONLY((short)2), BOTH((short)3);
	private short status;

	ENUM_NotificationCodes(short status) {
		this.status = status;
	}

	public short getStatus() {
		return status;
	}

}