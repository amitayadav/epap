package com.auction.commons.enums;

import java.util.ArrayList;
import java.util.List;

public enum ENUM_AccountStatusCodes {

	/*
	 * DELETED((short) 1, "Deleted"), ACTIVE((short) 2, "Active"), PENDING((short) 3, "Pending"), BLOCKED((short) 4, "Blocked"), PENDING_APPROVAL((short) 5, "Pending Approval"),
	 * SUSPENDED_BY_OWNER((short) 6, "Suspended by Owner"), SUSPENDED_BY_ADMIN((short) 7, "Suspended by Admin"), APPROVED_BY_OWNER((short) 8, "Approved By Owner");
	 */

	PENDING_EMAIL_VERIFICATION((short) 1, "Pending Email Verification"),
	PENDING_PHASE_2((short) 2, "Pending Phase 2"),
	PENDING_OWNER_APPROVAL((short) 3, "Pending Owner Approval"),
	PENDING_ADMIN_APPROVAL((short) 4, "Pending Admin Approval"),
	BLOCKED((short) 5, "Blocked"),
	SUSPENDED_BY_OWNER((short) 6, "Suspended By Owner"),
	SUSPENDED_BY_ADMIN((short) 7, "Suspended By Admin"),
	DELETED((short) 8, "Deleted"),
	ACTIVE((short) 9, "Active"),
	APPROVED((short) 10, "Approved"),
	PENDING_PHONE_NUMBER((short) 11, "Pending Phone Number");

	private short status;
	private String desc;

	ENUM_AccountStatusCodes(short status, String desc) {
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

		for (ENUM_AccountStatusCodes codes : ENUM_AccountStatusCodes.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}

	public static Short[] getStatusCodesForUserDetailsUpdatingByAdmin() {
		List<Short> statusCodeList = new ArrayList<Short>();
		for (ENUM_AccountStatusCodes statusCode : ENUM_AccountStatusCodes.values()) {
			if (statusCode.getStatus() != ENUM_AccountStatusCodes.APPROVED.getStatus()) {
				statusCodeList.add(statusCode.getStatus());
			}
		}
		return statusCodeList.toArray(new Short[0]);
	}
	
	public static Short[] getAllStatusCodes() {
		List<Short> statusCodeList = new ArrayList<Short>();
		for (ENUM_AccountStatusCodes statusCode : ENUM_AccountStatusCodes.values()) {
			statusCodeList.add(statusCode.getStatus());
		}
		return statusCodeList.toArray(new Short[0]);
	}
}