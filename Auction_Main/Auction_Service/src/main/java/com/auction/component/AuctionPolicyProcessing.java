package com.auction.component;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.enums.ENUM_PickupTicketsAction;
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.BankDetailsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountProfileService;
import com.auction.service.AccountTypeCodesService;
import com.auction.service.AuctionSellersService;
import com.auction.service.LoginDetailsService;
import com.auction.service.PickupTicketsService;
import com.auction.service.util.AuctionCalculationHelper;

@Component
public class AuctionPolicyProcessing {

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private LoginDetailsService loginDetailsService;

/*	@Autowired
	private BankDetailsService bankDetailsService;*/

	@Autowired
	private BuyerTransactionHelper buyerTransactionHelper;

	@Autowired
	private BuyerBalanceHelper buyerBalanceHelper;

	@Autowired
	private LogHelper logHelper;

	@Autowired
	private AuctionOfferHelper acceptSellerBidHelper;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private AccountTypeCodesService accountTypeCodesService;

	@Autowired
	private AuctionStatusHelper auctionStatusHelper;
	
	@Autowired
	private BuyerBankDetailsHelper  buyerBankDetailsHelper;
	
	@Autowired
	private PickupTicketsService  pickupTicketsService;
	
	@Autowired
	private AuctionBidHelper auctionBidHelper;

	private AuctionLogger logger = new AuctionLogger(getClass().getName());
	
	public void auctionBidProcessing(AuctionBuyersBean auctionBuyersBean) {
		logger.info("Daily Auction Info "+"  "+" ProductId:="+auctionBuyersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId()+"  "+"Daily Auction Name:="+auctionBuyersBean.getDailyAuctionsBean().getProductCatalogBean().getProductName());
		logger.info("  "+" Daily Auction Id"+auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId());
		logger.info("Seller Info In Auction Seller Id"+" "+auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
		logger.info("Executed Buyer(Settling)rcalling method buyerBidSettling "+"  "+auctionBuyersBean.getAuctionBuyersId()+"  "+"auctionBuyers Id:= "+auctionBuyersBean.getAccountProfileBean().getAccountId());
		buyerBidSettling(auctionBuyersBean); 
		
	}
	private void buyerBidSettling(AuctionBuyersBean auctionBuyersBean) {
		logger.info("Executed Buyer(Settling) calling method endAuctionBuyerBid "+"  ");
		auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.SETTLING, 0, 0F);
		logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.SETTLING);
	}	
	public void auctionOfferProcessing(AuctionSellersBean auctionSellersBean) {
		if (auctionSellersBean.getAvailableQuantity() == 0) {
			auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus());
		}
		auctionSellersService.save(auctionSellersBean);
		
	}
	/* Order execution level 1 end */
	 
	

	public void acceptCompleteBuyerBid(AuctionBuyersBean auctionBuyersBean,int currentLoginId) {
		logger.info("Accept Buyer Bid OR (Confirm delivery)"+"    ");
		logger.info(" - "+" Daily Auction Id"+auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId() + " "+"Auction Buyaer Id"+auctionBuyersBean.getAuctionBuyersId()+"  -  "+"Auction Seller Id"+auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
		Character buyerAccountTypeCode = loginUser.getAccountTypeCodesBean().getAccountType();
		AccountProfileBean accountProfileBean = loginUser.getAccountProfileBean();
		BankDetailsBean bankDetailsBean = null;
		ENUM_TransactionCodes transactionCode = ENUM_TransactionCodes.PURCHASE;
		String tranDesc = "";
		if (buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER.getType())) {
			bankDetailsBean = accountProfileBean.getBankDetailsBean();

		} else if (buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER_AGENT.getType())) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			bankDetailsBean = ownerLoginDetailsBean.getAccountProfileBean().getBankDetailsBean();
		}
		buyerBankDetailsHelper.resetBuyerUserBalance(auctionBuyersBean); // Return block amount
		buyerBankDetailsHelper.substractBidActualExecutionCharge(auctionBuyersBean);
		
		
		// Preparing Transaction description with as per auction seller.
		AccountTypeCodesBean sellerAccountType = accountTypeCodesService
				.getAccountTypeCodeByAccountId(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getAccountId());
		Character sellerAccountTypeCode = sellerAccountType.getAccountType();

		if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER.getType())) {
			tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " from " + "("
					+ auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getPublicName() + ")");
		} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER_AGENT.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER.getType())) {
			tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " from " + "("
					+ accountProfileService.findOwnerPublicNameByAgentAccountId(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getAccountId()) + ") Agent ("
					+ auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getPublicName() + ")");
		} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER_AGENT.getType())) {
			tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " by Agent " + "(" + auctionBuyersBean.getAccountProfileBean().getPublicName() + ")"
					+ " from " + "(" + auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getPublicName() + ")");
		} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER_AGENT.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER_AGENT.getType())) {
			tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " by Agent (" + auctionBuyersBean.getAccountProfileBean().getPublicName() + ") from ("
					+ accountProfileService.findOwnerPublicNameByAgentAccountId(auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getAccountId()) + ") Agent ("
					+ auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getPublicName() + ")");
		}

		// after finishing bid accepting flow and transaction completion flow
		// appropriate log entry will create.
		logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXECUTED);

		auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.FINISHED, auctionBuyersBean.getExecutedQuantity(),
				auctionBuyersBean.getExecutedPrice());

		// creates transaction when bid accepted...
		Integer buyerTransactionId = buyerTransactionHelper.createBuyersTransaction(auctionBuyersBean);

		// manages balance in bank details tables after completed transaction.
		double executedBidAmount=AuctionCalculationHelper.calculateExecutedBidAmountOnConfirmDelivery(auctionBuyersBean);
		String comment="";
		buyerBalanceHelper.createBuyerBalanceDebit(buyerTransactionId, new BigDecimal(executedBidAmount), accountProfileBean, tranDesc, transactionCode,comment);

		// Managing seller transaction and balance updating.
		acceptSellerBidHelper.updateSellersBalance(auctionBuyersBean);

		
		/** Buyer Auction Trades   */
		auctionBidHelper.recordAuctionTrades(auctionBuyersBean);
		
		/** Update Pickup Tickets */
		pickupTicketsService.updatePickupTicketActionByBuyerAccountId(ENUM_PickupTicketsAction.Confirmed.getStatus(),currentLoginId,InternetTiming.getInternetDateTime(),auctionBuyersBean.getAccountProfileBean().getAccountId()
				,auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getAccountId(),auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId());
		logger.info("Accept Buyer Bid OR (Confirm delivery) ending ");
	}
	
}