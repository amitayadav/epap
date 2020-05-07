package com.auction.component;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.BankDetailsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.BankDetailsService;
import com.auction.service.LoginDetailsService;

@Component
public class SettlementBuyerBidHelper {

	@Autowired
	private BankDetailsService bankDetailsService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionOfferHelper acceptSellerBidHelper;

	@Autowired
	private BuyerTransactionHelper buyerTransactionHelper;

	@Autowired
	private BuyerBalanceHelper buyerBankDetailsHelper;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private LogHelper logHelper;

	private AuctionLogger logger = new AuctionLogger(this.getClass().getName());

	public void settelBuyerBidUserBalanceTransaction(AuctionSellersBean auctionSellersBean) {
		logger.info("SettlementBuyerBidHelper call settelBuyerBidUserBalanceTransaction");
		int loop = 1;
		try {
			List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(
					auctionSellersBean.getAuctionSellersId(), ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus());

			// .findByAuctionSellersAuctionSellersIdOrderByBidPriceDescAuctionBuyersIdAsc(auctionSellersBean.getAuctionSellersId());
			if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
				for (AuctionBuyersBean auctionBuyersBean : auctionBuyersBeanList) {
					if (null != auctionBuyersBean && auctionBuyersBean.getAuctionBuyersId() > 0) {
						logger.trace("auctionBuyersBean loop : " + loop);
						auctionSellersBean = auctionSellersService.findById(auctionSellersService.refresh(auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId()));
						auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);

						if (null != auctionSellersBean.getAvailableQuantity() && auctionSellersBean.getAvailableQuantity() > 0) {

							if (auctionBuyersBean.getBidQuantity() <= auctionSellersBean.getAvailableQuantity()) {
								BankDetailsBean bankDetailsBean = null;
								AccountProfileBean accountProfileBean = null;

								float royalty = auctionBuyersBean.getRoyaltyValue();
								float bidPrice = auctionBuyersBean.getBidPrice();
								int bidQty = auctionBuyersBean.getBidQuantity();
								float vat = auctionBuyersBean.getVat();
								float buyerShippingCharge = auctionBuyersBean.getBuyerShippingCharge();

								if (auctionSellersBean.getOfferPrice() > 0 && auctionBuyersBean.getBidPrice() > auctionSellersBean.getOfferPrice()) {
									bidPrice = auctionSellersBean.getOfferPrice();
								}

								LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
								if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER.getType()) {
									accountProfileBean = loginUser.getAccountProfileBean();
									bankDetailsBean = accountProfileBean.getBankDetailsBean();
								} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
									accountProfileBean = loginUser.getAccountProfileBean();
									LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
									bankDetailsBean = ownerLoginDetailsBean.getAccountProfileBean().getBankDetailsBean();
								}

								bidPrice = bidPrice * bidQty;
								float tempCommission=bidPrice * royalty / 100;
								float tempVat=bidPrice * vat / 100;
								bidPrice = bidPrice + tempCommission + tempVat+ buyerShippingCharge;
								
								

								BigDecimal newBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue());
								newBalance = newBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
								bankDetailsBean.setBlockedAmount(new BigDecimal(bankDetailsBean.getBlockedAmount().doubleValue() - bidPrice));
								bankDetailsService.save(bankDetailsBean);

								// creates transaction when bid accepted...
								Integer buyerTransactionId = buyerTransactionHelper.createBuyersTransaction(auctionBuyersBean);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Transaction created with id " + buyerTransactionId);

								// manages balance in bank details tables after completed transaction.
								String comment="";
								buyerBankDetailsHelper.createBuyerBalanceDebit(buyerTransactionId, new BigDecimal(bidPrice), accountProfileBean,
										auctionBuyersBean.getAuctionSellersBean().getAccountProfileBean().getPublicName(), ENUM_TransactionCodes.PURCHASE,comment);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Buyer Balance Debited transaction id " + buyerTransactionId
										+ " with amount " + bidPrice);

								// after finishing bid accepting flow and transaction completion flow
								// appropriate log entry will create.
								logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXECUTED);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Bid qutnatity " + auctionBuyersBean.getBidQuantity()
										+ " assiged to buyer id " + auctionBuyersBean.getAuctionBuyersId());

								// finishing status of BID
								auctionBuyersBean.setBuyerBidStatus(ENUM_AuctionStatusCodes.FINISHED.getStatus());
								auctionBuyersService.save(auctionBuyersBean);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Buyer Bid status is finished and buyer id "
										+ auctionBuyersBean.getAuctionBuyersId() + " with bid quantity " + auctionBuyersBean.getBidQuantity());

								// Managing seller transaction and balance updating.
								acceptSellerBidHelper.updateSellersBalance(auctionBuyersBean);
							} else {
								BankDetailsBean bankDetailsBean = null;
								AccountProfileBean accountProfileBean = null;

								float royalty = auctionBuyersBean.getRoyaltyValue();
								float vat = auctionBuyersBean.getVat();
								float buyerShippingCharge = auctionBuyersBean.getBuyerShippingCharge();
								float bidPrice = auctionBuyersBean.getBidPrice();
								int bidQty = auctionBuyersBean.getBidQuantity();

								if (auctionSellersBean.getOfferPrice() > 0 && auctionBuyersBean.getBidPrice() > auctionSellersBean.getOfferPrice()) {
									bidPrice = auctionSellersBean.getOfferPrice();
								}

								LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
								if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER.getType()) {
									accountProfileBean = loginUser.getAccountProfileBean();
									bankDetailsBean = accountProfileBean.getBankDetailsBean();
								} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
									accountProfileBean = loginUser.getAccountProfileBean();
									LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
									bankDetailsBean = ownerLoginDetailsBean.getAccountProfileBean().getBankDetailsBean();
								}

								
								float netPurchase = bidPrice * bidQty;
							    float tempCommission=(netPurchase * royalty / 100);
							    float tempVat=(netPurchase * vat / 100);
							    netPurchase += tempCommission + tempVat + buyerShippingCharge;
								

								// Calculating difference price
								int differenceQuantity = auctionBuyersBean.getBidQuantity() - auctionSellersBean.getAvailableQuantity();
								int remainingQuantity = differenceQuantity;

								differenceQuantity = auctionBuyersBean.getBidQuantity() - differenceQuantity;

							
								
								float differencePrice = bidPrice * differenceQuantity;
								float tempDifferenceCommission =  differencePrice * royalty / 100;
								float tempDifferenceVat =  differencePrice * vat / 100;
								differencePrice += tempDifferenceCommission + tempDifferenceVat + buyerShippingCharge;

								// final calculated difference amount.
								differencePrice = (netPurchase - differencePrice);

								bidPrice = netPurchase - differencePrice;

								bankDetailsBean.setBlockedAmount(new BigDecimal(bankDetailsBean.getBlockedAmount().doubleValue() - netPurchase));
								bankDetailsBean.setAvailableBalance(bankDetailsBean.getAvailableBalance().add(new BigDecimal(differencePrice)));
								bankDetailsService.save(bankDetailsBean);

								// auctionBuyersBean.setBidQuantity(differenceQuantity);

								// New flow starts from here....
								// creates transaction when bid accepted...
								Integer buyerTransactionId = buyerTransactionHelper.createBuyersTransaction(auctionBuyersBean);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Transaction created with id " + buyerTransactionId);

								// manages balance in bank details tables after completed transaction.
								String comment="";
								buyerBankDetailsHelper.createBuyerBalanceDebit(buyerTransactionId, new BigDecimal(bidPrice), accountProfileBean,
										auctionBuyersBean.getAccountProfileBean().getPublicName(), ENUM_TransactionCodes.PURCHASE,comment);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Buyer Balance Debited for transaction id " + buyerTransactionId
										+ " with amount " + bidPrice);

								// after finishing bid accepting flow and transaction completion flow
								// appropriate log entry will create.
								logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);

								// finishing status of BID
								auctionBuyersBean.setBuyerBidStatus(ENUM_AuctionStatusCodes.FINISHED.getStatus());
								auctionBuyersService.save(auctionBuyersBean);
								logger.info(this.getClass().getName() + " : settelBuyerBidUserBalanceTransaction : Buyer bid updated and buyer auction id : "
										+ auctionBuyersBean.getAuctionBuyersId());

								// Managing seller transaction and balance updating.
								acceptSellerBidHelper.updateSellersBalance(auctionBuyersBean);

								// Managing Remaining Quantity for Buyer Log
								logHelper.BuyerLogForRemainingQuantity(auctionBuyersBean, remainingQuantity);
								logger.info(this.getClass().getName() + " method : settelBuyerBidUserBalanceTransaction : id : " + auctionBuyersBean.getAuctionBuyersId()
										+ " remaining quantity " + remainingQuantity);

								logHelper.SellerLogForRemainingQuantity(auctionSellersBean, remainingQuantity);
								logger.info(this.getClass().getName() + " method : settelBuyerBidUserBalanceTransaction : id : " + auctionSellersBean.getAuctionSellersId()
										+ " remaining quantity " + remainingQuantity);
							}

						} else {
							auctionBuyersBean.setBuyerBidStatus(ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus());
							auctionBuyersService.save(auctionBuyersBean);
							logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
						}
					}

				}
			}
			logger.info("=== ending settelBuyerBidUserBalanceTransaction ===");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void settelSellerOfferQuantity(Integer auctionSellersId) {
		AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
		if (auctionSellersBean.getAvailableQuantity() > 0) {
			logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.EXPIRED);
		}
	}

}