package com.auction.commons.util;

public class RegExpPatterns {

	public static String EMAIL = "^(([a-zA-Z0-9_.-])+([@]{1})+([a-zA-Z0-9_.-])+([.]{1})+([a-zA-Z.]{2,3}))$";
	public static String PASSWORD = "^(?=[a-zA-Z])(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-z0-9]{6,20}$";
	public static String MOBILE = "[0-9]{10}";
	public static String NUMBER = "\\d+";
	public static String PRICE = "^([0-9]{0,4}((.)[0-9][0]{0,1}))$";
}