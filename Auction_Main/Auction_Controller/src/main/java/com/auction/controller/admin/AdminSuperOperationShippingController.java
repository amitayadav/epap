package com.auction.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.component.AuctionPolicyProcessing;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.PickupTicketsBean;
import com.auction.service.AuctionBuyersService;
import com.auction.service.LoginDetailsService;
import com.auction.service.PickupTicketsService;

@Controller
@RequestMapping("/sproptshp")
public class AdminSuperOperationShippingController {

	@Autowired
	private AuctionBuyersService auctionBuyersService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuctionPolicyProcessing auctionPolicyProcessing;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private PickupTicketsService  pickupTicketsService;
	
	@Autowired
	private LoginDetailsService loginDetailsService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	/**
	 * This method is used to display manage shipping.
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return manage shipping
	 */
	@RequestMapping("/manageshipping")
	public String dashboard(Model model) {
		logger.info("AdminSuperOperationShipping Controller Call dashboard method");
		Short[] buyerBidStatusList = { ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus(),
				ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus() };
				List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getManageShippingBuyerAccount(buyerBidStatusList);
				model.addAttribute("auctionBuyersBeanList", auctionBuyersBeanList);
		logger.info("=== Ending  dashboard method ===");
		return ViewConstant.ADMIN_MANAGE_SHIPPING;
	}
	
	/**NON AJAX manageShipping */
	@RequestMapping("/manageShipping")
	public String syacDashboard(Model model) {
		logger.info("AdminSuperOperationShipping Controller Call syacDashboard method");
		Short[] buyerBidStatusList = { ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus(),
				ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus() };
				List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getManageShippingBuyerAccount(buyerBidStatusList);
				model.addAttribute("auctionBuyersBeanList", auctionBuyersBeanList);
	  			logger.info("=== Ending  syacDashboard method ===");
		return ViewConstant.NONAJAX_ADMIN_MANAGE_SHIPPING;
	}
	
	/* Confirm Delivery in adminShipper  */
	@RequestMapping(value = "/confirmdelivery/{auctionBuyerId}")
	public ModelAndView confirmDelivery(@PathVariable("auctionBuyerId") Integer auctionBuyerId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("AdminSuperOperationShipping Controller Call confirmDelivery method");
		LoginDetailsBean currentLoginDetailsBean = loginDetailsService.findById(SessionHelper.getLoginUserId(request));
		if (null != auctionBuyerId) {
			AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyerId);
			if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId()
					&& auctionBuyersBean.getAuctionBuyersId() > 0) {
				auctionPolicyProcessing.acceptCompleteBuyerBid(auctionBuyersBean,currentLoginDetailsBean.getAccountProfileBean().getAccountId());
				redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("bba.confirmdelivery.success",
						null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("bba.confirmdelivery.notfound",
						null, localeResolver.resolveLocale(request)));
			}
		} else {
			redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("bba.confirmdelivery.invalidid",
					null, localeResolver.resolveLocale(request)));
		}
		logger.info("=== Ending  confirmDelivery method ===");
		RedirectView red = new RedirectView("/sproptshp/manageshipping", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
	
	/** AdminSuper And AdminShipper PickupTickets    */
	@RequestMapping(value = "/asyncPickupTickets")
	public String asyncPickupTickets(HttpServletRequest request, Model model) {
		model.addAttribute("PickupTicketList",pickupTicketsService.findAll());
		return ViewConstant.ADMIN_PICKUP_TICKETS;

	}
	/**NON AJAX manageShipping */
	@RequestMapping(value = "/PickupTickets")
	public String PickupTickets(HttpServletRequest request, Model model) {
		model.addAttribute("PickupTicketList",pickupTicketsService.findAll());
		return ViewConstant.NONAJAX_ADMIN_PICKUP_TICKETS;

	}
	
		}
	

	
