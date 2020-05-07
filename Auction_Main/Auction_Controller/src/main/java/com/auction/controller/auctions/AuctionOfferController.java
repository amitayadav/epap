package com.auction.controller.auctions;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
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
import org.springframework.web.multipart.MultipartFile;
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
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.FileUtils;
import com.auction.commons.util.InternetTiming;
import com.auction.component.AuctionBidHelper;
import com.auction.component.AuctionOfferHelper;
import com.auction.component.BuyerBankDetailsHelper;
import com.auction.component.LogHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.controller.validator.AuctionSellerValidator;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.bean.ProductCatalogBean;
import com.auction.model.bean.SellerOfferInfoBean;
import com.auction.model.bean.SellerOfferPicturesBean;
import com.auction.service.AccountLocationsService;
import com.auction.service.AccountProfileService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.DailyAuctionsService;
import com.auction.service.RoyaltyCodeService;
import com.auction.service.SellerOfferInfoService;
import com.auction.service.SellerOfferPicturesService;
import com.auction.service.SellerTransactionsService;

@Controller
@RequestMapping("/ssa/offer/")
public class AuctionOfferController {

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AccountLocationsService accountLocationsService;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private SellerOfferInfoService sellerOfferInfoService;

	@Autowired
	private SellerOfferPicturesService sellerOfferPicturesService;

	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	AuctionOfferHelper auctionOfferHelper;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private AuctionSellerValidator sellerAuctionOfferInfoValidator;

	@Autowired
	private RoyaltyCodeService royaltyCodeService;

	@Autowired
	private BuyerBankDetailsHelper balanceHelper;

	@Autowired
	private LogHelper logHelper;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionBidHelper acceptBuyersBidHelper;
	
	@Autowired
	private SellerTransactionsService sellerTransactionsService;

	@Value("${auction.default.updownprice}")
	private float defaultPrice;

	@Value("${auction.selleroffer.picture.size}")
	private long sellerOfferPictureSize;
	
	
	private int offerQty;
	private Float offerPrice;
	private Float shippingCharge;
	private int  shipperId;
	private int truckNumber;
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@RequestMapping(value = { "/{dailyAuctionId}", "/{dailyAuctionId}/{auctionSellersId}" })
	public String sellerOffers(Model model, @PathVariable("dailyAuctionId") Integer dailyAuctionId,
			@PathVariable(value = "auctionSellersId", required = false) Integer auctionSellersId, HttpServletRequest request) {
		logger.info("AuctionOfferController call sellerOffers method");	
		DailyAuctionsBean dailyAuctionsBean = dailyAuctionsService.findById(dailyAuctionId);
		if (null != dailyAuctionId && dailyAuctionId > 0) {
			if (null != auctionSellersId && auctionSellersId > 0) {
				AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
				if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId()) {
					offerQty = auctionSellersBean.getOfferQuantity();
					offerPrice = auctionSellersBean.getOfferPrice();
					shippingCharge = auctionSellersBean.getSellerShippingCharge();
					if(auctionSellersBean.getShipperAccountProfileBean().getAccountId() != null) {
						shipperId =auctionSellersBean.getShipperAccountProfileBean().getAccountId();
					}
					
					
					model.addAttribute("auctionSellersBean", auctionSellersBean);

					if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					} else {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					}
					model.addAttribute("shipperList", accountProfileService.findByAccountTypeCodes(new Character[] {ENUM_AccountTypeCodes.SHIPPER.getType(),ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}));
					List<SellerOfferInfoBean> sellerOfferInfoBeanList = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					if (null != sellerOfferInfoBeanList && sellerOfferInfoBeanList.size() > 0) {
						auctionSellersBean.setSellerOfferInfoBean(sellerOfferInfoBeanList.get(0));
					}
					boolean SellerShipperCount = sellerTransactionsService.countShippingChargeByAuctionSeller(auctionSellersBean.getAuctionSellersId());
					if(SellerShipperCount) {
						auctionSellersBean.setAllowShipperSelection(false);
					}
					List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
					logger.info("AuctionSellerOfferController : sellerOffers : Seller offer updated...");
					return ViewConstant.SELLER_AUCTION_OFFER_PLACING;
				} else {
					Map<String, Object> map = model.asMap();
					if (map.containsKey("auctionSellersBean")) {
						auctionSellersBean = (AuctionSellersBean) map.get("auctionSellersBean");
					} else {
						auctionSellersBean = new AuctionSellersBean();
					}

					auctionSellersBean.setRoyaltyValue(royaltyCodeService.findByRoyaltyCodeByAccountProfileId(SessionHelper.getAccountProfileId(request)));
					auctionSellersBean.setVat(dailyAuctionsBean.getAuctionSettingsBean().getVatSellers());

					auctionSellersBean.setDailyAuctionsBean(dailyAuctionsBean);
					auctionSellersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
					model.addAttribute("auctionSellersBean", auctionSellersBean);
					if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					} else {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					}
					model.addAttribute("shipperList", accountProfileService.findByAccountTypeCodes(new Character[] {ENUM_AccountTypeCodes.SHIPPER.getType(),ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}));
					List<SellerOfferInfoBean> sellerOfferInfoBeanList = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					if (null != sellerOfferInfoBeanList && sellerOfferInfoBeanList.size() > 0) {
						auctionSellersBean.setSellerOfferInfoBean(sellerOfferInfoBeanList.get(0));
					}
					List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
					logger.info("AuctionSellerOfferController : sellerOffers : Seller offer created...");
					return ViewConstant.SELLER_AUCTION_OFFER_PLACING;
				}
			} else {
				AuctionSellersBean auctionSellersBean = new AuctionSellersBean();
				auctionSellersBean.setPartialAllowed(false);
				auctionSellersBean.setRoyaltyValue(royaltyCodeService.findByRoyaltyCodeByAccountProfileId(SessionHelper.getAccountProfileId(request)));
				auctionSellersBean.setVat(dailyAuctionsBean.getAuctionSettingsBean().getVatSellers());
				auctionSellersBean.setDailyAuctionsBean(dailyAuctionsBean);
				auctionSellersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
				if(auctionSellersBean.getTruckNumber() == null) {
					auctionSellersBean.setTruckNumber(01);
				}
				model.addAttribute("auctionSellersBean", auctionSellersBean);
				if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
					model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				} else {
					model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				}
				model.addAttribute("shipperList", accountProfileService.findByAccountTypeCodes(new Character[] {ENUM_AccountTypeCodes.SHIPPER.getType(),ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}));
				List<SellerOfferInfoBean> sellerOfferInfoBeanList = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(
						SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
				if (null != sellerOfferInfoBeanList && sellerOfferInfoBeanList.size() > 0) {
					auctionSellersBean.setSellerOfferInfoBean(sellerOfferInfoBeanList.get(0));
				}
				List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(
						SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
				model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
				logger.info("AuctionSellerOfferController : sellerOffers : Seller offer created...");
				return ViewConstant.SELLER_AUCTION_OFFER_PLACING;
			}
		}
		logger.info("=== Ending sellerOffers method ===");	
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

	@RequestMapping(value = "/saveofferdetails", method = RequestMethod.POST)
	public ModelAndView saveOfferDetails(@ModelAttribute("auctionSellersBean") AuctionSellersBean auctionSellersBean, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.info("AuctionOfferController call saveOfferDetails method");	
		String view = ("/auctions/offers/" + auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());

		if (null != auctionSellersBean && null != auctionSellersBean.getDailyAuctionsBean() && null != auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId()
				&& auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId() > 0) {

			// Fetching existing day auction details
			DailyAuctionsBean dailyAuctionsBean = dailyAuctionsService.findById(auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());

			// Checking existing day auction details and auction status
			if ((dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.OPEN.getStatus()
					|| dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus())) {

				AuctionSellersBean currentAuctionSellersBean = null;
				List<String> errorStack = null;
				// Validating auction seller offers
				if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId() && auctionSellersBean.getAuctionSellersId() > 0) {
					// Validating existing auction seller offers
					currentAuctionSellersBean = auctionSellersService.findById(auctionSellersBean.getAuctionSellersId());
					errorStack = sellerAuctionOfferInfoValidator.validateSellerAuctionInfo(currentAuctionSellersBean, auctionSellersBean, request);
				} else {
					// Validating new auction seller offers
					errorStack = sellerAuctionOfferInfoValidator.validateSellerAuctionInfo(auctionSellersBean, request);
				}

				if (null == errorStack || errorStack.size() == 0) {
					if (null != currentAuctionSellersBean && auctionSellersBean.getAuctionSellersId() > 0) {
						currentAuctionSellersBean.setAccountLocationsBean(auctionSellersBean.getAccountLocationsBean());
						currentAuctionSellersBean.setPartialAllowed(auctionSellersBean.getPartialAllowed());
						currentAuctionSellersBean.setOfferPrice(auctionSellersBean.getOfferPrice());

						if (auctionSellersBean.getOfferPrice() != currentAuctionSellersBean.getOfferPrice()
								|| auctionSellersBean.getOfferQuantity() != currentAuctionSellersBean.getOfferQuantity()) {
							if (ENUM_AuctionStatusCodes.RUNNING.getStatus() == currentAuctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean()
									.getAuctionStatusCode()) {
								if (auctionSellersBean.getOfferQuantity() > currentAuctionSellersBean.getOfferQuantity()) {
									short diffQty = (short) (auctionSellersBean.getOfferQuantity() - currentAuctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() + diffQty);
								} else {
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity());
								}
							} else if (ENUM_AuctionStatusCodes.OPEN.getStatus() == currentAuctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean()
									.getAuctionStatusCode()) {
								if (auctionSellersBean.getOfferQuantity() > currentAuctionSellersBean.getOfferQuantity()) {
									short diffQty = (short) (auctionSellersBean.getOfferQuantity() - currentAuctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() + diffQty);
								} else {
									short diffQty = (short) (currentAuctionSellersBean.getOfferQuantity() - auctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() - diffQty);
								}
							} else {
								if (auctionSellersBean.getOfferQuantity() > currentAuctionSellersBean.getOfferQuantity()) {
									short diffQty = (short) (auctionSellersBean.getOfferQuantity() - currentAuctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() + diffQty);
								} else {
									short diffQty = (short) (currentAuctionSellersBean.getOfferQuantity() - auctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() - diffQty);
								}
							}

							// Update seller shipper details when there is not any one buyer's bid not finished
							//auctionSellersBean.getIsAllowShipperSelection() == true means this offer's one bid has finished
							if(auctionSellersBean.getAllowShipperSelection()) {
								currentAuctionSellersBean.setShipperAccountProfileBean(auctionSellersBean.getShipperAccountProfileBean());
								if(null != auctionSellersBean.getShipperAccountProfileBean() && null != auctionSellersBean.getShipperAccountProfileBean().getAccountId() && auctionSellersBean.getShipperAccountProfileBean().getAccountId() > 0) {
									currentAuctionSellersBean.setSellerShippingCharge(auctionSellersBean.getSellerShippingCharge());
								}else {
									currentAuctionSellersBean.setSellerShippingCharge(0F);
								}
							}
							
							currentAuctionSellersBean.setOfferQuantity(auctionSellersBean.getOfferQuantity());
							currentAuctionSellersBean.setMinimumQuantity(auctionSellersBean.getMinimumQuantity());
							currentAuctionSellersBean.setOfferUpdatedTime(InternetTiming.getInternetDateTime());
							auctionSellersService.save(currentAuctionSellersBean);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerupdated.success", null, localeResolver.resolveLocale(request)));
							// logHelper.sellerLog(currentAuctionSellersBean,
							// ENUM_OfferOperationCodes.UPDATED);
							logger.info("AuctionSellerOfferController : sellerOffers : Seller offer updated...");
						} else {
							currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity());
							auctionSellersService.save(currentAuctionSellersBean);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerupdated.success", null, localeResolver.resolveLocale(request)));
						}

						SellerOfferInfoBean sellerOfferInfoBean = auctionSellersBean.getSellerOfferInfoBean();
						SellerOfferInfoBean currentSellerOfferInfoBean = auctionSellersBean.getSellerOfferInfoBean();
						currentSellerOfferInfoBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
						currentSellerOfferInfoBean.setProductCatalogBean(auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean());
						currentSellerOfferInfoBean.setInfoLine1(sellerOfferInfoBean.getInfoLine1());
						currentSellerOfferInfoBean.setInfoLine2(sellerOfferInfoBean.getInfoLine2());
						sellerOfferInfoService.save(currentSellerOfferInfoBean);

						if( auctionSellersBean.getShipperAccountProfileBean() !=null &&auctionSellersBean.getShipperAccountProfileBean().getAccountId() !=null) {
							if ((auctionSellersBean.getOfferQuantity().compareTo(offerQty) != 0) || (auctionSellersBean.getOfferPrice().compareTo(offerPrice) != 0) || (auctionSellersBean.getSellerShippingCharge().compareTo(shippingCharge) != 0) || (auctionSellersBean.getShipperAccountProfileBean().getAccountId().compareTo(shipperId) != 0) || (auctionSellersBean.getTruckNumber().compareTo(truckNumber) != 0)) {
								logHelper.sellerLog(currentAuctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
							}
							}else {
								if ((auctionSellersBean.getOfferQuantity().compareTo(offerQty) != 0) || (auctionSellersBean.getOfferPrice().compareTo(offerPrice) != 0) || (auctionSellersBean.getSellerShippingCharge().compareTo(shippingCharge) != 0)) {
									logHelper.sellerLog(currentAuctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
								}
							}

						simpMessagingTemplate.convertAndSend("/auctions/refreshOfferUI", "AuctionSellerOfferPlaced");
					} else {
						Integer count = auctionSellersService.getAuctionSellersByDailyAuctionsId(auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId(),
								SessionHelper.getAccountProfileId(request));
						if (null == count || count.intValue() == 0) {
							auctionSellersBean.setActualStartTime(InternetTiming.getInternetDateTime());
							auctionSellersBean.setAvailableQuantity(auctionSellersBean.getOfferQuantity());
							auctionSellersBean.setSellerOfferStatus(auctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode());
							auctionSellersBean.setOfferUpdatedTime(InternetTiming.getInternetDateTime());
							auctionSellersService.save(auctionSellersBean);

							SellerOfferInfoBean sellerOfferInfoBean = auctionSellersBean.getSellerOfferInfoBean();
							sellerOfferInfoBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
							sellerOfferInfoBean.setProductCatalogBean(auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean());
							sellerOfferInfoService.save(sellerOfferInfoBean);

							// It will create log in seller_log table.
							logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.PLACED);

							// Add +1 in bid count.
							AccountProfileBean accountProfileBean = accountProfileService.findById(auctionSellersBean.getAccountProfileBean().getAccountId());
							accountProfileBean.setOfferOrBidCount(accountProfileBean.getOfferOrBidCount() + 1);
							accountProfileService.save(accountProfileBean);

							simpMessagingTemplate.convertAndSend("/auctions/refreshOfferUI", "AuctionSellerOfferPlaced");
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerplaced.success", null, localeResolver.resolveLocale(request)));
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.Offeralreadyplaced.error", null, localeResolver.resolveLocale(request)));
						}
					}

				} else {
					redirectAttributes.addFlashAttribute("errorStack", errorStack);
					view = "/ssa/offer/" + auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId();
					if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId() && auctionSellersBean.getAuctionSellersId() > 0) {
						view += ("/" + auctionSellersBean.getAuctionSellersId());
					}
					redirectAttributes.addFlashAttribute("auctionSellersBean", auctionSellersBean);
				}

			} else {
				redirectAttributes.addFlashAttribute("ERROR",
						messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerplaced.timeout", null, localeResolver.resolveLocale(request)));
			}
		} else {
			redirectAttributes.addFlashAttribute("ERROR",
					messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerplaced.timeout", null, localeResolver.resolveLocale(request)));
			view = "/auctions/auctionList";
		}
		logger.info("=== Ending saveOfferDetails method ===");	
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used for return code / HTML output via AJAX for display existing offer details before cancel.
	 * 
	 * @param accountId
	 *            from requested URL spring will get accountId provided in AJAX request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return it will return processed output for upload seller offer picture view.
	 * 
	 */
	@RequestMapping("/viewofferdetails")
	public String viewOfferDetails(Model model, HttpServletRequest request) {
		logger.info("AuctionOfferController call viewOfferDetails method");	
		if (null != request.getParameter("auctionSellersId") && !request.getParameter("auctionSellersId").isEmpty()) {
			int auctionSellersId = Integer.parseInt(request.getParameter("auctionSellersId"));
			AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
			model.addAttribute("auctionSellersBean", auctionSellersBean);
		}
		logger.info("=== Ending viewOfferDetails method ===");	
		return "/seller/SM_viewOfferDetails";
	}

	@RequestMapping(value = "/cancel/{dailyAuctionId}/{auctionSellersId}")
	public ModelAndView cancelSellerOffers(@PathVariable("dailyAuctionId") Integer dailyAuctionId, @PathVariable(value = "auctionSellersId") Integer auctionSellersId,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AuctionOfferController call cancelSellerOffers  method");	
		String view = ("/auctions/offers/" + dailyAuctionId + "/");
		boolean cancelAffected =true;
		if ((null != dailyAuctionId && dailyAuctionId > 0) && (null != auctionSellersId && auctionSellersId > 0)) {
			AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
			int buyersWithStatusSettling = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(),
					ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus());
			if(buyersWithStatusSettling != 0 ) {
				auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus());
				cancelAffected =false;
			}else {
			auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.CANCELLED.getStatus());
			}
			auctionSellersBean.setActualEndTime(InternetTiming.getInternetDateTime());
			auctionSellersService.save(auctionSellersBean);

			logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.CANCELLED);

			// Add number of cancellations.
			if(cancelAffected) {
			AccountProfileBean accountProfileBean = accountProfileService.findById(SessionHelper.getAccountProfileId(request));
			accountProfileBean.setNoOfCancellations((accountProfileBean.getNoOfCancellations() + 1));
			accountProfileService.save(accountProfileBean);
			}
			List<AuctionBuyersBean> listAuctionBuyersBean = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(
					auctionSellersBean.getAuctionSellersId(), auctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode());
			if (null != listAuctionBuyersBean && listAuctionBuyersBean.size() > 0) {
				for (AuctionBuyersBean auctionBuyersBean : listAuctionBuyersBean) {
					// auctionBuyersService.updateBidOfferStatusByAuctionBuyersId(ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus(),
					// auctionBuyersBean.getAuctionBuyersId());
					/**  MileStone9_Comments_ـFeb11_2019  issues No#7    */
					auctionBuyersBean.setExecutedPrice(0.0f);
					auctionBuyersBean.setBuyerBidStatus(ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus());
					auctionBuyersBean.setActualEndTime(InternetTiming.getInternetDateTime());
					auctionBuyersService.save(auctionBuyersBean);
					balanceHelper.resetBuyerUserBalance(auctionBuyersBean);
					/** 
					 * this method used MBBR 11-12-2019 
					 */
					//balanceHelper.resetBuyerUserBalanceWithMBBR(auctionBuyersBean);
					logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
				}
			}
			redirectAttributes.addFlashAttribute("SUCCESS",
					messageSource.getMessage("auctionoffercontroller.cancelSellerOffers.success", null, localeResolver.resolveLocale(request)));
		} else {
			redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("auctionoffercontroller.cancelSellerOffers.error", null, localeResolver.resolveLocale(request)));
		}

		simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionSellerOfferCancled");
		logger.info("=== Ending cancelSellerOffers method ===");	
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	@RequestMapping("/sellerofferinfopictures")
	public String sellerOfferInfo(HttpServletRequest request, Model model) {
		return ViewConstant.SELLER_AUCTION_OFFER_PLACING;
	}

	/**
	 * This method is used for return code / HTML output via AJAX for upload seller offer pictures.
	 * 
	 * @param accountId
	 *            from requested URL spring will get accountId provided in AJAX request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return it will return processed output for upload seller offer picture view.
	 * 
	 */
	@RequestMapping("/sellerOfferPictureAjax")
	public String sellerOfferPictureAjax(Model model, HttpServletRequest request) {
		logger.info("AuctionOfferController call sellerOfferPictureAjax  method");	
		model.addAttribute("sellerOfferPicturesBean", new SellerOfferPicturesBean());
		int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int auctionSellersId = ((null != request.getParameter("auctionSellersId") && !request.getParameter("auctionSellersId").isEmpty())
				? (Integer.parseInt(request.getParameter("auctionSellersId")))
				: 0);
		model.addAttribute("dailyAuctionsId", dailyAuctionsId);
		model.addAttribute("productId", productId);
		model.addAttribute("auctionSellersId", auctionSellersId);
		logger.info("=== Ending sellerOfferPictureAjax method ===");	
		return "/seller/SM_sellerOfferPictureUploadAjaxForm";
	}

	@RequestMapping(value = "/uploadsellerofferpicture", method = RequestMethod.POST)
	public ModelAndView uploadSellerOfferPicture(HttpServletRequest request, @RequestParam(value = "sellerOfferPicture", required = false) MultipartFile[] sellerOfferPictures,
			RedirectAttributes redirectAttributes) {
		logger.info("AuctionOfferController call uploadSellerOfferPicture  method");	
		int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int auctionSellersId = ((null != request.getParameter("auctionSellersId") && !request.getParameter("auctionSellersId").isEmpty())
				? (Integer.parseInt(request.getParameter("auctionSellersId")))
				: 0);
		
		if (null != sellerOfferPictures && sellerOfferPictures.length > 0) {
			for (MultipartFile sellerOfferPicture : sellerOfferPictures) {
				//Blob blob=null;
		        try{
		       //byte[] contents = sellerOfferPicture.getBytes();
		       		//blob = new SerialBlob(contents);
		       }
		       catch(Exception e)
		        {e.printStackTrace();
		       }
				String fileName = sellerOfferPicture.getOriginalFilename();
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					// fileName = arr[0];
					// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
					fileName = ("seller_offer_picture_" + new Date().getTime() + "." + arr[1]);
				} else {
					// fileName = fileName.concat("_" + new Date().getTime());
					fileName = ("seller_offer_picture_" + new Date().getTime());
				}
				//epapFilesHelper.updateSellerOrBuyerpitcher(SessionHelper.getAccountProfileId(request),"B" , fileName,blob);
				FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_OFFER_PICTURE_PATH, sellerOfferPicture);
				SellerOfferPicturesBean sellerOfferPicturesBean = new SellerOfferPicturesBean();
				sellerOfferPicturesBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
				sellerOfferPicturesBean.setProductCatalogBean(new ProductCatalogBean(productId));
				sellerOfferPicturesBean.setPictureLocation(fileName);

				sellerOfferPicturesService.save(sellerOfferPicturesBean);
			}
		}
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.uploadsellerpicture.success", null, localeResolver.resolveLocale(request)));
		String view = "/ssa/offer/" + dailyAuctionsId + "/" + (auctionSellersId > 0 ? auctionSellersId : "");
		logger.info("=== Ending uploadSellerOfferPicture method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}


	/**
	 * This method is used to remove the particular seller offer uploaded picture by picture id
	 * 
	 * @param id
	 *            picture id by remove image
	 * @return true if successfully remove image
	 */
	@RequestMapping("/removepicture/{id}")
	public @ResponseBody String removepicture(@PathVariable("id") Integer id) {
		//SellerOfferPicturesBean sellerOfferPicturesBean = sellerOfferPicturesService.findById(id);
		//FileUtils.deleteFile(sellerOfferPicturesBean.getAccountProfileBean().getAccountId(), CommonConstants.SELLER_OFFER_PICTURE_PATH,
				//sellerOfferPicturesBean.getPictureLocation());
		logger.info("AuctionOfferController call removepicture  method");	
		sellerOfferPicturesService.delete(new SellerOfferPicturesBean(id));
		logger.info("=== Ending removepicture method ===");
		return "true";
	}

	@RequestMapping(value = "/changeofferprice/{auctionSellersId}", method = RequestMethod.POST)
	public @ResponseBody String updateOfferPriceOnDecrease(@PathVariable("auctionSellersId") Integer auctionSellersId, HttpServletRequest request) {
		logger.info("AuctionOfferController call updateOfferPriceOnDecrease  method");	
		String msg = "";
		if (null != request.getParameter("action") && !request.getParameter("action").isEmpty() && null != auctionSellersId && auctionSellersId > 0) {
			String action = request.getParameter("action");

			AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
			Short dailyAuctionStatus = auctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode();
			Short sellerOfferStatus = auctionSellersBean.getSellerOfferStatus();

			if ((auctionSellersBean.getOfferPrice() > 0)
					&& (ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus() == sellerOfferStatus || ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == sellerOfferStatus)) {
				auctionSellersBean.setOfferUpdatedTime(InternetTiming.getInternetDateTime());
				if (ENUM_AuctionStatusCodes.OPEN.getStatus() == dailyAuctionStatus && ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus() == sellerOfferStatus) {
					/*
					 * if (action.equals(CommonConstants.INCREASE_PRICE)) { auctionSellersBean.setOfferPrice(auctionSellersBean.getOfferPrice() + defaultPrice);
					 * auctionSellersService.save(auctionSellersBean);
					 * 
					 * logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.UPDATED); } else
					 */ if (action.equals(CommonConstants.DECREASE_PRICE)) {
						auctionSellersBean.setOfferPrice(auctionSellersBean.getOfferPrice() - defaultPrice);
						auctionSellersService.save(auctionSellersBean);

						logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
					}
				} else if (ENUM_AuctionStatusCodes.RUNNING.getStatus() == dailyAuctionStatus && ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus() == sellerOfferStatus) {
					if (action.equals(CommonConstants.DECREASE_PRICE)) {
						auctionSellersBean.setOfferPrice(auctionSellersBean.getOfferPrice() - defaultPrice);
						auctionSellersService.save(auctionSellersBean);

						logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
					}
				}

			 

				if ((auctionSellersBean.getOfferPrice() > 0)
						&& (dailyAuctionStatus == ENUM_AuctionStatusCodes.RUNNING.getStatus() && sellerOfferStatus == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus())) {

					List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice(
							ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(), ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(),
							ENUM_AuctionStatusCodes.RUNNING.getStatus(), auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId(), auctionSellersBean.getAuctionSellersId());

					if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
						for (AuctionBuyersBean auctionBuyersBean : auctionBuyersBeanList) {
							acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
						}
					}
				}
			}

			msg = auctionSellersBean.getOfferPrice() +"";
			simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferPrice", ""/*, auctionSellersBean.getOfferPrice()*/);
			//simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferUI", "AuctionSellerOfferUpdated");
		}	
		logger.info("=== Ending updateOfferPriceOnDecrease method ===");
		return msg;
	}

	@RequestMapping(value = "/takebid/{auctionSellersId}")
	public ModelAndView takeBidBySeller(@PathVariable("auctionSellersId") Integer auctionSellersId, RedirectAttributes redirectAttributes) {
		logger.info("AuctionOfferController call takeBidBySeller  method");	
		acceptBuyersBidHelper.acceptTakeBidProcess(auctionSellersId);
		String view = ("/auctions/bids/" + auctionSellersId);
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		logger.info("=== Ending takeBidBySeller method ===");
		return new ModelAndView(red);
	}
	
	
		@RequestMapping(value = "/syncsaveofferdetails", method = RequestMethod.POST)
	public ModelAndView syncSaveOfferDetails(@ModelAttribute("auctionSellersBean") AuctionSellersBean auctionSellersBean, HttpServletRequest request, @RequestParam(value = "sellerPicture", required = false) MultipartFile[] sellerPicture,
			RedirectAttributes redirectAttributes) {
			logger.info("AuctionOfferController call syncSaveOfferDetails  method");	
			boolean isSellerOfferPicture =true;
		String view = ("/auctions/syncoffers/" + auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());

		if (null != auctionSellersBean && null != auctionSellersBean.getDailyAuctionsBean() && null != auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId()
				&& auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId() > 0) {

			// Fetching existing day auction details
			DailyAuctionsBean dailyAuctionsBean = dailyAuctionsService.findById(auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());

			// Checking existing day auction details and auction status
			if ((dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.OPEN.getStatus()
					|| dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus())) {

				AuctionSellersBean currentAuctionSellersBean = null;
				List<String> errorStack = null;
				// Validating auction seller offers
				if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId() && auctionSellersBean.getAuctionSellersId() > 0) {
					// Validating existing auction seller offers
					currentAuctionSellersBean = auctionSellersService.findById(auctionSellersBean.getAuctionSellersId());
					errorStack = sellerAuctionOfferInfoValidator.validateSellerAuctionInfo(currentAuctionSellersBean, auctionSellersBean, request);
				} else {
					// Validating new auction seller offers
					errorStack = sellerAuctionOfferInfoValidator.validateSellerAuctionInfo(auctionSellersBean, request);
				}

				if (null == errorStack || errorStack.size() == 0) {
					boolean isEqual = true;
					if (null != currentAuctionSellersBean && auctionSellersBean.getAuctionSellersId() > 0) {
						currentAuctionSellersBean.setAccountLocationsBean(auctionSellersBean.getAccountLocationsBean());
						currentAuctionSellersBean.setPartialAllowed(auctionSellersBean.getPartialAllowed());
						currentAuctionSellersBean.setOfferPrice(auctionSellersBean.getOfferPrice());

					
						if (auctionSellersBean.getOfferPrice() != currentAuctionSellersBean.getOfferPrice()
								|| auctionSellersBean.getOfferQuantity() != currentAuctionSellersBean.getOfferQuantity()) {
							if (ENUM_AuctionStatusCodes.RUNNING.getStatus() == currentAuctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean()
									.getAuctionStatusCode()) {
								if (auctionSellersBean.getOfferQuantity() > currentAuctionSellersBean.getOfferQuantity()) {
									short diffQty = (short) (auctionSellersBean.getOfferQuantity() - currentAuctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() + diffQty);
								} else {
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity());
								}
							} else if (ENUM_AuctionStatusCodes.OPEN.getStatus() == currentAuctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean()
									.getAuctionStatusCode()) {
								if (auctionSellersBean.getOfferQuantity() > currentAuctionSellersBean.getOfferQuantity()) {
									short diffQty = (short) (auctionSellersBean.getOfferQuantity() - currentAuctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() + diffQty);
								} else {
									short diffQty = (short) (currentAuctionSellersBean.getOfferQuantity() - auctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() - diffQty);
								}
							} else {
								if (auctionSellersBean.getOfferQuantity() > currentAuctionSellersBean.getOfferQuantity()) {
									short diffQty = (short) (auctionSellersBean.getOfferQuantity() - currentAuctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() + diffQty);
								} else {
									short diffQty = (short) (currentAuctionSellersBean.getOfferQuantity() - auctionSellersBean.getOfferQuantity());
									currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity() - diffQty);
								}
							}
							if(auctionSellersBean.getAllowShipperSelection()) {
								isEqual = (currentAuctionSellersBean.getArrangedShipping() == auctionSellersBean.getArrangedShipping());
							if(!auctionSellersBean.getArrangedShipping()) {
								auctionSellersBean.setShipperAccountProfileBean(null);
							}
							}
							// Update seller shipper details when there is not any one buyer's bid not finished
							//auctionSellersBean.getIsAllowShipperSelection() == true means this offer's one bid has finished
							if(auctionSellersBean.getAllowShipperSelection()) {
								currentAuctionSellersBean.setShipperAccountProfileBean(auctionSellersBean.getShipperAccountProfileBean());
								if(null != auctionSellersBean.getShipperAccountProfileBean() && null != auctionSellersBean.getShipperAccountProfileBean().getAccountId() && auctionSellersBean.getShipperAccountProfileBean().getAccountId() > 0) {
									currentAuctionSellersBean.setSellerShippingCharge(auctionSellersBean.getSellerShippingCharge());
								}else {
									currentAuctionSellersBean.setSellerShippingCharge(0F);
								}
							}
							currentAuctionSellersBean.setOfferQuantity(auctionSellersBean.getOfferQuantity());
							currentAuctionSellersBean.setMinimumQuantity(auctionSellersBean.getMinimumQuantity());
							currentAuctionSellersBean.setTruckNumber(auctionSellersBean.getTruckNumber());
							currentAuctionSellersBean.setOfferUpdatedTime(InternetTiming.getInternetDateTime());
							auctionSellersService.save(currentAuctionSellersBean);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerupdated.success", null, localeResolver.resolveLocale(request)));
							auctionOfferHelper.reExecuteAuctionOffer(currentAuctionSellersBean);
							if(currentAuctionSellersBean.getPartialAllowed() == false && currentAuctionSellersBean.getOfferPrice() > 0) {
								auctionOfferHelper.validBuyerBidAndExecuteAuctionOffeAndExecuteBuyerBid(currentAuctionSellersBean);
							}
							// logHelper.sellerLog(currentAuctionSellersBean,
							// ENUM_OfferOperationCodes.UPDATED);
							logger.info("AuctionSellerOfferController : sellerOffers : Seller offer updated...");
						} else {
							currentAuctionSellersBean.setAvailableQuantity(auctionSellersBean.getAvailableQuantity());
							auctionSellersService.save(currentAuctionSellersBean);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerupdated.success", null, localeResolver.resolveLocale(request)));
						}

						SellerOfferInfoBean sellerOfferInfoBean = auctionSellersBean.getSellerOfferInfoBean();
						SellerOfferInfoBean currentSellerOfferInfoBean = auctionSellersBean.getSellerOfferInfoBean();
						currentSellerOfferInfoBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
						currentSellerOfferInfoBean.setProductCatalogBean(auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean());
						currentSellerOfferInfoBean.setInfoLine1(sellerOfferInfoBean.getInfoLine1());
						currentSellerOfferInfoBean.setInfoLine2(sellerOfferInfoBean.getInfoLine2());
						sellerOfferInfoService.save(currentSellerOfferInfoBean);
						if( auctionSellersBean.getShipperAccountProfileBean() !=null &&auctionSellersBean.getShipperAccountProfileBean().getAccountId() !=null) {
						if ((auctionSellersBean.getOfferQuantity().compareTo(offerQty) != 0) || (auctionSellersBean.getOfferPrice().compareTo(offerPrice) != 0) || (auctionSellersBean.getSellerShippingCharge().compareTo(shippingCharge) != 0) || (auctionSellersBean.getShipperAccountProfileBean().getAccountId().compareTo(shipperId) != 0)|| (auctionSellersBean.getTruckNumber().compareTo(truckNumber) != 0)) {
							logHelper.sellerLog(currentAuctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
						}
						}else if(!isEqual) {
							logHelper.sellerLog(currentAuctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
						}else {
							if ((auctionSellersBean.getOfferQuantity().compareTo(offerQty) != 0) || (auctionSellersBean.getOfferPrice().compareTo(offerPrice) != 0) || (auctionSellersBean.getSellerShippingCharge().compareTo(shippingCharge) != 0)|| (auctionSellersBean.getTruckNumber().compareTo(truckNumber) != 0)) {
								logHelper.sellerLog(currentAuctionSellersBean, ENUM_OfferOperationCodes.UPDATED);
							}
						}
					//  Update SellerOffer  (Seller picture )
						if (null != sellerPicture && sellerPicture.length > 0) {
							for (MultipartFile sellerOfferPicture : sellerPicture) {
								Blob blob=null;
						        try{
						       byte[] contents = sellerOfferPicture.getBytes();
						       		blob = BlobProxy.generateProxy(contents);
						       		if(sellerOfferPictureSize < sellerOfferPicture.getSize()) {
						       			isSellerOfferPicture = false;
						       			//errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.sellershippercharge.number", null, locale));
						       		}
						       }
						       catch(Exception e)
						        {e.printStackTrace();
						       }
								String fileName = sellerOfferPicture.getOriginalFilename();
								 if(sellerOfferPicture.getSize() > 0) {
								if (isSellerOfferPicture) {
								if (fileName.contains(".")) {
									String[] arr = fileName.split("\\.");
									// fileName = arr[0];
									// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
									fileName = ("seller_offer_picture_" + new Date().getTime() + "." + arr[1]);
								} else {
									// fileName = fileName.concat("_" + new Date().getTime());
									fileName = ("seller_offer_picture_" + new Date().getTime());
								}
								//epapFilesHelper.updateSellerOrBuyerpitcher(SessionHelper.getAccountProfileId(request),ENUM_AccountTypeCodes.SELLER.getType() , fileName,blob,contentType,CommonConstants.SELLER_OFFER_PICTURE);
								//FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_OFFER_PICTURE_PATH, sellerOfferPicture);
							
								SellerOfferPicturesBean sellerOfferPicturesBean = new SellerOfferPicturesBean();
								sellerOfferPicturesBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
								sellerOfferPicturesBean.setProductCatalogBean(new ProductCatalogBean(auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId()));
								sellerOfferPicturesBean.setPictureLocation(fileName);
								sellerOfferPicturesBean.setContents(blob);
								sellerOfferPicturesService.save(sellerOfferPicturesBean);
							}
								 }
								isSellerOfferPicture=true;
							}
							
						}
						String response = "{\"msg\":\"" +"AuctionSellerOfferPlaced" + "\"}";
						simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferUI", response);
					} else {
						Integer count = auctionSellersService.getAuctionSellersByDailyAuctionsId(auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId(),
								SessionHelper.getAccountProfileId(request));
						if (null == count || count.intValue() == 0) {
							auctionSellersBean.setActualStartTime(InternetTiming.getInternetDateTime());
							auctionSellersBean.setAvailableQuantity(auctionSellersBean.getOfferQuantity());
							auctionSellersBean.setSellerOfferStatus(auctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode());
							if(!auctionSellersBean.getArrangedShipping()) {
								auctionSellersBean.setShipperAccountProfileBean(null);
								auctionSellersBean.setSellerShippingCharge(0F);
							}
							auctionSellersBean.setOfferUpdatedTime(InternetTiming.getInternetDateTime());
							auctionSellersService.save(auctionSellersBean);

							SellerOfferInfoBean sellerOfferInfoBean = auctionSellersBean.getSellerOfferInfoBean();
							sellerOfferInfoBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
							sellerOfferInfoBean.setProductCatalogBean(auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean());
							sellerOfferInfoService.save(sellerOfferInfoBean);

							// It will create log in seller_log table.
							logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.PLACED);

							// Add +1 in bid count.
							AccountProfileBean accountProfileBean = accountProfileService.findById(auctionSellersBean.getAccountProfileBean().getAccountId());
							accountProfileBean.setOfferOrBidCount(accountProfileBean.getOfferOrBidCount() + 1);
							accountProfileService.save(accountProfileBean);

							//   Seller Offer picture 
							if (null != sellerPicture && sellerPicture.length > 0) {
								for (MultipartFile sellerOfferPicture : sellerPicture) {
									Blob blob=null;
							        try{
							       byte[] contents = sellerOfferPicture.getBytes();
							       		blob = BlobProxy.generateProxy(contents);
							       		if(sellerOfferPictureSize < sellerOfferPicture.getSize()) {
							       			isSellerOfferPicture = false;
							       			//errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.sellershippercharge.number", null, locale));
							       		}
							       }
							       catch(Exception e)
							        {e.printStackTrace();
							       }
									String fileName = sellerOfferPicture.getOriginalFilename();
									 if(sellerOfferPicture.getSize() > 0) {
									if (isSellerOfferPicture) {
									if (fileName.contains(".")) {
										String[] arr = fileName.split("\\.");
										// fileName = arr[0];
										// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
										fileName = ("seller_offer_picture_" + new Date().getTime() + "." + arr[1]);
									} else {
										// fileName = fileName.concat("_" + new Date().getTime());
										fileName = ("seller_offer_picture_" + new Date().getTime());
									}
									//epapFilesHelper.updateSellerOrBuyerpitcher(SessionHelper.getAccountProfileId(request),ENUM_AccountTypeCodes.SELLER.getType() , fileName,blob,contentType,CommonConstants.SELLER_OFFER_PICTURE);
									//FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_OFFER_PICTURE_PATH, sellerOfferPicture);
								
									SellerOfferPicturesBean sellerOfferPicturesBean = new SellerOfferPicturesBean();
									sellerOfferPicturesBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
									sellerOfferPicturesBean.setProductCatalogBean(new ProductCatalogBean(auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId()));
									sellerOfferPicturesBean.setPictureLocation(fileName);
									sellerOfferPicturesBean.setContents(blob);
									sellerOfferPicturesService.save(sellerOfferPicturesBean);
								}
								}
									isSellerOfferPicture=true;
								}
								
							}
							String response = "{\"msg\":\"" +"AuctionSellerOfferPlaced" + "\"}";
							simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferUI", response);
							redirectAttributes.addFlashAttribute("SUCCESS",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerplaced.success", null, localeResolver.resolveLocale(request)));
						} else {
							redirectAttributes.addFlashAttribute("ERROR",
									messageSource.getMessage("auctionoffercontroller.saveofferdetails.Offeralreadyplaced.error", null, localeResolver.resolveLocale(request)));
						}
					}

				} else {
					redirectAttributes.addFlashAttribute("errorStack", errorStack);
					view = "/ssa/offer/syncselleroffer/" + auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId();
					if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId() && auctionSellersBean.getAuctionSellersId() > 0) {
						view += ("/" + auctionSellersBean.getAuctionSellersId());
					}
					redirectAttributes.addFlashAttribute("auctionSellersBean", auctionSellersBean);
				}

			} else {
				redirectAttributes.addFlashAttribute("ERROR",
						messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerplaced.timeout", null, localeResolver.resolveLocale(request)));
			}
		} else {
			redirectAttributes.addFlashAttribute("ERROR",
					messageSource.getMessage("auctionoffercontroller.saveofferdetails.offerplaced.timeout", null, localeResolver.resolveLocale(request)));
			view = "/auctions/syncauctionList";
		}
		logger.info("=== Ending syncSaveOfferDetails method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
	
	
	@RequestMapping(value = { "/syncselleroffer/{dailyAuctionId}", "/syncselleroffer/{dailyAuctionId}/{auctionSellersId}" })
	public String syncSellerOffers(Model model, @PathVariable("dailyAuctionId") Integer dailyAuctionId,
			@PathVariable(value = "auctionSellersId", required = false) Integer auctionSellersId, HttpServletRequest request) {
		//String tikentNo=request.getParameter("truckNumber");
		logger.info("AuctionOfferController call syncSellerOffers  method");	
		DailyAuctionsBean dailyAuctionsBean = dailyAuctionsService.findById(dailyAuctionId);
		if (null != dailyAuctionId && dailyAuctionId > 0) {
			if (null != auctionSellersId && auctionSellersId > 0) {
				AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
				if (null != auctionSellersBean && null != auctionSellersBean.getAuctionSellersId()) {
					offerQty = auctionSellersBean.getOfferQuantity();
					offerPrice = auctionSellersBean.getOfferPrice();
					shippingCharge = auctionSellersBean.getSellerShippingCharge();
					if(auctionSellersBean.getShipperAccountProfileBean().getAccountId() != null) {
						shipperId =auctionSellersBean.getShipperAccountProfileBean().getAccountId();
					}
					model.addAttribute("auctionSellersBean", auctionSellersBean);

					if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					} else {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					}
					model.addAttribute("shipperList", accountProfileService.findByAccountTypeCodes(new Character[] {ENUM_AccountTypeCodes.SHIPPER.getType(),ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}));
					List<SellerOfferInfoBean> sellerOfferInfoBeanList = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					if (null != sellerOfferInfoBeanList && sellerOfferInfoBeanList.size() > 0) {
						auctionSellersBean.setSellerOfferInfoBean(sellerOfferInfoBeanList.get(0));
					}
					List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
					logger.info("AuctionSellerOfferController : sellerOffers : Seller offer updated...");
					return ViewConstant.NONAJAX_SELLER_AUCTION_OFFER_PLACING;
				} else {
					Map<String, Object> map = model.asMap();
					if (map.containsKey("auctionSellersBean")) {
						auctionSellersBean = (AuctionSellersBean) map.get("auctionSellersBean");
					} else {
						auctionSellersBean = new AuctionSellersBean();
					}

					auctionSellersBean.setRoyaltyValue(royaltyCodeService.findByRoyaltyCodeByAccountProfileId(SessionHelper.getAccountProfileId(request)));
					auctionSellersBean.setVat(dailyAuctionsBean.getAuctionSettingsBean().getVatSellers());

					auctionSellersBean.setDailyAuctionsBean(dailyAuctionsBean);
					auctionSellersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
					model.addAttribute("auctionSellersBean", auctionSellersBean);
					if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					} else {
						model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
								new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
					}
					model.addAttribute("shipperList", accountProfileService.findByAccountTypeCodes(new Character[] {ENUM_AccountTypeCodes.SHIPPER.getType(),ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}));
					List<SellerOfferInfoBean> sellerOfferInfoBeanList = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					if (null != sellerOfferInfoBeanList && sellerOfferInfoBeanList.size() > 0) {
						auctionSellersBean.setSellerOfferInfoBean(sellerOfferInfoBeanList.get(0));
					}
					List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(
							SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
					model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
					logger.info("AuctionSellerOfferController : sellerOffers : Seller offer created...");
					return ViewConstant.NONAJAX_SELLER_AUCTION_OFFER_PLACING;
				}
			} else {
				AuctionSellersBean auctionSellersBean = new AuctionSellersBean();
				auctionSellersBean.setRoyaltyValue(royaltyCodeService.findByRoyaltyCodeByAccountProfileId(SessionHelper.getAccountProfileId(request)));
				auctionSellersBean.setPartialAllowed(false);
				auctionSellersBean.setVat(dailyAuctionsBean.getAuctionSettingsBean().getVatSellers());
				auctionSellersBean.setDailyAuctionsBean(dailyAuctionsBean);
				auctionSellersBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
				if(auctionSellersBean.getTruckNumber() == null) {
					auctionSellersBean.setTruckNumber(01);
				}
				model.addAttribute("auctionSellersBean", auctionSellersBean);
				if (ENUM_AccountTypeCodes.isUserAgent(SessionHelper.getAccountType(request, false))) {
					model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getOwnerAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				} else {
					model.addAttribute("sellerLocationList", accountLocationsService.findByAccountProfileAccountIdAndStatusIn(SessionHelper.getAccountProfileId(request),
							new Short[] { ENUM_AccountLocationStatus.ACTIVE.getStatus() }));
				}
				model.addAttribute("shipperList", accountProfileService.findByAccountTypeCodes(new Character[] {ENUM_AccountTypeCodes.SHIPPER.getType(),ENUM_AccountTypeCodes.SHIPPER_AGENT.getType()}));
				List<SellerOfferInfoBean> sellerOfferInfoBeanList = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(
						SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
				if (null != sellerOfferInfoBeanList && sellerOfferInfoBeanList.size() > 0) {
					auctionSellersBean.setSellerOfferInfoBean(sellerOfferInfoBeanList.get(0));
				}
				List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(
						SessionHelper.getAccountProfileId(request), auctionSellersBean.getDailyAuctionsBean().getProductCatalogBean().getProductId());
				model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
				logger.info("AuctionSellerOfferController : sellerOffers : Seller offer created...");
				return ViewConstant.NONAJAX_SELLER_AUCTION_OFFER_PLACING;
			}
		}
		logger.info("=== Ending syncSellerOffers method ===");
		return ("redirect: " + request.getContextPath() + "/auctions/syncauctionList");
	}

	@RequestMapping(value = "/syncuploadsellerofferpicture", method = RequestMethod.POST)
	public ModelAndView syncUploadSellerOfferPicture(HttpServletRequest request, @RequestParam(value = "sellerOfferPicture", required = false) MultipartFile[] sellerOfferPictures,
			RedirectAttributes redirectAttributes) {
		logger.info("AuctionOfferController call syncUploadSellerOfferPicture  method");	
		boolean isSellerOfferPicture =true;
//		List<String> errorStack = new ArrayList<String>(0);
//		Locale locale = localeResolver.resolveLocale(request);
		int dailyAuctionsId = Integer.parseInt(request.getParameter("dailyAuctionsId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int auctionSellersId = ((null != request.getParameter("auctionSellersId") && !request.getParameter("auctionSellersId").isEmpty())
				? (Integer.parseInt(request.getParameter("auctionSellersId")))
				: 0);
		if (null != sellerOfferPictures && sellerOfferPictures.length > 0) {
			for (MultipartFile sellerOfferPicture : sellerOfferPictures) {
				Blob blob=null;
		        try{
		       byte[] contents = sellerOfferPicture.getBytes();
		       		blob = BlobProxy.generateProxy(contents);
		       		if(sellerOfferPictureSize < sellerOfferPicture.getSize()) {
		       			isSellerOfferPicture = false;
		       			//errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.sellershippercharge.number", null, locale));
		       		}
		       }
		       catch(Exception e)
		        {e.printStackTrace();
		       }
				String fileName = sellerOfferPicture.getOriginalFilename();
				if (isSellerOfferPicture) {
				if (fileName.contains(".")) {
					String[] arr = fileName.split("\\.");
					// fileName = arr[0];
					// fileName = fileName.concat("_" + new Date().getTime() + "." + arr[1]);
					fileName = ("seller_offer_picture_" + new Date().getTime() + "." + arr[1]);
				} else {
					// fileName = fileName.concat("_" + new Date().getTime());
					fileName = ("seller_offer_picture_" + new Date().getTime());
				}
				//epapFilesHelper.updateSellerOrBuyerpitcher(SessionHelper.getAccountProfileId(request),ENUM_AccountTypeCodes.SELLER.getType() , fileName,blob,contentType,CommonConstants.SELLER_OFFER_PICTURE);
				//FileUtils.uploadFile(SessionHelper.getAccountProfileId(request), fileName, CommonConstants.SELLER_OFFER_PICTURE_PATH, sellerOfferPicture);
			
				SellerOfferPicturesBean sellerOfferPicturesBean = new SellerOfferPicturesBean();
				sellerOfferPicturesBean.setAccountProfileBean(SessionHelper.getAccountProfile(request));
				sellerOfferPicturesBean.setProductCatalogBean(new ProductCatalogBean(productId));
				sellerOfferPicturesBean.setPictureLocation(fileName);
				sellerOfferPicturesBean.setContents(blob);
				sellerOfferPicturesService.save(sellerOfferPicturesBean);
			}
				
				isSellerOfferPicture=true;
			}
			
		}
	
		redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("sellercontroller.uploadsellerpicture.success", null, localeResolver.resolveLocale(request)));
		String view = "/ssa/offer/syncselleroffer/" + dailyAuctionsId + "/" + (auctionSellersId > 0 ? auctionSellersId : "");
		logger.info("=== Ending syncUploadSellerOfferPicture method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	@RequestMapping(value = "/synccancel/{dailyAuctionId}/{auctionSellersId}")
	public ModelAndView syncCancelSellerOffers(@PathVariable("dailyAuctionId") Integer dailyAuctionId, @PathVariable(value = "auctionSellersId") Integer auctionSellersId,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AuctionOfferController call syncCancelSellerOffers  method");	
		String view = ("/auctions/syncoffers/" + dailyAuctionId + "/");
		boolean cancelAffected =true;
		if ((null != dailyAuctionId && dailyAuctionId > 0) && (null != auctionSellersId && auctionSellersId > 0)) {
			AuctionSellersBean auctionSellersBean = auctionSellersService.findById(auctionSellersId);
			int buyersWithStatusSettling = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(),
					ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus());
			
			int buyersWithStatusFinished = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(),
					ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus());
			if(buyersWithStatusSettling != 0 ) {
				auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus());
				cancelAffected =false;
			}else if(buyersWithStatusFinished  != 0) {
				auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus());
				cancelAffected =false;
			}else {
			auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.CANCELLED.getStatus());
			}
			auctionSellersBean.setActualEndTime(InternetTiming.getInternetDateTime());
			auctionSellersService.save(auctionSellersBean);

			logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.CANCELLED);

			// Add number of cancellations.
			if(cancelAffected) {
			AccountProfileBean accountProfileBean = accountProfileService.findById(SessionHelper.getAccountProfileId(request));
			accountProfileBean.setNoOfCancellations((accountProfileBean.getNoOfCancellations() + 1));
			accountProfileService.save(accountProfileBean);
			}
			List<AuctionBuyersBean> listAuctionBuyersBean = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(
					auctionSellersBean.getAuctionSellersId(), auctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode());
			if (null != listAuctionBuyersBean && listAuctionBuyersBean.size() > 0) {
				for (AuctionBuyersBean auctionBuyersBean : listAuctionBuyersBean) {
					// auctionBuyersService.updateBidOfferStatusByAuctionBuyersId(ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus(),
					// auctionBuyersBean.getAuctionBuyersId());
					auctionBuyersBean.setExecutedPrice(0.0f);
					auctionBuyersBean.setBuyerBidStatus(ENUM_AuctionBuyerBidStatusCodes.EXPIRED.getStatus());
					auctionBuyersBean.setActualEndTime(InternetTiming.getInternetDateTime());
					auctionBuyersService.save(auctionBuyersBean);
					balanceHelper.resetBuyerUserBalance(auctionBuyersBean);
						/*		
						 	* this method used MBBR 11-12-2019
						balanceHelper.resetBuyerUserBalanceWithMBBR(auctionBuyersBean);*/
					
					logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
				}
			}
			redirectAttributes.addFlashAttribute("SUCCESS",
					messageSource.getMessage("auctionoffercontroller.cancelSellerOffers.success", null, localeResolver.resolveLocale(request)));
		} else {
			redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("auctionoffercontroller.cancelSellerOffers.error", null, localeResolver.resolveLocale(request)));
		}
		/**this socket used AuctionSellerOfferCancled  **/
		simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", auctionSellersId);
		logger.info("=== Ending syncCancelSellerOffers method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
	
	

}