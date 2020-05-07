package com.auction.commons.enums;

public enum ENUM_AgentPrivileges {

	PRIVILEGE1((short) 1), PRIVILEGE2((short) 2);

	private short privilege;

	/**
	 * The agent is allowed to trade only, e.g., place offers or bids, adjust prices
	 * and quantities and cancel orders. and quantities and cancel orders. 
	 * 
	 * The agent is allowed to trade as in privileges = 1, plus the ability to view account
	 * and produce reports.
	 */
	private ENUM_AgentPrivileges(short privilege) {
		this.privilege = privilege;
	}

	public short getPrivilege() {
		return this.privilege;
	}
}
