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
import com.auction.model.bean.ShipperBalanceBean;
import com.auction.model.bean.ShipperBalanceIdBean;
import com.auction.model.bean.ShipperTransactionsBean;
import com.auction.model.bean.TransactionCodeBean;
import com.auction.service.AccountProfileService;
import com.auction.service.EpapService;
import com.auction.service.LoginDetailsService;
import com.auction.service.ShipperBalanceService;

@Component
public class ShipperBalanceHelper {

	@Autowired
	private ShipperBalanceService shipperBalanceService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private EpapBalanceHelper epapBalanceHelper;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private EpapService epapService;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	public void createBankTransactionDebit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			Integer createdOrUpdatedAccountId, String comments) {
		logger.info("ShipperBalanceHelper call createBankTransactionDebit method");
		ShipperBalanceIdBean shipperBalanceIdBean = new ShipperBalanceIdBean();
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());

		BigDecimal debitCredit = price;
		Integer epapTransactionId;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			shipperBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		epapTransactionId = epapBalanceHelper.adminWithdrawToEpapAccount(debitCredit, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(), comments);
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
		ShipperBalanceBean shipperBalanceBean2 = shipperBalanceService.getShipperBalanceNew(shipperBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != shipperBalanceBean2 && null != shipperBalanceBean2.getId() && null != shipperBalanceBean2.getId().getAccountProfileBean()
				&& null != shipperBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().subtract((price)));
		} else {
			shipperBalanceBean.setBalance(price);
		}
		debitCredit = debitCredit.negate();
		shipperBalanceBean.setDebitCredit(debitCredit);
		shipperBalanceBean.setAdvanceBalance(shipperBalanceIdBean.getAccountProfileBean().getBankDetailsBean().getAdvanceBalance());
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		shipperBalanceBean.setTransactionDescription((StringUtils.capitalize(transactionCodes.toString().toLowerCase()) + " By " + accountProfileBean.getFullName()));
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setComments(comments);
		shipperBalanceBean.setEpapTransactionId(epapTransactionId);
		shipperBalanceService.save(shipperBalanceBean);
		logger.info("=== ending  createBankTransactionDebit method ===");

	}

	public void createBankTransactionCredit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, String tranDesc, ENUM_TransactionCodes transactionCodes,
			Integer createdOrUpdatedAccountId, String comments) {
		logger.info("ShipperBalanceHelper call createBankTransactionCredit method");
		ShipperBalanceIdBean shipperBalanceIdBean = new ShipperBalanceIdBean();
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());

		BigDecimal debitCredit = price;

		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			shipperBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		ShipperBalanceBean shipperBalanceBean2 = shipperBalanceService.getShipperBalanceNew(shipperBalanceIdBean.getAccountProfileBean().getAccountId());
		shipperDepositUpdateAvailableBalance(shipperBalanceBean2, transactionId, price, accountProfileBean, transactionCodes, tranDesc, createdOrUpdatedAccountId,
				shipperBalanceIdBean, comments, loginUser.getAccountTypeCodesBean().getAccountType());
		if (accountProfileBean.getBankDetailsBean().getAdvanceBalance().compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser1 = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			accountProfileBean = loginUser1.getAccountProfileBean();
			shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);
			BigDecimal oldShipperAdvanceBalance = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
			BigDecimal newAvailableBlanace = new BigDecimal(0.0);
			newAvailableBlanace = new BigDecimal(accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue() - debitCredit.doubleValue());
			if (debitCredit.doubleValue() > accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue()) {
				oldShipperAdvanceBalance = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
				Integer epapTransactionId;
				epapTransactionId = epapBalanceHelper.advanceUpdateToEpapAccount(oldShipperAdvanceBalance, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(),
						comments);
				shipperSalesProductReturnAdvanceAmount(createdOrUpdatedAccountId, transactionId, oldShipperAdvanceBalance, shipperBalanceIdBean, accountProfileBean,
						epapTransactionId);

			} else {
				oldShipperAdvanceBalance = debitCredit;
				accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAvailableBlanace);
				Integer epapTransactionId;
				epapTransactionId = epapBalanceHelper.advanceUpdateToEpapAccount(oldShipperAdvanceBalance, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(),
						comments);
				shipperDepositReturnAdvanceAmount(createdOrUpdatedAccountId, transactionId, debitCredit, tranDesc, transactionCodes, shipperBalanceBean2, shipperBalanceIdBean,
						comments, newAvailableBlanace, accountProfileBean, epapTransactionId);
				logger.info("=== ending  createBankTransactionCredit method ===");
			}
		}
	}

	public void createShipperAdvanceBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			Integer createdOrUpdatedAccountId, String comments) {
		logger.info("ShipperBalanceHelper call createShipperAdvanceBalance method");
		ShipperBalanceIdBean shipperBalanceIdBean = new ShipperBalanceIdBean();
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		Integer epapTransactionId;
		BigDecimal debitCredit = price;
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		if (lastBalance != null && lastBalance.compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
				shipperBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
			} else {
				shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);

			}
			epapTransactionId = epapBalanceHelper.advanceToEpapAccount(debitCredit, accountProfileBean, loginUser.getAccountTypeCodesBean().getAccountType(), comments);
			ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
			ShipperBalanceBean shipperBalanceBean2 = shipperBalanceService.getShipperBalanceNew(shipperBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != shipperBalanceBean2 && null != shipperBalanceBean2.getId() && null != shipperBalanceBean2.getId().getAccountProfileBean()
					&& null != shipperBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().add((price)));
				if (ENUM_TransactionCodes.ADVANCE == transactionCodes) {
					shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCE.getType()));
					shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
				} else {
					shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
					shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				}
			} else {
				shipperBalanceBean.setBalance(price);
				shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCE.toString().toLowerCase()));
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
			shipperBalanceBean.setDebitCredit(debitCredit);
			shipperBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(price));
			shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
			shipperBalanceBean.setComments(comments);
			shipperBalanceBean.setEpapTransactionId(epapTransactionId);
			shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
			shipperBalanceService.save(shipperBalanceBean);
			accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
			accountProfileBean.getBankDetailsBean().setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(price));
			accountProfileService.save(accountProfileBean);
			logger.info("=== ending  createShipperAdvanceBalance method ===");
		}
	}

	public void createShipperAdjustCreditBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			Integer createdOrUpdatedAccountId, String comments) {
		logger.info("ShipperBalanceHelper call createShipperAdjustCreditBalance method");
		ShipperBalanceIdBean shipperBalanceIdBean = new ShipperBalanceIdBean();
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		Integer epapTransactionId;
		BigDecimal debitCredit = price;
		BigDecimal lastBalance = epapService.getLastEpapBalance();
		if (lastBalance != null && lastBalance.compareTo(BigDecimal.ZERO) != 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
				shipperBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
			} else {
				shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);
			}
			epapTransactionId = epapBalanceHelper.adjustCreditFromEpapAccount(debitCredit, accountProfileBean, transactionCodes, comments,
					loginUser.getAccountTypeCodesBean().getAccountType());
			ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
			ShipperBalanceBean shipperBalanceBean2 = shipperBalanceService.getShipperBalanceNew(shipperBalanceIdBean.getAccountProfileBean().getAccountId());
			if (null != shipperBalanceBean2 && null != shipperBalanceBean2.getId() && null != shipperBalanceBean2.getId().getAccountProfileBean()
					&& null != shipperBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
				shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().add((price)));
				if (ENUM_TransactionCodes.ADJUSTCREDIT == transactionCodes) {
					shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTCREDIT.getType()));
					shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
				} else {
					shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
					shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
				}
			} else {
				shipperBalanceBean.setBalance(price);
				shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTCREDIT.toString().toLowerCase()));
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
			shipperBalanceBean.setDebitCredit(debitCredit);
			shipperBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
			shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
			shipperBalanceBean.setComments(comments);
			shipperBalanceBean.setEpapTransactionId(epapTransactionId);
			shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
			shipperBalanceService.save(shipperBalanceBean);
			accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
			accountProfileService.save(accountProfileBean);
			logger.info("=== ending  createShipperAdjustCreditBalance method ===");
		}
	}

	public void createShipperAdjustDebitBalance(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, ENUM_TransactionCodes transactionCodes,
			Integer createdOrUpdatedAccountId, String comments) {
		logger.info("ShipperBalanceHelper call createShipperAdjustDebitBalance method");
		ShipperBalanceIdBean shipperBalanceIdBean = new ShipperBalanceIdBean();
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		Integer epapTransactionId;
		BigDecimal debitCredit = price;

		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			shipperBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		epapTransactionId = epapBalanceHelper.AdjustDebitFromEpapAccount(debitCredit, accountProfileBean, transactionCodes, comments,
				loginUser.getAccountTypeCodesBean().getAccountType());
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
		ShipperBalanceBean shipperBalanceBean2 = shipperBalanceService.getShipperBalanceNew(shipperBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != shipperBalanceBean2 && null != shipperBalanceBean2.getId() && null != shipperBalanceBean2.getId().getAccountProfileBean()
				&& null != shipperBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().subtract((price)));
			if (ENUM_TransactionCodes.ADJUSTDEBIT == transactionCodes) {
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTDEBIT.getType()));
				shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
			} else {
				shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
		} else {
			shipperBalanceBean.setBalance(price);
			shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
			shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		}
		debitCredit = debitCredit.negate();
		shipperBalanceBean.setDebitCredit(debitCredit);
		shipperBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setComments(comments);
		shipperBalanceBean.setEpapTransactionId(epapTransactionId);
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceService.save(shipperBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(price));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  createShipperAdjustDebitBalance method ===");
	}

	public void shipperDepositUpdateAvailableBalance(ShipperBalanceBean shipperBalanceBean2, Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean,
			ENUM_TransactionCodes transactionCodes, String tranDesc, Integer createdOrUpdatedAccountId, ShipperBalanceIdBean shipperBalanceIdBean, String comments,
			Character AccountType) {
		logger.info("ShipperBalanceHelper call shipperDepositUpdateAvailableBalance method");
		Integer epapTransactionId;
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
		epapTransactionId = epapBalanceHelper.cashPositionToEpapAccount(price, accountProfileBean, AccountType, comments);
		if (null != shipperBalanceBean2 && null != shipperBalanceBean2.getId() && null != shipperBalanceBean2.getId().getAccountProfileBean()
				&& null != shipperBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().add((price)));
			if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType()));
				shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.DEPOSIT.toString().toLowerCase()));
			} else {
				shipperBalanceBean.setTransactionDescription(tranDesc);
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
		} else {
			if (null == tranDesc || tranDesc.length() == 0) {
				tranDesc = StringUtils.capitalize(transactionCodes.toString().toLowerCase());
			}
			shipperBalanceBean.setBalance(price);
			shipperBalanceBean.setTransactionDescription(tranDesc);
			shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		}
		shipperBalanceBean.setDebitCredit(price);
		shipperBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setComments(comments);
		shipperBalanceBean.setEpapTransactionId(epapTransactionId);
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceService.save(shipperBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(price));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  shipperDepositUpdateAvailableBalance method ===");
	}

	public void shipperSalesProductReturnAdvanceAmount(Integer createdOrUpdatedAccountId, Integer transactionId, BigDecimal oldShipperAdvanceBalance,
			ShipperBalanceIdBean shipperBalanceIdBean, AccountProfileBean accountProfileBean, Integer epapTransactionId) {
		logger.info("ShipperBalanceHelper call shipperSalesProductReturnAdvanceAmount method");
		transactionId = null;
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		oldShipperAdvanceBalance = oldShipperAdvanceBalance.negate();
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean();
		shipperBalanceBean.setDebitCredit(oldShipperAdvanceBalance);
		shipperBalanceBean.setAdvanceBalance(new BigDecimal(0.00));
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
		shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
		shipperBalanceBean.setComments("");
		shipperBalanceBean.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(oldShipperAdvanceBalance.abs()));
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceBean.setId(shipperBalanceIdBean);
		shipperBalanceBean.setEpapTransactionId(epapTransactionId);
		shipperBalanceService.save(shipperBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(oldShipperAdvanceBalance.abs()));
		accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(0.00));
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  shipperSalesProductReturnAdvanceAmount method ===");
	}

	public void shipperDepositReturnAdvanceAmount(Integer createdOrUpdatedAccountId, Integer transactionId, BigDecimal debitCredit, String tranDesc,
			ENUM_TransactionCodes transactionCodes, ShipperBalanceBean shipperBalanceBean2, ShipperBalanceIdBean shipperBalanceIdBean, String comments,
			BigDecimal newAvailableBlanace, AccountProfileBean accountProfileBean, Integer epapTransactionId) {
		logger.info("ShipperBalanceHelper call shipperDepositReturnAdvanceAmount method");
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		shipperBalanceBean.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
			shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
			shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
		} else {
			shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADVANCEPAYOFF.toString().toLowerCase()));
			shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADVANCEPAYOFF.getType()));
		}
		debitCredit = debitCredit.negate();
		shipperBalanceBean.setAdvanceBalance(newAvailableBlanace);
		shipperBalanceBean.setDebitCredit(debitCredit);
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setComments(comments);
		shipperBalanceBean.setEpapTransactionId(epapTransactionId);
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceService.save(shipperBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(debitCredit.abs()));
		accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAvailableBlanace);
		accountProfileService.save(accountProfileBean);
		logger.info("=== ending  shipperDepositReturnAdvanceAmount method ===");
	}

	public void shipperSalescreateBankTransactionCredit(Integer transactionId, BigDecimal price, AccountProfileBean accountProfileBean, String tranDesc,
			ENUM_TransactionCodes transactionCodes, Integer createdOrUpdatedAccountId, String comments) {
		logger.info("ShipperBalanceHelper call shipperSalescreateBankTransactionCredit method");
		ShipperBalanceIdBean shipperBalanceIdBean = new ShipperBalanceIdBean();
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		BigDecimal debitCredit = price;
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(accountProfileBean.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			accountProfileBean = ownerLoginDetailsBean.getAccountProfileBean();
			shipperBalanceIdBean.setAccountProfileBean(ownerLoginDetailsBean.getAccountProfileBean());
		} else {
			shipperBalanceIdBean.setAccountProfileBean(accountProfileBean);
		}
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
		ShipperBalanceBean shipperBalanceBean2 = shipperBalanceService.getShipperBalanceNew(shipperBalanceIdBean.getAccountProfileBean().getAccountId());
		if (null != shipperBalanceBean2 && null != shipperBalanceBean2.getId() && null != shipperBalanceBean2.getId().getAccountProfileBean()
				&& null != shipperBalanceBean2.getId().getAccountProfileBean().getAccountId()) {
			shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().add((price)));
			if (ENUM_TransactionCodes.OPENING_BALANCE == transactionCodes) {
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.DEPOSIT.getType()));
				shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.DEPOSIT.toString().toLowerCase()));
			} else {
				shipperBalanceBean.setTransactionDescription(tranDesc);
				shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
			}
		} else {
			if (null == tranDesc || tranDesc.length() == 0) {
				tranDesc = StringUtils.capitalize(transactionCodes.toString().toLowerCase());
			}
			shipperBalanceBean.setBalance(price);
			shipperBalanceBean.setTransactionDescription(tranDesc);
			shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(transactionCodes.getType()));
		}
		shipperBalanceBean.setDebitCredit(debitCredit);
		shipperBalanceBean.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance());
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setComments(comments);
		shipperBalanceBean.setEpapTransactionId(null);
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceService.save(shipperBalanceBean);
		if (accountProfileBean.getBankDetailsBean().getAdvanceBalance().compareTo(BigDecimal.ZERO) != 0) {
			shipperBalanceBean2.setBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance());
			shipperSalesReturnAdvanceAmount(price, accountProfileBean, createdOrUpdatedAccountId, shipperBalanceBean2, shipperBalanceIdBean,
					loginUser.getAccountTypeCodesBean().getAccountType());

		}
		logger.info("=== ending  shipperSalescreateBankTransactionCredit method ===");
	}

	public void shipperSalesReturnAdvanceAmount(BigDecimal price, AccountProfileBean accountProfileBean, Integer createdOrUpdatedAccountId, ShipperBalanceBean shipperBalanceBean2,
			ShipperBalanceIdBean shipperBalanceIdBean, Character AccountType) {
		logger.info("ShipperBalanceHelper call shipperSalesReturnAdvanceAmount method");
		shipperBalanceIdBean.setTransactionDate(InternetTiming.getInternetDateTime());
		BigDecimal debitCredit = price;
		Integer transactionId = null;
		BigDecimal newdebitCredit = new BigDecimal(0.0);
		ShipperBalanceBean shipperBalanceBean = new ShipperBalanceBean(shipperBalanceIdBean);
		BigDecimal newAdvanceBlanace = new BigDecimal(0.0);
		newAdvanceBlanace = new BigDecimal(accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue() - debitCredit.doubleValue());

		if (debitCredit.doubleValue() > accountProfileBean.getBankDetailsBean().getAdvanceBalance().doubleValue()) {
			newdebitCredit = accountProfileBean.getBankDetailsBean().getAdvanceBalance();
			accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(0.00));
			shipperBalanceBean.setAdvanceBalance(new BigDecimal(0.00));
		} else {
			newdebitCredit = debitCredit;
			accountProfileBean.getBankDetailsBean().setAdvanceBalance(newAdvanceBlanace);
			shipperBalanceBean.setAdvanceBalance(newAdvanceBlanace);
		}
		shipperBalanceBean.setBalance(shipperBalanceBean2.getBalance().subtract((newdebitCredit)));
		shipperBalanceBean.setTransactionDescription(StringUtils.capitalize(ENUM_TransactionCodes.ADJUSTDEBIT.toString().toLowerCase()));
		shipperBalanceBean.setTransactionCodeBean(new TransactionCodeBean(ENUM_TransactionCodes.ADJUSTDEBIT.getType()));
		newdebitCredit = newdebitCredit.negate();
		shipperBalanceBean.setDebitCredit(newdebitCredit);
		shipperBalanceBean.setCreatedAccountProfileBean(new AccountProfileBean(createdOrUpdatedAccountId));
		shipperBalanceBean.setComments("");
		shipperBalanceBean.setEpapTransactionId(null);
		shipperBalanceBean.setShipperTransactionsBean(new ShipperTransactionsBean(transactionId));
		shipperBalanceService.save(shipperBalanceBean);
		accountProfileBean.getBankDetailsBean().setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(newdebitCredit.abs()));
		accountProfileService.save(accountProfileBean);
		epapBalanceHelper.advanceUpdateToEpapAccount(newdebitCredit.abs(), accountProfileBean, AccountType, "");
		logger.info("=== ending  shipperSalesReturnAdvanceAmount method ===");
	}
}