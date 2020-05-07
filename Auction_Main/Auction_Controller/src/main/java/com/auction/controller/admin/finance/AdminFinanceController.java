package com.auction.controller.admin.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountLocationStatus;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.component.BuyerBalanceHelper;
import com.auction.component.SellerBalanceHelper;
import com.auction.component.ShipperBalanceHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountLocationsBean;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountLocationsService;
import com.auction.service.AccountProfileService;
import com.auction.service.EpapService;
import com.auction.service.LoginDetailsService;

@Controller
@RequestMapping("/finance")
public class AdminFinanceController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private SellerBalanceHelper sellerBankDetailsHelper;

	@Autowired
	private BuyerBalanceHelper buyerBankDetailsHelper;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ShipperBalanceHelper shipperBankDetailsHelper;

	@Autowired
	private AccountLocationsService accountLocationsService;

	@Autowired
	private EpapService epapService;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	/* Balance Management */
	@RequestMapping("/userbalancedetailslist")
	public String userbalancedetailslist(Model model) {
		logger.info("AdminFinanceController Call userbalancedetailslist method ");
		List<Character> typeList = new ArrayList<Character>(0);
		typeList.add(ENUM_AccountTypeCodes.BUYER.getType());
		typeList.add(ENUM_AccountTypeCodes.SELLER.getType());
		typeList.add(ENUM_AccountTypeCodes.SHIPPER.getType());
		model.addAttribute("loginDetailsList", loginDetailsService.findByAccountTypeCodesIn(typeList));
		logger.info("=== Ending userbalancedetailslist method  ====");
		return ViewConstant.ADMIN_FINANCE_USER_BALANCE_DETAILS_LIST;
	}

	/* Balance Management non Ajax */
	@RequestMapping("/userBalanceDetailsList")
	public String userBalanceDetailsList(Model model) {
		logger.info("AdminFinanceController Call userBalanceDetailsList method ");
		List<Character> typeList = new ArrayList<Character>(0);
		typeList.add(ENUM_AccountTypeCodes.BUYER.getType());
		typeList.add(ENUM_AccountTypeCodes.SELLER.getType());
		typeList.add(ENUM_AccountTypeCodes.SHIPPER.getType());
		model.addAttribute("loginDetailsList", loginDetailsService.findByAccountTypeCodesIn(typeList));
		logger.info("=== Ending userBalanceDetailsList method  ====");
		return ViewConstant.NONAJAX_ADMIN_FINANCE_USER_BALANCE_DETAILS_LIST;
	}

	/**
	 * This method is used to getUserBalace
	 * 
	 * @param accountId
	 *            pass the image by particular account id folder
	 * @param accountId
	 *            selecting accountId
	 * 
	 * @return sAM_FIN_userBalanceUpdateAjaxForm page
	 */
	@RequestMapping("/getUserBalace/{accountId}")
	public String getUserBalace(@PathVariable("accountId") Integer accountId, Model model, HttpServletRequest request) {
		logger.info("AdminFinanceController Call getUserBalace method ");
		LoginDetailsBean loginDetailsBean = loginDetailsService.getLoginDetailsByAccountProfileId(accountId);

		model.addAttribute("loginUserid", loginDetailsBean.getLoginUserid());
		model.addAttribute("bankDetails", loginDetailsBean.getAccountProfileBean().getBankDetailsBean());
		logger.info("=== Ending getUserBalace method  ====");
		return "/admin/finance/AM_FIN_userBalanceUpdateAjaxForm";
	}

	/**
	 * This method is used To userBalanceUpdate
	 *
	 * * @param request HttpServletRequest is for getting request value.
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return userbalancedetailslist page
	 */
	@RequestMapping(value = "/userBalanceUpdate", method = RequestMethod.POST)
	public ModelAndView userBalanceUpdate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AdminFinanceController Call userBalanceUpdate method ");
		String view = "/finance/userbalancedetailslist";

		// Getting data from request.
		String balanceType = request.getParameter("balanceType");
		String newBalanceStr = request.getParameter("balance");
		String loginUserid = request.getParameter("loginUserid");
		String comments = request.getParameter("comments");

		if ((null != balanceType && !balanceType.isEmpty()) && (null != newBalanceStr && !newBalanceStr.isEmpty()) && (null != loginUserid && !loginUserid.isEmpty())) {
			if (new BigDecimal(newBalanceStr).doubleValue() >= 0.01 && new BigDecimal(newBalanceStr).doubleValue() <= 9999999.99) {
				LoginDetailsBean loginDetailsBean = loginDetailsService.findById(loginUserid);
				AccountProfileBean accountProfileBean = loginDetailsBean.getAccountProfileBean();
				BigDecimal lastBalance = epapService.getLastEpapBalance();
				if (loginDetailsBean.getAccountTypeCodesBean().getAccountType().equals(ENUM_AccountTypeCodes.SELLER.getType())) {
					if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_DEBIT_CODE)) {
						BigDecimal currentBalance = null;
						if (null != accountProfileBean && null != accountProfileBean.getBankDetailsBean()
								&& null != accountProfileBean.getBankDetailsBean().getAvailableBalance()) {
							currentBalance = accountProfileBean.getBankDetailsBean().getAvailableBalance();
						}
						BigDecimal debitCredit = new BigDecimal(newBalanceStr);
						if (debitCredit.doubleValue() <= currentBalance.doubleValue()) {
							sellerBankDetailsHelper.createBankTransactionDebit(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.WITHDRAWAL, comments);
							accountProfileBean.getBankDetailsBean()
									.setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(new BigDecimal(newBalanceStr)));
							accountProfileService.save(accountProfileBean);
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.userbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADVANCE_CODE)) {
						Boolean checkEpaplastBalance = checkEapaBalance(lastBalance, new BigDecimal(newBalanceStr));
						if (checkEpaplastBalance) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.epapbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						} else {
							sellerBankDetailsHelper.createSellerAdvanceBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADVANCE, comments);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADJUSTCREDIT_CODE)) {
						Boolean checkEpaplastBalance = checkEapaBalance(lastBalance, new BigDecimal(newBalanceStr));
						if (checkEpaplastBalance) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.epapbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						} else {
							sellerBankDetailsHelper.createSellerAdjustCreditBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADJUSTCREDIT,
									comments);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADJUSTDEBIT_CODE)) {
						sellerBankDetailsHelper.createSellerAdjustDebitBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADJUSTDEBIT,
								comments);
					} else {
						sellerBankDetailsHelper.createBankTransactionCredit(null, new BigDecimal(newBalanceStr), accountProfileBean,
								StringUtils.capitalize(ENUM_TransactionCodes.OPENING_BALANCE.toString().toLowerCase()), ENUM_TransactionCodes.OPENING_BALANCE, comments);
					}
				} else if (loginDetailsBean.getAccountTypeCodesBean().getAccountType().equals(ENUM_AccountTypeCodes.BUYER.getType())) {

					if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_DEBIT_CODE)) {
						BigDecimal currentBalance = null;
						if (null != accountProfileBean && null != accountProfileBean.getBankDetailsBean()
								&& null != accountProfileBean.getBankDetailsBean().getAvailableBalance()) {
							currentBalance = accountProfileBean.getBankDetailsBean().getAvailableBalance();
						}
						BigDecimal debitCredit = new BigDecimal(newBalanceStr);
						Boolean withdraw = checkCurrentBalanceBalancAndWithdrawBalancAndAdvanceBalance(currentBalance, debitCredit,
								accountProfileBean.getBankDetailsBean().getAdvanceBalance());
						if (withdraw) {
							buyerBankDetailsHelper.createBuyerBalanceDebit(null, new BigDecimal(newBalanceStr), accountProfileBean,
									StringUtils.capitalize(ENUM_TransactionCodes.WITHDRAWAL.toString().toLowerCase()), ENUM_TransactionCodes.WITHDRAWAL, comments);
							accountProfileBean.getBankDetailsBean()
									.setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(new BigDecimal(newBalanceStr)));
							accountProfileService.save(accountProfileBean);
						} else {
							if (withdraw == false) {
								redirectAttributes.addFlashAttribute("ERROR",
										messageSource.getMessage("admincontroller.epapbalanceupdate.error.withdraw", null, localeResolver.resolveLocale(request)));
								RedirectView red = new RedirectView(view, true);
								red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
								return new ModelAndView(red);
							} else {
								redirectAttributes.addFlashAttribute("ERROR",
										messageSource.getMessage("admincontroller.userbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
								RedirectView red = new RedirectView(view, true);
								red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
								return new ModelAndView(red);
							}

						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADVANCE_CODE)) {
						Boolean checkEpaplastBalance = checkEapaBalance(lastBalance, new BigDecimal(newBalanceStr));
						if (checkEpaplastBalance) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.epapbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						} else {
							buyerBankDetailsHelper.createBuyerAdvanceBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADVANCE, comments);
							if (null != accountProfileBean.getBankDetailsBean().getAdvanceBalance()) {
								accountProfileBean.getBankDetailsBean()
										.setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(new BigDecimal(newBalanceStr)));
								accountProfileBean.getBankDetailsBean()
										.setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(new BigDecimal(newBalanceStr)));

							} else {
								accountProfileBean.getBankDetailsBean().setAvailableBalance(new BigDecimal(newBalanceStr));
								accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(newBalanceStr));
							}
							accountProfileService.save(accountProfileBean);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADJUSTCREDIT_CODE)) {
						Boolean checkEpaplastBalance = checkEapaBalance(lastBalance, new BigDecimal(newBalanceStr));
						if (checkEpaplastBalance) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.epapbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						} else {
							buyerBankDetailsHelper.createBuyerAdjustCreditBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADJUSTCREDIT,
									comments);
							if (null != accountProfileBean.getBankDetailsBean().getAdvanceBalance()) {
								// accountProfileBean.getBankDetailsBean().setAdvanceBalance(accountProfileBean.getBankDetailsBean().getAdvanceBalance().add(new
								// BigDecimal(newBalanceStr)));
								accountProfileBean.getBankDetailsBean()
										.setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().add(new BigDecimal(newBalanceStr)));

							} else {
								// accountProfileBean.getBankDetailsBean().setAvailableBalance(new
								// BigDecimal(newBalanceStr));
								accountProfileBean.getBankDetailsBean().setAdvanceBalance(new BigDecimal(newBalanceStr));
							}
							accountProfileService.save(accountProfileBean);
						}

					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADJUSTDEBIT_CODE)) {
						buyerBankDetailsHelper.createBuyerAdjustDebitBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADJUSTDEBIT, comments);
					} else {
						buyerBankDetailsHelper.createBuyerBalanceCredit(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.OPENING_BALANCE, comments);
					}
				}

				else if (loginDetailsBean.getAccountTypeCodesBean().getAccountType().equals(ENUM_AccountTypeCodes.SHIPPER.getType())) {
					if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_DEBIT_CODE)) {
						BigDecimal currentBalance = null;
						if (null != accountProfileBean && null != accountProfileBean.getBankDetailsBean()
								&& null != accountProfileBean.getBankDetailsBean().getAvailableBalance()) {
							currentBalance = accountProfileBean.getBankDetailsBean().getAvailableBalance();
						}
						BigDecimal debitCredit = new BigDecimal(newBalanceStr);
						if (debitCredit.doubleValue() <= currentBalance.doubleValue()) {
							shipperBankDetailsHelper.createBankTransactionDebit(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.WITHDRAWAL,
									SessionHelper.getAccountProfileId(request), comments);
							accountProfileBean.getBankDetailsBean()
									.setAvailableBalance(accountProfileBean.getBankDetailsBean().getAvailableBalance().subtract(new BigDecimal(newBalanceStr)));
							accountProfileService.save(accountProfileBean);
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.userbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADVANCE_CODE)) {
						Boolean checkEpaplastBalance = checkEapaBalance(lastBalance, new BigDecimal(newBalanceStr));
						if (checkEpaplastBalance) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.epapbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						} else {
							shipperBankDetailsHelper.createShipperAdvanceBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADVANCE,
									SessionHelper.getAccountProfileId(request), comments);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADJUSTCREDIT_CODE)) {
						Boolean checkEpaplastBalance = checkEapaBalance(lastBalance, new BigDecimal(newBalanceStr));
						if (checkEpaplastBalance) {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("admincontroller.epapbalanceupdate.error.debitbalance", null, localeResolver.resolveLocale(request)));
							RedirectView red = new RedirectView(view, true);
							red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
							return new ModelAndView(red);
						} else {
							shipperBankDetailsHelper.createShipperAdjustCreditBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADJUSTCREDIT,
									SessionHelper.getAccountProfileId(request), comments);
						}
					} else if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_ADJUSTDEBIT_CODE)) {
						shipperBankDetailsHelper.createShipperAdjustDebitBalance(null, new BigDecimal(newBalanceStr), accountProfileBean, ENUM_TransactionCodes.ADJUSTDEBIT,
								SessionHelper.getAccountProfileId(request), comments);

					} else {
						shipperBankDetailsHelper.createBankTransactionCredit(null, new BigDecimal(newBalanceStr), accountProfileBean,
								StringUtils.capitalize(ENUM_TransactionCodes.OPENING_BALANCE.toString().toLowerCase()), ENUM_TransactionCodes.OPENING_BALANCE,
								SessionHelper.getAccountProfileId(request), comments);
					}
				}

				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.userbalanceupdate.error.updatedbalance", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("ERROR",
						messageSource.getMessage("admincontroller.userbalanceupdate.error.balanceinput", null, localeResolver.resolveLocale(request)));
			}
		} else {
			redirectAttributes.addFlashAttribute("ERROR",
					messageSource.getMessage("admincontroller.userbalanceupdate.error.missinginput", null, localeResolver.resolveLocale(request)));
		}
		logger.info("=== Ending userBalanceUpdate method  ====");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/* Shipping Listing */
	@RequestMapping(value = "/shipperlist")
	public String shippersList(Model model) {
		logger.info("AdminFinanceController Call shippersList method ");
		List<LoginDetailsBean> shipperAndShipperAgentList = loginDetailsService
				.getLoginDetailsListByAccountTypes(new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType(), ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() });

		for (LoginDetailsBean loginDetailsBean : shipperAndShipperAgentList) {
			if (null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getAccountId()
					&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
				List<AccountLocationsBean> accountLocationBeanList = accountLocationsService.findByAccountProfileAccountIdAndStatusIn(
						loginDetailsBean.getAccountProfileBean().getAccountId(), new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() });
				if (null != accountLocationBeanList && accountLocationBeanList.size() > 0) {
					loginDetailsBean.getAccountProfileBean().setExistingAccountLocationBean(accountLocationBeanList);
				}
			}
		}
		logger.info("=== Ending shippersList method  ====");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.ADMIN_FINANCE_SHIPPERS;
	}

	/* Shipping Listing NONAJAx */
	@RequestMapping(value = "/shipper/List")
	public String syacShippersList(Model model) {
		logger.info("AdminFinanceController Call syacShippersList method ");
		List<LoginDetailsBean> shipperAndShipperAgentList = loginDetailsService
				.getLoginDetailsListByAccountTypes(new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType(), ENUM_AccountTypeCodes.SHIPPER_AGENT.getType() });

		for (LoginDetailsBean loginDetailsBean : shipperAndShipperAgentList) {
			if (null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getAccountId()
					&& loginDetailsBean.getAccountProfileBean().getAccountId() > 0) {
				List<AccountLocationsBean> accountLocationBeanList = accountLocationsService.findByAccountProfileAccountIdAndStatusIn(
						loginDetailsBean.getAccountProfileBean().getAccountId(), new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() });
				if (null != accountLocationBeanList && accountLocationBeanList.size() > 0) {
					loginDetailsBean.getAccountProfileBean().setExistingAccountLocationBean(accountLocationBeanList);
				}
			}
		}
		logger.info("=== Ending syacShippersList method  ====");
		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		return ViewConstant.NONAJAX_ADMIN_FINANCE_SHIPPERS;
	}

	public static Boolean checkCurrentBalanceBalancAndWithdrawBalancAndAdvanceBalance(BigDecimal currentBalance, BigDecimal debitCredit, BigDecimal advanceBalance) {
		double BuyerDebitCredit = currentBalance.doubleValue() - debitCredit.doubleValue();
		if (BuyerDebitCredit >= advanceBalance.doubleValue()) {
			return true;
		} else {
			return false;
		}

	}

	public static Boolean checkEapaBalance(BigDecimal lastBalance, BigDecimal newBalanceStr) {
		if (lastBalance == null) {
			return true;
		} else if (lastBalance.compareTo(BigDecimal.ZERO) == 0.0) {
			return true;
		} else if (lastBalance.doubleValue() < newBalanceStr.doubleValue()) {
			return true;
		} else {
			return false;
		}
	}

}