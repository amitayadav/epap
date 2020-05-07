package com.auction.controller.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.component.VatBalanceHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.VatBalanceBean;
import com.auction.service.LoginDetailsService;
import com.auction.service.VatService;
@Controller
@RequestMapping("/sprfinvat")
public class AdminSuperFinanceVatController {

	@Autowired
	private VatService vatService;
	
	@Autowired
	private VatBalanceHelper vatBalanceHelper;
	
	@Autowired
	private LoginDetailsService loginDetailsService;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/* View Vat Account Statment Details */
	@RequestMapping(value = "/vatAccountStatement")
	public String vatAccountStatement(Model model, HttpServletRequest request) {
		logger.info("Admin SuperFinance Vat Controller Call vatAccountStatement method ");
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
		List<VatBalanceBean> vatAccountStatementList = vatService.getVatBalanceBeanBetweenDate(DateHelper.getSqlDateFromString(startDateStr),
				DateHelper.getSqlDateFromString(endDateStr));
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		model.addAttribute("vatList", vatAccountStatementList);
		logger.info("=== Ending  vatAccountStatement method === ");
		return ViewConstant.ADMIN_VAT_ACCOUNT_STATEMENT;

	}

	/* View Vat Account Statment Details non ajax */
	@RequestMapping(value = "/vatAccountStatementList")
	public String vatAccountStatementList(Model model, HttpServletRequest request) {
		logger.info("Admin SuperFinance Vat Controller Call vatAccountStatementList method ");
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
		List<VatBalanceBean> vatAccountStatementList = vatService.getVatBalanceBeanBetweenDate(DateHelper.getSqlDateFromString(startDateStr),
				DateHelper.getSqlDateFromString(endDateStr));
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		model.addAttribute("vatList", vatAccountStatementList);
		logger.info("=== Ending  vatAccountStatementList method === ");
		return ViewConstant.NONAJAX_ADMIN_VAT_ACCOUNT_STATEMENT;

	}
	
	/* View Epap Account Balance Details */
	@RequestMapping("/getVatBalnace")
	public String getVatBalnace(Model model, HttpServletRequest request) {
		logger.info("Admin SuperFinance Vat Controller Call getVatBalnace method ");
		 LoginDetailsBean loginDetailsBean =
					SessionHelper.getLoginDetailsBean(request);
			LoginDetailsBean loginUserInBankDetails = loginDetailsService.getLoginDetailsByAccountProfile(ENUM_AccountTypeCodes.ADMIN_VAT.getType());
		model.addAttribute("loginUserinAccountType", loginDetailsBean.getAccountTypeCodesBean().getAccountType());
		model.addAttribute("bankDetails", loginUserInBankDetails.getAccountProfileBean().getBankDetailsBean());
		model.addAttribute("availableBalance",vatBalanceHelper.getAvailableBalance());
		logger.info("=== Ending  getVatBalnace  method === ");
		return "/admin/AM_vatBalanceUpdateAjaxFrom";
	}
	
	/**
	 * This method is used To user update VatBalance *
	 * 
	 * @param balanceType,balance,comments
	 *            selecting balanceType,balance,comments
	 * 
	 * @return vatAccountStatement page
	 */
	@RequestMapping(value="/updateVatBalance",method = RequestMethod.POST)
	public ModelAndView updateVatBalnace(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		logger.info("Admin SuperFinance Vat Controller Call updateVatBalnace method ");
		String view = "/sprfinvat/vatAccountStatement";
		String balanceType = request.getParameter("balanceType");
		String newBalanceStr = request.getParameter("balance");
		String comments =request.getParameter("comments");
		if ((null != balanceType && !balanceType.isEmpty()) && (null != newBalanceStr && !newBalanceStr.isEmpty())) {
			AccountProfileBean accountProfileBean=SessionHelper.getAccountProfile(request);
			if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_DEBIT_CODE)) {
				BigDecimal debitCredit = new BigDecimal(newBalanceStr);
				vatBalanceHelper.withdrawalFromVatAccount(debitCredit,accountProfileBean,comments);
			}
			if (balanceType.equalsIgnoreCase(CommonConstants.BALANCE_CREDIT_CODE)) {
				BigDecimal debitCredit = new BigDecimal(newBalanceStr);
				vatBalanceHelper.depositToVatAccount(debitCredit,accountProfileBean,comments);
			}
		}
		logger.info("=== Ending  updateVatBalnace  method === ");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
}