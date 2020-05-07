package com.auction.service;

import java.util.Date;
import java.util.List;

import com.auction.model.bean.DailyAuctionsBean;
import com.auction.model.views.DailyAuctionsView;
import com.auction.service.generic.GenericService;

public interface DailyAuctionsService extends GenericService<DailyAuctionsBean, Integer> {

	public void updateAuctionStatusBasedOnBeginTime(Short auctionStatusCodes, Date beginDate);

	public void updateAuctionStatusBasedOnDailyAuctionsId(Short auctionStatusCodes, Integer dailyAuctionsId);

	public List<DailyAuctionsBean> findByAuctionStatusCodesAuctionStatusCodeAndBeginTime(Short auctionStatusCode, Date date);

	public List<DailyAuctionsBean> getByAuctionStatusCodesAuctionStatusCodeAndEndTime(Short auctionStatusCode, Date endDate);

	public List<DailyAuctionsBean> findByAuctionStatusCodeAndTodayDate(Short[] auctionStatusCode, Date startDate, Date endDate);

	public List<DailyAuctionsBean> findByDuplicateAuction(Integer dailyAuctionsId, Short[] auctionStatusCode, Date startDate, Date endDate, String productGroupName,
			String productName, String productTypeName);

	// Daily Auction View Data JSON
	public List<DailyAuctionsView> findViewByAuctionStatusCodeAndTodayDate(Short[] auctionStatusCode, Date startDate, Date endDate);

	public DailyAuctionsView findViewByAuctionId(Integer auctionId);
}