package com.auction.commons.enums;

public enum ENUM_ProductCatalogStatus {

	APPROVED((short) 1, "Approved"), PENDING((short) 2, "Pending"), SUSPENDED((short) 3, "Suspended");
	private short status;
	private String desc;

	ENUM_ProductCatalogStatus(short status, String desc) {
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

		for (ENUM_ProductCatalogStatus codes : ENUM_ProductCatalogStatus.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}
}
