package com.auction.commons.enums;

import java.util.ArrayList;
import java.util.List;

public enum ENUM_AccountLocationStatus {

	ACTIVE((short) 1, "Active"), ONHOLD((short) 2, "ON Hold"), DELETED((short) 3, "Deleted");
	private short status;
	private String desc;

	ENUM_AccountLocationStatus(short status, String desc) {
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
		for (ENUM_AccountLocationStatus codes : ENUM_AccountLocationStatus.values()) {
			if (codes.getStatus() == status) {
				return codes.getDesc();
			}
		}
		return null;
	}

	public static Short[] getNonDeletedStatus() {
		List<Short> statusList = new ArrayList<Short>();
		for (ENUM_AccountLocationStatus status : ENUM_AccountLocationStatus.values()) {
			if (status.getStatus() != ENUM_AccountLocationStatus.DELETED.getStatus()) {
				statusList.add(status.getStatus());
			}
		}
		return statusList.toArray(new Short[0]);
	}

}