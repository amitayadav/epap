package com.auction.controller.auctions;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.CommonsUtil;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.FileUtils;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.AuctionTradesBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.bean.SellerInfoBean;
import com.auction.model.bean.SellerOfferInfoBean;
import com.auction.model.bean.SellerOfferPicturesBean;
import com.auction.model.bean.SellerPicturesBean;
import com.auction.model.views.AuctionBuyersBidsView;
import com.auction.model.views.AuctionSellerOffersView;
import com.auction.model.views.DailyAuctionsView;
import com.auction.service.AccountProfileService;
import com.auction.service.AccountTypeCodesService;
import com.auction.service.AgentOwnerService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.AuctionTradesService;
import com.auction.service.BankDetailsService;
import com.auction.service.DailyAuctionsService;
import com.auction.service.LoginDetailsService;
import com.auction.service.SellerInfoService;
import com.auction.service.SellerOfferInfoService;
import com.auction.service.SellerOfferPicturesService;
import com.auction.service.SellerPicturesService;

@Controller
@RequestMapping("/auctions")
public class AuctionCommonController {

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private SellerOfferPicturesService sellerOfferPicturesService;

	@Autowired
	private SellerOfferInfoService sellerOfferInfoService;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private AccountTypeCodesService accountTypeCodesService;

	@Autowired
	private SellerInfoService sellerInfoService;

	@Autowired
	private SellerPicturesService sellerPicturesService;

	@Autowired
	private BankDetailsService bankDetailsService;

	@Autowired
	private LoginDetailsService loginDetailsService;
	
	@Value("${auction.default.updownprice}")
	private float defaultPrice;

	@Value("${google.map.key}")
	private String googleMapKey;

	@Autowired
	private AuctionTradesService tradesService;

	@Autowired
	private AgentOwnerService agentOwnerService;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method is used to display the auctionList page of application
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return auctionList page
	 */
	@RequestMapping(value = "/auctionList")
	public String displayAuctionList(Model model) {
		return ViewConstant.AUCTION_LIST;
	}

	/**
	 * This method is used to display the auctionList page of application
	 * 
	 * @param model
	 *            Spring model for passing data controller to view page
	 * @return auctionList page
	 */

	@RequestMapping(value = "/auctionlist")
	public String displaySyncAuctionList(Model model) {
		return ViewConstant.NONAJAX_AUCTION_LIST;
	}
	
	@RequestMapping(value = "/offers/{dailyAuctionId}")
	public String sellerOffers(Model model, @PathVariable("dailyAuctionId") Integer dailyAuctionId, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall sellerOffers ");
		DailyAuctionsView dailyAuctionView = dailyAuctionsService.findViewByAuctionId(dailyAuctionId);
		if (null != dailyAuctionView && null != dailyAuctionView.getDailyAuctionsId() && dailyAuctionView.getDailyAuctionsId() > 0) {
			model.addAttribute("dailyAuctionView", dailyAuctionView);
			model.addAttribute("defaultPrice", defaultPrice);
			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid()) {
				if ((loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType())
						|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType())) {
					Integer accountId = loginDetailsBean.getAccountProfileBean().getAccountId();
					List<AuctionSellersBean> auctionSellersBeanList = auctionSellersService.findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId(dailyAuctionId, accountId);
					if (null != auctionSellersBeanList && auctionSellersBeanList.size() > 0) {
						model.addAttribute("auctionSellersBean", auctionSellersBeanList.get(0));
						model.addAttribute("account", auctionSellersBeanList.get(0).getAccountLocationsBean().getAccountProfileBean().getAccountId());
					}

				}
			}
			return ViewConstant.AUCTION_OFFERS;
		}
		logger.info("=== Ending  sellerOffers === ");
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

	@RequestMapping(value = "/bids/{auctionSellersId}")
	public String buyerBids(Model model, @PathVariable("auctionSellersId") Integer auctionSellersId, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall buyerBids ");
		AuctionSellerOffersView auctionSellerOffersView = auctionSellersService.findViewByAuctionSellersId(auctionSellersId);
		if (null != auctionSellerOffersView && null != auctionSellerOffersView.getAccountId() && auctionSellerOffersView.getAccountId() > 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionSellerOffersView.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType()) {
				model.addAttribute("sellerAccountId", loginUser.getAccountProfileBean().getAccountId());
			} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				model.addAttribute("sellerAccountId", ownerLoginDetailsBean.getAccountProfileBean().getAccountId());
			}
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
			return ViewConstant.AUCTION_BIDS;
		}
		logger.info("=== Ending  buyerBids === ");
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

	@ResponseBody
	@RequestMapping(value = "/offerBuyerBids/{auctionSellersId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<AuctionSellerOffersView> sellerOfferView(@PathVariable("auctionSellersId") Integer auctionSellersId, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall sellerOfferView ");
		AuctionSellerOffersView auctionSellerOffersView = auctionSellersService.findViewByAuctionSellersId(auctionSellersId);
		LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionSellerOffersView.getAccountId());
		if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType()) {
			auctionSellerOffersView.setOwnerAccountId(loginUser.getAccountProfileBean().getAccountId());
		} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
			LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
			auctionSellerOffersView.setOwnerAccountId(ownerLoginDetailsBean.getAccountProfileBean().getAccountId());
		}
		List<AuctionSellerOffersView> list = new ArrayList<AuctionSellerOffersView>(0);
		list.add(auctionSellerOffersView);
		logger.info("AuctionCommonControllerCall sellerOfferView ending");
		return list;
	}

	@RequestMapping("/auctiontrades")
	public String auctionTrades(Model model, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall auctionTrades ");
		Date startDate = new Date();
		Date endDate = new Date();
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		// String endDateStr= startDateStr
		List<AuctionTradesBean> auctionTradeList = new ArrayList<AuctionTradesBean>();
		boolean isDatesEmpty = false;
		if (null == startDateStr || startDateStr.isEmpty()) {
			isDatesEmpty = true;
		}
		if (null == endDateStr || endDateStr.isEmpty()) {
			isDatesEmpty = true;
		}
		if (!isDatesEmpty) {
			auctionTradeList = tradesService.getAuctionTradesBeanBetweenDate(java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr));
		}
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		model.addAttribute("auctionTradeList", auctionTradeList);
		logger.info("=== Ending  auctionTrades === ");
		return ViewConstant.AUCTION_TRADES_LIST;
	}

	/* AuctionTradesList NONAJAX */
	@RequestMapping("/auctionTrades")
	public String auctionTradesList(Model model, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall auctionTradesList ");
		Date startDate = new Date();
		Date endDate = new Date();
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");
		List<AuctionTradesBean> auctionTradeList = new ArrayList<AuctionTradesBean>();
		boolean isDatesEmpty = false;
		if (null == startDateStr || startDateStr.isEmpty()) {
			isDatesEmpty = true;
		}
		if (null == endDateStr || endDateStr.isEmpty()) {
			isDatesEmpty = true;
		}
		if (!isDatesEmpty) {
			auctionTradeList = tradesService.getAuctionTradesBeanBetweenDate(java.sql.Date.valueOf(startDateStr), java.sql.Date.valueOf(endDateStr));
		}
		model.addAttribute("startDate", startDateStr);
		model.addAttribute("endDate", endDateStr);
		model.addAttribute("auctionTradeList", auctionTradeList);
		logger.info("=== Ending  auctionTradesList === ");
		return ViewConstant.NONAJAX_AUCTION_TRADES_LIST;
	}

	/* JSON Responses or View data list generator */
	@ResponseBody
	@RequestMapping(value = "/dailyAuctionViewList", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<DailyAuctionsView> dailyAuctionViewList(HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall dailyAuctionViewList ");
		Date internetTime = InternetTiming.getInternetDateTime();
		Date startDate = DateHelper.getDate(internetTime, true);
		Date endDate = DateHelper.getDate(internetTime, false);
		List<DailyAuctionsView> dailyAuctionViewList = dailyAuctionsService.findViewByAuctionStatusCodeAndTodayDate(ENUM_AuctionStatusCodes.getAllStatusCodes(), startDate,
				endDate);
		if ((ENUM_AccountTypeCodes.SELLER.getType() == SessionHelper.getAccountType(request, false))
				|| (ENUM_AccountTypeCodes.SELLER_AGENT.getType() == SessionHelper.getAccountType(request, false))) {
			List<Integer> dailyAuctionsIds = auctionSellersService.getDailyAuctionIdsBySellerAccountId(SessionHelper.getAccountProfileId(request), startDate, endDate);
			if (null != dailyAuctionsIds && dailyAuctionsIds.size() > 0) {
				dailyAuctionViewList.forEach(dailyAuction -> dailyAuction.setAuctionSellers(dailyAuctionsIds.contains(dailyAuction.getDailyAuctionsId())));
			}
		} else if ((ENUM_AccountTypeCodes.BUYER.getType() == SessionHelper.getAccountType(request, false))
				|| (ENUM_AccountTypeCodes.BUYER_AGENT.getType() == SessionHelper.getAccountType(request, false))) {
			List<Integer> dailyAuctionsIds = auctionBuyersService.getDailyAuctionIdsByBuyerAccountId(SessionHelper.getAccountProfileId(request), startDate, endDate);
			if (null != dailyAuctionsIds && dailyAuctionsIds.size() > 0) {
				dailyAuctionViewList.forEach(dailyAuction -> dailyAuction.setAuctionBuyers(dailyAuctionsIds.contains(dailyAuction.getDailyAuctionsId())));
			}
		}
		logger.info("=== Ending  dailyAuctionViewList === ");
		return dailyAuctionViewList;
	}

	@ResponseBody
	@RequestMapping(value = "/selleroffers/{dailyAuctionId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<AuctionSellerOffersView> sellerOffers(@PathVariable("dailyAuctionId") Integer dailyAuctionId, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall sellerOffers method  ");
		List<AuctionSellerOffersView> auctionSellerOffersViewList = auctionSellersService.findByAuctionSellersAuctionSellersIdAndSellerOfferInAscOfferUpdatedTime(dailyAuctionId,
				ENUM_AuctionSellerOfferStatusCodes.getAllAuctionOfferStatus());
		if (null != auctionSellerOffersViewList && auctionSellerOffersViewList.size() > 0) {
			for (AuctionSellerOffersView sellerOffersViewList : auctionSellerOffersViewList) {
				LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(sellerOffersViewList.getAccountId());
				if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType()) {
					// model.addAttribute("sellerAccountId",loginUser.getAccountProfileBean().getAccountId());
					sellerOffersViewList.setOwnerAccountId(loginUser.getAccountProfileBean().getAccountId());
				} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
					LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
					sellerOffersViewList.setOwnerAccountId(ownerLoginDetailsBean.getAccountProfileBean().getAccountId());
					// model.addAttribute("sellerAccountId",ownerLoginDetailsBean.getAccountProfileBean().getAccountId());
				}
			}
		}
		if ((ENUM_AccountTypeCodes.BUYER.getType() == SessionHelper.getAccountType(request, false))
				|| (ENUM_AccountTypeCodes.BUYER_AGENT.getType() == SessionHelper.getAccountType(request, false))) {
			List<Integer> auctionSellersIds = auctionSellersService.getAuctionSellerIdsByBuyerAccountId(SessionHelper.getAccountProfileId(request), dailyAuctionId);
			if (null != auctionSellersIds && auctionSellersIds.size() > 0) {
				auctionSellerOffersViewList.forEach(auctionSeller -> auctionSeller.setAuctionBuyers(auctionSellersIds.contains(auctionSeller.getAuctionSellersId())));
			}
		}
		logger.info("=== Ending  sellerOffers method === ");
		return auctionSellerOffersViewList;
	}

	@ResponseBody
	@RequestMapping(value = "/buyerbids/{auctionSellersId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<AuctionBuyersBidsView> buyerBids(@PathVariable("auctionSellersId") Integer auctionSellersId) {
		logger.info("AuctionCommonControllerCall buyerBids method  ");
		logger.info("=== Ending  buyerBids method === ");
		List<AuctionBuyersBidsView>auctionBuyersBidsViewList= auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime(auctionSellersId,
				ENUM_AuctionBuyerBidStatusCodes.getAllAuctionBidStatus());
		AuctionSellersBean auctionSellerBean=	auctionSellersService.findById(auctionSellersId);
		if(auctionSellerBean.getOfferPrice() >0.0 && auctionSellerBean.getPartialAllowed() !=true) {
			return auctionBuyersBidsViewList = sortBuyerBidQuantitytAndBidStusta(auctionBuyersBidsViewList);
				}
		return auctionBuyersBidsViewList;
	}

	public static List<AuctionBuyersBidsView> sortBuyerBidQuantitytAndBidStusta(List<AuctionBuyersBidsView>auctionBuyersBidsViewList) {
		int i=0;
		int j=0;
		 int buyerBidStatusCount=0;
		 boolean buyerBidStatus = true;
		List<AuctionBuyersBidsView> selectedBuyersListSortQuantity = new ArrayList<AuctionBuyersBidsView>();
		List<AuctionBuyersBidsView> selectedBuyersBidStatusList = new ArrayList<AuctionBuyersBidsView>();
		int lastIndex =auctionBuyersBidsViewList.size()-1;
		boolean result = true;
		for(i=0; i<=auctionBuyersBidsViewList.size()-1; i++) {
			if(result) {
			List<AuctionBuyersBidsView> selectedBuyersList = new ArrayList<AuctionBuyersBidsView>();
			double bidPrice= auctionBuyersBidsViewList.get(i).getBidPrice();
			for( j=i; j<=auctionBuyersBidsViewList.size()-1; j++) {
				buyerBidStatus=true;
				if(bidPrice == auctionBuyersBidsViewList.get(j).getBidPrice()) {
					if(auctionBuyersBidsViewList.get(j).getBuyerBidStatusCode() == ENUM_AuctionBuyerBidStatusCodes.OPEN.getStatus()) {
						selectedBuyersList.add(auctionBuyersBidsViewList.get(j));
					}else if(auctionBuyersBidsViewList.get(j).getBuyerBidStatusCode() == ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus()) {
						selectedBuyersList.add(auctionBuyersBidsViewList.get(j));
					}else {
						selectedBuyersBidStatusList.add(auctionBuyersBidsViewList.get(j));
						buyerBidStatusCount =selectedBuyersBidStatusList.size();
						buyerBidStatus = false;
					}
					
					if(lastIndex == j ) {
						Collections.sort(selectedBuyersList,new Comparator<AuctionBuyersBidsView>() {
							@Override
							public int compare(AuctionBuyersBidsView o1, AuctionBuyersBidsView o2) {
								// TODO Auto-generated method stub
								 return (int) (o2.getBidQuantity().compareTo(o1.getBidQuantity()));
							}
						}); 
						result=false;
						selectedBuyersListSortQuantity.addAll(selectedBuyersList);
						selectedBuyersListSortQuantity.addAll(selectedBuyersBidStatusList);
						
						return selectedBuyersListSortQuantity;
					
					}
				}else {
					if(buyerBidStatus) {
						Collections.sort(selectedBuyersList,new Comparator<AuctionBuyersBidsView>() {
							@Override
							public int compare(AuctionBuyersBidsView o1, AuctionBuyersBidsView o2) {
								// TODO Auto-generated method stub
								 return (int) (o2.getBidQuantity().compareTo(o1.getBidQuantity()));
							}
						}); 
						selectedBuyersListSortQuantity.addAll(selectedBuyersList);
					}
					i=0;
					j=0;
					int listSize= selectedBuyersListSortQuantity.size() +buyerBidStatusCount ;
					i =i+listSize-1;
					j=j+listSize-1;
					break;
				}
			}
		}
		}
		return selectedBuyersListSortQuantity;
	}
	@RequestMapping("/offerLocation")
	public String offerLocation(HttpServletRequest request, Model model) {
		logger.info("AuctionCommonControllerCall offerLocation method  ");
		model.addAttribute("latitude", request.getParameter("latitude"));
		model.addAttribute("longitude", request.getParameter("longitude"));
		model.addAttribute("googleMapKey", googleMapKey);
		logger.info("=== Ending  offerLocation method === ");
		return "/common/viewLocationManagementAjaxForm";
	}

	@RequestMapping(value = "/viewPictures")
	public String viewSellerPicture(@RequestParam("accountId") Integer accountId, @RequestParam("productId") Integer productId, @RequestParam("sellerId") Integer sellerId,
			Model model) {
		logger.info("AuctionCommonControllerCall viewSellerPicture  method  ");
		List<SellerOfferPicturesBean> sellerOfferPicturesBeanList = sellerOfferPicturesService.findByAccountProfileAccountIdAndProductCatalogProductId(accountId, productId);
		AuctionSellersBean auctionSellersBean = auctionSellersService.findById(sellerId);
		SellerOfferInfoBean sellerOfferInfoBean = sellerOfferInfoService.findByAccountProfileAccountIdAndProductCatalogProductId(accountId, productId).get(0);
		model.addAttribute("infoLine1", sellerOfferInfoBean.getInfoLine1());
		model.addAttribute("infoLine2", sellerOfferInfoBean.getInfoLine2());
		model.addAttribute("sellerOfferPicturesBeanList", sellerOfferPicturesBeanList);
		logger.info("=== Ending  viewSellerPicture method === ");
		return "/common/viewSellerOfferPictureAjaxForm";

	}

	// Image fetching from image repository
	/**
	 * This method is used to get all the uploaded image for current login user
	 * 
	 * @param accountId
	 *            pass the image by particular account id folder
	 * @param fileName
	 *            pass the file name by get the image
	 * @return seller info and picture page
	 */
	@RequestMapping("/offerpictures/{sellerId}/{fileName:.+}")
	public @ResponseBody byte[] getProcuctImage(@PathVariable("sellerId") Integer sellerId, @PathVariable("fileName") String fileName) {
		logger.info("AuctionCommonControllerCall getProcuctImage  method  ");
		Blob sellerimg = sellerOfferPicturesService.getSellerOfferProductImg(fileName, sellerId);
		byte[] imgBytes = new byte[1024];
		try {
			imgBytes = FileUtils.getFileContent(sellerimg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("=== Ending  getProcuctImage method === ");
		return imgBytes;
	}

	/**
	 * This method is used to get all the details of account profile for auction
	 * seller
	 * 
	 * @return seller account profile by @RequestParam("sellerId")Integer sellerId
	 */
	@RequestMapping("/profileInfo")
	public String profileInfo(@RequestParam("sellerId") Integer sellerId, Model model) {
		logger.info("AuctionCommonControllerCall profileInfo  method  ");
		if (null != sellerId && sellerId > 0) {
			SellerInfoBean sellerInfoBeananOwner = null;
			List<SellerPicturesBean> sellerPicturesBeanListOwner = null;
			AccountProfileBean accountProfileBean = accountProfileService.findById(sellerId);
			SellerInfoBean sellerInfoBean = sellerInfoService.findById(sellerId);
			List<SellerPicturesBean> sellerPicturesBeanList = sellerPicturesService.getByAccountProfileId(accountProfileBean.getAccountId());

			AccountTypeCodesBean accountTypeCodesBean = accountTypeCodesService.getAccountTypeCodeByAccountId(sellerId);
			if (ENUM_AccountTypeCodes.isUserAgent(accountTypeCodesBean.getAccountType())) {
				AccountProfileBean ownerAccountProfileBean = accountProfileService.findOwnerAccountProfileByAgentAccountId(sellerId);
				if (sellerInfoBean.getSellerId() == null && sellerPicturesBeanList == null) {
					sellerInfoBeananOwner = sellerInfoService.findById(ownerAccountProfileBean.getAccountId());
					sellerPicturesBeanListOwner = sellerPicturesService.getByAccountProfileId(ownerAccountProfileBean.getAccountId());
				}
				model.addAttribute("ownerName", ownerAccountProfileBean.getFullName());
			}

			if (null != sellerInfoBean) {
				accountProfileBean.setSellerInfoBean(sellerInfoBean);

			}
			if (null != sellerInfoBeananOwner) {
				accountProfileBean.setSellerInfoBean(sellerInfoBeananOwner);
			}

			if (null != sellerPicturesBeanList && sellerPicturesBeanList.size() != 0) {
				model.addAttribute("sellerPicturesBeanList", sellerPicturesBeanList);
			}
			if (null != sellerPicturesBeanListOwner && sellerPicturesBeanListOwner.size() != 0) {
				model.addAttribute("sellerPicturesBeanList", sellerPicturesBeanListOwner);
			}

			model.addAttribute("accountProfileBean", accountProfileBean);
			model.addAttribute("rating", Math.round(accountProfileBean.getRating()));
		}
		logger.info("=== Ending  profileInfo method === ");
		return "/common/profileInfoAjaxForm";
	}

	@ResponseBody
	@RequestMapping(value = "/getAvailableBalance", method = RequestMethod.GET)
	public BigDecimal getAvailableBalance(HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall getAvailableBalance  method  ");
		LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
		BigDecimal availableBalance = new BigDecimal(0.00);
		Double purchasePower = 999999.99;
		if (null != loginDetailsBean && null != loginDetailsBean.getAccountProfileBean() && null != loginDetailsBean.getAccountProfileBean().getAccountId())
			if (ENUM_AccountTypeCodes.isUserAgent(loginDetailsBean.getAccountTypeCodesBean().getAccountType())) {
				if (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.BUYER_AGENT.getType()) {
					AgentOwnerBean agentOwnerBean = agentOwnerService.getPurchaseLimitOfAgentByAccountId(loginDetailsBean.getLoginUserid());
					if (agentOwnerBean.getPurchaseLimit().doubleValue() == purchasePower || agentOwnerBean.getPurchaseLimit().doubleValue() == 0.00) {
						availableBalance = bankDetailsService.getAvailableBalanceOfOwnerByAccountId(loginDetailsBean.getLoginUserid());
					} else {
						Double purchaseower = agentOwnerBean.getPurchaseLimit().doubleValue() - agentOwnerBean.getLimitSpent().doubleValue();
						BigDecimal ownerAvailableBalance = bankDetailsService.getAvailableBalanceOfOwnerByAccountId(loginDetailsBean.getLoginUserid());
						purchaseower = CommonsUtil.checkMinimum(ownerAvailableBalance.doubleValue(), purchaseower);
						BigDecimal newAvailableBalance = new BigDecimal(purchaseower);
						availableBalance = newAvailableBalance;

					}

				} else {
					availableBalance = bankDetailsService.getAvailableBalanceOfOwnerByAccountId(loginDetailsBean.getLoginUserid());
				}
			} else {
				availableBalance = bankDetailsService.getAvailableBalanceByAccountId(loginDetailsBean.getAccountProfileBean().getAccountId());
			}
		logger.info("=== Ending  getAvailableBalance method === ");
		return availableBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@RequestMapping(value = "/syncoffers/{dailyAuctionId}")
	public String asyncSellerOffers(Model model, @PathVariable("dailyAuctionId") Integer dailyAuctionId, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall asyncSellerOffers  method  ");
		DailyAuctionsView dailyAuctionView = dailyAuctionsService.findViewByAuctionId(dailyAuctionId);
		if (null != dailyAuctionView && null != dailyAuctionView.getDailyAuctionsId() && dailyAuctionView.getDailyAuctionsId() > 0) {
			model.addAttribute("dailyAuctionView", dailyAuctionView);
			model.addAttribute("defaultPrice", defaultPrice);
			LoginDetailsBean loginDetailsBean = SessionHelper.getLoginDetailsBean(request);
			if (null != loginDetailsBean && null != loginDetailsBean.getLoginUserid()) {
				if ((loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType())
						|| (loginDetailsBean.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType())) {
					Integer accountId = loginDetailsBean.getAccountProfileBean().getAccountId();
					List<AuctionSellersBean> auctionSellersBeanList = auctionSellersService.findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId(dailyAuctionId, accountId);
					if (null != auctionSellersBeanList && auctionSellersBeanList.size() > 0) {
						model.addAttribute("auctionSellersBean", auctionSellersBeanList.get(0));
					}
				}
			}
			logger.info("=== Ending  asyncSellerOffers method === ");
			return ViewConstant.NONAJAX_AUCTION_OFFERS;
		}
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

	@RequestMapping(value = "/syncbids/{auctionSellersId}")
	public String syncBuyerBids(Model model, @PathVariable("auctionSellersId") Integer auctionSellersId, HttpServletRequest request) {
		logger.info("AuctionCommonControllerCall buyerBids ");
		AuctionSellerOffersView auctionSellerOffersView = auctionSellersService.findViewByAuctionSellersId(auctionSellersId);
		if (null != auctionSellerOffersView && null != auctionSellerOffersView.getAccountId() && auctionSellerOffersView.getAccountId() > 0) {
			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionSellerOffersView.getAccountId());
			if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER.getType()) {
				model.addAttribute("sellerAccountId", loginUser.getAccountProfileBean().getAccountId());
			} else if (loginUser.getAccountTypeCodesBean().getAccountType() == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				model.addAttribute("sellerAccountId", ownerLoginDetailsBean.getAccountProfileBean().getAccountId());
			}
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
			return ViewConstant.NONAJAX_AUCTION_BIDS;
		}
		logger.info("=== Ending  buyerBids === ");
		return ("redirect: " + request.getContextPath() + "/auctions/auctionList");
	}

}