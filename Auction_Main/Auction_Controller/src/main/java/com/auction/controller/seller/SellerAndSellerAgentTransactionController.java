package com.auction.controller.seller;

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

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.views.AccountStatementsView;
import com.auction.service.AccountProfileService;
import com.auction.service.SellerBalanceService;

@Controller
@RequestMapping("/stransaction/")
public class SellerAndSellerAgentTransactionController {

	@Autowired
	private SellerBalanceService sellerBalanceService;

	@Autowired
	private AccountProfileService accountProfileService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@RequestMapping(value = "/financialstatement")
	public String financialstatement(Model model) {
		return ViewConstant.SELLER_FINANCIAL_STATEMENT;
	}
	
	/**NON AJAX  */
	@RequestMapping(value = "/financialStatement")
	public String financialStatement(Model model) {
		return ViewConstant.NONAJAX_SELLER_FINANCIAL_STATEMENT;
	}

	@RequestMapping(value = "/financialstatement", method = RequestMethod.POST)
	public ModelAndView financialstatementByDate(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("SellerAndSellerAgentTransactionController Call financialstatement Method");
		if ((null != request.getParameter("startDate") && !request.getParameter("startDate").isEmpty())
				&& (null != request.getParameter("endDate") && !request.getParameter("endDate").isEmpty())) {

			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
			Integer accountId = null;
			if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
				accountId = accountProfileService.findOwnerAccountIdByAgentAccountId(SessionHelper.getAccountProfileId(request));
			} else {
				accountId = SessionHelper.getAccountProfileId(request);
			}

			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			
			Date startDate = DateHelper.getStringToDate(startDateStr, true);
			Date endDate = DateHelper.getStringToDate(endDateStr, false);
			endDate=DateHelper.incrementDateByOneDay(endDate);
			List<AccountStatementsView> sellerAccountStatementsView=  sellerBalanceService.getSellerAccountStatementBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr));
			redirectAttributes.addFlashAttribute("transactionList", sellerBalanceService.getSellerAccountStatementBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr)));
			redirectAttributes.addFlashAttribute("startDate", startDateStr);
			redirectAttributes.addFlashAttribute("endDate", endDateStr);
		}
		logger.info("=== ending  financialstatement Method");
		RedirectView red = new RedirectView("/stransaction/financialstatement", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
}