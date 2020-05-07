package com.auction.controller.seller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionRequestStatus;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.SellerValidator;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionRequestBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.RatingsLogBean;
import com.auction.service.AccountProfileService;
import com.auction.service.AuctionRequestService;
import com.auction.service.PickupTicketsService;
import com.auction.service.ProductCatalogService;
import com.auction.service.RatingsLogService;

@Controller
@RequestMapping("/ssa")
public class SellerAndSellerAgentController {

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private AuctionRequestService auctionRequestService;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SellerValidator sellerValidator;
	
	@Autowired
	private PickupTicketsService  pickupTicketsService;
	
	@Autowired
	private RatingsLogService  ratingsLogService;
	
	@Autowired
	private AccountProfileService  accountProfileService;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	/**
	 * This method is used to return the seller auction request list view
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return auction request list view
	 */
	@RequestMapping("/auctionrequestlist")
	public String auctionrequestlist(HttpServletRequest request, Model model) {
		logger.info("SellerAndSellerAgentController Call auctionrequestlist method ");
		model.addAttribute("loginDetailBean", SessionHelper.getLoginDetailsBean(request));
		model.addAttribute("auctionRequestBeanList", auctionRequestService.findAll());
		logger.info(" === ending  auctionrequestlist method ===");
		return ViewConstant.SELLER_AUCTION_REQUEST_LIST;
	}
	
	
	@RequestMapping("/auctionrequest/List")
	public String syscAuctionrequestList(HttpServletRequest request, Model model) {
		logger.info("SellerAndSellerAgentController Call syscAuctionrequestList method ");
		model.addAttribute("loginDetailBean", SessionHelper.getLoginDetailsBean(request));
		model.addAttribute("auctionRequestBeanList", auctionRequestService.findAll());
		logger.info(" === ending  syscAuctionrequestList method ===");
		return ViewConstant.NONAJAX_SELLER_AUCTION_REQUEST_LIST;
	}

	/**
	 * This method is used to return the seller auction request page
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return seller auction request page
	 */
	@RequestMapping("/auctionrequest")
	public String auctionrequest(Model model, HttpServletRequest request) {
		logger.info("SellerAndSellerAgentController Call auctionrequest method ");
		Map<String, Object> mapModel = model.asMap();
		if (!mapModel.containsKey("auctionRequestBean")) {
			model.addAttribute("auctionRequestBean", new AuctionRequestBean());
		}
		model.addAttribute("productCatalogList", productCatalogService.findAll());
		logger.info(" === ending  auctionrequest method ===");
		return ViewConstant.SELLER_AUCTION_REQUEST;
	}

	/**
	 * This method is used to update or edit the seller auction request
	 * 
	 * @param requestId
	 *            seller auction request id
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return return the seller auction request page view
	 */
	@RequestMapping("/auctionrequest/{requestId}")
	public String editauctionrequest(@PathVariable("requestId") Integer requestId, HttpServletRequest request, Model model) {
		logger.info("SellerAndSellerAgentController Call editauctionrequest method ");
		model.addAttribute("productCatalogList", productCatalogService.findAll());
		model.addAttribute("auctionRequestBean", auctionRequestService.findById(requestId));
		logger.info(" === ending  editauctionrequest method ===");
		return ViewConstant.SELLER_AUCTION_REQUEST;
	}

	/**
	 * This method is used to persist and update the seller auction request
	 * 
	 * @param auctionRequestBean
	 *            spring will get auctionRequestBean from model attribute of spring.
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any request mapping / URL.
	 * @return auction request list page view
	 */
	@RequestMapping(value = "/auctionrequest", method = RequestMethod.POST)
	public ModelAndView auctionRequest(@ModelAttribute("auctionRequestBean") AuctionRequestBean auctionRequestBean, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.info("SellerAndSellerAgentController Call auctionRequest method ");
		String view = "/ssa/auctionrequestlist";
		List<String> errorStack = sellerValidator.validateSellerAuctionRequest(auctionRequestBean, request);
		if (null == errorStack || errorStack.size() == 0) {
			LoginDetailsBean loginUser = SessionHelper.getLoginDetailsBean(request);

			auctionRequestBean.setStatus(ENUM_AuctionRequestStatus.RQUESTED.getStatus());
			auctionRequestBean.setAccountProfileBySellerId(new AccountProfileBean(loginUser.getAccountProfileBean().getAccountId()));
			auctionRequestBean.setCreatedOn(InternetTiming.getInternetDateTime());
			auctionRequestBean.setAccountProfileByCreatedBy(new AccountProfileBean(loginUser.getAccountProfileBean().getAccountId()));

			if (null != auctionRequestBean.getRequestId()) {
				auctionRequestBean.setUpdatedOn(InternetTiming.getInternetDateTime());
				auctionRequestBean.setAccountProfileByUpdatedBy(new AccountProfileBean(loginUser.getAccountProfileBean().getAccountId()));
				auctionRequestBean.setCreatedOn(auctionRequestBean.getCreatedOn());
				auctionRequestBean.setStatus(auctionRequestBean.getStatus());
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("sellercontroller.auctionrequest.success.updated", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("SUCCESS",
						messageSource.getMessage("sellercontroller.auctionrequest.success.added", null, localeResolver.resolveLocale(request)));
			}
			auctionRequestService.save(auctionRequestBean);
		} else {
			view = "/ssa/auctionrequest";
			if (null != auctionRequestBean && null != auctionRequestBean.getRequestId()) {
				view = view + "/" + auctionRequestBean.getRequestId();
			}
			redirectAttributes.addFlashAttribute("auctionRequestBean", auctionRequestBean);
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
		}
		logger.info(" === ending  auctionRequest method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	@RequestMapping("/auctionrequestview/{requestId}")
	public String auctionrequestview(@PathVariable("requestId") Integer requestId, HttpServletRequest request, Model model) {
		logger.info("SellerAndSellerAgentController Call auctionrequestview method ");
		model.addAttribute("auctionRequestBean", auctionRequestService.findById(requestId));
		logger.info(" === ending  auctionrequestview method ===");
		return "/seller/SM_auctionRequestView";
	}
	
	/* Shipping Listing */
	@RequestMapping(value = "/shipperlist")
	public String shippersList(HttpServletRequest request, Model model) {
		//model.addAttribute("loginDetailBeaninSeller", SessionHelper.getLoginDetailsBean(request));
		//model.addAttribute("loginDetailBean", new LoginDetailsBean());
		return ViewConstant.SELLER_SHIPPERS;
	}
	
	/** Seller And Seller Agent PickupTicket    */
	@RequestMapping(value = "/asyncPickupTickets")
	public String asyncPickupTickets(HttpServletRequest request, Model model) {
		logger.info("SellerAndSellerAgentController Call asyncPickupTickets method ");
		LoginDetailsBean loginUser = SessionHelper.getLoginDetailsBean(request);
		if(loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT
				.getType()) {
			model.addAttribute("SellerPickupTicketList",pickupTicketsService.findPickupTicketsBySellerAccountId(loginUser.getAccountProfileBean().getAccountId()));
		}else {
			model.addAttribute("SellerPickupTicketList",pickupTicketsService.findPickupTicketsBySellerAccountId(loginUser.getAccountProfileBean().getAccountId()));
		}
		logger.info(" === ending  asyncPickupTickets method ===");
		return ViewConstant.SELLER_PICKUP_TICKETS;
	}
	
	/**NON AJAX  */
	@RequestMapping(value = "/pickupTickets")
	public String pickupTickets(HttpServletRequest request, Model model) {
		return ViewConstant.NONAJAX_SELLER_PICKUP_TICKETS;
	}
	
	@RequestMapping("/viewSellerRate")
	public String viewSellerRate(Model model, HttpServletRequest request) {
		logger.info("SellerAndSellerAgentController Call viewSellerRate method ");
		if (null != request.getParameter("sellerid") && !request.getParameter("sellerid").isEmpty() && null != request.getParameter("buyerid") && !request.getParameter("buyerid").isEmpty() && null != request.getParameter("dailyAuctionsId") && !request.getParameter("dailyAuctionsId").isEmpty()) {
			int accountSellerId = Integer.parseInt(request.getParameter("sellerid"));
			int accountBuyerId = Integer.parseInt(request.getParameter("buyerid"));
			int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
			model.addAttribute("accountSellerId", accountSellerId);
			model.addAttribute("accountBuyerId", accountBuyerId);
			model.addAttribute("dailyAuctionsId",dailyAuctionsId);
			model.addAttribute("ratingsLog",ratingsLogService.getRatingByRatedId(accountSellerId, accountBuyerId, dailyAuctionsId));
		}
		logger.info(" === ending  viewSellerRate method ===");
		return "/seller/SM_sellerRate";
	}
	@ResponseBody
	@RequestMapping(value = "/saveSellerRate", method = RequestMethod.POST)
	public String saveSellerRate(Model model, HttpServletRequest request) {
		logger.info("SellerAndSellerAgentController Call saveSellerRate method ");
		String msg="";
		int accountSellerId = Integer.parseInt(request.getParameter("selleraccountId"));
		int ratedId = Integer.parseInt(request.getParameter("buyeraccountId"));
		int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
		String commnet= request.getParameter("comment");
	    int buyerRatingNumber=Integer.parseInt(request.getParameter("rateNumber"));
	    boolean count =ratingsLogService.getRatingCountByRatedId(accountSellerId, ratedId, dailyAuctionsId);
		if(count == false) {
	    if (null != request.getParameter("selleraccountId") && !request.getParameter("selleraccountId").isEmpty() && null != request.getParameter("buyeraccountId") && !request.getParameter("buyeraccountId").isEmpty() && null != request.getParameter("dailyAuctionsId") && !request.getParameter("dailyAuctionsId").isEmpty()) {
			RatingsLogBean  ratingsLogBean = new RatingsLogBean();
			AccountProfileBean ratorId= new AccountProfileBean();
			ratorId.setAccountId(accountSellerId);
			ratingsLogBean.setAccountProfileByRatorId(ratorId);
			AccountProfileBean Rated= new AccountProfileBean();
			Rated.setAccountId(ratedId);
			ratingsLogBean.setAccountProfileByRatedId(Rated);
			ratingsLogBean.setComments(commnet);
			if(buyerRatingNumber == 0) {
				ratingsLogBean.setRatingValue(null);
			}else {
			ratingsLogBean.setRatingValue((short)buyerRatingNumber);
			}
			ratingsLogBean.setRatingTimestamp(InternetTiming.getInternetDateTime());
			DailyAuctionsBean dailyAuctionsBean = new DailyAuctionsBean();
			dailyAuctionsBean.setDailyAuctionsId(dailyAuctionsId);
			ratingsLogBean.setDailyAuctionsBean(dailyAuctionsBean);
			ratingsLogService.save(ratingsLogBean);
		}
		if(ratedId != 0) {
			List<RatingsLogBean> ratingCountByRateId= ratingsLogService.calculateAverageRatingByRatedId(ratedId);
			accountProfileService.updateRatingByAccountProflieId(ratingCountByRateId.get(0).getRating(),ratingCountByRateId.get(0).getRatingTotal(), ratingCountByRateId.get(0).getNumOfRatings(), ratedId);
		}
		}
		logger.info(" === ending  saveSellerRate method ===");
		return msg;
	
	}
}