package com.auction.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.ShipperTransactionsBean;
import com.auction.service.ShipperTransactionsService;


@Component
public class ShipperTransactionHelper {

	@Autowired
	private ShipperTransactionsService shipperTransactionsService;
	
	@Autowired
	private VatBalanceHelper vatBalanceHelper;
		 
	@Value("${auction.default.shipper.vat}")
	private String defaultShipperVat1;
	
	private float defaultShipperVat=0.00f;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	public Integer createShippersTransaction(AuctionBuyersBean auctionBuyersBean, AccountProfileBean shipperAccountProfileBean) {
		logger.info("ShipperTransactionHelper call createShippersTransaction method");
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
		ShipperTransactionsBean shipperTransactionsBean = new ShipperTransactionsBean();
			/*System.out.println(defaultShipperVat1);*/
		
		BigDecimal grossRevenue = new BigDecimal((auctionSellersBean.getSellerShippingCharge()));
		BigDecimal netRevenue = grossRevenue;
		BigDecimal royaltyAmount = (netRevenue.multiply(new BigDecimal(shipperAccountProfileBean.getRoyaltyCodesBean().getRoyaltyValue())).divide(new BigDecimal(100)));
		//BigDecimal vatAmount = (royaltyAmount.multiply(new BigDecimal(defaultShipperVat).divide(new BigDecimal(100))));
		
		netRevenue = netRevenue.subtract(royaltyAmount);
		//netRevenue = netRevenue.subtract(vatAmount);
		netRevenue = netRevenue.subtract(new BigDecimal(defaultShipperVat));
		shipperTransactionsBean.setAccountLocationsByShippingFromLocation(auctionSellersBean.getAccountLocationsBean());
		shipperTransactionsBean.setAccountLocationsByShippingToLocation(auctionBuyersBean.getAccountLocationsBean());
		shipperTransactionsBean.setAccountProfile(shipperAccountProfileBean);
		shipperTransactionsBean.setAuctionBuyers(auctionBuyersBean);
		shipperTransactionsBean.setGrossRevenue(grossRevenue);
		shipperTransactionsBean.setRoyaltyAmount(royaltyAmount);
		shipperTransactionsBean.setRoyaltyPercentage(shipperAccountProfileBean.getRoyaltyCodesBean().getRoyaltyValue());
		shipperTransactionsBean.setTransactionCreation(InternetTiming.getInternetDateTime());		
		shipperTransactionsBean.setVatAmount(new BigDecimal(defaultShipperVat));
		shipperTransactionsBean.setVatPercentage(defaultShipperVat);
		shipperTransactionsBean.setNetRevenue(netRevenue);
		shipperTransactionsService.save(shipperTransactionsBean);
		vatBalanceHelper.createShipperVatCredit(shipperTransactionsBean,new BigDecimal(defaultShipperVat));
		logger.info("=== ending  createShippersTransaction method ===");
		return shipperTransactionsBean.getTransactionId();
	}
}