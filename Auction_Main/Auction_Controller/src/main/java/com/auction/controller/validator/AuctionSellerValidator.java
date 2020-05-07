package com.auction.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.util.RegExpPatterns;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;

@Component
public class AuctionSellerValidator {

	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	// This method is validating already exist offers for new policy rule.
	
	public List<String> validateSellerAuctionInfo(AuctionSellersBean currentAuctionSellersBean, AuctionSellersBean auctionSellersBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);
		
		if (null != auctionSellersBean) {
			errorStack = validateSellerAuctionInfo(auctionSellersBean, request);
		
				// Validating for seller offer price can't be change to upward then last existing offer price.
				if (null !=currentAuctionSellersBean &&auctionSellersBean.getOfferPrice() > currentAuctionSellersBean.getOfferPrice() && currentAuctionSellersBean.getOfferPrice() !=0) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerPrice.max", null, locale));
				}
				if (null !=currentAuctionSellersBean &&auctionSellersBean.getOfferPrice() < currentAuctionSellersBean.getOfferPrice() && currentAuctionSellersBean.getOfferPrice() !=0 ) {
					if( currentAuctionSellersBean.getSellerOfferStatus() == ENUM_AuctionStatusCodes.OPEN.getStatus()) {
						errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerPrice.max", null, locale));
					}
				}
				if (null !=currentAuctionSellersBean && currentAuctionSellersBean.getOfferPrice() == 0 ) {
					if( currentAuctionSellersBean.getSellerOfferStatus() == ENUM_AuctionStatusCodes.OPEN.getStatus()) {
						errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.open.offerPrice.max", null, locale));
					}
				}
					
				// Validating for if partial allowed is false you can not change the qty of existing offer quantity.
				if ((!auctionSellersBean.getPartialAllowed()) && (!(auctionSellersBean.getOfferQuantity().equals(currentAuctionSellersBean.getOfferQuantity())))) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerQuantity.partialnotallowed.cannotchange", null, locale));
				}
				// Validating Seller Truck Number Validations
				if(null == currentAuctionSellersBean.getTruckNumber()) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.truckNumber.required", null, locale));
				}else if(currentAuctionSellersBean.getTruckNumber() == 0) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.truckNumber.equalzero", null, locale));
				}
		}
		return errorStack;
	}

	// This method is validating new offers.
	public List<String> validateSellerAuctionInfo(AuctionSellersBean auctionSellersBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
		Locale locale = localeResolver.resolveLocale(request);

		if (null != auctionSellersBean) {

			// Validating Seller offer Info Location
			if (null != auctionSellersBean.getAccountLocationsBean()
					&& (null == auctionSellersBean.getAccountLocationsBean().getLocationId() || auctionSellersBean.getAccountLocationsBean().getLocationId() == 0)) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.location.required", null, locale));
			}

			// Validating Seller Shipping Validations
			if(auctionSellersBean.getAllowShipperSelection()) {
				if(null == auctionSellersBean.getArrangedShipping()) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.arrangedforshipping.required", null, locale));				
				}else if(null != auctionSellersBean.getArrangedShipping() && auctionSellersBean.getArrangedShipping()) {
					if(null == auctionSellersBean.getShipperAccountProfileBean() || null == auctionSellersBean.getShipperAccountProfileBean().getAccountId()) {
						errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.shipperselection.required", null, locale));
					}
					if(null == auctionSellersBean.getSellerShippingCharge()) {
						errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.sellershippercharge.required", null, locale));
					}else if(null != auctionSellersBean.getSellerShippingCharge() && (!auctionSellersBean.getSellerShippingCharge().toString().matches(RegExpPatterns.PRICE))) {
						errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.sellershippercharge.number", null, locale));
					}
				}
			}
			
			// Validating Seller Offer Info OfferQuantity
			if (null == auctionSellersBean.getOfferQuantity() || auctionSellersBean.getOfferQuantity() == 0) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerQuantity.required", null, locale));
			} else if (null != auctionSellersBean.getOfferQuantity() && !auctionSellersBean.getOfferQuantity().toString().matches(RegExpPatterns.NUMBER)) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerQuantity.number", null, locale));
			}

			if(null == auctionSellersBean.getPartialAllowed()) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.partialexecution.required", null, locale));
			}else if(auctionSellersBean.getPartialAllowed()) {
				if(null == auctionSellersBean.getMinimumQuantity()) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.minimumQuantity.required", null, locale));
				}else if(null != auctionSellersBean.getMinimumQuantity() && !auctionSellersBean.getMinimumQuantity().toString().matches(RegExpPatterns.NUMBER)) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.minimumQuantity.number", null, locale));
				}else if(null != auctionSellersBean.getOfferPrice() && null != auctionSellersBean.getMinimumQuantity() && auctionSellersBean.getMinimumQuantity() >= auctionSellersBean.getOfferQuantity()) {
					errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.minimumQuantity.lessthanofferquantity", null, locale));
				}
			}
			
			// Validating Seller Offer Info OfferPrice
			if (null == auctionSellersBean.getOfferPrice()) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerPrice.required", null, locale));
			} else if ((!auctionSellersBean.getOfferPrice().toString().matches(RegExpPatterns.PRICE))) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.offerPrice.number", null, locale));
			}
			// Validating Seller Offer Info 1
			if (null == auctionSellersBean.getSellerOfferInfoBean().getInfoLine1() || auctionSellersBean.getSellerOfferInfoBean().getInfoLine1().trim().length() == 0) {
				errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.info1.required", null, locale));
			}
			
			// Validating Seller Truck Number Validations
		if(null == auctionSellersBean.getTruckNumber()) {
			errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.truckNumber.required", null, locale));
		}else if(auctionSellersBean.getTruckNumber() == 0) {
			errorStack.add(messageSource.getMessage("seller.auctionofferplacing.lbl.validation.truckNumber.equalzero", null, locale));
		}
		}
		return errorStack;
	}
}