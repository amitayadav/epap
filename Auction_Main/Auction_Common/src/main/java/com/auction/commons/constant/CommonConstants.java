package com.auction.commons.constant;

import java.text.SimpleDateFormat;

public class CommonConstants {

	// Common constant files
	public static final String ARABIC = "ar";
	public static final String ENGLISH = "en";
	public static final short DUFAULT_ROYALTY_CODE = 1;

	public static final String PROFILE_PICTURE_PATH = "PROFILE_PICTURE_PATH";
	public static final String SELLER_PICTURE_PATH = "SELLER_PICTURE_PATH";
	public static final String SELLER_OFFER_PICTURE_PATH = "SELLER_OFFER_PICTURE_PATH";

	public static final String BALANCE_CREDIT_CODE = "C";
	public static final String BALANCE_DEBIT_CODE = "D";
	public static final String BALANCE_ADVANCE_CODE ="A";
	public static final String BALANCE_ADJUSTCREDIT_CODE ="K";
	public static final String BALANCE_ADJUSTDEBIT_CODE ="J";

	public static final String BALANCE_CREDIT_NAME = "CREDIT";
	public static final String BALANCE_DEBIT_NAME = "DEBIT";

	public static final String INCREASE_PRICE = "increasePrice";
	public static final String DECREASE_PRICE = "decreasePrice";

	public static final short MAXIMUM_QUANTITY = 30000;

	public static final SimpleDateFormat UI_SIMPLE_DATE_FORMAT_WITH_DAYNAME = new SimpleDateFormat("EEE dd-MM-yyyy");
	public static final SimpleDateFormat UI_SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat UI_SIMPLE_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a");

	public static final SimpleDateFormat UI_AUCTION_SIMPLE_TIME_FORMAT = new SimpleDateFormat("hh:mm a");

	public static final String INDIA_TIME_ZONE = "GMT+5:30";
	public static final String RIYADH_TIME_ZONE = "UTC+3";

	public static int auctionDuration = 45;

	public static final String PROFILE_PICTURE = "PROFILE_PICTURE";
	public static final String SELLER_PICTURE = "SELLER_PICTURE";
	public static final String SELLER_OFFER_PICTURE = "SELLER_OFFER_PICTURE";
	
	public static final String WITHDRAWAL_BY="By";
	public static final String DEPOSIT_FROM="From";
	
	public static final String ANNOUNCEMENT="ON";

}