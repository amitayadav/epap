package com.auction.controller.test;

import org.apache.commons.lang.StringUtils;

public class Testing {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String buyerNumber ="P18";
		String formatted = String.format("%09d", 10);
		String  PurchaseInvoices =buyerNumber+formatted;
		System.out.println(PurchaseInvoices);
		}
}
