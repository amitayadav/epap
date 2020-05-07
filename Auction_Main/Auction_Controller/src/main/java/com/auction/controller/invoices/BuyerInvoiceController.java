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
import com.auction.service.BuyerBalanceService;

@Controller
@RequestMapping("/invoicepurchase")
public class BuyerInvoiceController {
	
	@Autowired
	private BuyerBalanceService buyerBalanceService;

	@Autowired
	private AccountProfileService accountProfileService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@RequestMapping("/buyer/invoicePurchase")
	public String invoicePurchaseBuyer() {
		return ViewConstant.INVOICE_PURCHASE_BUYER;
	}
	
	/**Non Ajax metod  */
	@RequestMapping("/buyer/invoicePurchaseList")
	public String invoicePurchaseBuyers() {
		return ViewConstant.NONAJAX_INVOICE_PURCHASE_BUYER;
	}
	
	@RequestMapping(value = "/buyer/invoicePurchase", method = RequestMethod.POST)
	public ModelAndView invoicePurchaseBuyer(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("BuyerInvoiceController Call invoicePurchaseBuyer Method ");
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
			
			redirectAttributes.addFlashAttribute("invoicePurchaseList", buyerBalanceService.getBuyerPurchaseInvoicesBetweenDate(accountId, java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr),ENUM_TransactionCodes.PURCHASE.getType()));
			redirectAttributes.addFlashAttribute("startDate", startDateStr);
			redirectAttributes.addFlashAttribute("endDate", endDateStr);
		}
		logger.info("=== Ending  invoicePurchaseBuyer Method ====");
		RedirectView red = new RedirectView("/invoicepurchase/buyer/invoicePurchase", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
}
