package com.auction.commons.enums;

public enum ENUM_PickupTicketsAction {
	
	Notconfirmed((int)0),Confirmed((int)1),Cancelled((int)2);
	private int status;
	
	ENUM_PickupTicketsAction(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
