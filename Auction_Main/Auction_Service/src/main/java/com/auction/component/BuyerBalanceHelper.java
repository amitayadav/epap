package com.auction.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.BuyerBalanceBean;
import com.auction.model.bean.BuyerBalanceIdBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.TransactionCodeBean;
import com.auction.service.AccountProfileService;
import com.auction.service.BuyerBalanceService;
import com.auction.service.EpapService;
import com.auction.service.LoginDetailsService;

@Component
public class BuyerBalanceHelper {

	@Autowired
	private BuyerBalanceService buyerBalanceService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private EpapBalanceHelper epapBalanceHelper;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private EpapService epapService;
	

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	public void createBuyerBalanceDebit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, String tranDesc, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("BuyerBalanceHelper call createBuyerBalanceDebit method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		BuyerBalanceIdBean buyerBalanceIdBean = new BuyerBalanceIdBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId = null;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			buyerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			buyerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		if (transactionId == null) {
			epapTransactionId = epapBalanceHelper.adminWithdrawToEpapAccount(debitCredit, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(), comments);
		}
		BuyerBalanceBean buyerBalanceBean2 = buyerBalanceService.getBuyerBalanceNew(buyerBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != buyerBalanceBean2 && null != buyerBalanceBean2.getId() && null != buyerBalanceBean2.getId().getAccountProfileBean()
				&& null != buyerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			BigDecimal newBuyerBalance = new BigDecimal(buyerBalanceBean2.getBalance().doubleValue() - price.doubleValue());
			newBuyerBalance =newBuyerBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
			buyerBalanceBean.setBalance(newBuyerBalance);
		} else {
			buyerBalanceBean.setBalance(price);
		}

		debitCredit = debitCredit.negate();
		buyerBalanceBean.setDebitCredit(debitCredit);
		buyerBalanceBean.setEpapTransactionId(epapTransactionId);
		buyerBalanceBean.setAdvanceBalance(buyerBalanceIdBean.getAccountProfileBean().getBankDetailsBean().getAdvanceBalance());
		buyerBalanceBean.setTransactionId(transactionId);
		buyerBalanceBean.setId(buyerBalanceIdBean);
		buyerBalanceBean.setComments(comments);
		buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		buyerBalanceBean.setTransactionDescription(tranDesc);
		logger.info("=== ending  createBuyerBalanceDebit method ");
		buyerBalanceService.save(buyerBalanceBean);

	}

	public void createBuyerBalanceCredit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes, String comments) {
		logger.info("BuyerBalanceHelper call createBuyerBalanceCredit method ");
		BuyerBalanceIdBean buyerBalanceIdBean = new BuyerBalanceIdBean();
		BigDecimal debitCredit = price;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			buyerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			buyerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		BuyerBalanceBean buyerBalanceBean2 = buyerBalanceService.getBuyerBalanceNew(buyerBalanceIdBean.getAccountProfileBean().getAccountId());
		buyewrDepositUpdateAvailableBalance(buyerBalanceBean2, transactionId, price, accountProfileBean, transactionCodes, buyerBalanceIdBean, comments,
				loginUser.getAccountTypeCodesBean().getAccountType());
		if (accountProfileBean.getBankDetailsBean().getAdvanceBalance().compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser1 = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			accountProfileBean = loginUser1.getAccountProfileBean();
			buyerBalanceIdBean.setAccountProfileBean(accountProfileBean);
			BigDecimal oldBuyerAdvanceBalance = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
			BigDecimal newAvailableBlanace = new BigDecimal(0.0);
			newAvailableBlanace = new BigDecimal(loginUser.getAccountProfileBean().getBankDetailsBean().getAdvanceBalance().doubleValue() - debitCredit.doubleValue());
			if (debitCredit.doubleValue() > accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue()) {
				oldBuyerAdvanceBalance = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
				Integer epapTransactionId;
				epapTransactionId = epapBalanceHelper.advanceUpdateToEpapAccount(oldBuyerAdvanceBalance, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(),
						comments);
				buyerReturnAdvanceAmount(transactionId, transactionCodes, oldBuyerAdvanceBalance, buyerBalanceIdBean, accountProfileBean, epapTransactionId);

			} else {
				Integer epapTransactionId;
				epapTransactionId = epapBalanceHelper.advanceUpdateToEpapAccount(debitCredit, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(), comments);
				buyerDepositReturnAdvanceAmount(transactionId, transactionCodes, debitCredit, buyerBalanceIdBean, comments, newAvailableBlanace, accountProfileBean,
						epapTransactionId);
				logger.info("=== ending  createBuyerBalanceCredit method ");
			}
		}
	}

	public void createBuyerAdvanceBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes, String comments) {
		logger.info("BuyerBalanceHelper call createBuyerAdvanceBalance method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		BuyerBalanceIdBean buyerBalanceIdBean = new BuyerBalanceIdBean();
		BigDecimal debitCredit = price;
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		Integer epapTransactionId;
		if (lastBalance != null && lastBalance.compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
				buyerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
			} else {
				buyerBalanceIdBean.setAccountProfileBean(accountProfileBean);
			}
			epapTransactionId = epapBalanceHelper.advanceToEpapAccount(debitCredit, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(), comments);
			BuyerBalanceBean buyerBalanceBean2 = buyerBalanceService.getBuyerBalanceNew(buyerBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != buyerBalanceBean2 && null != buyerBalanceBean2.getId() && null != buyerBalanceBean2.getId().getAccountProfileBean()
					&& null != buyerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				buyerBalanceBean.setBalance(buyerBalanceBean2.getBalance().add(price));
				if (ENUM_TransactionCodes.ADVANCE == transactionCodes) {
					buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCE.getType()));
					buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
				} else {
					buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
					buyerBalanceBean.setTransactionDescription((StringUtils.capitalize(transactionCodes.toString().toLowerCase()) + " By " + accountProfileBean.getFullName()));
				}
			} else {
				buyerBalanceBean.setBalance(price);
				buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(transactionCodes.toString().toLowerCase()));
			}

			buyerBalanceBean.setDebitCredit(debitCredit);
			buyerBalanceBean.setEpapTransactionId(epapTransactionId);
			buyerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(price));
			buyerBalanceBean.setTransactionId(transactionId);
			buyerBalanceBean.setId(buyerBalanceIdBean);
			buyerBalanceBean.setComments(comments);
			buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
			logger.info("=== ending  createBuyerAdvanceBalance method  ===");
			buyerBalanceService.save(buyerBalanceBean);

		}
	}

	public void createBuyerAdjustCreditBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("BuyerBalanceHelper call createBuyerAdjustCreditBalance method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		BuyerBalanceIdBean buyerBalanceIdBean = new BuyerBalanceIdBean();
		BigDecimal debitCredit = price;
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		Integer epapTransactionId;
		if (lastBalance != null && lastBalance.compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
				buyerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
			} else {
				buyerBalanceIdBean.setAccountProfileBean(accountProfileBean);
			}
			epapTransactionId = epapBalanceHelper.adjustCreditFromEpapAccount(debitCredit, accountProfileBean, transactionCodes, comments,
					loginUser.getAccountTypeCodesBean().getAccountType());
			BuyerBalanceBean buyerBalanceBean2 = buyerBalanceService.getBuyerBalanceNew(buyerBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != buyerBalanceBean2 && null != buyerBalanceBean2.getId() && null != buyerBalanceBean2.getId().getAccountProfileBean()
					&& null != buyerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				buyerBalanceBean.setBalance(buyerBalanceBean2.getBalance().add(price));
				if (ENUM_TransactionCodes.ADJUSTCREDIT == transactionCodes) {
					buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTCREDIT.getType()));
					buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
				} else {
					buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
					buyerBalanceBean.setTransactionDescription((StringUtils.capitalize(transactionCodes.toString().toLowerCase()) + " By " + accountProfileBean.getFullName()));
				}
			} else {
				buyerBalanceBean.setBalance(price);
				buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(transactionCodes.toString().toLowerCase()));
			}

			buyerBalanceBean.setDebitCredit(debitCredit);
			buyerBalanceBean.setEpapTransactionId(epapTransactionId);
			buyerBalanceBean.setAdvanceBalance(buyerBalanceIdBean.getAccountProfileBean().getBankDetailsBean().getAdvanceBalance());
			buyerBalanceBean.setTransactionId(transactionId);
			buyerBalanceBean.setId(buyerBalanceIdBean);
			buyerBalanceBean.setComments(comments);
			buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
			logger.info("=== ending  createBuyerAdjustCreditBalance method  ===");
			buyerBalanceService.save(buyerBalanceBean);

		}
	}

	public void createBuyerAdjustDebitBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("BuyerBalanceHelper call createBuyerAdjustDebitBalance method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		BuyerBalanceIdBean buyerBalanceIdBean = new BuyerBalanceIdBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			buyerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			buyerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		epapTransactionId = epapBalanceHelper.AdjustDebitFromEpapAccount(debitCredit, accountProfileBean, transactionCodes, comments,
				loginUser.getAccountTypeCodesBean().getAccountType());
		if (loginUser.getAccountProfileBean().getBankDetailsBean().getAvailableBalance().doubleValue() != 0) {
			BuyerBalanceBean buyerBalanceBean2 = buyerBalanceService.getBuyerBalanceNew(buyerBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != buyerBalanceBean2 && null != buyerBalanceBean2.getId() && null != buyerBalanceBean2.getId().getAccountProfileBean()
					&& null != buyerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				buyerBalanceBean.setBalance(buyerBalanceBean2.getBalance().subtract(price));
				if (ENUM_TransactionCodes.ADJUSTDEBIT == transactionCodes) {
					buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTDEBIT.getType()));
					buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.getDesc().toString().toLowerCase()));
				} else {
					buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
					buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.getDesc().toString().toLowerCase()));
				}
			} else {
				buyerBalanceBean.setBalance(price);
				buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.getDesc().toString().toLowerCase()));
			}
			debitCredit = debitCredit.negate();
			buyerBalanceBean.setDebitCredit(debitCredit);
			buyerBalanceBean.setAdvanceBalance(buyerBalanceIdBean.getAccountProfileBean().getBankDetailsBean().getAdvanceBalance());
			buyerBalanceBean.setTransactionId(transactionId);
			buyerBalanceBean.setEpapTransactionId(epapTransactionId);
			buyerBalanceBean.setId(buyerBalanceIdBean);
			buyerBalanceBean.setComments(comments);
			buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
			buyerBalanceService.save(buyerBalanceBean);
			accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(price));
			logger.info("=== ending  createBuyerAdjustDebitBalance method  ===");
			accountProfileService.save(accountProfileBean);

		}
	}

	public void buyerDepositReturnAdvanceAmount(Integer transactionId, ENUM_TransactionCodes transactionCodes, BigDecimal debitCredit, BuyerBalanceIdBean buyerBalanceIdBean,
			String comments, BigDecimal newAvailableBlanace, AccountProfileBean accountProfileBean, Integer epapTransactionId) {
		logger.info("BuyerBalanceHelper call buyerDepositReturnAdvanceAmount method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		buyerBalanceBean.setBalance(buyerBalanceIdBean.getAccountProfileBean().getBankDetailsBean().getAvailableBalance().subtract(debitCredit));
		if (ENUM_TransactionCodes.ADJUSTDEBIT == transactionCodes) {
			buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTDEBIT.getType()));
			buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
		} else {
			buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
			buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
		}
		debitCredit = debitCredit.negate();
		buyerBalanceBean.setDebitCredit(debitCredit);
		buyerBalanceBean.setEpapTransactionId(epapTransactionId);
		buyerBalanceBean.setAdvanceBalance(newAvailableBlanace);
		buyerBalanceBean.setTransactionId(transactionId);
		buyerBalanceBean.setId(buyerBalanceIdBean);
		buyerBalanceBean.setComments(comments);
		buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		buyerBalanceService.save(buyerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAvailableBlanace);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		logger.info("=== ending  buyerDepositReturnAdvanceAmount method  ===");
		accountProfileService.save(accountProfileBean);
	}

	public void buyerReturnAdvanceAmount(Integer transactionId, ENUM_TransactionCodes transactionCodes, BigDecimal oldBuyerAdvanceBalance, BuyerBalanceIdBean buyerBalanceIdBean,
			AccountProfileBean accountProfileBean, Integer epapTransactionId) {
		logger.info("BuyerBalanceHelper call buyerReturnAdvanceAmount method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		oldBuyerAdvanceBalance = oldBuyerAdvanceBalance.negate();
		buyerBalanceBean.setDebitCredit(oldBuyerAdvanceBalance);
		buyerBalanceBean.setEpapTransactionId(epapTransactionId);
		buyerBalanceBean.setAdvanceBalance(new BigDecimal(0.00));
		buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
		buyerBalanceBean.setTransactionId(null);
		buyerBalanceBean.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(oldBuyerAdvanceBalance.abs()));
		buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		buyerBalanceBean.setId(buyerBalanceIdBean);
		buyerBalanceBean.setComments("");
		buyerBalanceService.save(buyerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(oldBuyerAdvanceBalance.abs()));
		accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(0.00));
		logger.info("=== ending  buyerReturnAdvanceAmount method  ===");
		accountProfileService.save(accountProfileBean);
	}

	public void buyewrDepositUpdateAvailableBalance(BuyerBalanceBean buyerBalanceBean2, Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean,
			ENUM_TransactionCodes transactionCodes, BuyerBalanceIdBean buyerBalanceIdBean, String comments, Character AccountType) {
		logger.info("BuyerBalanceHelper call buyewrDepositUpdateAvailableBalance method ");
		BuyerBalanceBean buyerBalanceBean = new BuyerBalanceBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		epapTransactionId = epapBalanceHelper.cashPositionToEpapAccount(debitCredit, accountProfileBean, AccountType, comments);
		if (null != buyerBalanceBean2 && null != buyerBalanceBean2.getId() && null != buyerBalanceBean2.getId().getAccountProfileBean()
				&& null != buyerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			buyerBalanceBean.setBalance(buyerBalanceBean2.getBalance().add(price));
			if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
				buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType()));
				buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.DEPOSIT.toString().toLowerCase()));
			} else {
				buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				buyerBalanceBean.setTransactionDescription((StringUtils.capitalize(transactionCodes.toString().toLowerCase()) + " By " + accountProfileBean.getFullName()));
			}
		} else {
			buyerBalanceBean.setBalance(price);
			buyerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			buyerBalanceBean.setTransactionDescription(StringUtils.capitalize(transactionCodes.toString().toLowerCase()));
		}

		buyerBalanceBean.setDebitCredit(debitCredit);
		buyerBalanceBean.setEpapTransactionId(epapTransactionId);
		buyerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		buyerBalanceBean.setTransactionId(transactionId);
		buyerBalanceBean.setId(buyerBalanceIdBean);
		buyerBalanceBean.setComments(comments);
		buyerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		buyerBalanceService.save(buyerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  buyewrDepositUpdateAvailableBalance method  ===");
	}

	public static void main(String args[]) {
		/*
		 * // BigDecimal lastBalance = epapService.getLastEpapBalance(); BigDecimal
		 * mBalance = new BigDecimal(10000); BigDecimal eprice = new BigDecimal(1000);
		 * BigDecimal newAvailableBlanace = new BigDecimal(200); if
		 * (mBalance.doubleValue() <= eprice.doubleValue() ) {
		 * System.out.println("yes"); }
		 */
	}

}