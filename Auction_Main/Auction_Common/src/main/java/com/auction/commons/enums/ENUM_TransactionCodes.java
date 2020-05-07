package com.auction.commons.enums;

public enum ENUM_TransactionCodes {

	ADJUSTMENT('A',"Adjustment"),
	CLOSING_BALANCE('C',"Closing Balance"),
	DEPOSIT('D',"Deposit"),
	OPENING_BALANCE('O',"Opening Balance"),
	PURCHASE('P', "Purchase"),
	SALES('S', "Sales"),
	SHIPPING_REVENUE('R',"Revenue"),
	WITHDRAWAL('W',"Withdrawal"),
	ONBEHALF_OF_SELLER('B',"On Behalf of Seller"),
	FOR_SHIPPING_TO('F',"For Shipping To"),
	COMMISSION('M',"Commission"),
	VAT('V',"Vat"),
	ADVANCE ('A', "Advance Taken"),
	ADJUSTCREDIT ('K', "Adjustment Plus"),
	ADJUSTDEBIT ('J', "Advance Minus"),
	ADVANCEPAYOFF ('Y',"Advance Payoff");
	private char type;
	private String desc;

	ENUM_TransactionCodes(char type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public char getType() {
		return this.type;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return desc;
	}
}