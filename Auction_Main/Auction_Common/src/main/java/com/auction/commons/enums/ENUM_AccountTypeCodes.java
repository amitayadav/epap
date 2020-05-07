package com.auction.commons.enums;

import java.util.ArrayList;
import java.util.List;

public enum ENUM_AccountTypeCodes {

	ADMIN_SUPERUSER('A'),
	ADMIN_OPERATION('O'),
	ADMIN_FINANCE('F'),
	ADMIN_RELATIONS('R'),
	ADMIN_SHIPPING('N'),
	ADMIN_VAT('W'),
	BUYER('B'),
	BUYER_AGENT('Y'),
	SELLER('S'),
	SELLER_AGENT('E'),
	SHIPPER('H'),
	SHIPPER_AGENT('I'),
	USER('U'),
	VISITOR('V');

	private char type;

	ENUM_AccountTypeCodes(char type) {
		this.type = type;
	}

	public char getType() {
		return this.type;
	}

	public static Character[] getNonAdminTypes() {
		List<Character> typeList = new ArrayList<Character>();
		for (ENUM_AccountTypeCodes type : ENUM_AccountTypeCodes.values()) {
			if (type.getType() != ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_OPERATION.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_FINANCE.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType()) {
				typeList.add(type.getType());
			}
		}
		return typeList.toArray(new Character[0]);
	}

	public static Character[] getNonAdminAndNonUserTypes() {
		List<Character> typeList = new ArrayList<Character>();
		for (ENUM_AccountTypeCodes type : ENUM_AccountTypeCodes.values()) {
			if (type.getType() != ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_OPERATION.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_FINANCE.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_VAT.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType()
					&& type.getType() != ENUM_AccountTypeCodes.VISITOR.getType()) {
				typeList.add(type.getType());
			}
		}
		return typeList.toArray(new Character[0]);
	}

	public static Character[] getTypesForAdminRelationsType() {
		List<Character> typeList = new ArrayList<Character>();
		for (ENUM_AccountTypeCodes type : ENUM_AccountTypeCodes.values()) {
			if (type.getType() != ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_OPERATION.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_FINANCE.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_RELATIONS.getType() && type.getType() != ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType()
					&& type.getType() != ENUM_AccountTypeCodes.USER.getType() && type.getType() != ENUM_AccountTypeCodes.VISITOR.getType()) {
				typeList.add(type.getType());
			}
		}
		return typeList.toArray(new Character[0]);
	}

	public static Character[] getTypesForAdminOperationType() {
		List<Character> typeList = new ArrayList<Character>();
		for (ENUM_AccountTypeCodes type : ENUM_AccountTypeCodes.values()) {
			if (type.getType() != ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()
					&& type.getType() != ENUM_AccountTypeCodes.ADMIN_OPERATION.getType()) {
				typeList.add(type.getType());
			}
		}
		return typeList.toArray(new Character[0]);
	}

	public static Character[] getTypesForAdminSuperuserType() {
		List<Character> typeList = new ArrayList<Character>();
		for (ENUM_AccountTypeCodes type : ENUM_AccountTypeCodes.values()) {
			if (type.getType() != ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()) {
				typeList.add(type.getType());
			}
		}
		return typeList.toArray(new Character[0]);
	}

	public static boolean isUserAgent(char type) {
		return (ENUM_AccountTypeCodes.BUYER_AGENT.getType() == type || ENUM_AccountTypeCodes.SELLER_AGENT.getType() == type
				|| ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() == type);
	}

}