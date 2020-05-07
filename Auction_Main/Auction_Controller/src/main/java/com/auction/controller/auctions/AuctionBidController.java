package com.auction.controller.auctions;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountLocationStatus;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.component.AuctionBidHelper;
import com.auction.component.BuyerBankDetailsHelper;
import com.auction.component.LogHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.AuctionBuyerValidator;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.views.AuctionSellerOffersView;
import com.auction.service.AccountLocationsService;
import com.auction.service.AccountProfileService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.DailyAuctionsService;
import com.auction.service.RoyaltyCodeService;

@Controller
@RequestMapping("/bba/bid/")
public class AuctionBidController {

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private AccountLocationsService accountLocationsService;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionBuyerValidator buyerAuctionBidPlacingValidator;

	@Autowired
	private LogHelper logHelper;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private AuctionBidHelper acceptBuyersBidHelper;

	@Autowired
	private BuyerBankDetailsHelper balanceHelper;

	@Autowired
	private RoyaltyCodeService royaltyCodeService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Value("${auction.default.updownprice}")
	private float defaultPrice;

	@Value("${auction.default.shipping.charge}")
	private float defaultShippingCharge;

	
	
	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	Integer offerQty;
	Float offerPrice;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method is used To buyerBids *
	 * 
	 * @param Request
	 * 
	 * 
	 * @return auctionList page
	 */
	@RequestMapping(value = { "/{auctionSellersId}", "/{auctionSellersId}/{auctionBuyersId}" })
	public String buyerBids(Model model, @PathVariable("auctionSellersId") Integer auctionSellersId,
			@PathVariable(value = "auctionBuyersId", required = false) Integer auctionBuyersId, HttpServletRequest request) {
		logger.info("AuctionBidController Call buyerBids Method ");
		AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
		if (null != auctionSellersId && auctionSellersId > 0) {
			if (null != auctionBuyersId && auctionBuyersId > 0) {
				AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyersId);
				auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
				auctionBuyersBean.setDailyAuctionsBean(auctionSellersBean.getDailyAuctionsBean());
				auctionBuyersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
				offerQty = auctionBuyersBean.getBidQuantity();
				offerPrice = auctionBuyersBean.getBidPrice();
				// auctionBuyersBean.setBuyerShippingCharge(defaultShippingCharge);
				model.addAttribute("auctionBuyersBean", auctionBuyersBean);
				if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
					model.addAttribute("buyerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				} else {
					model.addAttribute("buyerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				}

				return ViewConstant.BUYER_AUCTION_BID_PLACING;
			} else if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId() && auctionSellersBean.getAuctionSellersId() > 0) {
				AuctionBuyersBean auctionBuyersBean = new AuctionBuyersBean();
				auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
				auctionBuyersBean.setDailyAuctionsBean(auctionSellersBean.getDailyAuctionsBean());
				auctionBuyersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));

				auctionBuyersBean.setRoyaltyValue(royaltyCodeService.findByRoyaltyCodeByAccountProfileId(SessionHelper.getAccountProfileId(request)));
				auctionBuyersBean.setVat(auctionBuyersBean.getDailyAuctionsBean().getAuctionSettingsBean().getVatBuyers());
				// auctionBuyersBean.setBuyerShippingCharge(defaultShippingCharge);

				model.addAttribute("auctionBuyersBean", auctionBuyersBean);
				if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
					model.addAttribute("buyerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				} else {
					model.addAttribute("buyerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				}
				return ViewConstant.BUYER_AUCTION_BID_PLACING;
			}
			return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
		}
		logger.info("=== Ending buyerBids Method ===");
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

	/**
	 * This method is used To saveBuyerBids *
	 * 
	 * @param AuctionBuyersBean
	 *            selecting AuctionBuyersBean
	 * 
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 * 
	 * @return page
	 */

	@RequestMapping(value = "/savebuyerbids", method = RequestMethod.POST)
	public ModelAndView saveBuyerBids(@ModelAttribute("auctionBuyersBean") AuctionBuyersBean auctionBuyersBean, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AuctionBidController Call saveBuyerBids Method ");
		String view = ("/auctions/bids/" + auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
		AuctionSellersBean auctionSellerBean = auctionSellersService.findById(auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
		if (!auctionBuyersBean.getPartialAllowed()) {
			auctionBuyersBean.setMinimumQuantity(auctionBuyersBean.getBidQuantity());
		}
		if (auctionBuyersBean.getExecutedPrice() == null) {
			auctionBuyersBean.setExecutedPrice(0.0f);
		}
		auctionBuyersBean.setAuctionSellersBean(auctionSellerBean);
		List<String> errorStack = buyerAuctionBidPlacingValidator.validateBuyerAuctionBidPlacing(auctionBuyersBean, request, auctionSellerBean);
		if (null == errorStack || errorStack.size() == 0) {
			DailyAuctionsBean dailyAuctionsBean = auctionSellerBean.getDailyAuctionsBean();
			logger.info("Auction Bid Controller Calling updateBidUserBalance");
			errorStack = balanceHelper.updateBidUserBalance(request, auctionBuyersBean);
			/**    this is method use MBBR            
			errorStack = balanceHelper.updateUserBalanceWithMBBR(request, auctionBuyersBean);
			 	*/			if ((null == errorStack || errorStack.size() == 0)) {
				if ((dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.OPEN.getStatus()
						|| dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus())
						&& (auctionSellerBean.getSellerOfferStatus() == ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus())
						|| auctionSellerBean.getSellerOfferStatus() == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()) {

					if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId() && auctionBuyersBean.getAuctionBuyersId() > 0) {
						AuctionBuyersBean currentAuctionBuyersBean = auctionBuyersService.findById(auctionBuyersBean.getAuctionBuyersId());

						if (auctionBuyersBean.getBidPrice() > currentAuctionBuyersBean.getBidPrice()
								|| auctionBuyersBean.getBidQuantity() > currentAuctionBuyersBean.getBidQuantity()
										&& currentAuctionBuyersBean.getBuyerBidStatus() == ENUM_AuctionStatusCodes.RUNNING.getStatus()) {

							currentAuctionBuyersBean.setAccountLocationsBean(auctionBuyersBean.getAccountLocationsBean());
							currentAuctionBuyersBean.setBidQuantity(auctionBuyersBean.getBidQuantity());
							currentAuctionBuyersBean.setMinimumQuantity(auctionBuyersBean.getMinimumQuantity());
							currentAuctionBuyersBean.setExecutedQuantity(0);
							currentAuctionBuyersBean.setBidPrice(auctionBuyersBean.getBidPrice());
							currentAuctionBuyersBean.setExecutedPrice(auctionBuyersBean.getExecutedPrice());
							currentAuctionBuyersBean.setBidUpdatedTime(InternetTiming.getInternetDateTime());
							auctionBuyersService.save(currentAuctionBuyersBean);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionbidcontroller.savebuyerbids.bidupdated.success", null, localeResolver.resolveLocale(request)));

							// It will create log in buyer_log while update record in table.
							logHelper.BuyerLog(currentAuctionBuyersBean, ENUM_BidOperationCodes.UPDATED);

						} else {
							currentAuctionBuyersBean.setAccountLocationsBean(auctionBuyersBean.getAccountLocationsBean());
							currentAuctionBuyersBean.setBidQuantity(auctionBuyersBean.getBidQuantity());
							currentAuctionBuyersBean.setMinimumQuantity(auctionBuyersBean.getMinimumQuantity());
							currentAuctionBuyersBean.setExecutedQuantity(0);
							currentAuctionBuyersBean.setPartialAllowed(auctionBuyersBean.getPartialAllowed());
							currentAuctionBuyersBean.setBidPrice(auctionBuyersBean.getBidPrice());
							currentAuctionBuyersBean.setExecutedPrice(auctionBuyersBean.getExecutedPrice());
						
							
							auctionBuyersService.save(currentAuctionBuyersBean);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionbidcontroller.savebuyerbids.bidupdated.success", null, localeResolver.resolveLocale(request)));
							// It will create log in buyer_log while update record in table.
							logHelper.BuyerLog(currentAuctionBuyersBean, ENUM_BidOperationCodes.UPDATED);
							logger.info("AuctionBidController : Buyer Bid : Buyer Bid Updated...");
						}

					} else {
						Integer count = auctionBuyersService.getAuctionBuyersByAuctionSellerId(auctionSellerBean.getAuctionSellersId(), SessionHelper.getAccountProfileId(request));
						if (null == count || count.intValue() == 0) {
							auctionBuyersBean.setExecutedQuantity(0);
							auctionBuyersBean.setBuyerBidStatus(auctionSellerBean.getSellerOfferStatus());
							auctionBuyersBean.setExecutedPrice(auctionBuyersBean.getExecutedPrice());
							auctionBuyersBean.setBidUpdatedTime( InternetTiming.getInternetDateTime());  
							/*
							 * if (auctionSellerBean.getOfferPrice() > 0 && auctionBuyersBean.getBidPrice()
							 * > auctionSellerBean.getOfferPrice()) {
							 * auctionBuyersBean.setExecutedPrice(auctionSellerBean.getOfferPrice()); } else
							 * { auctionBuyersBean.setExecutedPrice(auctionBuyersBean.getBidPrice()); }
							 */
							auctionBuyersBean.setActualStartTime(InternetTiming.getInternetDateTime());
							auctionBuyersService.save(auctionBuyersBean);
							// It will create log in buyer_log while create record in table.
							logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.PLACED);
							logger.info("AuctionBidController : Buyer Bid : Buyer Bid Placed...");
							// Add +1 in bid count.
							AccountProfileBean accountProfileBean = accountProfileService.findById(auctionBuyersBean.getAccountProfileBean().getAccountId());
							accountProfileBean.setOfferOrBidCount(accountProfileBean.getOfferOrBidCount() + 1);
							accountProfileService.save(accountProfileBean);

							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionbidcontroller.savebuyerbids.bidplaced.success", null, localeResolver.resolveLocale(request)));
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("auctionbidcontroller.savebuyerbids.bidplaced.error", null, localeResolver.resolveLocale(request)));
						}
					}
					if (dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus()
							&& auctionSellerBean.getSellerOfferStatus() == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()) {
						auctionBuyersBean.setAccountProfileBean(accountProfileService.findById(auctionBuyersBean.getAccountProfileBean().getAccountId()));
						acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
					}
					simpMessagingTemplate.convertAndSend("/wssauctions/refreshBidUI", "AuctionBuyerBidPlaced");
				} else {
					redirectAttributes.addFlashAttribute("ERROR",
							messageSource.getMessage("auctionbidcontroller.savebuyerbids.bidplaced.timeout", null, localeResolver.resolveLocale(request)));
				}
			} else {
				/*
				 * errorStack.set(0,messageSource.getMessage(errorStack.get(0), null,
				 * localeResolver.resolveLocale(request)));
				 * redirectAttributes.addFlashAttribute("errorStack", errorStack);
				 */
				String msg = "";
				if (errorStack.size() == 2) {
					Locale locale = localeResolver.resolveLocale(request);
					msg = messageSource.getMessage("buyer.auctionbidplacing.lbl.totalprice.validation.max", null, locale);
					redirectAttributes.addFlashAttribute("errorStack", msg);
				} else {
					Locale locale = localeResolver.resolveLocale(request);
					msg = messageSource.getMessage("agentstatusajaxform.purchasingPoewr.validation", null, locale);
					redirectAttributes.addFlashAttribute("errorStack", msg);
				}
				view = ("/bba/bid/" + auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
				if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId() && auctionBuyersBean.getAuctionBuyersId() > 0) {
					view += "/" + auctionBuyersBean.getAuctionBuyersId();
				}
				redirectAttributes.addFlashAttribute("auctionBuyersBean", auctionBuyersBean);
			}
		} else {
			redirectAttributes.addFlashAttribute("errorStack", errorStack);
			view = ("/bba/bid/" + auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
			if (null != auctionBuyersBean && null != auctionBuyersBean.getAuctionBuyersId() && auctionBuyersBean.getAuctionBuyersId() > 0) {
				view += "/" + auctionBuyersBean.getAuctionBuyersId();
			}
			redirectAttributes.addFlashAttribute("auctionBuyersBean", auctionBuyersBean);
		}
		logger.info("=== Ending saveBuyerBids Method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used To change bid price *
	 * 
	 * @param auctionBuyersId
	 *            selecting auctionBuyersId
	 * 
	 * @return same page
	 */
	@ResponseBody
	@RequestMapping(value = "/changebidprice/{auctionBuyersId}", produces = "text/plain;charset=UTF-8")
	public  String changeBidPrice(@PathVariable(value = "auctionBuyersId") Integer auctionBuyersId, HttpServletRequest request) {
		String msg = "";
		logger.info("Auction Bid changebidprice");
		logger.info("=== Ending Bid changebidprice Method ===");
		if (null != request.getParameter("action") && !request.getParameter("action").isEmpty() && null != auctionBuyersId && auctionBuyersId > 0) {
			String action = request.getParameter("action");

			AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyersId);
			AuctionSellersBean auctionSellerBean = auctionBuyersBean.getAuctionSellersBean();
			DailyAuctionsBean dailyAuctionsBean = auctionBuyersBean.getDailyAuctionsBean();

			if (ENUM_AuctionBuyerBidStatusCodes.OPEN.getStatus() == auctionBuyersBean.getBuyerBidStatus()
					|| ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus() == auctionBuyersBean.getBuyerBidStatus()) {

				if (ENUM_AuctionStatusCodes.OPEN.getStatus() == dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode()
						&& ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus() == auctionSellerBean.getSellerOfferStatus()) {
					if (action.equals(CommonConstants.INCREASE_PRICE)) {
						auctionBuyersBean.setBidPrice((auctionBuyersBean.getBidPrice() + defaultPrice));
					} /*
						 * else if (action.equals(CommonConstants.DECREASE_PRICE)) {
						 * auctionBuyersBean.setBidPrice((auctionBuyersBean.getBidPrice() -
						 * defaultPrice)); }
						 */
				} else if (ENUM_AuctionStatusCodes.RUNNING.getStatus() == dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode()
						&& ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == auctionSellerBean.getSellerOfferStatus()) {
					if (action.equals(CommonConstants.INCREASE_PRICE)) {
						auctionBuyersBean.setBidPrice((auctionBuyersBean.getBidPrice() + defaultPrice));
					}
				}if(auctionSellerBean.getOfferPrice()>0) {
					
					if(auctionBuyersBean.getBidPrice() <= auctionSellerBean.getOfferPrice()) {
						List<String> errorStack = balanceHelper.updateBidUserBalance(request, auctionBuyersBean);
						if (null == errorStack || errorStack.size() == 0) {
							// acceptBuyersBidHelper.changeBidUserBalance(auctionBuyersBean);
							// updateBidUserBalanceTransaction
							auctionBuyersBean.setBidUpdatedTime(InternetTiming.getInternetDateTime());
							auctionBuyersService.save(auctionBuyersBean);
							// acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
							logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.UPDATED);
							if (dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus()
									&& auctionSellerBean.getSellerOfferStatus() == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() && auctionSellerBean.getOfferPrice() > 0.0) {
								auctionBuyersBean.setAuctionSellersBean(auctionSellerBean);
								// acceptBuyersBidHelper.changeBidUserBalance(auctionBuyersBean);
								acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
							}
							simpMessagingTemplate.convertAndSend("/wssauctions/refreshBidUI", "AuctionBuyerBidPlaced");
						}else {
							if (errorStack.size() == 2) {
								Locale locale = localeResolver.resolveLocale(request);
								msg = messageSource.getMessage("buyer.auctionbidplacing.lbl.totalprice.validation.max", null, locale);

							} else {
								Locale locale = localeResolver.resolveLocale(request);
								msg = messageSource.getMessage("agentstatusajaxform.purchasingPoewr.validation", null, locale);
							}

						}
					}
				}else {
						List<String> errorStack = balanceHelper.updateBidUserBalance(request, auctionBuyersBean);
						if (null == errorStack || errorStack.size() == 0) {
							// acceptBuyersBidHelper.changeBidUserBalance(auctionBuyersBean);
							// updateBidUserBalanceTransaction
							auctionBuyersBean.setBidUpdatedTime(InternetTiming.getInternetDateTime());
							auctionBuyersService.save(auctionBuyersBean);
							// acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
							logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.UPDATED);
							if (dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus()
									&& auctionSellerBean.getSellerOfferStatus() == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() && auctionSellerBean.getOfferPrice() > 0.0) {
								auctionBuyersBean.setAuctionSellersBean(auctionSellerBean);
								// acceptBuyersBidHelper.changeBidUserBalance(auctionBuyersBean);
								acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
							}
							simpMessagingTemplate.convertAndSend("/wssauctions/refreshBidUI", "AuctionBuyerBidPlaced");
						}else {
							if (errorStack.size() == 2) {
								Locale locale = localeResolver.resolveLocale(request);
								msg = messageSource.getMessage("buyer.auctionbidplacing.lbl.totalprice.validation.max", null, locale);

							} 
					}
				}
				
				
				
				
			}
		}
		logger.info("====End===================Auction Bid change Bidprice===========End");
		return msg;
	}

	/**
	 * This method is used for return code / HTML output via AJAX for display
	 * existing bid details before cancel.
	 * 
	 * @param accountId
	 *            from requested URL spring will get accountId provided in AJAX
	 *            request.
	 * @param model
	 *            model is spring model for passing data from controller to view
	 *            page.
	 * @return it will return processed output for upload seller offer picture view.
	 * 
	 */
	@RequestMapping("/viewbiddetails")
	public String viewBidDetails(Model model, HttpServletRequest request) {
		logger.info("viewBidDetails changebidprice");
		if (null != request.getParameter("auctionBuyersId") && !request.getParameter("auctionBuyersId").isEmpty()) {
			int auctionBuyersId = Integer.parseInt(request.getParameter("auctionBuyersId"));
			AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyersId);
			model.addAttribute("auctionBuyersBean", auctionBuyersBean);
		}
		logger.info("=== Ending viewBidDetails Method ===");
		return "/buyer/BM_viewBidDetails";
	}

	/**
	 * This method is used To cancel *
	 * 
	 * @param auctionBuyersId
	 *            selecting auctionBuyersId
	 * 
	 * @return auctionBids page
	 */
	@RequestMapping(value = "/cancel/{auctionSellersId}/{auctionBuyersId}")
	public String cancelBuyerBid(@PathVariable("auctionSellersId") Integer auctionSellersId, @PathVariable(value = "auctionBuyersId") Integer auctionBuyersId,
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		// String view = ("/bba/bid/" + auctionSellersId + "/");
		// String view = ("/auctions/bids/" + auctionSellersId + "/");
		logger.info("Auction Bid cancel");
		AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyersId);
		if ((null != auctionSellersId && auctionSellersId > 0) && (null != auctionBuyersId && auctionBuyersId > 0)) {
			auctionBuyersBean.setBuyerBidStatus(ENUM_AuctionBuyerBidStatusCodes.CANCELLED.getStatus());
			auctionBuyersBean.setActualEndTime(InternetTiming.getInternetDateTime());
			/** MileStone9_Comments_ـFeb11_2019 issues No#7 */
			auctionBuyersBean.setExecutedPrice(0.0f);
			auctionBuyersService.save(auctionBuyersBean);
			/** 
			 * this method old method call issues  no#92 10-10-2019 
			 */
			balanceHelper.resetBuyerUserBalance(auctionBuyersBean);
			/**
			 * this is method used MBBR
			 * balanceHelper.resetBuyerUserBalanceWithMBBR(auctionBuyersBean);
			 */
			logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.CANCELLED);
			AccountProfileBean accountProfileBean = accountProfileService.findById(SessionHelper.getAccountProfileId(request));
			accountProfileBean.setNoOfCancellations((accountProfileBean.getNoOfCancellations() + 1));
			accountProfileService.save(accountProfileBean);
			/*
			 * redirectAttributes.addFlashAttribute("SUCCESS",
			 * messageSource.getMessage("auctionbidcontroller.cancelSellerOffers.success",
			 * null, localeResolver.resolveLocale(request)));
			 */
		} else {
			/*
			 * redirectAttributes.addFlashAttribute("ERROR",
			 * messageSource.getMessage("auctionbidcontroller.cancelSellerOffers.error",
			 * null, localeResolver.resolveLocale(request)));
			 */
		}
		simpMessagingTemplate.convertAndSend("/wssauctions/refreshBidUI", "AuctionSellerOfferCancled");
		/*
		 * RedirectView red = new RedirectView(view, true);
		 * red.setStatusCode(HttpStatus.MOVED_PERMANENTLY); return new
		 * ModelAndView(red);
		 */

		AuctionSellerOffersView auctionSellerOffersView = auctionSellersService.findViewByAuctionSellersId(auctionSellersId);
		if (null != auctionSellerOffersView && null != auctionSellerOffersView.getAccountId() && auctionSellerOffersView.getAccountId() > 0) {
			model.addAttribute("auctionSellerOffersView", auctionSellersService.findViewByAuctionSellersId(auctionSellersId));
			model.addAttribute("dailyAuctionView", dailyAuctionsService.findViewByAuctionId(auctionSellerOffersView.getDailyAuctionsId()));
			model.addAttribute("defaultPrice", defaultPrice);
			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid()) {
				if ((loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER.getType())
						|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType())) {
					Integer accountId = loginDetailsBean.getAccountProfileBean().getAccountId();
					List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndAccountProfileAccountId(auctionSellersId,
							accountId);
					if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
						model.addAttribute("auctionBuyersBean", auctionBuyersBeanList.get(0));
					}
				}
			}

		}
		logger.info("==================End===========Auction Bid cancel==================End");
		return ViewConstant.AUCTION_BIDS;

	}

	/**
	 * This method is used To buyerBidsAjax *
	 * 
	 * @param auctionSellersId
	 *            selecting auctionSellersId
	 * 
	 * @return BM_auctionBidPlacingAjax page
	 */

	@RequestMapping(value = "/bidajax")
	public String buyerBidsAjax(Model model, @RequestParam("sellerId") Integer auctionSellersId, @RequestParam(value = "buyerId", required = false) Integer auctionBuyersId,
			HttpServletRequest request) {
		logger.info("Auction Bid controller Call buyerBidsAjax method");
		AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
		if (null != auctionSellersId && auctionSellersId > 0) {
			if (null != auctionBuyersId && auctionBuyersId > 0) {
				AuctionBuyersBean auctionBuyersBean = auctionBuyersService.findById(auctionBuyersId);
				auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
				auctionBuyersBean.setDailyAuctionsBean(auctionSellersBean.getDailyAuctionsBean());
				auctionBuyersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
				offerQty = auctionBuyersBean.getBidQuantity();
				offerPrice = auctionBuyersBean.getBidPrice();

				// auctionBuyersBean.setBuyerShippingCharge(defaultShippingCharge);

				model.addAttribute("auctionBuyersBean", auctionBuyersBean);
				model.addAttribute("buyerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
						new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				return "/buyer/BM_auctionBidPlacingAjax";
			} else if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId() && auctionSellersBean.getAuctionSellersId() > 0) {
				AuctionBuyersBean auctionBuyersBean = new AuctionBuyersBean();
				auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
				auctionBuyersBean.setDailyAuctionsBean(auctionSellersBean.getDailyAuctionsBean());
				auctionBuyersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));

				auctionBuyersBean.setRoyaltyValue(royaltyCodeService.findByRoyaltyCodeByAccountProfileId(SessionHelper.getAccountProfileId(request)));
				auctionBuyersBean.setVat(auctionBuyersBean.getDailyAuctionsBean().getAuctionSettingsBean().getVatBuyers());
				// auctionBuyersBean.setBuyerShippingCharge(defaultShippingCharge);

				model.addAttribute("auctionBuyersBean", auctionBuyersBean);
				model.addAttribute("buyerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
						new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				return "/buyer/BM_auctionBidPlacingAjax";
			}
			return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
		}
		logger.info("=== Ending  buyerBidsAjax  method ===");
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

}