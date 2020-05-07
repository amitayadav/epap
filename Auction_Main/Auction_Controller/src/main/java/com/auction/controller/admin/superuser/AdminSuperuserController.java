package com.auction.controller.admin.superuser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.component.EpapBalanceHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountLocationsBean;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionSettingsBean;
import com.auction.model.bean.EpapBalanceBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.RoyaltyCodesBean;
import com.auction.service.AccountLocationsService;
import com.auction.service.AuctionSettingsService;
import com.auction.service.CurrencyCodesService;
import com.auction.service.EpapService;
import com.auction.service.LoginDetailsService;
import com.auction.service.RoyaltyCodeService;

@Controller
@RequestMapping("/super/")
public class AdminSuperuserController {

	@Autowired
	private RoyaltyCodeService royaltyCodeService;

	@Autowired
	private AuctionSettingsService auctionSettingsService;

	@Autowired
	private CurrencyCodesService currencyCodesService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private AccountLocationsService accountLocationsService;

	@Autowired
	private EpapService epapService;

	@Autowired
	private EpapBalanceHelper epapBalanceHelper;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/* Royalty Code Master */
	@RequestMapping("/royaltycodedetailslist")
	public String royaltycodedetails(Model model) {
		model.addAttribute("royaltyCodeList", royaltyCodeService.getRoyaltyCodesOrderByRoyaltyValue());
		return ViewConstant.ADMIN_ROYALTY_CODE_LIST;
	}

	/* Royalty Code Master non ajax */
	@RequestMapping("/royaltyCodeDetailsList")
	public String royaltycodedetailsList(Model model) {
		model.addAttribute("royaltyCodeList", royaltyCodeService.getRoyaltyCodesOrderByRoyaltyValue());
		return ViewConstant.NONAJAX_ADMIN_ROYALTY_CODE_LIST;
	}

	/**
	 * This method is used To royalty code details *
	 * 
	 * @param royaltyCode
	 *            selecting royaltyCode
	 * 
	 * @return AM_royaltyCodeAjaxForm page
	 */
	@RequestMapping("/royaltycodedetails/{royaltyCode}")
	public String royaltycodedetails(@PathVariable(value = "royaltyCode", required = false) Short royaltyCode, Model model) {
		logger.info("AdminSuperuserControoller Call royaltycodedetails method");
		if (null != royaltyCode && royaltyCode > 0) {
			model.addAttribute("royaltyCodesBean", royaltyCodeService.findById(royaltyCode));
		} else {
			model.addAttribute("royaltyCodesBean", new RoyaltyCodesBean());
		}
		logger.info("=== Ending  royaltycodedetails method ===");
		return "/admin/AM_royaltyCodeAjaxForm";
	}

	/**
	 * This method is used To Save royalty code details *
	 * 
	 * @param RoyaltyCodesBean
	 *            selecting RoyaltyCodesBean
	 * 
	 * @return royaltycodedetailslist page
	 */
	@RequestMapping(value = "/saveroyaltycodedetails", method = RequestMethod.POST)
	public ModelAndView saveroyaltycodedetails(@ModelAttribute("royaltyCodesBean") RoyaltyCodesBean royaltyCodesBean, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		logger.info("AdminSuperuserControoller Call saveroyaltycodedetails method");
		RoyaltyCodesBean currentRoyaltyCodesBean = royaltyCodeService
				.findByRoyaltyCodeAndRoyaltyValue((null != royaltyCodesBean.getRoyaltyCode() ? royaltyCodesBean.getRoyaltyCode() : 0), royaltyCodesBean.getRoyaltyValue());

		if (null == currentRoyaltyCodesBean || null == currentRoyaltyCodesBean.getRoyaltyCode()) {
			if (null != royaltyCodesBean) {
				royaltyCodeService.save(royaltyCodesBean);
				if (null != royaltyCodesBean.getRoyaltyCode() && royaltyCodesBean.getRoyaltyCode() > 0) {
					redirectAttributes.addFlashAttribute("SUCCESS",
							messageSource.getMessage("royalty.code", null, localeResolver.resolveLocale(request)) + " " + royaltyCodesBean.getRoyaltyValue() + " "
									+ messageSource.getMessage("admincontroller.saveroyaltycodedetails.success.updated", null, localeResolver.resolveLocale(request)));
				} else {
					redirectAttributes.addFlashAttribute("SUCCESS",
							(messageSource.getMessage("royalty.code", null, localeResolver.resolveLocale(request)) + " " + royaltyCodesBean.getRoyaltyValue() + " "
									+ messageSource.getMessage("admincontroller.saveroyaltycodedetails.success.added", null, localeResolver.resolveLocale(request))));
				}
			}
		} else {
			redirectAttributes.addFlashAttribute("ERROR",
					(messageSource.getMessage("royalty.code", null, localeResolver.resolveLocale(request)) + " " + royaltyCodesBean.getRoyaltyValue() + " "
							+ messageSource.getMessage("admincontroller.saveroyaltycodedetails.error", null, localeResolver.resolveLocale(request))));
		}
		logger.info("=== Ending  saveroyaltycodedetails method ===");
		RedirectView red = new RedirectView("/super/royaltycodedetailslist", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/* Auction Settings Master */
	@RequestMapping("/auctionsettingslist")
	public String auctionsettingslist(Model model) {
		model.addAttribute("auctionSettingsBeanList", auctionSettingsService.findAll());
		return ViewConstant.ADMIN_AUCTION_SETTINGS_LIST;
	}

	/* Auction Settings Master non ajax */
	@RequestMapping("/auctionSettingsList")
	public String auctionsettingsList(Model model) {
		model.addAttribute("auctionSettingsBeanList", auctionSettingsService.findAll());
		return ViewConstant.NONAJAX_ADMIN_AUCTION_SETTINGS_LIST;
	}

	/**
	 * This method is used To auction settings *
	 * 
	 * @param auctionSettingsId
	 *            selecting auctionSettingsId
	 * 
	 * @return AM_auctionSettingsAjaxForm page
	 */

	@RequestMapping("/auctionsettings/{auctionSettingsId}")
	public String auctionsettings(@PathVariable(value = "auctionSettingsId", required = false) Integer auctionSettingsId, Model model) {
		logger.info("AdminSuperuserControoller Call auctionsettings method");
		if (null != auctionSettingsId && auctionSettingsId > 0) {
			model.addAttribute("auctionSettingsBean", auctionSettingsService.findById(auctionSettingsId));
		} else {
			model.addAttribute("auctionSettingsBean", new AuctionSettingsBean());
		}
		model.addAttribute("currencyCodesBeanList", currencyCodesService.getCurrencyCodesOrderBycurrencyName());
		logger.info("=== Ending  auctionsettings method ===");
		return "/admin/AM_auctionSettingsAjaxForm";
	}

	/**
	 * This method is used To save auction settings *
	 * 
	 * @param AuctionSettingsBean
	 *            selecting AuctionSettingsBean
	 * 
	 * @return auctionsettingslist page
	 */

	@RequestMapping(value = "/saveauctionsettings", method = RequestMethod.POST)
	public ModelAndView saveauctionsettings(@ModelAttribute("auctionSettingsBean") AuctionSettingsBean auctionSettingsBean, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		logger.info("AdminSuperuserControoller Call saveauctionsettings method");
		if (null != auctionSettingsBean) {
			if (null != auctionSettingsBean.getAuctionSettingsId()) {
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.saveauctionsettings.success.updated", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("admincontroller.saveauctionsettings.success.added", null, localeResolver.resolveLocale(request)));
			}
			auctionSettingsService.save(auctionSettingsBean);
		}
		logger.info("=== Ending  saveauctionsettings method ===");
		RedirectView red = new RedirectView("/super/auctionsettingslist", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/* Shipping Listing */
	@RequestMapping(value = "/shipperlist")
	public String shippersList(Model model) {
		logger.info("AdminSuperuserControoller Call shippersList  method");
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

		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		logger.info("=== Ending  shippersList  method ===");
		return ViewConstant.ADMIN_SUPER_SHIPPERS;
	}

	/* Shipping Listing NONAJAX */
	@RequestMapping(value = "/shipperList")
	public String syacShippersList(Model model) {
		logger.info("AdminSuperuserControoller Call syacShippersList  method");
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

		model.addAttribute("shipperAndShipperAgentList", shipperAndShipperAgentList);
		logger.info("=== Ending  syacShippersList  method ===");
		return ViewConstant.NONAJAX_ADMIN_SUPER_SHIPPERS;
	}

	/* View Epap Account Statment Details */
	@RequestMapping(value = "/epapAccountStatement")
	public String epapAccountStatement(Model model, HttpServletRequest request) {
		logger.info("AdminSuperuserControoller Call epapAccountStatement  method");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		Date startDate = new Date();
		Date endDate = new Date();
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		if (null != startDateStr && !startDateStr.isEmpty()) {
			startDate = DateHelper.getStringToDate(startDateStr, true);
		}
		if (null != endDateStr && !endDateStr.isEmpty()) {
			endDate = DateHelper.getStringToDate(endDateStr, false);
			endDate = DateHelper.incrementDateByOneDay(endDate);
		}
		List<EpapBalanceBean> epapAccountStatementList = epapService.getEpapBalanceBeanBetweenDate(DateHelper.getSqlDateFromString(startDateStr),
				DateHelper.getSqlDateFromString(endDateStr));
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		model.addAttribute("epapList", epapAccountStatementList);
		model.addAttribute("loginDtl", loginDetailsBean);
		logger.info("=== Ending  epapAccountStatement  method ===");
		return ViewConstant.ADMIN_EPAP_ACCOUNT_STATEMENT;

	}

	/* View Epap Account Statment Details non Ajax */
	@RequestMapping(value = "/epapAccountStatementList")
	public String epapAccountStatementList(Model model, HttpServletRequest request) {
		logger.info("AdminSuperuserControoller Call epapAccountStatementList  method");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		Date startDate = new Date();
		Date endDate = new Date();
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		if (null != startDateStr && !startDateStr.isEmpty()) {
			startDate = DateHelper.getStringToDate(startDateStr, true);
		}
		if (null != endDateStr && !endDateStr.isEmpty()) {
			endDate = DateHelper.getStringToDate(endDateStr, false);
			endDate = DateHelper.incrementDateByOneDay(endDate);
		}
		List<EpapBalanceBean> epapAccountStatementList = epapService.getEpapBalanceBeanBetweenDate(DateHelper.getSqlDateFromString(startDateStr),
				DateHelper.getSqlDateFromString(endDateStr));
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		model.addAttribute("epapList", epapAccountStatementList);
		model.addAttribute("loginDtl", loginDetailsBean);
		logger.info("=== Ending  epapAccountStatementList  method ===");
		return ViewConstant.NONAJAX_ADMIN_EPAP_ACCOUNT_STATEMENT;

	}

	/* View Epap Account Balance Details */
	@RequestMapping("/getEpapBalance/{accountId}")
	public String getEpapBalance(@PathVariable("accountId") Integer accountId, Model model, HttpServletRequest request) {
		logger.info("AdminSuperuserControoller Call getEpapBalance  method");
		LoginDetailsBean loginDetailsBean = loginDetailsService.getLoginDetailsByAccountProfileId(accountId);
		model.addAttribute("loginUserid", loginDetailsBean.getLoginUserid());
		model.addAttribute("bankDetails", loginDetailsBean.getAccountProfileBean().getBankDetailsBean());
		logger.info("=== Ending  getEpapBalance  method ===");
		return "/admin/AM_epapBalanceUpdateAjaxForm";
	}

	/* View Epap Account Balance Details */
	@RequestMapping(value = "/updateEpapBalance", method = RequestMethod.POST)
	public ModelAndView updateEpapBalance(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AdminSuperuserControoller Call updateEpapBalance  method");
		String view = "/super/epapAccountStatement";
		String balanceType = request.getParameter("balanceType");
		String newBalanceStr = request.getParameter("balance");
		String comments = request.getParameter("comments");
		if ((null != balanceType && !balanceType.isEmpty()) && (null != newBalanceStr && !newBalanceStr.isEmpty())) {

			AccountProfileBean accountProfileBean = SessionHelper.getAccountProfile(request);

			if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_DEBIT_CODE)) {
				BigDecimal debitCredit = new BigDecimal(newBalanceStr);
				epapBalanceHelper.withdrawalFromEpapAccount(debitCredit, accountProfileBean, comments);
			}
			if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_CREDIT_CODE)) {
				BigDecimal debitCredit = new BigDecimal(newBalanceStr);
				epapBalanceHelper.depositToEpapAccount(debitCredit, accountProfileBean, ENUM_AccountTypeCodes.ADMIN_SUPERUSER.getType(), comments);
			}
		}
		logger.info("=== Ending  updateEpapBalance  method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

}