package com.auction.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.auction.model.bean.AuctionRequestBean;
import com.auction.model.bean.SellerInfoBean;

@Component
public class SellerValidator {

	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	// Validation : Seller Info
	public List<String> validateSellerInfo(SellerInfoBean sellerInfoBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);

		// Validating Seller Info 1
		if (null == sellerInfoBean.getInfoLine1() || sellerInfoBean.getInfoLine1().trim().length() == 0) {
			errorStack.add(messageSource.getMessage("seller.sellerinfopicture.validation.info1.required", null, locale));
		} else if (null != sellerInfoBean.getInfoLine1() && sellerInfoBean.getInfoLine1().length() > 255) {
			errorStack.add(messageSource.getMessage("seller.sellerinfopicture.validation.info1.maxlength", null, locale));
		}

		// Validating Seller Info 2
		if (null != sellerInfoBean.getInfoLine2() && sellerInfoBean.getInfoLine2().length() > 255) {
			errorStack.add(messageSource.getMessage("seller.sellerinfopicture.validation.info2.maxlength", null, locale));
		}

		return errorStack;
	}
	
	// Validation : Seller Auction Request
		public List<String> validateSellerAuctionRequest(AuctionRequestBean auctionRequestBean, HttpServletRequest request) {
			List<String> errorStack = new ArrayList<String>(0);
			Locale locale = localeResolver.resolveLocale(request);

			// Validating Product Catalog for Auction Request
			if (null == auctionRequestBean.getProductCatalogBean() || null == auctionRequestBean.getProductCatalogBean().getProductId()) {
				errorStack.add(messageSource.getMessage("seller.auctionrequest.validation.productId.required", null, locale));
			}

			// Validating Seller Info 2
			if (null == auctionRequestBean.getSellerComment() || auctionRequestBean.getSellerComment().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("seller.auctionrequest.validation.sellerComment.required", null, locale));
			} else if (null != auctionRequestBean.getSellerComment() && auctionRequestBean.getSellerComment().length() > 500) {
				errorStack.add(messageSource.getMessage("seller.auctionrequest.validation.sellerComment.maxlength", null, locale));
				auctionRequestBean.setSellerComment(auctionRequestBean.getSellerComment().substring(0, 500));
			}

			return errorStack;
		}
	
	
	
}