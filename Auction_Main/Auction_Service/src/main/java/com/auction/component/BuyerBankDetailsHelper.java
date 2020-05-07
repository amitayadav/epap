package com.auction.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.BankDetailsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AgentOwnerService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.BankDetailsService;
import com.auction.service.LoginDetailsService;

@Component
public class BuyerBankDetailsHelper {

	@Autowired
	private BankDetailsService bankDetailsService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AgentOwnerService agentOwnerService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private AuctionBuyersService auctionBuyersService;



	private AuctionLogger logger = new AuctionLogger(this.getClass());

	

	/**
	 * This method will update balance while cancel bid by admin or by seller.
	 * 
	 * @param auctionBuyersBean
	 * @return
	 */
	public List<String> resetBuyerUserBalance(AuctionBuyersBean auctionBuyersBean) {
		logger.info("Start =======Accept Buyer Bid resetBuyerUserBalance===");
		logger.info("Auction Buyer ID" + " = :" + auctionBuyersBean.getAuctionBuyersId());
		logger.info("Buyer ID" + " = :" + auctionBuyersBean.getAccountProfileBean().getAccountId());
		logger.info("Buyer Public Name" + " = :" + auctionBuyersBean.getAccountProfileBean().getPublicName());
		List<String> errorStack = null;
		if (null != auctionBuyersBean) {
			double blockedAmount = 0;
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
			BankDetailsBean bankDetailsBean = getBuyerBankDetails(auctionBuyersBean);
			if (null != bankDetailsBean) {
				blockedAmount = calculateBlockedAmount(auctionBuyersBean.getBidQuantity(), auctionBuyersBean.getBidPrice(), auctionBuyersBean.getVat(),
						auctionBuyersBean.getRoyaltyValue(), auctionBuyersBean.getBuyerShippingCharge());
				BigDecimal newAvailableBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue() + blockedAmount);
				logger.info("newAvailableBalance" + " = :" + newAvailableBalance);
				BigDecimal newBlockedAmount = new BigDecimal(0.0);
				BigDecimal blocked_amount = bankDetailsBean.getBlockedAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
				newBlockedAmount = new BigDecimal(blocked_amount.doubleValue() - blockedAmount);
				newAvailableBalance = newAvailableBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
				newBlockedAmount = newBlockedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
				bankDetailsBean.setAvailableBalance(newAvailableBalance);
				bankDetailsBean.setBlockedAmount(newBlockedAmount.abs());
				logger.info("newBlockedAmount" + " = :" + newBlockedAmount);
				bankDetailsService.save(bankDetailsBean);
				/** Buyer Agent reset PurchasingPoewr (rest Limit Spent) */
				if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
					resetPurchasingPoewr(loginUser, blockedAmount);
				}

			}
		}
		logger.info("Accept Buyer -- ResetBuyerUserBalance--End");
		return errorStack;
	}



	/**
	 * this is method substract UserBanlces
	 * 
	 * @param auctionBuyersBean
	 * @return
	 */
	public List<String> substractBidActualExecutionCharge(AuctionBuyersBean auctionBuyersBean) {
		List<String> errorStack = null;
		logger.info("=========Start =======Substract BidActualExecutionCharge===============");
		logger.info("Auction Buyer ID" + " = :" + auctionBuyersBean.getAuctionBuyersId());
		logger.info("Buyer ID" + " = :" + auctionBuyersBean.getAccountProfileBean().getAccountId());
		logger.info("Buyer Public Name" + " = :" + auctionBuyersBean.getAccountProfileBean().getPublicName());
		if (null != auctionBuyersBean) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
			BankDetailsBean bankDetailsBean = getBuyerBankDetails(auctionBuyersBean);
			if (null != bankDetailsBean) {
				double exexutedPrice = calculationTotalCost(bankDetailsBean, auctionBuyersBean);
				BigDecimal newAvailableBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue() - exexutedPrice);
				newAvailableBalance = newAvailableBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
				bankDetailsBean.setAvailableBalance(newAvailableBalance);
				logger.info("newAvailableBalance" + " = :" + newAvailableBalance);
				bankDetailsService.save(bankDetailsBean);
				/** check Buyer Agent And Add Limit Spent */
				if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
					addPurchasingPower(loginUser, exexutedPrice);
				}
			}
			logger.info("=========End======Substract BidActualExecutionCharge=============");
		}
		return errorStack;
	}

	/**
	 * this is method calculateBlockedAmount with buyerBid
	 * 
	 * @param bidQ
	 * @param bidPrice
	 * @param buyerVAT
	 * @param buyerCom
	 * @param buyerShipping
	 * @return
	 */
	private double calculateBlockedAmount(float bidQ, float bidPrice, float buyerVAT, float buyerCom, float buyerShipping) {
		logger.info("===========calculate Blocked Amount  ===========");
		BigDecimal b1 = new BigDecimal(bidQ).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal b2 = new BigDecimal(bidPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal b3 = new BigDecimal(buyerVAT).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal b4 = new BigDecimal(buyerCom).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal b5 = new BigDecimal(buyerShipping).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal blockedAmount1 = new BigDecimal(b1.doubleValue() * b2.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal buyerVAT1 = new BigDecimal((blockedAmount1.doubleValue() * b3.doubleValue()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal buyerCom1 = new BigDecimal((blockedAmount1.doubleValue() * b4.doubleValue()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal finalblockedAmount = new BigDecimal(blockedAmount1.doubleValue() + buyerVAT1.doubleValue() + buyerCom1.doubleValue() + b5.doubleValue()).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		logger.info("Buyer Bid blockedAmount In  bidQ * bidPrice :--##" + "   " + blockedAmount1);
		logger.info("Buyer Bid buyerVAT = (bidQ * bidPrice* buyerVAT / 100); :--##" + "   " + buyerVAT1);
		logger.info("Buyer Bid  buyerCom = (bidQ *bidPrice*buyerCom / 100) :--##" + "   " + buyerCom1);
		logger.info("Buye bidVAT blockedAmount += (buyerCom + buyerVAT + buyerShipping) :--##" + "   " + finalblockedAmount);
		logger.info("===========calculate Blocked Amount End ===========");
		return finalblockedAmount.doubleValue();
	}

	/**
	 * this method getBuyerBankDetails
	 * 
	 * @param auctionBuyersBean
	 * @return
	 */
	private BankDetailsBean getBuyerBankDetails(AuctionBuyersBean auctionBuyersBean) {
		BankDetailsBean bankDetailsBean = null;
		logger.info("=========== Buyar BankDetails ===========   " + auctionBuyersBean.getAuctionBuyersId() + " " + " Buyer Names "
				+ auctionBuyersBean.getAccountProfileBean().getFullName() + "  " + "Buyer Account Id := " + auctionBuyersBean.getAccountProfileBean().getAccountId());
		if (null != auctionBuyersBean) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER.getType()) {
				AccountProfileBean accountProfileBean = loginUser.getAccountProfileBean();
				bankDetailsBean = accountProfileBean.getBankDetailsBean();
				logger.info("Auction  Buyer BankDetails Info" + "  " + " BankName:=  " + bankDetailsBean.getBankName());
				logger.info("  " + " Bank  AccountNumbe:=  " + bankDetailsBean.getBankAccountNumber());
				logger.info("  " + " AccountId:=  " + bankDetailsBean.getAccountId());
				logger.info("  " + " AvailableBalance:=  " + bankDetailsBean.getAvailableBalance());
				logger.info("  " + " BlockedAmount:=  " + bankDetailsBean.getBlockedAmount());
				logger.info("=========== Buyar BankDetails End ===========  " + auctionBuyersBean.getAuctionBuyersId() + " " + " Buyer Names "
						+ auctionBuyersBean.getAccountProfileBean().getFullName() + "  " + "Buyer Account Id := " + auctionBuyersBean.getAccountProfileBean().getAccountId());
			} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				bankDetailsBean = ownerLoginDetailsBean.getAccountProfileBean().getBankDetailsBean();
				logger.info("Auction Buyar AGENT BankDetails Info" + "  " + " BankName:=  " + bankDetailsBean.getBankName());
				logger.info("  " + " Bank  AccountNumbe:=  " + bankDetailsBean.getBankAccountNumber());
				logger.info("  " + " AccountId:=  " + bankDetailsBean.getAccountId());
				logger.info("  " + " AvailableBalance:=  " + bankDetailsBean.getAvailableBalance());
				logger.info("  " + " BlockedAmount:=  " + bankDetailsBean.getBlockedAmount());
				logger.info("=========== Buyar BankDetails End ===========   " + auctionBuyersBean.getAuctionBuyersId() + " " + " Buyer Names "
						+ auctionBuyersBean.getAccountProfileBean().getFullName() + "  " + "Buyer Account Id := " + auctionBuyersBean.getAccountProfileBean().getAccountId());
			}

		}
		return bankDetailsBean;
	}

	/**
	 * Buyer Agent This Method Check Purchasing Power
	 * 
	 * @param loginUser
	 * @param totalAmount
	 * @param MBBR
	 * @return
	 */
	public Boolean enoughPurchasingPower(LoginDetailsBean loginUser, double totalAmount, double MBBR) {
		logger.info("BuyerBnakDetailsHelper call enoughPurchasingPower method");
		AgentOwnerBean agentOwnerBean = agentOwnerService.getAgentOwnerByLoginUserId(loginUser.getLoginUserid());
		double newLimitSpent = 0;
		newLimitSpent = agentOwnerBean.getLimitSpent().doubleValue() + totalAmount - MBBR;
		BigDecimal limitSpent = new BigDecimal(newLimitSpent);
		limitSpent.setScale(2, BigDecimal.ROUND_HALF_UP);
		agentOwnerBean.setLimitSpent(limitSpent);
		agentOwnerService.save(agentOwnerBean);
		logger.info("BuyerBnakDetailsHelper call enoughPurchasingPower method ending");
		return true;
	}



	/** Buyer Agent This Method Add Limit Spent */
	public void addPurchasingPower(LoginDetailsBean loginUser, double exexutedPrice) {
		logger.info("BuyerBnakDetailsHelper call addPurchasingPower method");
		AgentOwnerBean agentOwnerBean = agentOwnerService.getAgentOwnerByLoginUserId(loginUser.getLoginUserid());
		double newLimitSpent = agentOwnerBean.getLimitSpent().doubleValue() + exexutedPrice;
		BigDecimal limitSpent = new BigDecimal(newLimitSpent);
		limitSpent.setScale(2, BigDecimal.ROUND_HALF_UP);
		agentOwnerBean.setLimitSpent(limitSpent);
		logger.info("=== ending addPurchasingPower method ===");
		agentOwnerService.save(agentOwnerBean);
	}

	/**
	 * Buyer Agent This Method Reset Purchasing Power (Limit Spent)
	 * 
	 * @param loginUser
	 * @param blockedAmount
	 */
	public void resetPurchasingPoewr(LoginDetailsBean loginUser, double blockedAmount) {
		logger.info("BuyerBnakDetailsHelper call resetPurchasingPoewr method");
		AgentOwnerBean agentOwnerBean = agentOwnerService.getAgentOwnerByLoginUserId(loginUser.getLoginUserid());
		double newLimitSpent = agentOwnerBean.getLimitSpent().doubleValue() - blockedAmount;
		BigDecimal limitSpent = new BigDecimal(newLimitSpent);
		limitSpent.setScale(2, BigDecimal.ROUND_HALF_UP);
		agentOwnerBean.setLimitSpent(limitSpent);
		agentOwnerService.save(agentOwnerBean);
		logger.info("=== ending resetPurchasingPoewr method ===");
	}






	/**
	 * this method buyer total cost calculation
	 * 
	 * @param bankDetailsBean
	 * @param auctionBuyersBean
	 * @return
	 */
	public double calculationTotalCost(BankDetailsBean bankDetailsBean, AuctionBuyersBean auctionBuyersBean) {
		logger.info("BuyerBnakDetailsHelper call calculationTotalCost method");
		if (null != bankDetailsBean) {
			BigDecimal b1 = new BigDecimal(auctionBuyersBean.getExecutedQuantity()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b2 = new BigDecimal(auctionBuyersBean.getExecutedPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b3 = new BigDecimal(auctionBuyersBean.getVat()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b4 = new BigDecimal(auctionBuyersBean.getRoyaltyValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal b5 = new BigDecimal(auctionBuyersBean.getBuyerShippingCharge()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal totalCostAmount1 = new BigDecimal(b1.doubleValue() * b2.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal buyerVAT1 = new BigDecimal((totalCostAmount1.doubleValue() * b3.doubleValue()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal buyerCom1 = new BigDecimal((totalCostAmount1.doubleValue() * b4.doubleValue()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal calculationTotalCostAmount = new BigDecimal(totalCostAmount1.doubleValue() + buyerVAT1.doubleValue() + buyerCom1.doubleValue() + b5.doubleValue())
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			logger.info("Buyer Bid toalCostAmount1 In  bidQ * bidPrice :--##" + "   " + totalCostAmount1);
			logger.info("Buyer Bid buyerVAT = (bidQ * bidPrice* buyerVAT / 100); :--##" + "   " + buyerVAT1);
			logger.info("Buyer Bid  buyerCom = (bidQ *bidPrice*buyerCom / 100) :--##" + "   " + buyerCom1);
			logger.info("Buye bidVAT blockedAmount += (buyerCom + buyerVAT + buyerShipping) :--##" + "   " + calculationTotalCostAmount);
			logger.info("===========calculate calculationTotalCost End ===========");
			return calculationTotalCostAmount.doubleValue();
		}
		return 0.00;

	}

	/**
	 * this is method checkenoughPurchasingPower in BuyerAgnet
	 * 
	 * @param loginUser
	 * @param totalCost
	 * @param MBBR
	 */
	public void checkenoughPurchasingPower(LoginDetailsBean loginUser, Double totalCost, Double MBBR) {
		logger.info("BuyerBnakDetailsHelper call checkenoughPurchasingPower method");
		AgentOwnerBean agentOwnerBean = agentOwnerService.getAgentOwnerByLoginUserId(loginUser.getLoginUserid());
		double newLimitSpent = agentOwnerBean.getLimitSpent().doubleValue() - totalCost + MBBR;
		BigDecimal limitSpent = new BigDecimal(newLimitSpent);
		limitSpent.setScale(2, BigDecimal.ROUND_HALF_UP);
		agentOwnerBean.setLimitSpent(limitSpent);
		agentOwnerService.save(agentOwnerBean);
		logger.info("BuyerBnakDetailsHelper call checkenoughPurchasingPower method ending");
	}



	/**
	 * This method will update balance while new bid will place or bid will update
	 */
	public List<String> updateBidUserBalance(HttpServletRequest request, AuctionBuyersBean auctionBuyersBean) {
		logger.info("BuyerBnakDetailsHelper call updateBidUserBalance method");
		Locale locale = localeResolver.resolveLocale(request);
		List<String> errorStack = null;
		BigDecimal newAvailableBalance = new BigDecimal(0.0);
		BigDecimal newBlockedAmount = new BigDecimal(0.0);
		Boolean bidUpdate = false;
		double oldlimitSpent = 0;
		if (null != auctionBuyersBean) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionBuyersBean.getAccountProfileBean().getAccountId());
			BankDetailsBean bankDetailsBean = getBuyerBankDetails(auctionBuyersBean);
			if (null != bankDetailsBean) {
				float buyerCom = auctionBuyersBean.getRoyaltyValue();
				int bidQty = auctionBuyersBean.getBidQuantity();
				float bidVAT = auctionBuyersBean.getVat();
				float buyerShipping = auctionBuyersBean.getBuyerShippingCharge();
				float bidPrice = auctionBuyersBean.getBidPrice();
				double blockedAmount = calculateBlockedAmount(bidQty, bidPrice, bidVAT, buyerCom, buyerShipping);
				BigDecimal oldAvailableBalance = bankDetailsBean.getAvailableBalance();
				if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId() && auctionBuyersBean.getAuctionBuyersId() > 0) {
					bidUpdate = true;
					double oldBlockedAmount = getOldBlockedAmount(auctionBuyersBean.getAuctionBuyersId());
					oldlimitSpent = oldBlockedAmount;
					newBlockedAmount = new BigDecimal(bankDetailsBean.getBlockedAmount().doubleValue() - oldBlockedAmount + blockedAmount);
					newAvailableBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue() + oldBlockedAmount - blockedAmount);
					oldAvailableBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue() + oldBlockedAmount);
				} else {
					newBlockedAmount = new BigDecimal(bankDetailsBean.getBlockedAmount().doubleValue() + blockedAmount);
					newAvailableBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue() - blockedAmount);
				}
				newAvailableBalance =newAvailableBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
				newBlockedAmount = newBlockedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
				if (blockedAmount <= oldAvailableBalance.doubleValue()) {
					bankDetailsBean.setAvailableBalance(newAvailableBalance);
					bankDetailsBean.setBlockedAmount(newBlockedAmount.abs());
					/** Buyer Agent check PurchasingPoewr */
					if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
						Boolean checkPurchasingPoewr = enoughPurchasingPower(loginUser, blockedAmount, bidUpdate, oldlimitSpent);
						if (checkPurchasingPoewr == true) {
							bankDetailsService.save(bankDetailsBean);
						} else {
							errorStack = new ArrayList<String>(0);
							errorStack.add("agentstatusajaxform.purchasingPoewr.validation");
							return errorStack;
						}
					} else {
						bankDetailsService.save(bankDetailsBean);
					}
				} else {
					errorStack = new ArrayList<String>(0);
					errorStack.add("buyer.auctionbidplacing.lbl.totalprice.validation.max");
					errorStack.add(" ");

				}
			}
		}
		logger.info("=== ending updateBidUserBalance method ===");
		return errorStack;
	}

	private double getOldBlockedAmount(Integer auctionBuyersId) {
		AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyersId);
		logger.info("===========calculate OldBlockedAmount  ===========");
		float buyerCom = auctionBuyersBean.getRoyaltyValue();
		logger.info("buyerCom " + "  " + buyerCom);
		int bidQty = auctionBuyersBean.getBidQuantity();
		logger.info("bidQty " + "  " + bidQty);
		float bidVAT = auctionBuyersBean.getVat();
		logger.info("bidVAT " + "  " + bidVAT);
		float buyerShipping = auctionBuyersBean.getBuyerShippingCharge();
		logger.info("buyerShipping " + "  " + buyerShipping);
		float bidPrice = auctionBuyersBean.getBidPrice();
		logger.info("bidPrice " + "  " + bidPrice);
		double blockedAmount = calculateBlockedAmount(bidQty, bidPrice, bidVAT, buyerCom, buyerShipping);
		logger.info("====End=======calculate OldBlockedAmount End ========End===");
		return blockedAmount;
	}

	/** Buyer Agent This Method Check Purchasing Power */
	public Boolean enoughPurchasingPower(LoginDetailsBean loginUser, double blockedAmount, Boolean bidUpdate, double oldlimitSpent) {
		logger.info("BuyerBnakDetailsHelper call enoughPurchasingPower method");
		AgentOwnerBean agentOwnerBean = agentOwnerService.getAgentOwnerByLoginUserId(loginUser.getLoginUserid());
		double purchasePower = 0;
		double newLimitSpent = 0;
		if (bidUpdate == true) {
			double oldLimitSpent = agentOwnerBean.getLimitSpent().doubleValue() - oldlimitSpent;
			purchasePower = agentOwnerBean.getPurchaseLimit().doubleValue() - oldLimitSpent;
			newLimitSpent = oldLimitSpent + blockedAmount;
		} else {
			purchasePower = agentOwnerBean.getPurchaseLimit().doubleValue() - agentOwnerBean.getLimitSpent().doubleValue();
			newLimitSpent = agentOwnerBean.getLimitSpent().doubleValue() + blockedAmount;
		}

		if (purchasePower >= blockedAmount) {
			BigDecimal limitSpent = new BigDecimal(newLimitSpent);
			limitSpent.setScale(2, BigDecimal.ROUND_HALF_UP);
			agentOwnerBean.setLimitSpent(limitSpent);
			agentOwnerService.save(agentOwnerBean);
			logger.info("=== ending enoughPurchasingPower method ===");
			return true;
		} else {
			return false;
		}
	}

}