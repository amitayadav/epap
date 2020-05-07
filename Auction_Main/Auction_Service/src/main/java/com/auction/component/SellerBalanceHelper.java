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
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.SellerBalanceBean;
import com.auction.model.bean.SellerBalanceIdBean;
import com.auction.model.bean.TransactionCodeBean;
import com.auction.service.AccountProfileService;
import com.auction.service.EpapService;
import com.auction.service.LoginDetailsService;
import com.auction.service.SellerBalanceService;

@Component
public class SellerBalanceHelper {

	@Autowired
	private SellerBalanceService sellerBalanceService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private EpapBalanceHelper epapBalanceHelper;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private EpapService epapService;
	
	private AuctionLogger logger = new AuctionLogger(getClass().getName());
	
	public void createBankTransactionDebit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("SellerBalanceHelper call createBankTransactionDebit method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		SellerBalanceIdBean sellerBalanceIdBean = new SellerBalanceIdBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			sellerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		epapTransactionId= epapBalanceHelper.adminWithdrawToEpapAccount(debitCredit, accountProfileBean , loginUser.getAccountTypeCodesBean().getAccountType() , comments);
		SellerBalanceBean sellerBalanceBean2 = sellerBalanceService.getSellerBalanceNew(sellerBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != sellerBalanceBean2 && null != sellerBalanceBean2.getId() && null != sellerBalanceBean2.getId().getAccountProfileBean()
				&& null != sellerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().subtract((price)));
		} else {
			sellerBalanceBean.setBalance(price);
		}
		debitCredit = debitCredit.negate();
		sellerBalanceBean.setDebitCredit(debitCredit);
		sellerBalanceBean.setAdvanceBalance(sellerBalanceIdBean.getAccountProfileBean().getBankDetailsBean().getAdvanceBalance());
		sellerBalanceBean.setTransactionId(transactionId);
		sellerBalanceBean.setEpapTransactionId(epapTransactionId);
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceBean.setComments(comments);
		sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		sellerBalanceBean.setTransactionDescription((StringUtils.capitalize(transactionCodes.toString().toLowerCase()) + " By " + accountProfileBean.getFullName()));
		sellerBalanceService.save(sellerBalanceBean);
		logger.info("=== ending  createBankTransactionDebit method ===");
	}

	public void createBankTransactionCredit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, String tranDesc, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("SellerBalanceHelper call createBankTransactionCredit method ");
		SellerBalanceIdBean sellerBalanceIdBean = new SellerBalanceIdBean();
		BigDecimal debitCredit = price;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			sellerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		SellerBalanceBean sellerBalanceBean2 = sellerBalanceService.getSellerBalanceNew(sellerBalanceIdBean.getAccountProfileBean().getAccountId());
			sellerDepositUpdateAvailableBalance(sellerBalanceBean2, transactionId, transactionCodes, debitCredit, sellerBalanceIdBean, tranDesc, price, accountProfileBean,
					comments,loginUser.getAccountTypeCodesBean().getAccountType());
			if (accountProfileBean.getBankDetailsBean().getAdvanceBalance().compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser1 = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			accountProfileBean = loginUser1.getAccountProfileBean();
			sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
			BigDecimal oldSellerAdvanceBalance = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
			BigDecimal newAvailableBlanace = new BigDecimal(0.0);
			newAvailableBlanace = new BigDecimal(accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue() - debitCredit.doubleValue());
			if (debitCredit.doubleValue() > accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue()) {
				Integer epapTransactionId;
				oldSellerAdvanceBalance = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
				epapTransactionId=epapBalanceHelper.advanceUpdateToEpapAccount(oldSellerAdvanceBalance,accountProfileBean,loginUser.getAccountTypeCodesBean().getAccountType(),comments);
				sellerSalesProductReturnAdvanceAmount(transactionId, transactionCodes, oldSellerAdvanceBalance, sellerBalanceIdBean, accountProfileBean,epapTransactionId);
			
			} else {
				accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAvailableBlanace);
				oldSellerAdvanceBalance = debitCredit;
				Integer epapTransactionId;
				epapTransactionId=epapBalanceHelper.advanceUpdateToEpapAccount(oldSellerAdvanceBalance,accountProfileBean,loginUser.getAccountTypeCodesBean().getAccountType(),comments);
				sellerDepositReturnAdvanceAmount(sellerBalanceBean2, transactionId, transactionCodes, debitCredit, sellerBalanceIdBean, tranDesc, comments,newAvailableBlanace,accountProfileBean,epapTransactionId);
				
			}
			}
			logger.info("=== ending  createBankTransactionCredit method ===");
		}


	public void createSellerAdvanceBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("SellerBalanceHelper call createSellerAdvanceBalance method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		SellerBalanceIdBean sellerBalanceIdBean = new SellerBalanceIdBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		if (lastBalance != null && lastBalance.compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
				sellerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
			} else {
				sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
			}
			epapTransactionId=epapBalanceHelper.advanceToEpapAccount(debitCredit, accountProfileBean ,loginUser.getAccountTypeCodesBean().getAccountType(),comments);
			SellerBalanceBean sellerBalanceBean2 = sellerBalanceService.getSellerBalanceNew(sellerBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != sellerBalanceBean2 && null != sellerBalanceBean2.getId() && null != sellerBalanceBean2.getId().getAccountProfileBean()
					&& null != sellerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().add((price)));
				if (ENUM_TransactionCodes.ADVANCE == transactionCodes) {
					sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCE.getType()));
					sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
				} else {
					sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
					sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				}
			} else {

				sellerBalanceBean.setBalance(price);
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
			sellerBalanceBean.setDebitCredit(debitCredit);
			sellerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(price));
			sellerBalanceBean.setTransactionId(transactionId);
			sellerBalanceBean.setEpapTransactionId(epapTransactionId);
			sellerBalanceBean.setId(sellerBalanceIdBean);
			sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
			sellerBalanceBean.setComments(comments);
			sellerBalanceService.save(sellerBalanceBean);
			accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
			accountProfileBean.getBankDetailsBean().setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(price));
			accountProfileService.save(accountProfileBean);
			logger.info("=== ending  createSellerAdvanceBalance method ===");
		}
	}

	public void createSellerAdjustCreditBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("SellerBalanceHelper call createSellerAdjustCreditBalance method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		SellerBalanceIdBean sellerBalanceIdBean = new SellerBalanceIdBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		if (lastBalance != null && lastBalance.compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
				sellerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
			} else {
				sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
			}
			epapTransactionId=epapBalanceHelper.adjustCreditFromEpapAccount(debitCredit, accountProfileBean, transactionCodes, comments, loginUser.getAccountTypeCodesBean().getAccountType());
			SellerBalanceBean sellerBalanceBean2 = sellerBalanceService.getSellerBalanceNew(sellerBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != sellerBalanceBean2 && null != sellerBalanceBean2.getId() && null != sellerBalanceBean2.getId().getAccountProfileBean()
					&& null != sellerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().add((price)));
				if (ENUM_TransactionCodes.ADJUSTCREDIT == transactionCodes) {
					sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTCREDIT.getType()));
					sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
				} else {
					sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
					sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				}
			} else {

				sellerBalanceBean.setBalance(price);
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
			sellerBalanceBean.setDebitCredit(debitCredit);
			sellerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
			sellerBalanceBean.setTransactionId(transactionId);
			sellerBalanceBean.setEpapTransactionId(epapTransactionId);
			sellerBalanceBean.setId(sellerBalanceIdBean);
			sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
			sellerBalanceBean.setComments(comments);
			sellerBalanceService.save(sellerBalanceBean);
			accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
			accountProfileService.save(accountProfileBean);
			logger.info("=== ending  createSellerAdjustCreditBalance method ===");
		}
	}

	public void createSellerAdjustDebitBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			String comments) {
		logger.info("SellerBalanceHelper call createSellerAdjustDebitBalance method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		SellerBalanceIdBean sellerBalanceIdBean = new SellerBalanceIdBean();
		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			sellerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		epapTransactionId=epapBalanceHelper.AdjustDebitFromEpapAccount(debitCredit, accountProfileBean, transactionCodes, comments, loginUser.getAccountTypeCodesBean().getAccountType());
		SellerBalanceBean sellerBalanceBean2 = sellerBalanceService.getSellerBalanceNew(sellerBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != sellerBalanceBean2 && null != sellerBalanceBean2.getId() && null != sellerBalanceBean2.getId().getAccountProfileBean()
				&& null != sellerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().subtract((price)));
			if (ENUM_TransactionCodes.ADJUSTDEBIT == transactionCodes) {
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTDEBIT.getType()));
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
			} else {
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
		} else {
			sellerBalanceBean.setBalance(price);
			sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
			sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		}
		debitCredit = debitCredit.negate();
		sellerBalanceBean.setDebitCredit(debitCredit);
		sellerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		sellerBalanceBean.setTransactionId(transactionId);
		sellerBalanceBean.setEpapTransactionId(epapTransactionId);
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceBean.setComments(comments);
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceService.save(sellerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(price));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  createSellerAdjustDebitBalance method ===");
	}

	public void sellerSalesProductReturnAdvanceAmount(Integer transactionId, ENUM_TransactionCodes transactionCodes, BigDecimal oldSellerAdvanceBalance,
			SellerBalanceIdBean sellerBalanceIdBean, AccountProfileBean accountProfileBean,Integer epapTransactionId) {
		logger.info("SellerBalanceHelper call sellerSalesProductReturnAdvanceAmount method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		oldSellerAdvanceBalance = oldSellerAdvanceBalance.negate();
		sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
		sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
		sellerBalanceBean.setDebitCredit(oldSellerAdvanceBalance);
		sellerBalanceBean.setAdvanceBalance(new BigDecimal(0.00));
		sellerBalanceBean.setTransactionId(null);
		sellerBalanceBean.setEpapTransactionId(epapTransactionId);
		sellerBalanceBean.setComments("");
		sellerBalanceBean.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(oldSellerAdvanceBalance.abs()));
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceService.save(sellerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(oldSellerAdvanceBalance.abs()));
		accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(0.00));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  sellerSalesProductReturnAdvanceAmount method ===");
	}

	public void sellerDepositReturnAdvanceAmount(SellerBalanceBean sellerBalanceBean2, Integer transactionId, ENUM_TransactionCodes transactionCodes, BigDecimal debitCredit,
			SellerBalanceIdBean sellerBalanceIdBean, String tranDesc, String comments,BigDecimal newAvailableBlanace,AccountProfileBean accountProfileBean,Integer epapTransactionId) {
		logger.info("SellerBalanceHelper call sellerDepositReturnAdvanceAmount method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
			sellerBalanceBean.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
			if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
			} else {
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
			}
		debitCredit = debitCredit.negate();
		sellerBalanceBean.setDebitCredit(debitCredit);
		sellerBalanceBean.setAdvanceBalance(newAvailableBlanace);
		sellerBalanceBean.setTransactionId(transactionId);
		sellerBalanceBean.setEpapTransactionId(epapTransactionId);
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceBean.setComments(comments);
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceService.save(sellerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAvailableBlanace);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  sellerDepositReturnAdvanceAmount method ===");
	}

	public void sellerDepositUpdateAvailableBalance(SellerBalanceBean sellerBalanceBean2, Integer transactionId, ENUM_TransactionCodes transactionCodes, BigDecimal debitCredit,
			SellerBalanceIdBean sellerBalanceIdBean, String tranDesc, BigDecimal price, AccountProfileBean accountProfileBean, String comments,Character AccountType) {
		logger.info("SellerBalanceHelper call sellerDepositUpdateAvailableBalance method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		Integer epapTransactionId;
		epapTransactionId=epapBalanceHelper.cashPositionToEpapAccount(debitCredit, accountProfileBean ,AccountType,comments);
		if (null != sellerBalanceBean2 && null != sellerBalanceBean2.getId() && null != sellerBalanceBean2.getId().getAccountProfileBean()
				&& null != sellerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().add((price)));
			if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType()));
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.DEPOSIT.toString().toLowerCase()));
			} else {
				sellerBalanceBean.setTransactionDescription(tranDesc);
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
		} else {
			if (null == tranDesc || tranDesc.length() == 0) {
				tranDesc = StringUtils.capitalize(transactionCodes.toString().toLowerCase());
			}
			sellerBalanceBean.setBalance(price);
			sellerBalanceBean.setTransactionDescription(tranDesc);
			sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		}
		sellerBalanceBean.setDebitCredit(debitCredit);
		sellerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		sellerBalanceBean.setTransactionId(transactionId);
		sellerBalanceBean.setEpapTransactionId(epapTransactionId);
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceBean.setComments(comments);
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceService.save(sellerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  sellerDepositUpdateAvailableBalance method ===");
	}

	public void sellerSaleCreateBankTransactionCredit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, String tranDesc,
			ENUM_TransactionCodes transactionCodes, String comments) {
		logger.info("=========Start =======SellerSale CreateBank TransactionCredit===============");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		SellerBalanceIdBean sellerBalanceIdBean = new SellerBalanceIdBean();
		BigDecimal debitCredit = price;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			sellerBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			sellerBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		SellerBalanceBean sellerBalanceBean2 = sellerBalanceService.getSellerBalanceNew(sellerBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != sellerBalanceBean2 && null != sellerBalanceBean2.getId() && null != sellerBalanceBean2.getId().getAccountProfileBean()
				&& null != sellerBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().add((price)));
			if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType()));
				sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.DEPOSIT.toString().toLowerCase()));
			} else {
				sellerBalanceBean.setTransactionDescription(tranDesc);
				sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
		} else {
			if (null == tranDesc || tranDesc.length() == 0) {
				tranDesc = StringUtils.capitalize(transactionCodes.toString().toLowerCase());
			}
			sellerBalanceBean.setBalance(price);
			sellerBalanceBean.setTransactionDescription(tranDesc);
			sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		}
		sellerBalanceBean.setDebitCredit(debitCredit);
		sellerBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		sellerBalanceBean.setTransactionId(transactionId);
		sellerBalanceBean.setEpapTransactionId(null);
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceBean.setComments(comments);
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceService.save(sellerBalanceBean);
		logger.info("Seller ID =:" +accountProfileBean.getAccountId());
		logger.info("debitCredit =:" +debitCredit);
		logger.info("AdvanceBalance =:" +accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		logger.info("TransactionId =:" +transactionId);
		if (accountProfileBean.getBankDetailsBean().getAdvanceBalance().compareTo(BigDecimal.ZERO) != 0) {
			sellerBalanceBean2.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance());
			sellerSaleReturnAdvanceAmount(debitCredit, accountProfileBean, sellerBalanceBean2, sellerBalanceIdBean,loginUser.getAccountTypeCodesBean().getAccountType());
		}
		logger.info("=========End =======SellerSale CreateBank TransactionCredit===============");
	}

	public void sellerSaleReturnAdvanceAmount(BigDecimal price, AccountProfileBean accountProfileBean, SellerBalanceBean sellerBalanceBean2,
			SellerBalanceIdBean sellerBalanceIdBean,Character AccountType) {
		logger.info("SellerBalanceHelper call sellerSaleReturnAdvanceAmount method ");
		SellerBalanceBean sellerBalanceBean = new SellerBalanceBean();
		BigDecimal newAdvanceBlanace = new BigDecimal(0.0);
		BigDecimal newdebitCredit = new BigDecimal(0.0);
		BigDecimal debitCredit = price;
		newAdvanceBlanace = new BigDecimal(accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue() - debitCredit.doubleValue());
		if (debitCredit.doubleValue() > accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue()) {
			newdebitCredit = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
			accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(0.00));
			sellerBalanceBean.setAdvanceBalance(new BigDecimal(0.00));
		} else {
			newdebitCredit = debitCredit;
			accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAdvanceBlanace);
			sellerBalanceBean.setAdvanceBalance(newAdvanceBlanace);
		}
		sellerBalanceBean.setBalance(sellerBalanceBean2.getBalance().subtract(newdebitCredit));
		sellerBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
		sellerBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTDEBIT.getType()));
		newdebitCredit = newdebitCredit.negate();
		sellerBalanceBean.setDebitCredit(newdebitCredit);
		sellerBalanceBean.setTransactionId(null);
		sellerBalanceBean.setEpapTransactionId(null);
		sellerBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		sellerBalanceBean.setId(sellerBalanceIdBean);
		sellerBalanceBean.setComments("");
		sellerBalanceService.save(sellerBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(newdebitCredit.abs()));
		accountProfileService.save(accountProfileBean);
		epapBalanceHelper.advanceUpdateToEpapAccount(newdebitCredit.abs(),accountProfileBean,AccountType,"");
		logger.info("=== ending  sellerSaleReturnAdvanceAmount method ===");
	}

}
