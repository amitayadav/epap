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
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.bean.BuyerTransactionsBean;
import com.auction.model.bean.EpapBalanceBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.SellerTransactionsBean;
import com.auction.model.bean.TransactionCodeBean;
import com.auction.service.AccountProfileService;
import com.auction.service.EpapService;
import com.auction.service.LoginDetailsService;

/*
 * Helper class to maintain Epap Financial Details
 * 
 */
@Component
public class EpapBalanceHelper {

	@Autowired
	private EpapService epapService;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	private static String COMMISSION_SEPRATOR = ":";
	private static String COMMISSION_SELLER_TITLE = "Seller";
	private static String COMMISSION_BUYER_TITLE = "Buyer";
	private static String BLANK_SPACE = " ";

	public void createSellerCommissionCredit(SellerTransactionsBean sellerTransactionsBean, BigDecimal debitCredit) {
		logger.info("EpapBalanceHepler call createSellerCommissionCredit method");
		if (debitCredit.compareTo(new BigDecimal("0.0")) != 0) {
			AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
			adminSupperAccountProfile = getEpapBankDetails();
			EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
			epapBalanceBean.setAccountProfileBean(sellerTransactionsBean.getAccountProfileBean());
			TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.COMMISSION.getType());
			epapBalanceBean.setTransactionCodebean(transactionCodeBean);
			epapBalanceBean.setTransactionId(sellerTransactionsBean.getTransactionId());
			epapBalanceBean.setTransactionDate(sellerTransactionsBean.getTransactionCreation());
			epapBalanceBean.setDebitCredit(debitCredit);
			epapBalanceBean.setTransactionDescription(createSellerCommissionTransaction(sellerTransactionsBean.getAccountProfileBean().getPublicName()));
			epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.SELLER.getType()));
			epapBalanceBean.setBalance(getNewEpapBalance(debitCredit));
			epapBalanceBean.setComments(StringUtils.capitalize(ENUM_TransactionCodes.COMMISSION.getDesc().toString().toLowerCase()));
			epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
			epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition());
			epapService.save(epapBalanceBean);
			adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().add(debitCredit));
			accountProfileService.save(adminSupperAccountProfile);
			logger.info("=== ending createSellerCommissionCredit method ===");
		}

	}

	public void createBuyerCommissionCredit(BuyerTransactionsBean buyerTransactionsBean, BigDecimal debitCredit) {
		logger.info("EpapBalanceHepler call createBuyerCommissionCredit method");
		if (debitCredit.compareTo(new BigDecimal("0.0")) != 0) {
			EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
			epapBalanceBean.setAccountProfileBean(buyerTransactionsBean.getAccountProfileBean());
			TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.COMMISSION.getType());
			epapBalanceBean.setTransactionCodebean(transactionCodeBean);
			epapBalanceBean.setTransactionId(buyerTransactionsBean.getTransactionId());
			epapBalanceBean.setTransactionDate(buyerTransactionsBean.getTransactionCreation());
			epapBalanceBean.setDebitCredit(debitCredit);
			epapBalanceBean.setTransactionDescription(createBuyerCommissionTransaction(buyerTransactionsBean.getAccountProfileBean().getPublicName()));
			epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.BUYER.getType()));
			epapBalanceBean.setBalance(getNewEpapBalance(debitCredit));
			epapBalanceBean.setComments("");
			epapService.save(epapBalanceBean);
			logger.info("=== ending createBuyerCommissionCredit method ===");
		}
	}

	public void depositToEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, Character accountType, String comments) {
		logger.info("EpapBalanceHepler call depositToEpapAccount method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit);
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.DEPOSIT.getDesc() + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(accountType));
		epapBalanceBean.setBalance(getNewEpapBalance(debitCredit));
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().add(debitCredit));
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().add(debitCredit));
		adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().add(debitCredit));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending depositToEpapAccount method ===");
	}

	public Integer cashPositionToEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, Character accountTypeCodes, String comments) {
		logger.info("EpapBalanceHepler call cashPositionToEpapAccount method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit);
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.DEPOSIT.getDesc() + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(accountTypeCodes));
		epapBalanceBean.setBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance());
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().add(debitCredit));
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().add(debitCredit));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending cashPositionToEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();
	}

	public Integer advanceToEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, Character accountTypeCodes, String comments) {
		logger.info("EpapBalanceHepler call advanceToEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.ADVANCE.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit.negate());
		epapBalanceBean
				.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.getDesc().toString().toLowerCase()) + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(accountTypeCodes));
		epapBalanceBean.setBalance(getNewEpapBalance(debitCredit.negate()));
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition());
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance().add(debitCredit));
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance().add(debitCredit));
		adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending advanceToEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();
	}

	public Integer advanceUpdateToEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, Character accountTypeCodes, String comments) {
		logger.info("EpapBalanceHepler call advanceUpdateToEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit);
		epapBalanceBean.setTransactionDescription(
				StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.getDesc().toString().toLowerCase()) + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(accountTypeCodes));
		epapBalanceBean.setBalance(getNewEpapBalance(debitCredit.abs()));
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition());
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance().subtract(debitCredit.abs()));
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance().subtract(debitCredit));
		adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().add(debitCredit));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending advanceUpdateToEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();
	}

	public void withdrawToEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, Character accountTypeCodes, String comments) {
		logger.info("EpapBalanceHepler call withdrawToEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.WITHDRAWAL.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit);
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.WITHDRAWAL.getDesc() + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(accountTypeCodes));
		epapBalanceBean.setBalance(lastBalance);
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		logger.info("=== ending withdrawToEpapAccount method ===");
		accountProfileService.save(adminSupperAccountProfile);
	}

	public Integer adminWithdrawToEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, Character accountTypeCodes, String comments) {
		logger.info("EpapBalanceHepler call adminWithdrawToEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.WITHDRAWAL.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit.negate());
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.WITHDRAWAL.getDesc() + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(accountTypeCodes));
		epapBalanceBean.setBalance(lastBalance);
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending adminWithdrawToEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();

	}

	public void withdrawalFromEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, String comments) {
		logger.info("EpapBalanceHepler call withdrawalFromEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.WITHDRAWAL.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit.negate());
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.WITHDRAWAL.getDesc());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType()));
		epapBalanceBean.setBalance(getNewEpapBalance(debitCredit.negate()));
		epapBalanceBean.setComments(comments);
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		logger.info("=== ending withdrawalFromEpapAccount method ===");
		accountProfileService.save(adminSupperAccountProfile);
	}

	public Integer adjustCreditFromEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes, String comments,
			Character AccountTypeCodes) {
		logger.info("EpapBalanceHepler call adjustCreditFromEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(transactionCodes.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit.negate());
		epapBalanceBean.setTransactionDescription(StringUtils.capitalize(transactionCodes.getDesc().toLowerCase()) + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(AccountTypeCodes));
		epapBalanceBean.setBalance(getNewEpapBalance(debitCredit.negate()));
		epapBalanceBean.setComments(comments);
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition());
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending adjustCreditFromEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();

	}

	public Integer AdjustDebitFromEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes, String comments,
			Character AccountTypeCodes) {
		logger.info("EpapBalanceHepler call AdjustDebitFromEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(transactionCodes.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit.abs());
		epapBalanceBean.setTransactionDescription(StringUtils.capitalize(transactionCodes.getDesc().toString().toLowerCase()) + "-" + accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(AccountTypeCodes));
		epapBalanceBean.setBalance(getNewEpapBalance(debitCredit.abs()));
		epapBalanceBean.setComments(comments);
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition());
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setAvailableBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance().add(debitCredit.abs()));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending AdjustDebitFromEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();
	}

	public Integer withdrawalFromVatEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, String comments) {
		logger.info("EpapBalanceHepler call withdrawalFromVatEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.WITHDRAWAL.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit.negate());
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.WITHDRAWAL.getDesc() + "-" +accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.ADMIN_VAT.getType()));
		epapBalanceBean.setBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance());
		epapBalanceBean.setComments(comments);
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().subtract(debitCredit.abs()));
		accountProfileService.save(adminSupperAccountProfile);
		logger.info("=== ending withdrawalFromVatEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();
	}

	public Integer depositToVatEpapAccount(BigDecimal debitCredit, AccountProfileBean accountProfileBean, String comments) {
		logger.info("EpapBalanceHepler call depositToVatEpapAccount  method");
		EpapBalanceBean epapBalanceBean = new EpapBalanceBean();
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		AccountProfileBean adminVatAccountProfile = new AccountProfileBean();
		adminSupperAccountProfile = getEpapBankDetails();
		adminVatAccountProfile = adminOperationGetBankDetails();
		epapBalanceBean.setAccountProfileBean(accountProfileBean);
		TransactionCodeBean transactionCodeBean = new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType());
		epapBalanceBean.setTransactionCodebean(transactionCodeBean);
		epapBalanceBean.setTransactionDate(InternetTiming.getInternetDateTime());
		epapBalanceBean.setDebitCredit(debitCredit);
		epapBalanceBean.setTransactionDescription(ENUM_TransactionCodes.DEPOSIT.getDesc() + "-" +accountProfileBean.getAccountId());
		epapBalanceBean.setAccountTypeCodesBean(new AccountTypeCodesBean(ENUM_AccountTypeCodes.ADMIN_VAT.getType()));
		epapBalanceBean.setBalance(adminSupperAccountProfile.getBankDetailsBean().getAvailableBalance());
		epapBalanceBean.setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().add(debitCredit));
		epapBalanceBean.setAdvanceBalance(adminSupperAccountProfile.getBankDetailsBean().getAdvanceBalance());
		epapBalanceBean.setComments(comments);
		epapService.save(epapBalanceBean);
		adminSupperAccountProfile.getBankDetailsBean().setCashposition(adminSupperAccountProfile.getBankDetailsBean().getCashposition().add(debitCredit));
		accountProfileService.save(adminSupperAccountProfile);
		adminVatAccountProfile.getBankDetailsBean().setAvailableBalance(adminVatAccountProfile.getBankDetailsBean().getAvailableBalance().add(debitCredit));
		accountProfileService.save(adminVatAccountProfile);
		logger.info("=== ending depositToVatEpapAccount method ===");
		return epapBalanceBean.getEpapBalanceId();
	}

	private String createSellerCommissionTransaction(String sellerName) {
		String transactionDesc = ENUM_TransactionCodes.COMMISSION.getDesc();
		transactionDesc = transactionDesc + COMMISSION_SEPRATOR + COMMISSION_SELLER_TITLE + BLANK_SPACE + sellerName;
		return transactionDesc;
	}

	private String createBuyerCommissionTransaction(String buyerName) {
		String transactionDesc = ENUM_TransactionCodes.COMMISSION.getDesc();
		transactionDesc = transactionDesc + COMMISSION_SEPRATOR + COMMISSION_BUYER_TITLE + BLANK_SPACE + buyerName;
		return transactionDesc;
	}

	public BigDecimal getAvailableBalance() {
		return getNewEpapBalance(new BigDecimal(0.0));
	}

	private BigDecimal getNewEpapBalance(BigDecimal debitCredit) {
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		BigDecimal newAvailableBlanace = new BigDecimal(0.0);
		if (null == lastBalance) {
			lastBalance = new BigDecimal(0.0);
		}
		newAvailableBlanace = new BigDecimal(lastBalance.doubleValue() + debitCredit.doubleValue());

		return newAvailableBlanace;

	}

	public AccountProfileBean getEpapBankDetails() {
		AccountProfileBean adminSupperAccountProfile = new AccountProfileBean();
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfile(ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType());
		adminSupperAccountProfile = loginUser.getAccountProfileBean();
		return adminSupperAccountProfile;

	}

	public AccountProfileBean adminOperationGetBankDetails() {
		AccountProfileBean adminVatAccountProfile = new AccountProfileBean();
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfile(ENUM_AccountTypeCodes.ADMIN_VAT.getType());
		adminVatAccountProfile = loginUser.getAccountProfileBean();
		return adminVatAccountProfile;
	}

}