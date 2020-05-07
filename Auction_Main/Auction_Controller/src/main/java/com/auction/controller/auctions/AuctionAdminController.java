package com.auction.controller.auctions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.enums.ENUM_ProductCatalogStatus;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.component.BuyerBankDetailsHelper;
import com.auction.component.LogHelper;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.AuctionSettingsBean;
import com.auction.model.bean.AuctionStatusCodesBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.bean.ProductCatalogBean;
import com.auction.model.views.DailyAuctionsView;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.AuctionStatusCodesService;
import com.auction.service.DailyAuctionsService;
import com.auction.service.ProductCatalogService;

@Controller
@RequestMapping("/admin/auctions")
public class AuctionAdminController {

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AuctionStatusCodesService auctionStatusCodesService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private BuyerBankDetailsHelper balanceHelper;

	@Autowired
	private LogHelper logHelper;

	@Value("${auction.default.duration}")
	private short auctionDefaultDuration;

	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/* Auction Management */
	/**
	 * This method is used to return the admin auction management list view
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or
	 *            request.
	 * @param model
	 *            model is spring model for passing data from controller to view
	 *            page.
	 * @return auction management list view
	 */
	@RequestMapping("/auctionlist")
	public String auctionList(HttpServletRequest request, Model model) {
		// model.addAttribute("internetTime",
		// InternetTiming.getInternetDateTimeAsMiliSeconds());
		return ViewConstant.ADMIN_AUCTION_MANAGEMENT_LIST;
	}

	/**
	 * This method is used get auction details if auctionId is not null otherwise
	 * new auction bean will set.
	 * 
	 * @param model
	 *            model is spring model for passing data from controller to view
	 *            page.
	 * @return manage auction page
	 */
	@RequestMapping(value = { "/manageauction", "/manageauction/{auctionId}" })
	public String manageauction(Model model, @PathVariable(value = "auctionId", required = false) Integer auctionId) {
		logger.info("AuctionAdminController Call manageauction method ");
		DailyAuctionsBean dailyAuctionsBean;
		if (null != auctionId && auctionId > 0) {
			dailyAuctionsBean = dailyAuctionsService.findById(auctionId);
			if ((dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.FINISHED.getStatus())
					|| (dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.CANCELLED.getStatus())) {
				model.addAttribute("dailyAuctionsBean", dailyAuctionsService.findById(auctionId));
				return "admin/AM_viewAuction";
			}
		} else {
			dailyAuctionsBean = new DailyAuctionsBean();
			dailyAuctionsBean.setAuctionDuration(auctionDefaultDuration);
		}
		model.addAttribute("dailyAuctionsBean", dailyAuctionsBean);
		model.addAttribute("productList", productCatalogService.getByStatusIn(new Short[] { ENUM_ProductCatalogStatus.APPROVED.getStatus() }));
		model.addAttribute("auctionStatusList", auctionStatusCodesService.findAll());
		logger.info("=== ending manageauction method === ");
		return "admin/AM_manageAuctionAjax";
	}

	/**
	 * This method is used to create or update the auction management on
	 * administrator
	 * 
	 * @param dailyAuctionsBean
	 * 
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/saveauction", method = RequestMethod.POST)
	public ModelAndView saveAuction(@ModelAttribute("dailyAuctionsBean") DailyAuctionsBean dailyAuctionsBean, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		logger.info("AuctionAdminController Call saveAuction method ");
		String view = "/admin/auctions/auctionlist";

		if (null != dailyAuctionsBean && null != dailyAuctionsBean.getDailyAuctionsId() && dailyAuctionsBean.getDailyAuctionsId() > 0) {

			final long ONE_MINUTE_IN_MILLIS = 60000;
			long curTimeInMs = dailyAuctionsBean.getBeginTime().getTime();
			Date endTime = new Date(curTimeInMs + (auctionDefaultDuration * ONE_MINUTE_IN_MILLIS));

			dailyAuctionsBean.setProductCatalogBean(productCatalogService.findById(dailyAuctionsBean.getProductCatalogBean().getProductId()));
			dailyAuctionsBean.setEndTime(endTime);
			List<DailyAuctionsBean> dailyAuctionBeans = dailyAuctionsService.findByDuplicateAuction(dailyAuctionsBean.getDailyAuctionsId(),
					new Short[] { ENUM_AuctionStatusCodes.CANCELLED.getStatus() }, dailyAuctionsBean.getBeginTime(), endTime,
					dailyAuctionsBean.getProductCatalogBean().getProductGroupName(), dailyAuctionsBean.getProductCatalogBean().getProductName(),
					dailyAuctionsBean.getProductCatalogBean().getProductTypeName());

			if (null == dailyAuctionBeans || dailyAuctionBeans.size() == 0) {
				Integer accountId = SessionHelper.getAccountProfileId(request);
				DailyAuctionsBean currentDailyAuctionBean = dailyAuctionsService.findById(dailyAuctionsBean.getDailyAuctionsId());
				currentDailyAuctionBean.setAuctionStatusCodesBean(new AuctionStatusCodesBean(dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode()));
				currentDailyAuctionBean.setProductCatalogBean(new ProductCatalogBean(dailyAuctionsBean.getProductCatalogBean().getProductId()));
				currentDailyAuctionBean.setBeginTime(dailyAuctionsBean.getBeginTime());
				currentDailyAuctionBean.setEndTime(endTime);
				currentDailyAuctionBean.setAccountProfileByUpdatedbyIdBean(new AccountProfileBean(accountId));
				currentDailyAuctionBean.setUpdatedOn(InternetTiming.getInternetDateTime());
				if (dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.CANCELLED.getStatus()) {
					currentDailyAuctionBean.setAccountProfileByCancelledbyIdBean(new AccountProfileBean(accountId));
					currentDailyAuctionBean.setCanceledOn(InternetTiming.getInternetDateTime());

					Short[] codeList = { ENUM_AuctionStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.OPEN.getStatus() };
					List<AuctionSellersBean> listAuctionSellersBean = auctionSellersService
							.findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn(dailyAuctionsBean.getDailyAuctionsId(), codeList);
					if (null != listAuctionSellersBean && listAuctionSellersBean.size() > 0) {
						for (AuctionSellersBean auctionSellersBean : listAuctionSellersBean) {
							if (auctionSellersBean.getSellerOfferStatus() == ENUM_AuctionStatusCodes.RUNNING.getStatus()
									|| auctionSellersBean.getSellerOfferStatus() == ENUM_AuctionStatusCodes.OPEN.getStatus())
								auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.CANCELLED.getStatus(),
										auctionSellersBean.getAuctionSellersId(), InternetTiming.getInternetDateTime());
							logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.EXPIRED);

							List<AuctionBuyersBean> listAuctionBuyersBean = auctionBuyersService
									.findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(auctionSellersBean.getAuctionSellersId(), auctionSellersBean.getSellerOfferStatus());
							if (null != listAuctionBuyersBean && listAuctionBuyersBean.size() > 0) {
								for (AuctionBuyersBean auctionBuyersBean : listAuctionBuyersBean) {
									/** MileStone9_Comments_ـFeb11_2019 issues No#7 */
									Float executedPrice = 0.0f;
									auctionBuyersService.updateBidOfferStatusAndExecutedPriceByAuctionBuyersId(ENUM_AuctionBuyerBidStatusCodes.CANCELLED.getStatus(),
											auctionBuyersBean.getAuctionBuyersId(), executedPrice, InternetTiming.getInternetDateTime());
									
									balanceHelper.resetBuyerUserBalance(auctionBuyersBean);
									/**
									 * this is method used MBBR 11-12-2019
									 * balanceHelper.resetBuyerUserBalanceWithMBBR(auctionBuyersBean);
									 */
									
									logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
								}
							}
						}
					}
				}
				dailyAuctionsService.save(currentDailyAuctionBean);
				/**
				 *  this socket used AuctionUpdated
				 */
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", dailyAuctionsBean.getDailyAuctionsId());
				redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("admincontroller.updateauction.success", null, localeResolver.resolveLocale(request)));
			} else {
				redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("admincontroller.saveaddnewauction.error", null, localeResolver.resolveLocale(request)));
			}
		} else {

			// Calculating AUCTION END TIME
			final long ONE_MINUTE_IN_MILLIS = 60000;
			long curTimeInMs = dailyAuctionsBean.getBeginTime().getTime();
			Date endTime = new Date(curTimeInMs + (auctionDefaultDuration * ONE_MINUTE_IN_MILLIS));
			// dailyAuctionsBean.setProductCatalogBean(productCatalogService.findById(dailyAuctionsBean.getProductCatalogBean().getProductId()));
			/* changes 29 -09-2018 remove query line no:212 */
			dailyAuctionsBean.setProductCatalogBean(new ProductCatalogBean((dailyAuctionsBean.getProductCatalogBean().getProductId())));
			List<DailyAuctionsBean> dailyAuctionBeans = dailyAuctionsService.findByDuplicateAuction(0, new Short[] { ENUM_AuctionStatusCodes.CANCELLED.getStatus() },
					dailyAuctionsBean.getBeginTime(), endTime, dailyAuctionsBean.getProductCatalogBean().getProductGroupName(),
					dailyAuctionsBean.getProductCatalogBean().getProductName(), dailyAuctionsBean.getProductCatalogBean().getProductTypeName());

			if (null == dailyAuctionBeans || dailyAuctionBeans.size() == 0) {
				Integer accountId = SessionHelper.getAccountProfileId(request);
				dailyAuctionsBean.setAuctionSettingsBean(new AuctionSettingsBean(1));
				dailyAuctionsBean.setAuctionDuration(auctionDefaultDuration);

				dailyAuctionsBean.setEndTime(endTime);
				dailyAuctionsBean.setAuctionStatusCodesBean(new AuctionStatusCodesBean(ENUM_AuctionStatusCodes.OPEN.getStatus()));
				dailyAuctionsBean.setAccountProfileByCreatedbyIdBean(new AccountProfileBean(accountId));
				dailyAuctionsBean.setCreatedOn(InternetTiming.getInternetDateTime());
				dailyAuctionsService.save(dailyAuctionsBean);
				redirectAttributes.addFlashAttribute("SUCCESS", messageSource.getMessage("admincontroller.saveaddnewauction.success", null, localeResolver.resolveLocale(request)));
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionCreated");
			} else {
				redirectAttributes.addFlashAttribute("ERROR", messageSource.getMessage("admincontroller.saveaddnewauction.error", null, localeResolver.resolveLocale(request)));
			}
		}
		logger.info("=== ending saveAuction method === ");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	/**
	 * This method is used To view auction *
	 * 
	 * @param auctionId
	 *            selecting auctionId
	 * 
	 * @return AM_viewAuction page
	 */

	@RequestMapping("/viewauction/{auctionId}")
	public String viewauction(@PathVariable("auctionId") Integer auctionId, Model model) {
		model.addAttribute("dailyAuctionsBean", dailyAuctionsService.findById(auctionId));
		return "admin/AM_viewAuction";
	}

	/**
	 * This method is used To dailyAuctionViewList *
	 * 
	 * @param Request
	 * 
	 * 
	 * @return same page
	 */
	@ResponseBody
	@RequestMapping(value = "/dailyAuctionViewList", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<DailyAuctionsView> dailyAuctionViewList() {
		logger.info("AuctionAdminController Call dailyAuctionViewList method ");
		Short[] auctionStatusCode = ENUM_AuctionStatusCodes.getAllStatusCodes();
		Date internetTime = InternetTiming.getInternetDateTime();
		Date startDate = DateHelper.getDate(internetTime, true);
		Date endDate = DateHelper.getDate(internetTime, false);
		logger.info("=== ending dailyAuctionViewList method === ");
		return dailyAuctionsService.findViewByAuctionStatusCodeAndTodayDate(auctionStatusCode, startDate, endDate);
	}

}