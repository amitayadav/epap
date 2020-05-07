package com.auction.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.bean.BuyerTransactionsBean;
import com.auction.model.bean.SellerTransactionsBean;
import com.auction.model.bean.ShipperTransactionsBean;
import com.auction.model.bean.TransactionCodeBean;
import com.auction.model.bean.VatBalanceBean;
import com.auction.service.VatService;

/*
 * Helper class to maintain Vat Financial Details
 * 
 */
@Component
public class VatBalanceHelper {

	@Autowired
	private VatService vatService;
	
	@Autowired 
	private EpapBalanceHelper epapBalanceHelper;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	private static String VAT_TITLE = "VAT";
	private static String VAT_SEPRATOR = ":";
	private static String VAT_SELLER_TITLE = "Seller";
	private static String VAT_BUYER_TITLE = "Buyer";
	private static String VAT_SHIPPER_TITLE = "Shipper";
	private static String BLANK_SPACE = " ";

	public void createSellerVatCredit(SellerTransactionsBean sellerTransactionsBean, BigDecimal debitCredit) {
		logger.info("VatBalanceHelper call createSellerVatCredit method ");
		if (debitCredit.compareTo(new BigDecimal("0.0")) != 0) {
			VatBalanceBean vatBalanceBean = new VatBalanceBean();
			vatBalanceBean.setAccountProfileBean(sellerTransactionsBean.getAccountProfileBean());
			TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.VAT.getType());
			vatBalanceBean.setTransactionCodebean(transactionCodeBean);
			vatBalanceBean.setTransactionId(sellerTransactionsBean.getTransactionId());
			vatBalanceBean.setTransactionDate(sellerTransactionsBean.getTransactionCreation());
			vatBalanceBean.setDebitCredit(debitCredit);
			vatBalanceBean.setTransactionDescription(
					createSellerVatTransaction(sellerTransactionsBean.getAccountProfileBean().getPublicName()));
			vatBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.SELLER.getType()));
			vatBalanceBean.setBalance(getNewVatBalance(debitCredit));
			vatBalanceBean.setComments("");
			vatService.save(vatBalanceBean);
			logger.info("=== ending  call createSellerVatCredit method ");
		}

	}

	public void createBuyerVatCredit(BuyerTransactionsBean buyerTransactionsBean, BigDecimal debitCredit) {
		if (debitCredit.compareTo(new BigDecimal("0.0")) != 0) {
			logger.info("=========Start =======Ereate Buyer Vat Credit===============");
			logger.info("Auction Buyer ID"+" = :"+buyerTransactionsBean.getAuctionBuyersBean().getAuctionBuyersId());
			logger.info("Buyer ID"+" = :"+buyerTransactionsBean.getAccountProfileBean().getAccountId());
			logger.info("Buyer Public Name"+" = :"+buyerTransactionsBean.getAccountProfileBean().getPublicName());
			VatBalanceBean vatBalanceBean = new VatBalanceBean();
			vatBalanceBean.setAccountProfileBean(buyerTransactionsBean.getAccountProfileBean());
			TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.VAT.getType());
			vatBalanceBean.setTransactionCodebean(transactionCodeBean);
			vatBalanceBean.setTransactionId(buyerTransactionsBean.getTransactionId());
			vatBalanceBean.setTransactionDate(buyerTransactionsBean.getTransactionCreation());
			vatBalanceBean.setDebitCredit(debitCredit);
			vatBalanceBean.setTransactionDescription(
					createBuyerVatTransaction(buyerTransactionsBean.getAccountProfileBean().getPublicName()));
			vatBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.BUYER.getType()));
			vatBalanceBean.setBalance(getNewVatBalance(debitCredit));
			vatBalanceBean.setComments("");
			logger.info("transactionCodeBean"+" = :"+transactionCodeBean);
			logger.info("BuyerTransaction Id "+" = :"+buyerTransactionsBean.getTransactionId());
			logger.info("BuyerTransaction Creation"+" = :"+buyerTransactionsBean.getTransactionCreation());
			logger.info("BuyerTransaction debitCredit"+" = :"+debitCredit);
			vatService.save(vatBalanceBean);
			logger.info("=========End =======Ereate Buyer Vat Credit===============");
		}

	}

	public void createShipperVatCredit(ShipperTransactionsBean shipperTransactionsBean, BigDecimal debitCredit) {
		logger.info("VatBalanceHelper call createShipperVatCredit method ");
		if (debitCredit.compareTo(new BigDecimal("0.0")) != 0) {
			VatBalanceBean vatBalanceBean = new VatBalanceBean();
			vatBalanceBean.setAccountProfileBean(shipperTransactionsBean.getAccountProfile());
			TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.VAT.getType());
			vatBalanceBean.setTransactionCodebean(transactionCodeBean);
			vatBalanceBean.setTransactionId(shipperTransactionsBean.getTransactionId());
			vatBalanceBean.setTransactionDate(shipperTransactionsBean.getTransactionCreation());
			vatBalanceBean.setDebitCredit(debitCredit);
			vatBalanceBean.setTransactionDescription(
					createShipperVatTransaction(shipperTransactionsBean.getAccountProfile().getPublicName()));
			vatBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.SHIPPER.getType()));
			vatBalanceBean.setBalance(getNewVatBalance(debitCredit));
			vatBalanceBean.setComments("");
			vatService.save(vatBalanceBean);
			logger.info("=== ending  call createShipperVatCredit method ");
		}

	}

	private String createSellerVatTransaction(String sellerName) {
		String transactionDesc = VAT_TITLE;
		transactionDesc = transactionDesc + VAT_SEPRATOR + VAT_SELLER_TITLE + BLANK_SPACE + sellerName;
		return transactionDesc;
	}

	private String createBuyerVatTransaction(String buyerName) {
		String transactionDesc = VAT_TITLE;
		transactionDesc = transactionDesc + VAT_SEPRATOR + VAT_BUYER_TITLE + BLANK_SPACE + buyerName;
		return transactionDesc;
	}

	private String createShipperVatTransaction(String shipperName) {
		String transactionDesc = VAT_TITLE;
		transactionDesc = transactionDesc + VAT_SEPRATOR + VAT_SHIPPER_TITLE + BLANK_SPACE + shipperName;
		return transactionDesc;
	}

	public void depositToVatAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean,String comments) {
		logger.info("VatBalanceHelper call depositToVatAccount method ");
		Integer transactionId= epapBalanceHelper.depositToVatEpapAccount(debitCredit,accountProfileBean,comments);
		VatBalanceBean vatBalanceBean = new VatBalanceBean();
		vatBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType());
		vatBalanceBean.setTransactionCodebean(transactionCodeBean);
		vatBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		vatBalanceBean.setDebitCredit(debitCredit);
		vatBalanceBean.setTransactionId(transactionId);
		vatBalanceBean.setTransactionDescription(ENUM_TransactionCodes.DEPOSIT.getDesc()+ "-" +accountProfileBean.getAccountId());
		vatBalanceBean
				.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.ADMIN_VAT.getType()));
		vatBalanceBean.setBalance(getNewVatBalance(debitCredit));
		vatBalanceBean.setComments(comments);
		vatService.save(vatBalanceBean);
		logger.info("=== ending  call depositToVatAccount method ");
	}

	public void withdrawalFromVatAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean,String comments) {
		logger.info("VatBalanceHelper call withdrawalFromVatAccount method ");
		Integer transactionId=epapBalanceHelper.withdrawalFromVatEpapAccount(debitCredit,accountProfileBean,comments);
		VatBalanceBean vatBalanceBean = new VatBalanceBean();
		vatBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.WITHDRAWAL.getType());
		vatBalanceBean.setTransactionCodebean(transactionCodeBean);
		vatBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		vatBalanceBean.setDebitCredit(debitCredit.negate());
		vatBalanceBean.setTransactionId(transactionId);
		vatBalanceBean.setTransactionDescription(ENUM_TransactionCodes.WITHDRAWAL.getDesc()+ "-" +accountProfileBean.getAccountId());
		vatBalanceBean
				.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.ADMIN_VAT.getType()));
		vatBalanceBean.setBalance(getNewVatBalance(debitCredit.negate()));
		vatBalanceBean.setComments(comments);
		vatService.save(vatBalanceBean);
		logger.info("=== ending  call withdrawalFromVatAccount method ");
	}

	public BigDecimal getAvailableBalance() {
		return getNewVatBalance(new BigDecimal(0.0));
	}

	private BigDecimal getNewVatBalance(BigDecimal debitCredit) {
		BigDecimal lastBalance = vatService.getLastVatBalance();
		BigDecimal newAvailableBlanace = new BigDecimal(0.0);
		if (null == lastBalance) {
			lastBalance = new BigDecimal(0.0);
		}
		newAvailableBlanace = new BigDecimal(lastBalance.doubleValue() + debitCredit.doubleValue());

		return newAvailableBlanace;

	}


	
}