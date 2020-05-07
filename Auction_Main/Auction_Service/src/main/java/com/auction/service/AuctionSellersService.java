package com.auction.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.entity.AuctionSellers;
import com.auction.model.views.AuctionBuyersBidsView;
import com.auction.model.views.AuctionSellerOffersView;
import com.auction.service.generic.GenericService;

public interface AuctionSellersService extends GenericService<AuctionSellersBean, Integer> {

	public Integer getCountByDailyAuctionsId(Integer dailyAuctionsId);
	
	public List<AuctionSellersBean> findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus);

	public List<AuctionSellersBean> findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus(Integer dailyAuctionsId, Short sellerOfferStatus);

	public List<AuctionSellersBean> findByDailyAuctionsDailyAuctionsIdAndAccountProfileAccountId(Integer dailyAuctionsId, Integer accountId);

	public void updateSellerOfferStatusByAuctionSellersId(Short sellerOfferStatus, Integer auctionSellersId);
	
	public void updateSellerOfferStatusByAuctionSellersId(Short sellerOfferStatus, Integer auctionSellersId, Date actualEndTime);

	public AuctionSellerOffersView findViewByAuctionSellersId(Integer auctionSellersId);

	public List<AuctionSellersBean> getAuctionSellersWithRemaingAvailableQuantity(Short auctionStatusCode, Integer dailyAuctionsId);

	public List<Integer> getDailyAuctionIdsBySellerAccountId(Integer accountId, Date beginTime, Date endTime);

	public List<Integer> getAuctionSellerIdsByBuyerAccountId(Integer accountId, Integer dailyAuctionId);

	public Integer getAuctionSellersByDailyAuctionsId(Integer dailyAuctionsId, Integer sellerAccountId);
	
	public List<Short> getSellerOfferStatusByDailyAuctionsId(Integer dailyAuctionsId);

	// View classes for provide only required data on UI.
	public List<AuctionSellerOffersView> findAuctionSellerOffersViewByDailyAuctionsIddAndSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus);
	
	public Integer countDailyAuctionSellerOfferStatusNotIn(Integer dailyAuctionsId, Short[] sellerOfferStatus);
	
	public Integer countDailyAuctionSellerOfferStatusIn(Integer dailyAuctionsId, Short[] sellerOfferStatus);
	
	public List<AuctionSellersBean> getAuctionSellersWithLimitPriceByBeginTime(Short sellerOfferStatus,  Short auctionStatusCode,  Date beginTime);

	public List<AuctionSellerOffersView> findByAuctionSellersAuctionSellersIdAndSellerOfferInAscOfferUpdatedTime(Integer dailyAuctionsId, Short[] sellerOfferStatus);

	  
}