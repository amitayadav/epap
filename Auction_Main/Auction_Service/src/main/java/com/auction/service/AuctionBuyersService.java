package com.auction.service;

import java.util.Date;
import java.util.List;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.views.AuctionBuyersBidsView;
import com.auction.service.generic.GenericService;

public interface AuctionBuyersService extends GenericService<AuctionBuyersBean, Integer> {

	public Integer getCountByAuctionSellersId(Integer auctionSellersId);

	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersId(Integer auctionSellersId);

	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId);

	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId, Short buyerBidStatus);
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInOrderByBidPriceDescAuctionBuyersIdAsc(Integer auctionSellersId, Short[] buyerBidStatus);
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndAccountProfileAccountId(Integer auctionSellersId, Integer accountId);

	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(Integer auctionSellersId, Short buyerBidStatus);
	 
	public List<AuctionBuyersBean> findByAuctionSellersAuctionSellersIdAndBuyerBidStatusIn(Integer auctionSellersId, Short[] buyerBidStatus);

	public void updateBidOfferStatusByAuctionBuyersId(Short buyerBidStatus, Integer auctionBuyersId);

	public void updateBidOfferStatusAndExecutedPriceByAuctionBuyersId(Short buyerBidStatus, Integer auctionBuyersId, Float executedPrice,Date actualEndTime); 
	
	public List<AuctionBuyersBean> getConfirmDeliveryByBuyerAccountId(Integer accountId, Short buyerBidStatus);

	public List<AuctionBuyersBean> getManageShippingBuyerAccount(Short[] buyerBidStatus);

	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(Short buyerBidStatus, Short sellerOfferStatus,
			Short auctionStatusCode, Date beginTime);

	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrLowerBidPriceThanOfferPriceByBeginTimeWithoutMarketPrice(Short buyerBidStatus, Short sellerOfferStatus,
			Short auctionStatusCode, Date beginTime);

	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPriceByBeginTimeWithMarketPrice(Short buyerBidStatus, Short sellerOfferStatus,
			Short auctionStatusCode, Date beginTime);

	public List<AuctionBuyersBean> getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice(Short buyerBidStatus, Short sellerOfferStatus, Short auctionStatusCode,
			Integer dailyAuctionsId, Integer auctionSellersId);

	public List<AuctionBuyersBean> getAuctionBuyersByStatusByAuctionSellersId(Short buyerBidStatus, Short sellerOfferStatus, Short auctionStatusCode, Integer auctionSellersId);
	
	public List<AuctionBuyersBean> getAuctionBuyersByStatusByAuctionSellersIdAscActualStartTime(Short buyerBidStatus, Short sellerOfferStatus, Short auctionStatusCode, Integer auctionSellersId);

	public List<Integer> getDailyAuctionIdsByBuyerAccountId(Integer accountId, Date beginTime, Date endTime);

	public List<Short> getBuyerBidStatusByAuctionSellerId(Integer auctionSellersId);

	public Integer getAuctionBuyersByAuctionSellerId(Integer auctionSellersId, Integer accountId);

	// View classes for provide only required data on UI.
	public List<AuctionBuyersBidsView> findViewByAuctionSellersId(Integer auctionSellersId);

	public AuctionBuyersBidsView findViewByAuctionBuyersId(Integer auctionBuyersId);

	public List<AuctionBuyersBidsView> findViewByAuctionSellersIdAndBuyerBidStatus(Integer auctionSellersId, Short[] buyerBidStatus);

	public List<AuctionBuyersBean> getConfirmDeliveryByBuyerAccountIdIn(Integer accountId, Short[] buyerBidStatus,Date beginDate,Date endDate);
	
	public Integer  countAuctionBuyerByStatus(Integer auctionSellersId, Short buyerBidStatus);
	
	public   List<AuctionBuyersBidsView>  findByAuctionSellersAuctionSellersIdAndBuyerBidStatusInAscBidUpdatedTime(Integer auctionSellersId, Short[] buyerBidStatus);
	
	public void updateBuyerBidQuantityByAuctionBuyersId(Integer bidQuantity, Integer auctionBuyersId);
	
}