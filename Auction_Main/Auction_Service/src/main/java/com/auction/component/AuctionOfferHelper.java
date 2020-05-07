package com.auction.component;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.auction.commons.enums.ENUM_AccountTypeCodes;
import com.auction.commons.enums.ENUM_AuctionBuyerBidStatusCodes;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.enums.ENUM_AuctionStatusCodes;
import com.auction.commons.enums.ENUM_BidOperationCodes;
import com.auction.commons.enums.ENUM_OfferOperationCodes;
import com.auction.commons.enums.ENUM_TransactionCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AccountProfileBean;
import com.auction.model.bean.AccountTypeCodesBean;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.bean.BankDetailsBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.service.AccountProfileService;
import com.auction.service.AccountTypeCodesService;
import com.auction.service.AuctionBuyersService;
import com.auction.service.AuctionSellersService;
import com.auction.service.BankDetailsService;
import com.auction.service.LoginDetailsService;
import com.auction.service.SellerLogService;
import com.auction.service.SellerTransactionsService;
import com.auction.service.util.AuctionCalculationHelper;
import com.auction.service.util.AuctionOfferResult;
import com.auction.service.util.OrderExecutionLevel1Mock;

@Component
public class AuctionOfferHelper {

	@Autowired
	private BankDetailsService bankDetailsService;

	@Autowired
	private AuctionBidHelper acceptBuyersBidHelper;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private SellerTransactionHelper sellerTransactionHelper;

	@Autowired
	private ShipperTransactionHelper shipperTransactionHelper;

	@Autowired
	private SellerBalanceHelper sellerBalanceHelper;

	@Autowired
	private ShipperBalanceHelper shipperBalanceHelper;

	@Autowired
	private AuctionPolicyProcessing auctionPolicyProcessing;
	
	@Autowired
	private LogHelper logHelper;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private AccountProfileService accountProfileService;

	@Autowired
	private AccountTypeCodesService accountTypeCodesService;

	@Autowired
	private AuctionStatusHelper auctionStatusHelper;

	@Autowired
	private AuctionBuyersService auctionBuyersService;

	@Autowired
	private AuctionSellersService auctionSellersService;
	
	@Autowired
	private SellerTransactionsService sellerTransactionsService;

	@Autowired
	private SellerLogService sellerLogService;

	
	private AuctionLogger logger = new AuctionLogger(getClass().getName());
	
	public void updateSellersBalance(AuctionBuyersBean auctionBuyersBean) {
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
		logger.info("=========Start =======Update Sellers Balance===============");
		logger.info("Update Sellers Balance auction Seller Id =:"+auctionSellersBean.getAuctionSellersId());
		logger.info("Update Sellers Balance Daily Auction Id =:"+auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());
		logger.info("Update Sellers Balance Seller Public  Name =:"+auctionSellersBean.getAccountProfileBean().getPublicName());
		logger.info("Update Sellers Balance Buyer Id =:"+auctionBuyersBean.getAuctionBuyersId());
		if (null != auctionSellersBean) {

			LoginDetailsBean loginUser = loginDetailsService.getLoginDetailsByAccountProfileId(auctionSellersBean.getAccountProfileBean().getAccountId());
			Character sellerAccountTypeCode = loginUser.getAccountTypeCodesBean().getAccountType();
			String tranDesc = "";
			ENUM_TransactionCodes transactionCode = ENUM_TransactionCodes.SALES;

			// Changes for shipper transactions
			String shipperTranDesc = "";

			AccountProfileBean accountProfileBean = loginUser.getAccountProfileBean();
			BankDetailsBean bankDetailsBean = null;
			if (sellerAccountTypeCode == ENUM_AccountTypeCodes.SELLER.getType()) {
				bankDetailsBean = accountProfileBean.getBankDetailsBean();
			} else if (sellerAccountTypeCode == ENUM_AccountTypeCodes.SELLER_AGENT.getType()) {
				LoginDetailsBean ownerLoginDetailsBean = loginDetailsService.findOwnerLoginDetailsByAgentLoginUserId(loginUser.getLoginUserid());
				bankDetailsBean = ownerLoginDetailsBean.getAccountProfileBean().getBankDetailsBean();
			}

			int bidQuantity = auctionBuyersBean.getBidQuantity();
			if ((auctionSellersBean.getAvailableQuantity() > 0) && (auctionBuyersBean.getBidQuantity() > auctionSellersBean.getAvailableQuantity())) {
				bidQuantity = auctionSellersBean.getAvailableQuantity();
			}
			boolean SellerShipperCount = sellerTransactionsService.countShippingChargeByAuctionSeller(auctionSellersBean.getAuctionSellersId());
			if(SellerShipperCount) {
				auctionBuyersBean.getAuctionSellersBean().setSellerShippingCharge(0.0f);
			}
			double executedPrice = AuctionCalculationHelper.calculateSellerOfferAmount(auctionBuyersBean);

			BigDecimal newAvailableBalance = null; 
			if (null != bankDetailsBean.getAvailableBalance()) {
				newAvailableBalance = new BigDecimal(bankDetailsBean.getAvailableBalance().doubleValue() + executedPrice);
			} else {
				newAvailableBalance = new BigDecimal(executedPrice);
			}

			newAvailableBalance = newAvailableBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
			bankDetailsBean.setAvailableBalance(newAvailableBalance);
			logger.info("Update Sellers newAvailableBalance =:"+newAvailableBalance);
			bankDetailsService.save(bankDetailsBean);

			// Preparing Transaction description with as per auction buyers.
			AccountTypeCodesBean buyerAccountTypeCodes = accountTypeCodesService.getAccountTypeCodeByAccountId(auctionBuyersBean.getAccountProfileBean().getAccountId());
			Character buyerAccountTypeCode = buyerAccountTypeCodes.getAccountType();
			if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER.getType())) {
				tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " to " + "(" + auctionBuyersBean.getAccountProfileBean().getPublicName() + ")");
			} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER_AGENT.getType())) {
				tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " to ("
						+ accountProfileService.findOwnerPublicNameByAgentAccountId(auctionBuyersBean.getAccountProfileBean().getAccountId()) + ") Agent ("
						+ auctionBuyersBean.getAccountProfileBean().getPublicName() + ")");
			} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER_AGENT.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER.getType())) {
				tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " by Agent " + "(" + auctionSellersBean.getAccountProfileBean().getPublicName()
						+ ") to (" + auctionBuyersBean.getAccountProfileBean().getPublicName() + ")");
			} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER_AGENT.getType()) && buyerAccountTypeCode.equals(ENUM_AccountTypeCodes.BUYER_AGENT.getType())) {
				tranDesc = (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " by Agent " + "(" + auctionSellersBean.getAccountProfileBean().getPublicName()
						+ ") to (" + accountProfileService.findOwnerPublicNameByAgentAccountId(auctionBuyersBean.getAccountProfileBean().getAccountId()) + ") Agent ("
						+ auctionBuyersBean.getAccountProfileBean().getPublicName() + ")");

			}

			// Preparing Transaction with description as per Auction Shipper
			if (null != auctionSellersBean.getShipperAccountProfileBean() && null != auctionSellersBean.getShipperAccountProfileBean().getAccountId()
					&& auctionSellersBean.getShipperAccountProfileBean().getAccountId() > 0) {
				// shipperAccpuntTypesCodeds rename with sellerShipperAccountTypeBean

				AccountTypeCodesBean shipperAccpuntTypesCodeds = accountTypeCodesService
						.getAccountTypeCodeByAccountId(auctionSellersBean.getShipperAccountProfileBean().getAccountId());

				// shipperAccpuntTypesCoded rename with sellerShipperAccountTypeCode
				Character shipperAccpuntTypesCoded = shipperAccpuntTypesCodeds.getAccountType();
				// Revenue by auctionSellersBean.getShipperAccountProfileBean().getPublicName()
				// For Shipping To

				StringBuffer shipperTransDescBuilder = new StringBuffer();

				if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER.getType()) && shipperAccpuntTypesCoded.equals(ENUM_AccountTypeCodes.SHIPPER.getType())) {
					shipperTransDescBuilder.append(ENUM_TransactionCodes.SHIPPING_REVENUE);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.FOR_SHIPPING_TO);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(auctionSellersBean.getAccountLocationsBean().getLocationName());
					shipperTransDescBuilder.append(")");
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.ONBEHALF_OF_SELLER);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append(auctionSellersBean.getAccountProfileBean().getPublicName());
					shipperTransDescBuilder.append(")");
					shipperTranDesc = shipperTransDescBuilder.toString();
					/*
					 * shipperTranDesc =
					 * (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " to " +
					 * "(" + auctionSellersBean.getShipperAccountProfileBean().getPublicName() +
					 * ")");
					 */

				} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER.getType()) && shipperAccpuntTypesCoded.equals(ENUM_AccountTypeCodes.SHIPPER_AGENT.getType())) {
					shipperTransDescBuilder.append(ENUM_TransactionCodes.SHIPPING_REVENUE);
					shipperTransDescBuilder.append(" (by agent ");
					shipperTransDescBuilder.append(auctionSellersBean.getShipperAccountProfileBean().getPublicName());
					shipperTransDescBuilder.append(") ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.FOR_SHIPPING_TO);
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(auctionSellersBean.getAccountLocationsBean().getLocationName());
					shipperTransDescBuilder.append(") ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.ONBEHALF_OF_SELLER);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(accountProfileService.findOwnerPublicNameByAgentAccountId(auctionSellersBean.getShipperAccountProfileBean().getAccountId()));
					shipperTransDescBuilder.append(")");
					shipperTranDesc = shipperTransDescBuilder.toString();

					/*
					 * shipperTranDesc =
					 * (StringUtils.capitalize(transactionCode.toString().toLowerCase()) + " to (" +
					 * accountProfileService.findOwnerPublicNameByAgentAccountId(auctionSellersBean.
					 * getShipperAccountProfileBean().getAccountId()) + ") Agent (" +
					 * auctionSellersBean.getShipperAccountProfileBean().getPublicName() + ")");
					 */
				} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER_AGENT.getType()) && shipperAccpuntTypesCoded.equals(ENUM_AccountTypeCodes.SHIPPER.getType())) {
					shipperTransDescBuilder.append(ENUM_TransactionCodes.SHIPPING_REVENUE);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.FOR_SHIPPING_TO);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(auctionSellersBean.getAccountLocationsBean().getLocationName());
					shipperTransDescBuilder.append(")");
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.ONBEHALF_OF_SELLER);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append(auctionSellersBean.getAccountProfileBean().getPublicName());
					shipperTransDescBuilder.append(")");
					shipperTranDesc = shipperTransDescBuilder.toString();

					/*
					 * shipperTranDesc =
					 * (StringUtils.capitalize(transactionCode.toString().toLowerCase()) +
					 * " by Agent " + "(" +
					 * auctionSellersBean.getShipperAccountProfileBean().getPublicName() + ")");
					 */

				} else if (sellerAccountTypeCode.equals(ENUM_AccountTypeCodes.SELLER_AGENT.getType())
						&& shipperAccpuntTypesCoded.equals(ENUM_AccountTypeCodes.SHIPPER_AGENT.getType())) {
					shipperTransDescBuilder.append(ENUM_TransactionCodes.SHIPPING_REVENUE);
					shipperTransDescBuilder.append(" (by agent ");
					shipperTransDescBuilder.append(auctionSellersBean.getShipperAccountProfileBean().getPublicName());
					shipperTransDescBuilder.append(") ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.FOR_SHIPPING_TO);
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(auctionSellersBean.getAccountLocationsBean().getLocationName());
					shipperTransDescBuilder.append(") ");
					shipperTransDescBuilder.append(ENUM_TransactionCodes.ONBEHALF_OF_SELLER);
					shipperTransDescBuilder.append(" ");
					shipperTransDescBuilder.append("(");
					shipperTransDescBuilder.append(accountProfileService.findOwnerPublicNameByAgentAccountId(auctionSellersBean.getShipperAccountProfileBean().getAccountId()));
					shipperTransDescBuilder.append(")");
					shipperTranDesc = shipperTransDescBuilder.toString();
				}
			}

			// creating seller transaction
			String comment="";
			Integer sellerTransactionId = sellerTransactionHelper.createSellersTransaction(auctionSellersBean, auctionBuyersBean);

			sellerBalanceHelper.sellerSaleCreateBankTransactionCredit(sellerTransactionId, new BigDecimal(executedPrice), accountProfileBean, tranDesc, transactionCode,comment);
		
			// after finishing bid accepting flow and transaction completion flow
			// appropriate log entry will create.
			logHelper.saveSellerLog(auctionBuyersBean, ENUM_OfferOperationCodes.EXECUTED);

			int buyersWithStatusSettling = auctionBuyersService.countAuctionBuyerByStatus(auctionSellersBean.getAuctionSellersId(),
					ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus());
			if (auctionSellersBean.getAvailableQuantity() == 0 && buyersWithStatusSettling == 0) {
				logger.info("Seller Status check auctionSellersBean.getAvailableQuantity() == 0 && buyersWithStatusSettling == 0");
				logger.info("Seller Status check"+auctionSellersBean.getAvailableQuantity() +"and "+buyersWithStatusSettling);
				auctionSellersBean = auctionStatusHelper.endAuctionSellerOffer(auctionBuyersBean);
				AuctionSellersBean tempAuctionSellersBean = auctionSellersService
						.findById(auctionSellersBean.getAuctionSellersId());	
				tempAuctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus());
				tempAuctionSellersBean.setActualEndTime(InternetTiming.getInternetDateTime());
				auctionSellersService.save(tempAuctionSellersBean);
				logger.info("Seller Status FINISHED");
			} else if (auctionSellersBean.getAvailableQuantity() != 0 && buyersWithStatusSettling == 0
					&& auctionSellersBean.getSellerOfferStatus() != ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()) {
				logger.info("Seller Status check auctionSellersBean.getAvailableQuantity() != 0 && buyersWithStatusSettling == 0\n" + 
						"					&& auctionSellersBean.getSellerOfferStatus() != ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus()");
				logger.info("Seller Status check"+auctionSellersBean.getAvailableQuantity() +"and "+buyersWithStatusSettling+"and"+auctionSellersBean.getSellerOfferStatus());
				auctionSellersBean = auctionStatusHelper.endAuctionSellerOffer(auctionBuyersBean);
				AuctionSellersBean tempAuctionSellersBean = auctionSellersService
						.findById(auctionSellersBean.getAuctionSellersId());	
				tempAuctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.FINISHED.getStatus());
				tempAuctionSellersBean.setActualEndTime(InternetTiming.getInternetDateTime());
				auctionSellersService.save(tempAuctionSellersBean);
				logger.info("Seller Status FINISHED");
			}
			if(!SellerShipperCount) {
			if (null != auctionSellersBean.getShipperAccountProfileBean() && null != auctionSellersBean.getShipperAccountProfileBean().getAccountId()
					&& auctionSellersBean.getShipperAccountProfileBean().getAccountId() > 0) {
				// creating shipper transaction
				/*
				 * Changed For Shipper and Shipper agent task in Place Offer
				 * 
				 */
				Integer shipperId = auctionSellersBean.getShipperAccountProfileBean().getAccountId();
				Integer ownerAccountId = accountProfileService.findOwnerAccountIdByAgentAccountId(shipperId);
				Integer accountId = shipperId;
				if (null != ownerAccountId) {
					accountId = ownerAccountId;
				}
				AccountProfileBean shipperAccountProfileBean = accountProfileService.findById(accountId);
				Integer shipperTransactionId = shipperTransactionHelper.createShippersTransaction(auctionBuyersBean, shipperAccountProfileBean);
				// Integer shipperTransactionId =
				// shipperTransactionHelper.createShippersTransaction(auctionBuyersBean,
				// auctionSellersBean.getShipperAccountProfileBean());

			
				/*
				 * Changed For Shipper and Shipper agent task in Place Offer
				 * 
				 */
				BigDecimal newAvailableBalanceShipper = null; 
			if (null != shipperAccountProfileBean.getBankDetailsBean().getAvailableBalance()) {
					newAvailableBalanceShipper = new BigDecimal(
							shipperAccountProfileBean.getBankDetailsBean().getAvailableBalance().doubleValue() + auctionSellersBean.getSellerShippingCharge());
				} else {
					newAvailableBalanceShipper = new BigDecimal(auctionSellersBean.getSellerShippingCharge());
				}

				newAvailableBalanceShipper = newAvailableBalanceShipper.setScale(2, BigDecimal.ROUND_HALF_UP);
				shipperAccountProfileBean.getBankDetailsBean().setAvailableBalance(newAvailableBalanceShipper);
				bankDetailsService.save(shipperAccountProfileBean.getBankDetailsBean());
				
					// Creating shipper balance
				
				shipperBalanceHelper.shipperSalescreateBankTransactionCredit(shipperTransactionId, new BigDecimal(auctionSellersBean.getSellerShippingCharge()),
						shipperAccountProfileBean, shipperTranDesc, transactionCode, auctionSellersBean.getAccountProfileBean().getAccountId(),comment);
				
			}
			}
			// Ending Daily Auction by DailyAuctionId
			Boolean isendDailyAuction= auctionStatusHelper.endDailyAuctionByDailyAuctionId(auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());

			/*
			 * else if (null != sellerOfferStatusList && sellerOfferStatusList.size() == 0)
			 * { dailyAuctionsService.updateAuctionStatusBasedOnDailyAuctionsId(
			 * ENUM_AuctionStatusCodes.EXPIRED.getStatus(),
			 * auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId()); }
			 */
			/**
			 *  "this socket used  used AuctionSellersOfferProcessed..."
			 */
		
			if(isendDailyAuction) {
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId());
			}else {
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "buyer");
			}
		
		}
	}

	public void reExecuteAuctionOffer(AuctionSellersBean auctionSellersBean) {
		logger.info("=========Start =======reExecuteAuctionOffer===============");
		AuctionSellersBean auctionSellersBeanStatus = auctionSellersService.findById(auctionSellersBean.getAuctionSellersId());
		Short dailyAuctionStatus = auctionSellersBeanStatus.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode();
		Short sellerOfferStatus = auctionSellersBeanStatus.getSellerOfferStatus();
		if ((auctionSellersBean.getOfferPrice() > 0)
				&& (dailyAuctionStatus == ENUM_AuctionStatusCodes.RUNNING.getStatus() && sellerOfferStatus == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus())) {

			List<AuctionBuyersBean> auctionBuyersBeanList = auctionBuyersService.getAuctionBuyersWithEqualOrHigherBidPriceThanOfferPrice(
					ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(), ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(),
					auctionSellersBean.getDailyAuctionsBean().getDailyAuctionsId(), auctionSellersBean.getAuctionSellersId());

			if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
				for (AuctionBuyersBean auctionBuyersBean : auctionBuyersBeanList) {
					acceptBuyersBidHelper.updateBidUserBalanceTransaction(auctionBuyersBean);
				}
				simpMessagingTemplate.convertAndSend("/wssauctions/refreshUI", "AuctionBuyerBidProcessed...");
			}
			
		}
	}

	public void validBuyerBidAndExecuteAuctionOffeAndExecuteBuyerBid(AuctionSellersBean auctionSellersBean) {
		logger.info("=========Start =======validBuyerBidAndExecuteAuctionOffeAndExecuteBuyerBid===============");
		AuctionSellersBean savedAuctionSellersBean = auctionSellersService.findById(auctionSellersBean.getAuctionSellersId());
		Short dailyAuctionStatus = savedAuctionSellersBean.getDailyAuctionsBean().getAuctionStatusCodesBean().getAuctionStatusCode();
		Short sellerOfferStatus = savedAuctionSellersBean.getSellerOfferStatus();
		if ((dailyAuctionStatus == ENUM_AuctionStatusCodes.RUNNING.getStatus() && sellerOfferStatus == ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus())) {
			List<AuctionBuyersBean> auctionBuyersBeanList =auctionBuyersService.getAuctionBuyersByStatusByAuctionSellersId(ENUM_AuctionBuyerBidStatusCodes.RUNNING.getStatus(), ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus(), ENUM_AuctionStatusCodes.RUNNING.getStatus(),savedAuctionSellersBean.getAuctionSellersId());
			if(savedAuctionSellersBean.getOfferPrice() > 0 && savedAuctionSellersBean.getPartialAllowed() !=true) {
				if(null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
					auctionBuyersBeanList =OrderExecutionLevel1Mock.sortBuyerBidQuantityt(auctionBuyersBeanList);
					if(savedAuctionSellersBean.getAvailableQuantity() !=savedAuctionSellersBean.getOfferQuantity()) {
						updateBuyerBidQunatity(savedAuctionSellersBean.getAvailableQuantity(),auctionBuyersBeanList);
					}
				}
			}
			if (null != auctionBuyersBeanList && auctionBuyersBeanList.size() > 0) {
				AuctionOfferResult auctionOfferResult = OrderExecutionLevel1Mock.verifyOffer(savedAuctionSellersBean, auctionBuyersBeanList);
				logger.info("Auction Offer Helper VerifyOffer Executed "+"  " +auctionOfferResult.isSellerQtyStatified());
				if (auctionOfferResult.isSellerQtyStatified()) {
					for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getSelectedBuyersBeans()) {
						logger.info("Executed Buyer(Setting) calling method auctionBidProcessing ");
						auctionPolicyProcessing.auctionBidProcessing(auctionBuyersBean);
						/*AuctionSellersBean tempAuctionSellersBean = auctionSellersService
									.findById(savedAuctionSellersBean.getAuctionSellersId());	
						tempAuctionSellersBean.setAvailableQuantity(auctionBuyersBean.getAuctionSellersBean().getAvailableQuantity());*/
						auctionSellersService.save(savedAuctionSellersBean);
						logger.info("Executed Buyer(Setting) Auction Seller Save"+" "+savedAuctionSellersBean.getAuctionSellersId()+" "+"Seller Available Quantity :=  "+savedAuctionSellersBean.getAvailableQuantity());
						/**this socket used AuctionBuyerBidProcessed...  
						 *  if condition  only used  socket**/
						if(savedAuctionSellersBean.getAvailableQuantity() !=0) {
							String response = "{\"auctionSellerId\":\"" + savedAuctionSellersBean.getAuctionSellersId() +  "\",\"quantity\":\"" + savedAuctionSellersBean.getAvailableQuantity() + "\",\"auctionOfferBuyerId\":\""+ auctionBuyersBean.getAuctionBuyersId()+ "\"}";
							simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferUI", response);
						}
						
						//auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(savedAuctionSellersBean.getAvailableQuantity());
						boolean SellerShipperCount = sellerLogService.countShippingChargeByAuctionSeller(savedAuctionSellersBean.getAuctionSellersId());
						if(SellerShipperCount) {
							auctionBuyersBean.getAuctionSellersBean().setSellerShippingCharge(0.0f);
						}
						logHelper.saveSellerLog(auctionBuyersBean, ENUM_OfferOperationCodes.SETTLING);
					}
					for (AuctionBuyersBean auctionBuyersBean : auctionOfferResult.getRejectedBuyersBeans()) {
						AuctionSellersBean tempAuctionSellersBean = auctionSellersService
								.findById(savedAuctionSellersBean.getAuctionSellersId());	
						if (tempAuctionSellersBean.getAvailableQuantity() == 0 ) {
							/** MileStone9_Comments_ـFeb11_2019 issues NO#7   */
							auctionBuyersBean.setExecutedPrice(0.0f);
							logger.info("Executed Buyer calling method endAuctionBuyerBid "+"  ");
						auctionBuyersBean = auctionStatusHelper.endAuctionBuyerBid(auctionBuyersBean, ENUM_AuctionBuyerBidStatusCodes.EXPIRED, 0, 0F);
						logHelper.BuyerLog(auctionBuyersBean, ENUM_BidOperationCodes.EXPIRED);
						tempAuctionSellersBean.setAvailableQuantity(auctionBuyersBean.getAuctionSellersBean().getAvailableQuantity());
						auctionSellersService.save(savedAuctionSellersBean);
						logger.info("Executed Buyer(EXPIRED) Auction Seller Save"+" "+savedAuctionSellersBean.getAuctionSellersId()+" "+"Seller Available Quantity :=  "+savedAuctionSellersBean.getAvailableQuantity());
						/**this socket used AuctionBuyerBidProcessed...  **/
						String response = "{\"auctionSellerId\":\"" + savedAuctionSellersBean.getAuctionSellersId() +  "\",\"quantity\":\"" + savedAuctionSellersBean.getAvailableQuantity() + "\"}";
						simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferUI",response);
						}
					}
					AuctionSellersBean tempAuctionSellersBean = auctionSellersService
							.findById(savedAuctionSellersBean.getAuctionSellersId());	
			
					if (tempAuctionSellersBean.getAvailableQuantity() == 0 ) {
						tempAuctionSellersBean.setSellerOfferStatus(ENUM_AuctionBuyerBidStatusCodes.SETTLING.getStatus());
						auctionSellersService.save(tempAuctionSellersBean);
						logger.info(" Auction Seller Save(Setting)"+" "+tempAuctionSellersBean.getAuctionSellersId()+" "+"Seller Available Quantity :=  "+tempAuctionSellersBean.getAvailableQuantity());
						logger.info(" "+tempAuctionSellersBean.getOfferPrice());
						/**this socket used AuctionBuyerBidProcessed...  **/
						String response = "{\"auctionSellerId\":\"" + savedAuctionSellersBean.getAuctionSellersId() +  "\",\"quantity\":\"" + savedAuctionSellersBean.getAvailableQuantity() + "\"}";
						simpMessagingTemplate.convertAndSend("/wssauctions/refreshOfferUI",response);
					}
				}
			}
		}
		
	}
	public List<AuctionBuyersBean>  updateBuyerBidQunatity(int sellerAvailableQuantity ,List<AuctionBuyersBean> auctionBuyersBeanList){
		for(AuctionBuyersBean auctionBuyerBean :auctionBuyersBeanList) {
			if(auctionBuyerBean.getBidQuantity() > sellerAvailableQuantity) {
				int bidQuantity =  Math.max(sellerAvailableQuantity, auctionBuyerBean.getMinimumQuantity());
				auctionBuyerBean.setBidQuantity(bidQuantity);
				auctionBuyersService.updateBuyerBidQuantityByAuctionBuyersId(bidQuantity,auctionBuyerBean.getAuctionBuyersId());
			}
		}
		return auctionBuyersBeanList;
	
		
	}
}
