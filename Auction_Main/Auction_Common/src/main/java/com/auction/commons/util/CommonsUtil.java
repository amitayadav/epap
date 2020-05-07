package com.auction.commons.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


public class CommonsUtil {
	/* Default Prefix for Invoices */
	public static final String DEFAULT_SALES_INVOICE_PREFIX = "S75";
	public static final String DEFAULT_PURCHASE_INVOICE_PREFIX = "P18";
	public static final String DEFAULT_SHIPPER_INVOICE_PREFIX = "H62";
	
	
	public static String getBaseUrl(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		url = StringUtils.remove(url, queryString);
		String uri = request.getRequestURI();
		url = StringUtils.remove(url, uri);
		return url + request.getServletContext().getContextPath();
	}

	public static String generatesalesNoticesNo( Integer salesNoticesNo) {
		String formatted = String.format("%09d", salesNoticesNo);
		return DEFAULT_SALES_INVOICE_PREFIX + formatted;
	}
	
	
	public static String  generatePurchaseInvoices( Integer PurchaseInvoicesNo) {
		String formatted = String.format("%09d", PurchaseInvoicesNo);
		return DEFAULT_PURCHASE_INVOICE_PREFIX + formatted;
	}
	
	public static int generateNumber()
	{
	int aNumber = 0; 
	aNumber = (int)((Math.random() * 90000000)+10000000); 
	return aNumber;
	}
	
	/**
	* this method check minimum values
	* @param ownerAvailableBalance
	* @param purchaseower
	* @return
	*/
	public static  Double  checkMinimum(Double ownerAvailableBalance,Double purchaseower) {

	if(ownerAvailableBalance > purchaseower ) {
	return purchaseower;
	}else {
	return ownerAvailableBalance;
	}

	}
	public static void main(String arg[]) {
		generateNumber();
	}
	
	
}