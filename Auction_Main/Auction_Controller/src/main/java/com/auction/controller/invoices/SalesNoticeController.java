package com.auction.controller.invoices;

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
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountProfileService;
import com.auction.service.SellerBalanceService;
@Controller
@RequestMapping("/salesNotice")
public class SalesNoticeController {
	
	@Autowired
	private SellerBalanceService sellerBalanceService;

	@Autowired
	private AccountProfileService accountProfileService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@RequestMapping("/seller")
	public String sellerSalesNotices() {
		return ViewConstant.SELLER_SALES_NOTICES;
	}
	
	@RequestMapping("/seller/salesNotice")
	public String sellersalesNotices() {
		return ViewConstant.NONAJAX_SELLER_SALES_NOTICES;
	}

	
	@RequestMapping(value = "/seller", method = RequestMethod.POST)
	public ModelAndView sellerSalesNotices(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("SalesNoticeController Call sellerSalesNotices Method ");
		if ((null != request.getParameter("startDate") && !request.getParameter("startDate").isEmpty())
				&& (null != request.getParameter("endDate") && !request.getParameter("endDate").isEmpty())) {
			String startDateStr = request.getParameter("startDate");
			String endDateStr = request.getParameter("endDate");
			Date startDate = DateHelper.getStringToDate(startDateStr, true);
			Date endDate = DateHelper.getStringToDate(endDateStr, false);
			endDate=DateHelper.incrementDateByOneDay(endDate);
			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
			Integer accountId = null;
			
			if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
				accountId = accountProfileService.findOwnerAccountIdByAgentAccountId(SessionHelper.getAccountProfileId(request));
			} else {
				accountId = SessionHelper.getAccountProfileId(request);
			}
			
			redirectAttributes.addFlashAttribute("sellerSalesNoticesViewList", sellerBalanceService.getSellerSalesNoticesBetweenDate(accountId,ENUM_TransactionCodes.SALES.getType(),java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr)));
			redirectAttributes.addFlashAttribute("startDate", startDateStr);
			redirectAttributes.addFlashAttribute("endDate", endDateStr);
		}
		logger.info("=== Ending  sellerSalesNotices Method ===");
		RedirectView red = new RedirectView("/salesNotice/seller", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
}
