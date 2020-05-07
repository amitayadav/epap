package com.auction.controller.admin;

import java.util.Date;

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

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountTypeCodesService;
import com.auction.service.BuyerBalanceService;
import com.auction.service.LoginDetailsService;
import com.auction.service.SellerBalanceService;
import com.auction.service.ShipperBalanceService;

@Controller
@RequestMapping("/sprfinshp/")
public class AdminSuperFinanceShippingController {

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private SellerBalanceService sellerBalanceService;

	@Autowired
	private BuyerBalanceService buyerBalanceService;

	@Autowired
	private ShipperBalanceService shipperBalanceService;

	@Autowired
	private AccountTypeCodesService accountTypeCodesService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/* View Financial Statment Details */
	@RequestMapping(value = "/userfinancialstatement")
	public String financialstatement(Model model, HttpServletRequest request) {
		logger.info("AdminSuperFinanceShipper Controller Call financialstatement method");
		Character accountType = SessionHelper.getAccountType(request, false);
		if (accountType == ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType()) {
			model.addAttribute("userList", loginDetailsService.findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(ENUM_AccountStatusCodes.ACTIVE.getStatus(),
					new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType() }));
		} else {
			model.addAttribute("userList", loginDetailsService.findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(ENUM_AccountStatusCodes.ACTIVE.getStatus(),
					new Character[] { ENUM_AccountTypeCodes.BUYER.getType(), ENUM_AccountTypeCodes.SELLER.getType(), ENUM_AccountTypeCodes.SHIPPER.getType() }));
		}
		logger.info("=== Ending financialstatement method === ");
		return ViewConstant.ADMIN_FINANCIAL_STATEMENT;
	}

	/* View Financial Statment Details non ajax */
	@RequestMapping(value = "/userFinancialStatement")
	public String financialStatement(Model model, HttpServletRequest request) {
		logger.info("AdminSuperFinanceShipper Controller Call financialStatement method");
		Character accountType = SessionHelper.getAccountType(request, false);
		if (accountType == ENUM_AccountTypeCodes.ADMIN_SHIPPING.getType()) {
			model.addAttribute("userList", loginDetailsService.findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(ENUM_AccountStatusCodes.ACTIVE.getStatus(),
					new Character[] { ENUM_AccountTypeCodes.SHIPPER.getType() }));
		} else {
			model.addAttribute("userList", loginDetailsService.findByAccountStatusCodesAccountStatusCodeAndAccountTypeCodesAccountTypeIn(ENUM_AccountStatusCodes.ACTIVE.getStatus(),
					new Character[] { ENUM_AccountTypeCodes.BUYER.getType(), ENUM_AccountTypeCodes.SELLER.getType(), ENUM_AccountTypeCodes.SHIPPER.getType() }));
		}
		logger.info("=== Ending financialStatement method === ");
		return ViewConstant.NONAJAX_ADMIN_FINANCIAL_STATEMENT;
	}

	/**
	 * This method is used To user financial statement *
	 * 
	 * @param startDate,endDate,accountId
	 *            selecting startDate,endDate,accountId
	 * 
	 * @return userfinancialstatement page
	 */
	@RequestMapping(value = "/userfinancialstatement", method = RequestMethod.POST)
	public ModelAndView financialstatementByDate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AdminSuperFinanceShipper Controller Call financialstatementByDate method");
		if ((null != request.getParameter("startDate") && !request.getParameter("startDate").isEmpty())
				&& (null != request.getParameter("endDate") && !request.getParameter("endDate").isEmpty())
				&& (null != request.getParameter("accountId") && !request.getParameter("accountId").isEmpty())) {
			Integer accountId = Integer.parseInt(request.getParameter("accountId"));
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			Date startDate = DateHelper.getStringToDate(startDateStr, true);
			Date endDate = DateHelper.getStringToDate(endDateStr, false);
			endDate = DateHelper.incrementDateByOneDay(endDate);
			LoginDetailsBean loginDetailsBean = loginDetailsService.getLoginDetailsByAccountProfileId(accountId);

			if (loginDetailsBean.getAccountTypeCodesBean().getAccountType().equals(ENUM_AccountTypeCodes.SELLER.getType())) {
				redirectAttributes.addFlashAttribute("transactionList",

						sellerBalanceService.getSellerAccountStatementBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr)));
			} else if (loginDetailsBean.getAccountTypeCodesBean().getAccountType().equals(ENUM_AccountTypeCodes.BUYER.getType())) {
				redirectAttributes.addFlashAttribute("transactionList",
						buyerBalanceService.getBuyerAccountStatementBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr)));
			} else if (loginDetailsBean.getAccountTypeCodesBean().getAccountType().equals(ENUM_AccountTypeCodes.SHIPPER.getType())) {
				redirectAttributes.addFlashAttribute("transactionList",
						shipperBalanceService.getShipperAccountStatementBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr)));
				redirectAttributes.addFlashAttribute("userAccountType", accountTypeCodesService.getAccountTypeCodeByAccountId(accountId));
			}
			redirectAttributes.addFlashAttribute("startDate", startDateStr);
			redirectAttributes.addFlashAttribute("endDate", endDateStr);
			redirectAttributes.addFlashAttribute("accountId", accountId);
			redirectAttributes.addFlashAttribute("accountType", loginDetailsBean.getAccountTypeCodesBean().getAccountType());
		}
		logger.info("=== Ending financialstatementByDate method === ");
		RedirectView red = new RedirectView("/sprfinshp/userfinancialstatement", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

}