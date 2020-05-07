package com.auction.service;

import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.component.AuctionSettlementPhase;
import com.auction.component.AuctionStatusHelper;
import com.auction.component.LogHelper;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.DailyAuctionsBean;
import com.auction.service.util.AuctionBidResult;
import com.auction.service.util.OrderExecutionLevel1Mock;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DailyAuctionFinishedJob extends QuartzJobBean {


	
	private DailyAuctionsService dailyAuctionsService;
	private AuctionSellersService auctionSellersService;
	private AuctionBuyersService auctionBuyersService;
	private SimpMessagingTemplate simpMessagingTemplate;
	private AuctionSettlementPhase auctionSettlementPhase;
	private AuctionStatusHelper auctionStatusHelper;
	private LogHelper logHelper;

	public void setDailyAuctionsService(DailyAuctionsService dailyAuctionsService) {
		this.dailyAuctionsService = dailyAuctionsService;
	}

	public void setAuctionSellersService(AuctionSellersService auctionSellersService) {
		this.auctionSellersService = auctionSellersService;
	}

	public void setAuctionBuyersService(AuctionBuyersService auctionBuyersService) {
		this.auctionBuyersService = auctionBuyersService;
	}

	public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public void setAuctionSettlementPhase(AuctionSettlementPhase auctionSettlementPhase) {
		this.auctionSettlementPhase = auctionSettlementPhase;
	}

	public void setAuctionStatusHelper(AuctionStatusHelper auctionStatusHelper) {
		this.auctionStatusHelper = auctionStatusHelper;
	}
	public void setLogHelper(LogHelper logHelper) {
		this.logHelper = logHelper;
	}



	private AuctionLogger logger = new AuctionLogger(getClass());

	@SuppressWarnings("null")
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			Date date = DateHelper.getAuctionDate(InternetTiming.getInternetDateTime(), false);
			logger.info("#25");
			/*System.out.println("#25");*/
			logger.trace(getClass().getSimpleName() + " for " + date + " Started... at " + new Date(System.currentTimeMillis()));
			logger.info("DailyAuctionFinishedJob" +" "+" for " + date + " Started... at " + new Date(System.currentTimeMillis()));
			/*System.out.println("#25"+"DailyAuctionFinishedJob" +" "+" for " + date + " Started... at " + new Date(System.currentTimeMillis()));*/
			List<AuctionSellersBean> auctionSellersBeanList = null;
			List<AuctionBuyersBean> auctionBuyersBeanList = null;
			// Retrieving daily auction list
			List<DailyAuctionsBean> dailyAuctionsBeansList = dailyAuctionsService.getByAuctionStatusCodesAuctionStatusCodeAndEndTime(ENUM_AuctionStatusCodes.RUNNING.getStatus(),
					date);
			if (null != dailyAuctionsBeansList && dailyAuctionsBeansList.size() > 0) {
				for (DailyAuctionsBean dailyAuctionsBean : dailyAuctionsBeansList) {
					logger.info("DailyAuctionID"+" "+dailyAuctionsBean.getDailyAuctionsId());
					/*System.out.println("#25"+"DailyAuctionID"+" "+dailyAuctionsBean.getDailyAuctionsId());*/
					boolean sellerWithStatusSettling=false;
					Integer count = auctionSellersService.getCountByDailyAuctionsId(dailyAuctionsBean.getDailyAuctionsId());
					if (null == count || count == 0) {
						dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(ENUM_AuctionStatusCodes.EXPIRED.getStatus(), dailyAuctionsBean.getDailyAuctionsId());
					} else {						
						//dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(ENUM_AuctionStatusCodes.SETTLING.getStatus(), dailyAuctionsBean.getDailyAuctionsId());
						// Retrieving auction seller offers list
						auctionSellersBeanList = auctionSellersService.findByDailyAuctionsDailyAuctionsIdAndSellerOfferStatus(dailyAuctionsBean.getDailyAuctionsId(),
								ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
						if (null != auctionSellersBeanList && auctionSellersBeanList.size() > 0) {
							int tempcounter=0;
							for (AuctionSellersBean auctionSellersBean : auctionSellersBeanList) {
								logger.info("DailyAuction In Seller Id"+"  "+auctionSellersBean.getAuctionSellersId()+ "  "+"Seller Auccount Id"+auctionSellersBean.getAccountProfileBean().getAccountId()+"  "+" Auction Seller Full Name"+auctionSellersBean.getAccountProfileBean().getFullName());
								/*System.out.println("#25"+"DailyAuction In Seller Id"+"  "+auctionSellersBean.getAuctionSellersId()+ "  "+"Seller Auccount Id"+auctionSellersBean.getAccountProfileBean().getAccountId()+"  "+" Auction Seller Full Name"+auctionSellersBean.getAccountProfileBean().getFullName());*/
								count = auctionBuyersService.getCountByAuctionSellersId(auctionSellersBean.getAuctionSellersId());
								if (null == count || count == 0) {
									auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.EXPIRED.getStatus(),
											auctionSellersBean.getAuctionSellersId(),InternetTiming.getInternetDateTime());
									 logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.EXPIRED);
										logger.info(" auctionSeller EXPIRED1");
								} else {
									
									// Retrieving auction buyer bids list /*auctionBuyersBeanList =
									/** Change for issue jan19-101 */
									/*auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus(),
											auctionSellersBean.getAuctionSellersId());
									*/
									auctionBuyersBeanList = auctionBuyersService.findByAuctionSellersAuctionSellersIdAndBuyerBidStatus(auctionSellersBean.getAuctionSellersId(),
											ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus());
									Integer buyersWithStatusSettling = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(), ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus());
									Integer buyersWithStatusFinished = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(), ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus());
									if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
										boolean anyBuyerWon=false;
										/** Change for 01-01-2019 */
										if ( buyersWithStatusSettling != null && buyersWithStatusSettling != 0) {
											anyBuyerWon = true;
										}
										/** Change for 01-01-2019 end */
										for (AuctionBuyersBean auctionBuyersBean : auctionBuyersBeanList) {
											logger.info("DailyAuction In Buyer Id"+"  "+auctionBuyersBean.getAuctionBuyersId()+ "  "+"Buyer Auccount Id"+auctionBuyersBean.getAccountProfileBean().getAccountId()+"  "+" Auction Buyer Full Name"+auctionBuyersBean.getAccountProfileBean().getFullName());
										/*	System.out.println("#25"+"DailyAuction In Buyer Id"+"  "+auctionBuyersBean.getAuctionBuyersId()+ "  "+"Buyer Auccount Id"+auctionBuyersBean.getAccountProfileBean().getAccountId()+"  "+" Auction Buyer Full Name"+auctionBuyersBean.getAccountProfileBean().getFullName());*/
											auctionBuyersBean = auctionBuyersService.findById(auctionBuyersBean.getAuctionBuyersId());
											AuctionBidResult auctionBidResult=OrderExecutionLevel1Mock.verifySingleBuyer(auctionBuyersBean);
											if(auctionBidResult.isResult()) {
												anyBuyerWon=false;
												tempcounter=tempcounter+0;
												/*auctionBuyersService.updateBidOfferStatusByAuctionBuyersId(ENUM_AuctionBuyerBidStatusCodes.INPROGRESS.getStatus(),
														auctionBuyersBean.getAuctionBuyersId());*/
												/** MileStone9_Comments_ـFeb11_2019 issues NO#7  */
												logger.info("======================================================== ");
											/*	System.out.println("#25"+"Buyer End Auction Buyer BId Method call "+auctionBidResult.getAuctionBuyersBean().getAuctionBuyersId());*/
												logger.info("Buyer End Auction Buyer BId Method call "+auctionBidResult.getAuctionBuyersBean().getAuctionBuyersId());
												auctionBuyersBean.setExecutedPrice(0.0f);
												auctionBuyersBean.setExecutedQuantity(0);
												auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
											
												logger.info("Buyer End Auction Buyer BId Method End "+auctionBidResult.getAuctionBuyersBean().getAuctionBuyersId());
												/*System.out.println("#25"+"Buyer End Auction Buyer BId Method call "+auctionBidResult.getAuctionBuyersBean().getAuctionBuyersId());*/
												logger.info("======================End================================== ");
											}else {
												/** MileStone9_Comments_ـFeb11_2019 issues NO#7  */
												logger.info("======================================================== ");
											/*	System.out.println("Buyer End Auction Buyer BId Method call "+auctionBuyersBean.getAuctionBuyersId());*/
												logger.info("Buyer End Auction Buyer BId Method call "+auctionBuyersBean.getAuctionBuyersId());
												auctionBuyersBean.setExecutedPrice(0.0f);
												auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
												logger.info("Buyer End Auction Buyer BId Method End "+auctionBuyersBean.getAuctionBuyersId());
												/*System.out.println("#25"+"Buyer End Auction Buyer BId Method End "+auctionBuyersBean.getAuctionBuyersId());*/
												logger.info("======================End================================== ");
											}
										}
										if(anyBuyerWon) {
											auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus(),
													auctionSellersBean.getAuctionSellersId());
											logger.info("auctionSeller SETTLING1");
										}else {
											/** Change for 01-01-2019  */
											//Integer buyersWithStatusFinished1 = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(), ENUM_AuctionBuyerBidStatusCodes.FINISHED.getStatus());
											if ( buyersWithStatusFinished != null && buyersWithStatusFinished != 0) {
												auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus(),
														auctionSellersBean.getAuctionSellersId(),InternetTiming.getInternetDateTime());
												auctionStatusHelper.endAuctionSellerOffer(auctionSellersBean);
												logger.info("auctionSeller FINISHED1");
												/** Change for 01-01-2019 end */
											}else {
											auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.EXPIRED.getStatus(),
													auctionSellersBean.getAuctionSellersId(),InternetTiming.getInternetDateTime());
											 logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.EXPIRED);
											 logger.info("auctionSeller EXPIRED2");
											}
										}
										if(tempcounter>0) {
											sellerWithStatusSettling=true;
										}
									}if ( buyersWithStatusSettling != null && buyersWithStatusSettling != 0) {
										auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus(),
												auctionSellersBean.getAuctionSellersId());
										 logHelper.SellerLogForRemainingQuantity(auctionSellersBean, auctionSellersBean.getAvailableQuantity());
										 logger.info("auctionSeller SETTLING2");
									}else if ( buyersWithStatusFinished != null && buyersWithStatusFinished != 0) {
										auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus(),
												auctionSellersBean.getAuctionSellersId(),InternetTiming.getInternetDateTime());
										auctionStatusHelper.endAuctionSellerOffer(auctionSellersBean);
										logHelper.SellerLogForRemainingQuantity(auctionSellersBean, auctionSellersBean.getAvailableQuantity());
										 logger.info("auctionSeller FINISHED2");
									}else {
										auctionSellersService.updateSellerOfferStatusByAuctionSellersId(ENUM_AuctionSellerOfferStatusCodes.EXPIRED.getStatus(),
												auctionSellersBean.getAuctionSellersId(),InternetTiming.getInternetDateTime());
										// logHelper.sellerLog(auctionSellersBean, ENUM_OfferOperationCodes.EXPIRED);
										 logger.info("auctionSeller EXPIRED3");
									}
									
								}
							}
							if(sellerWithStatusSettling) {
								dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(ENUM_AuctionStatusCodes.SETTLING.getStatus(), dailyAuctionsBean.getDailyAuctionsId());
								 logger.info("sellerWithStatusSettlingupdateAuctionStatusBasedOnDailyAuctionsId");
							}else {
								Short[] sellerStatusInList = { ENUM_AuctionStatusCodes.SETTLING.getStatus() };
								Integer settlingSellerCount = auctionSellersService.countDailyAuctionSellerOfferStatusIn(dailyAuctionsBean.getDailyAuctionsId(),
										sellerStatusInList);
								 logger.info("settlingSellerCount");
								if(DateHelper.getRemainingTimeToEnd(dailyAuctionsBean.getEndTime())<= 0 && settlingSellerCount == 0) {
									dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(ENUM_AuctionStatusCodes.FINISHED.getStatus(), dailyAuctionsBean.getDailyAuctionsId());
									 logger.info(" if updateAuctionStatusBasedOnDailyAuctionsId FINISHED 1");
								}else {
									dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(
											ENUM_AuctionStatusCodes.SETTLING.getStatus(), dailyAuctionsBean.getDailyAuctionsId());	
									logger.info("else updateAuctionStatusBasedOnDailyAuctionsId SETTLING");
								}
							}
						} else {
							Short[] sellerStatusInList = { ENUM_AuctionStatusCodes.SETTLING.getStatus() };
							Integer settlingSellerCount = auctionSellersService.countDailyAuctionSellerOfferStatusIn(dailyAuctionsBean.getDailyAuctionsId(),
									sellerStatusInList);
							if (settlingSellerCount > 0) {
							dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(
									ENUM_AuctionStatusCodes.SETTLING.getStatus(), dailyAuctionsBean.getDailyAuctionsId());		
						     	logger.info(" if updateAuctionStatusBasedOnDailyAuctionsId SETTLING2");
							}				
							auctionStatusHelper.endDailyAuctionByDailyAuctionId(dailyAuctionsBean.getDailyAuctionsId());
						}
					}
					 simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", dailyAuctionsBean.getDailyAuctionsId());
				}
			}

			
/**  changes code 02-01-2018  issue no 22  */
			//auctionSettlementPhase.settelOffersAndBids(date);

			logger.trace(getClass().getSimpleName() + " for " + date + " Ended... at " + new Date(System.currentTimeMillis()));
			logger.info("DailyAuctionFinishedJob" +" "+" for " + date + " Ended... at " + new Date(System.currentTimeMillis()));
			logger.info("#25 End");
			//simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}finally {
			/*simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionStatusUpdated");*/
		}
	}
}