package com.auction.controller.shipper;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountProfileService;
import com.auction.service.ShipperBalanceService;

@Controller
@RequestMapping("/shtransaction/")
public class ShipperAndShipperAgentTransactionController {

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private ShipperBalanceService shipperBalanceService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@RequestMapping(value = "/financialstatement")
	public String financialStatement() {
		return ViewConstant.SHIPPER_FINANCIAL_STATEMENT;
	}
	
	@RequestMapping(value = "/financialStatement")
	public String sysaFinancialStatement() {
		return ViewConstant.NONAJAX_SHIPPER_FINANCIAL_STATEMENT;
	}

	@RequestMapping(value = "/financialstatement", method = RequestMethod.POST)
	public ModelAndView financialstatementByDate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("ShipperAndShipperAgnetTransactionController  Call financialstatementByDate method ");
		if ((null != request.getParameter("startDate") && !request.getParameter("startDate").isEmpty())
				&& (null != request.getParameter("endDate") && !request.getParameter("endDate").isEmpty())) {

			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
			Integer accountId = null;
			if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
				accountId = accountProfileService
						.findOwnerAccountIdByAgentAccountId(SessionHelper.getAccountProfileId(request));
			} else {
				accountId = SessionHelper.getAccountProfileId(request);
			}

			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			Date startDate = DateHelper.getStringToDate(startDateStr, true);
			Date endDate = DateHelper.getStringToDate(endDateStr, false);
			endDate=DateHelper.incrementDateByOneDay(endDate);
			redirectAttributes.addFlashAttribute("transactionList",
					shipperBalanceService.getShipperAccountStatementBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr)));
			redirectAttributes.addFlashAttribute("startDate", startDateStr);
			redirectAttributes.addFlashAttribute("endDate", endDateStr);
		}
		logger.info("=== ending  financialstatementByDate method ===");
		RedirectView red = new RedirectView("/shtransaction/financialstatement", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
}
