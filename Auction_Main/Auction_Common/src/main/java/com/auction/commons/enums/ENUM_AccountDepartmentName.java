package com.auction.commons.enums;

public enum ENUM_AccountDepartmentName {

	MANAGEMENT(1, "Management"),
	OPERATIONS(2, "Operations"),
	FINANCE(3, "Finance"),
	RELATIONS(4, "Relations"),
	SHIPPING(5, "Shipping");
	
	private int code;
	private String name;

	ENUM_AccountDepartmentName(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}