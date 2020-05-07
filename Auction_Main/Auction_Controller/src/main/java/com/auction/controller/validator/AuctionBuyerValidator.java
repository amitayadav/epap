package com.auction.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.util.RegExpPatterns;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.DailyAuctionsService;

@Component
public class AuctionBuyerValidator {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AuctionBuyersService auctionBuyersServices;

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AuctionSellersService auctionSellersService;

	@Autowired
	private LocaleResolver localeResolver;
	
	public List<String> validateBuyerAuctionBidPlacing(AuctionBuyersBean currentAuctionBuyersBean,AuctionBuyersBean auctionBuyersBean, HttpServletRequest request) {
		List<String> errorStack = new ArrayList<String>(0);
	    //Locale locale = localeResolver.resolveLocale(request);
	    
	    if(null != currentAuctionBuyersBean && null != auctionBuyersBean) {
	    	
	    }
	    
	    return errorStack;
	}

	public List<String> validateBuyerAuctionBidPlacing(AuctionBuyersBean auctionBuyersBean, HttpServletRequest request, AuctionSellersBean auctionSellerBean ) {
		List<String> errorStack = new ArrayList<String>(0);
		if (null != auctionBuyersBean) {
			Locale locale = localeResolver.resolveLocale(request);
			AuctionBuyersBean currentAuctionBuyerBean = null;
			AuctionSellersBean auctionSellersBean = null;
			DailyAuctionsBean dailyAuctionsBean = null;
			if (null != auctionBuyersBean.getAuctionBuyersId()) {
				currentAuctionBuyerBean = auctionBuyersServices.findById(auctionBuyersBean.getAuctionBuyersId());
				dailyAuctionsBean = currentAuctionBuyerBean.getDailyAuctionsBean();
				auctionSellersBean = currentAuctionBuyerBean.getAuctionSellersBean();
			} else {
				dailyAuctionsBean = dailyAuctionsService.findById(auctionBuyersBean.getDailyAuctionsBean().getDailyAuctionsId());
				auctionSellersBean = auctionSellersService.findById(auctionBuyersBean.getAuctionSellersBean().getAuctionSellersId());
			}

			// Validating Seller offer Info Location
			if (null != auctionBuyersBean.getAccountLocationsBean()
					&& (null == auctionBuyersBean.getAccountLocationsBean().getLocationId() || auctionBuyersBean.getAccountLocationsBean().getLocationId() == 0)) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.validation.location.required", null, locale));
			}

			// Validating bid Offer bidQuantity
			if (null == auctionBuyersBean.getBidQuantity() || auctionBuyersBean.getBidQuantity() == 0) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidpalcing.lbl.validation.bidquantity.require", null, locale));
			} else if (!auctionBuyersBean.getBidQuantity().toString().matches(RegExpPatterns.NUMBER)) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.valication.bidquantity.number", null, locale));
			/** issues  no #7    MileStone9_Comments_ـJan28_2019   */
			} else if (auctionBuyersBean.getBidQuantity() > auctionSellersBean.getAvailableQuantity()) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.validation.bidprice.min", null, locale));
			}
			// Validating bid Offer bidPrice
			if (null == auctionBuyersBean.getBidPrice() || auctionBuyersBean.getBidPrice() == 0) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.validation.bidprice.require", null, locale));
			} else if (!auctionBuyersBean.getBidPrice().toString().matches(RegExpPatterns.PRICE)) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidpalcing.lbl.validation.bidprice.number", null, locale));
			} else if (null != currentAuctionBuyerBean &&  currentAuctionBuyerBean.getBidPrice()  > auctionBuyersBean.getBidPrice()) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.validation.bidprice.min", null, locale));
			}
			
			else if (null != currentAuctionBuyerBean && auctionBuyersBean.getBidPrice() < currentAuctionBuyerBean.getBidPrice()
					&& dailyAuctionsBean.getAuctionStatusCodesBean().getAuctionStatusCode() == ENUM_AuctionStatusCodes.RUNNING.getStatus()
					&& auctionSellersBean.getSellerOfferStatus() == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.validation.bidprice.range", null, locale) + "  " + currentAuctionBuyerBean.getBidPrice());
			} /*else if (null != currentAuctionBuyerBean &&  auctionBuyersBean.getBidPrice() <= auctionSellerBean.getOfferPrice()) {
				errorStack.add(messageSource.getMessage("buyer.auctionbidplacing.lbl.validation.bidprice.min", null, locale));
			}*/
			
			
		}
		return errorStack;
	}
}