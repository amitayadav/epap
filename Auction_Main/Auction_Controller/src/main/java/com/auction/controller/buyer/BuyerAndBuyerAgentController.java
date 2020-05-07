package com.auction.controller.buyer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.component.AuctionPolicyProcessing;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.PickupTicketsBean;
import com.auction.model.bean.RatingsLogBean;
import com.auction.service.AccountProfileService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.LoginDetailsService;
import com.auction.service.PickupTicketsService;
import com.auction.service.RatingsLogService;

@Controller
@RequestMapping("/bba")
public class BuyerAndBuyerAgentController {

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionPolicyProcessing auctionPolicyProcessing;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PickupTicketsService pickupTicketsService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private RatingsLogService ratingsLogService;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/* Confirm Delivery */
	@RequestMapping(value = "/confirmdelivery")
	public String confirmDelivery(Model model, HttpServletRequest request) {
		logger.info("BuyerAndBuyerAgentController Call confirmDelivery method");
		Short[] buyerBidStatusList = { ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus(),
				ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus() };
		Date internetTime = InternetTiming.getInternetDateTime();
		Date beginDate = DateHelper.getTomorrowDate(internetTime, true);
		Date endDate = DateHelper.getTomorrowDate(internetTime, false);
		List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getConfirmDeliveryByBuyerAccountIdIn(
				SessionHelper.getAccountProfileId(request), buyerBidStatusList, beginDate, endDate);
		model.addAttribute("auctionBuyersBeanList", auctionBuyersBeanList);
		logger.info("=== ending confirmDelivery method ===");
		return ViewConstant.BUYER_CONFIRM_DELIVERY;
	}

	/* Confirm Delivery non ajax */
	@RequestMapping(value = "/sysaConfirmDelivery")
	public String sysaConfirmDelivery(Model model, HttpServletRequest request) {
		logger.info("BuyerAndBuyerAgentController Call confirmDelivery method");
		Short[] buyerBidStatusList = { ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus(),
				ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus() };
		Date internetTime = InternetTiming.getInternetDateTime();
		Date beginDate = DateHelper.getTomorrowDate(internetTime, true);
		Date endDate = DateHelper.getTomorrowDate(internetTime, false);
		List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getConfirmDeliveryByBuyerAccountIdIn(
				SessionHelper.getAccountProfileId(request), buyerBidStatusList, beginDate, endDate);
		model.addAttribute("auctionBuyersBeanList", auctionBuyersBeanList);
		return ViewConstant.NONAJAX_BUYER_CONFIRM_DELIVERY;
	}

	/* Confirm Delivery */
	@RequestMapping(value = "/confirmdelivery/{auctionBuyerId}")
	public ModelAndView confirmDelivery(@PathVariable("auctionBuyerId") Integer auctionBuyerId,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("BuyerAndBuyerAgentController Call confirmDelivery method");
		LoginDetailsBean currentLoginDetailsBean = loginDetailsService.findById(SessionHelper.getLoginUserId(request));
		if (null != auctionBuyerId) {
			AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyerId);
			if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId()
					&& auctionBuyersBean.getAuctionBuyersId() > 0) {
				auctionPolicyProcessing.acceptCompleteBuyerBid(auctionBuyersBean,currentLoginDetailsBean.getAccountProfileBean().getAccountId());
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshBanner", "MovingBanner");
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
		logger.info("=== ending confirmDelivery method ===");
		RedirectView red = new RedirectView("/bba/confirmdelivery", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/* Shipping Listing */
	@RequestMapping(value = "/shipperlist")
	public String shippersList() {
		return ViewConstant.BUYER_SHIPPERS;
	}

	/** Buyer And Buyer Agent PickupTicket */
	@RequestMapping(value = "/asyncPickupTickets")
	public String asyncPickupTickets(HttpServletRequest request, Model model) {
		logger.info("BuyerAndBuyerAgentController Call asyncPickupTickets method");
		return ViewConstant.BUYER_PICKUP_TICKETS;
	}

	/** NON AJAX */
	@RequestMapping(value = "/pickupTickets")
	public String pickupTickets(HttpServletRequest request, Model model) {
		return ViewConstant.NONAJAX_BUYER_PICKUP_TICKETS;
	}

	/**
	 * Responses Buyer And Buyer Agent PickupTicket List
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pickupTicketsViewList", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<PickupTicketsBean> pickupTicketsViewList(HttpServletRequest request) {
		logger.info("BuyerAndBuyerAgentController Call pickupTicketsViewList method");
		List<PickupTicketsBean> pickupTicketsBeanList;
		LoginDetailsBean loginUser = SessionHelper.getLoginDetailsBean(request);
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
			pickupTicketsBeanList = pickupTicketsService.findPickupTicketsByBuyerAccountId(loginUser.getAccountProfileBean().getAccountId());
		} else {
			pickupTicketsBeanList = pickupTicketsService.findPickupTicketsByBuyerAccountId(loginUser.getAccountProfileBean().getAccountId());
		}
		logger.info("BuyerAndBuyerAgentController Call pickupTicketsViewList method - ending");
		return pickupTicketsBeanList;
	}

	/**
	 * this method view viewBuyerRate
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/viewBuyerRate")
	public String viewBuyerRate(Model model, HttpServletRequest request) {
		logger.info("BuyerAndBuyerAgentController Call viewBuyerRate method");
		if (null != request.getParameter("sellerid") && !request.getParameter("sellerid").isEmpty() && null != request.getParameter("buyerid")
				&& !request.getParameter("buyerid").isEmpty() && null != request.getParameter("dailyAuctionsId") && !request.getParameter("dailyAuctionsId").isEmpty()) {
			int sellerAccountId = Integer.parseInt(request.getParameter("sellerid"));
			int BuyeraAccountId = Integer.parseInt(request.getParameter("buyerid"));
			int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
			model.addAttribute("sellerAccountId", sellerAccountId);
			model.addAttribute("BuyeraAccountId", BuyeraAccountId);
			model.addAttribute("dailyAuctionsId", dailyAuctionsId);
			model.addAttribute("ratingsLog", ratingsLogService.getRatingByRatedId(BuyeraAccountId, sellerAccountId, dailyAuctionsId));
		}
		logger.info("=== ending viewBuyerRate method ===");
		return "/buyer/BM_buyerRate";
	}

	/**
	 * this is method saveBuyerRate
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBuyerRate", method = RequestMethod.POST)
	public String saveSellerRate(Model model, HttpServletRequest request) {
		logger.info("BuyerAndBuyerAgentController Call saveSellerRate method");
		String msg = "";
		int accountSellerId = Integer.parseInt(request.getParameter("selleraccountId"));// Seller (ratedId)
		int accountBuyerId  = Integer.parseInt(request.getParameter("buyeraccountId"));// Buyer (ratorId)
		int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
		String commnet = request.getParameter("comment");
		int buyerRatingNumber = Integer.parseInt(request.getParameter("rateNumber"));
		boolean count = ratingsLogService.getRatingCountByRatedId(accountBuyerId, accountSellerId, dailyAuctionsId);
		if (count == false) {
			if (null != request.getParameter("selleraccountId") && !request.getParameter("selleraccountId").isEmpty() && null != request.getParameter("buyeraccountId")
					&& !request.getParameter("buyeraccountId").isEmpty() && null != request.getParameter("dailyAuctionsId") && !request.getParameter("dailyAuctionsId").isEmpty()) {
				RatingsLogBean ratingsLogBean = new RatingsLogBean();
				AccountProfileBean ratorId = new AccountProfileBean();
				ratorId.setAccountId(accountBuyerId);
				ratingsLogBean.setAccountProfileByRatorId(ratorId);
				AccountProfileBean Rated = new AccountProfileBean();
				Rated.setAccountId(accountSellerId);
				ratingsLogBean.setAccountProfileByRatedId(Rated);
				ratingsLogBean.setComments(commnet);
				if (buyerRatingNumber == 0) {
					ratingsLogBean.setRatingValue(null);
				} else {
					ratingsLogBean.setRatingValue((short) buyerRatingNumber);
				}
				ratingsLogBean.setRatingTimestamp(InternetTiming.getInternetDateTime());
				DailyAuctionsBean dailyAuctionsBean = new DailyAuctionsBean();
				dailyAuctionsBean.setDailyAuctionsId(dailyAuctionsId);
				ratingsLogBean.setDailyAuctionsBean(dailyAuctionsBean);
				ratingsLogService.save(ratingsLogBean);
			}
			if (accountSellerId != 0) {
				List<RatingsLogBean> ratingCountByRateId = ratingsLogService.calculateAverageRatingByRatedId(accountSellerId);
				accountProfileService.updateRatingByAccountProflieId(ratingCountByRateId.get(0).getRating(), ratingCountByRateId.get(0).getRatingTotal(),
						ratingCountByRateId.get(0).getNumOfRatings(), accountSellerId);
			}
		}
		logger.info("=== ending saveSellerRate method ===");
		return msg;

	}

	

}