package com.auction.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.auction.commons.constant.ViewConstant;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.model.views.AuctionTradesView;
import com.auction.model.views.DailyAuctionsView;
import com.auction.service.AuctionTradesService;
import com.auction.service.DailyAuctionsService;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private DailyAuctionsService dailyAuctionsService;

	@Autowired
	private AuctionTradesService tradesService;


	private AuctionLogger logger = new AuctionLogger(this.getClass());

	/**
	 * This method will display home page of the application it contains tiles
	 * definition name. This is the main method of the application.
	 * 
	 * @return it will return home tiles definition name.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		logger.info("Home Controller Class in home Metohd call");
		logger.info("Home Controller Class in home Metohd ending");
		return ViewConstant.HOME_PAGE;
	}

	/**
	 * This method will display home page of the application it contains tiles
	 * definition name. This is the main method of the application.
	 * 
	 * @return it will return home tiles definition name.
	 * 
	 */
	@RequestMapping("/dayauctions")
	public String dayAuctions(Model model) {
		logger.info("Home Controller Class in dayAuctions Metohd call");
		logger.info("=== dayAuctions Metohd ending ===");
		return ViewConstant.HOME_DAY_AUCTIONS;
	}

	@RequestMapping("/mcq")
	public String mcq(Model model) {
		logger.info("Home Controller Class in dayAuctions Metohd call");
		logger.info("=== mcq Metohd ending ===");
		return ViewConstant.HOME_MCQ;
	}

	/* JSON Responses or View data list generator */
	@ResponseBody
	@RequestMapping(value = "/dailyAuctionViewList", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<DailyAuctionsView> dailyAuctionViewList(HttpServletRequest request) {
		logger.info("Home Controller Class in dailyAuctionViewList Metohd call");
		Date internetTime = InternetTiming.getInternetDateTime();
		Date startDate = DateHelper.getDate(internetTime, true);
		Date endDate = DateHelper.getDate(internetTime, false);
		List<DailyAuctionsView> dailyAuctionViewList = dailyAuctionsService.findViewByAuctionStatusCodeAndTodayDate(ENUM_AuctionStatusCodes.getAllStatusCodes(), startDate,
				endDate);

		logger.info("=== dailyAuctionViewList Metohd ending ===");
		return dailyAuctionViewList;
	}

	/**
	 * This method will display home page of the application it contains tiles
	 * definition name. This is the main method of the application.
	 * 
	 * @return it will return home tiles definition name.
	 * 
	 */

	@RequestMapping("/auctiontrades")
	public String auctionTrades(Model model, HttpServletRequest request) {
		return ViewConstant.HOME_AUCTION_TRADES;
	}

	@ResponseBody
	@RequestMapping(value = "/auctiontradesviewlist", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<AuctionTradesView> auctionTradesViewList(HttpServletRequest request) {
		logger.info("Home Controller Class in auctionTradesViewList Metohd call");
		List<AuctionTradesView> auctionTradeList = new ArrayList<AuctionTradesView>();
		auctionTradeList = tradesService.getAuctionTradesBetweenDate(new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()));

		logger.info("=== auctionTradesViewList Metohd ending ===");
		return auctionTradeList;
	}

}