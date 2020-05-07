package com.auction.commons.enums;

public enum ENUM_CommentsStatus {

	PENDING((short) 1, "Pending"),CLOSE((short) 2, "Closed"), REJECTED((short) 3, "Rejected");
	private short status;
	private String desc;

	ENUM_CommentsStatus(short status, String desc) {
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

		for (ENUM_CommentsStatus codes : ENUM_CommentsStatus.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}
}