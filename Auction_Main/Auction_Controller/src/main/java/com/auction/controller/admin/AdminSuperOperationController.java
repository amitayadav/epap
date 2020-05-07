package com.auction.controller.admin;

import java.util.HashMap;
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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AuctionRequestStatus;
import com.auction.commons.enums.ENUM_ProductCatalogStatus;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.controller.util.SessionHelper;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AuctionRequestBean;
import com.auction.model.bean.ProductCatalogBean;
import com.auction.service.AuctionRequestService;
import com.auction.service.ProductCatalogService;

@Controller
@RequestMapping("/spropt/")
public class AdminSuperOperationController {
	
	@Autowired
	private AuctionRequestService auctionRequestService;

	@Autowired
	private ProductCatalogService productCatalogService;

	@Autowired
	private LocaleResolver localeResolver;

	@Autowired
	private MessageSource messageSource;

	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	/* Product Catalog Master */
	@RequestMapping("/productcataloglist")
	public String productcataloglist(Model model) {
		logger.info("AdminSuperOperationController call productcataloglist method");
		model.addAttribute("productCatalogBeanList", productCatalogService.findAll());
		logger.info("=== Ending  productcataloglist method ===");
		return ViewConstant.ADMIN_PRODUCT_CATALOG_LIST;
	}

	
	/* Product Catalog Master non ajax*/
	@RequestMapping("/productCatalogList")
	public String productCatalogList(Model model) {
		model.addAttribute("productCatalogBeanList", productCatalogService.findAll());
		return ViewConstant.ADMIN_PRODUCT_CATALOG_LIST;
	}

	/**
	 * This method is used To product catalog *
	 * 
	 * @param productId
	 *            selecting productId
	 * 
	 * @return AM_productCatalogAjaxForm
	 */
	@RequestMapping("/productcatalog/{productId}")
	public String productcatalog(@PathVariable(value = "productId", required = false) Integer productId, Model model, HttpServletRequest request) {
		logger.info("AdminSuperOperationController call productcatalog method");
		if (null != productId && productId > 0) {
			if (null != request.getParameter("VIEWONLY") && !request.getParameter("VIEWONLY").isEmpty()) {
				model.addAttribute("VIEWONLY", true);
			}
			model.addAttribute("productCatalogBean", productCatalogService.findById(productId));
		} else {
			model.addAttribute("productCatalogBean", new ProductCatalogBean());
		}

		Map<Short, String> productStatusList = new HashMap<Short, String>(0);
		for (ENUM_ProductCatalogStatus val : ENUM_ProductCatalogStatus.values()) {
			productStatusList.put(val.getStatus(), val.getDesc());
		}

		model.addAttribute("productStatusList", productStatusList);
		model.addAttribute("productGroupNameList", productCatalogService.getProductGroupName());
		model.addAttribute("productTypeNameList", productCatalogService.getProductTypeName());
		logger.info("=== Ending  productcatalog method ===");
		return "/admin/AM_productCatalogAjaxForm";
	}

	/**
	 * This method is used To save product catalog *
	 * 
	 * @param ProductCatalogBean
	 *            selecting ProductCatalogBean
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any
	 *            request mapping / URL.
	 *            
	 * @return productcataloglist page
	 */
	@RequestMapping(value = "/saveproductcatalog", method = RequestMethod.POST)
	public ModelAndView saveproductcatalog(@ModelAttribute("productCatalogBean") ProductCatalogBean productCatalogBean, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		logger.info("AdminSuperOperationController call saveproductcatalog method");
		if (null != productCatalogBean) {
			Integer count = productCatalogService.getCountByGroupNameTypeId((null != productCatalogBean.getProductId() ? productCatalogBean.getProductId() : 0),
					productCatalogBean.getProductGroupName(), productCatalogBean.getProductName(), productCatalogBean.getProductTypeName());
			if (count > 0) {
				redirectAttributes.addFlashAttribute("ERROR",
						messageSource.getMessage("admincontroller.saveproductcatalog.error.duplicate", null, localeResolver.resolveLocale(request)));
			} else {
				productCatalogService.save(productCatalogBean);
				if (null != productCatalogBean.getProductId()) {
					redirectAttributes.addFlashAttribute("SUCCESS",
							messageSource.getMessage("admincontroller.saveproductcatalog.success.updated", null, localeResolver.resolveLocale(request)));
				} else {
					redirectAttributes.addFlashAttribute("SUCCESS",
							messageSource.getMessage("admincontroller.saveproductcatalog.success.added", null, localeResolver.resolveLocale(request)));
				}
			}
		}
		logger.info("=== Ending  saveproductcatalog method ===");
		RedirectView red = new RedirectView("/spropt/productcataloglist", true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}

	
	/*Auction Request Management*/
	/**
	 * This method is used to return the admin auction request list view
	 * 
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return auction request list view
	 */
	@RequestMapping("/adminauctionrequestlist")
	public String auctionrequestlist(HttpServletRequest request, Model model) {
		logger.info("AdminSuperOperationController call auctionrequestlist method");
		model.addAttribute("auctionRequestBeanList", auctionRequestService.findAll());
		logger.info("=== Ending  auctionrequestlist method ===");
		return ViewConstant.ADMIN_AUCTION_REQUEST_LIST;
	}
	
	@RequestMapping("/adminauctionRequestList")
	public String auctionRequestList(HttpServletRequest request, Model model) {
		logger.info("AdminSuperOperationController call auctionrequestlist method");
		model.addAttribute("auctionRequestBeanList", auctionRequestService.findAll());
		logger.info("=== Ending  auctionrequestlist method ===");
		return ViewConstant.NONAJAX_ADMIN_AUCTION_REQUEST_LIST;
	}

	/**
	 * This method is used to get the particular auction request data and also set all the auction request status list on auction request approval ajax model
	 * 
	 * @param requestId
	 *            specific auction request id
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param model
	 *            model is spring model for passing data from controller to view page.
	 * @return auction request approve ajax view model
	 */
	@RequestMapping("/auctionrequestedit/{requestId}")
	public String auctionrequestedit(@PathVariable("requestId") Integer requestId, HttpServletRequest request, Model model) {
		logger.info("AdminSuperOperationController call auctionrequestedit method");
		model.addAttribute("auctionRequestBean", auctionRequestService.findById(requestId));

		/*
		 * List<Short> AuctionStatusList = new ArrayList<Short>(); for (ENUM_AuctionStatus status : ENUM_AuctionStatus.values()) { AuctionStatusList.add(status.getStatus()); }
		 */
		Map<Short, String> AuctionStatusList = new HashMap<Short, String>(0);
		for (ENUM_AuctionRequestStatus val : ENUM_AuctionRequestStatus.values()) {
			AuctionStatusList.put(val.getStatus(), val.getDesc());
		}
		model.addAttribute("auctionStatusList", AuctionStatusList);
		logger.info("=== Ending  auctionrequestedit method ===");
		return "/admin/AM_auctionRequestApprovalAjax";
	}

	/**
	 * This method is used to update the seller auction status
	 * 
	 * @param auctionRequestBean
	 *            spring will get auctionRequestBean from model attribute of spring.
	 * @param request
	 *            HttpServletRequest class for getting values from session or request.
	 * @param redirectAttributes
	 *            it uses for passing messages or data while redirecting on any request mapping / URL.
	 * @return auction request list page view
	 */
	@RequestMapping(value = "/updateAuctionRequest", method = RequestMethod.POST)
	public ModelAndView updateAuctionRequest(@ModelAttribute("auctionRequestBean") AuctionRequestBean auctionRequestBean, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.info("AdminSuperOperationController call updateAuctionRequest method");
		String view = "/spropt/adminauctionrequestlist";
		Integer accountId = SessionHelper.getAccountProfileId(request);

		AuctionRequestBean currentAuctionRequestBean = auctionRequestService.findById(auctionRequestBean.getRequestId());

		currentAuctionRequestBean.setStatus(auctionRequestBean.getStatus());
		currentAuctionRequestBean.setFeedbackOn(InternetTiming.getInternetDateTime());
		currentAuctionRequestBean.setFeedback(auctionRequestBean.getFeedback());
		currentAuctionRequestBean.setAccountProfileByFeedbackBy(new AccountProfileBean(accountId));
		currentAuctionRequestBean.setAccountProfileByUpdatedBy(new AccountProfileBean(accountId));

		auctionRequestService.save(currentAuctionRequestBean);
		logger.info("=== Ending  updateAuctionRequest method ===");
		RedirectView red = new RedirectView(view, true);
		red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(red);
	}
	
}