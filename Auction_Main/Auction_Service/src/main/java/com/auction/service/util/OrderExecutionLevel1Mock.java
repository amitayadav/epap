package com.auction.service.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import com.auction.commons.enums.ENUM_AuctionSellerOfferStatusCodes;
import com.auction.commons.util.AuctionLogger;
import com.auction.model.bean.AuctionBuyersBean;
import com.auction.model.bean.AuctionSellersBean;
import com.auction.model.views.AuctionBuyersBidsView;

public class OrderExecutionLevel1Mock {

	private static AuctionLogger logger = new AuctionLogger(OrderExecutionLevel1Mock.class);
	private static int IS_EQUAL_AND_LESS = 0;
	private static int IS_EQUAL_AND_MORE = 1;

	private static boolean verifyBuyerExecution(boolean buyerPartialAllowed, int buyerQty, int buyerMinQty, float buyerPrice, boolean sellerPartialAllowed, int sellerQty,
			float sellerPrice) {
		boolean passMinQtyCheck = verifyBuyerMinimumQty(buyerMinQty, sellerQty);
		boolean passBuyerPriceCheck = verifyBuyerPrice(buyerPrice, sellerPrice, IS_EQUAL_AND_MORE);

		if (sellerPartialAllowed) {
			boolean passBuyerQtyCheck = verifyBuyerQty(buyerQty, sellerQty, IS_EQUAL_AND_LESS);
			boolean passBuyerQtyMoreCheck = verifyBuyerQty(buyerQty, sellerQty, IS_EQUAL_AND_MORE);
			if (buyerPartialAllowed && passBuyerQtyCheck && passMinQtyCheck && passBuyerPriceCheck) {
				return true;
			} else if (buyerPartialAllowed && !passBuyerQtyCheck && passBuyerQtyMoreCheck && passMinQtyCheck && passBuyerPriceCheck) {
				return true;
			} else if (!buyerPartialAllowed && passBuyerQtyCheck && passMinQtyCheck && passBuyerPriceCheck) {
				return true;
			}
		} else {
			// boolean passBuyerMoreQtyCheck=verifyBuyerQty(buyerQty,
			// sellerQty,IS_EQUAL_AND_MORE);
			// boolean passBuyerQtyCheck=verifyBuyerQty(buyerQty,
			// sellerQty,IS_EQUAL_AND_LESS);
			if (passBuyerPriceCheck) {
				return true;
			}
		}
		return false;
	}

	private static boolean verifyBuyerMinimumQty(int buyerMinQty, int sellerQty) {
		if (sellerQty >= buyerMinQty) {
			return true;
		}
		return false;
	}

	private static boolean verifyBuyerQty(int buyerQty, int sellerQty, int checkType) {
		if (checkType == IS_EQUAL_AND_LESS) {
			if (buyerQty <= sellerQty) {
				return true;
			}
			return false;
		} else {
			if (buyerQty >= sellerQty) {
				return true;
			}
			return false;
		}
	}

	private static boolean verifyBuyerPrice(float buyerPrice, float sellerPrice, int checkType) {
		if (checkType == IS_EQUAL_AND_MORE) {
			if (buyerPrice >= sellerPrice) {
				return true;
			}
			return false;
		} else {
			if (buyerPrice <= sellerPrice) {
				return true;
			}
			return false;
		}

	}

	private static AuctionOfferResult partialAllocateQtyToBuyer(AuctionSellersBean auctionSellersBean, List<AuctionBuyersBean> selectedBuyersList, int sellerQty,
			boolean SELLER_PARTIAL_ALLOWED) {
		int remaningSellerQty = sellerQty;
		List<AuctionBuyersBean> executedBuyersList = new ArrayList<AuctionBuyersBean>();
		List<AuctionBuyersBean> nonExecutedBuyersList = new ArrayList<AuctionBuyersBean>();
		for (Iterator iterator = selectedBuyersList.iterator(); iterator.hasNext();) {
			AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
			if (remaningSellerQty == 0) {
				auctionBuyersBean.getAuctionSellersBean().setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus());
			}
			if (remaningSellerQty > 0) {
				boolean passMinQtyCheck = verifyBuyerMinimumQty(auctionBuyersBean.getMinimumQuantity(), remaningSellerQty);
				boolean passQtyCheck = verifyBuyerQty(auctionBuyersBean.getBidQuantity(), remaningSellerQty, IS_EQUAL_AND_MORE);
				if (remaningSellerQty >= auctionBuyersBean.getBidQuantity()) {
					logger.info(auctionBuyersBean.getBidQuantity() + " Assign To " + auctionBuyersBean.getAuctionBuyersId());
					remaningSellerQty = remaningSellerQty - auctionBuyersBean.getBidQuantity();
					auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					executedBuyersList.add(auctionBuyersBean);
				} else if (!SELLER_PARTIAL_ALLOWED && passQtyCheck && auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
					/*
					 * auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					 * System.out.println(remaningSellerQty +" Assign To " +
					 * auctionBuyersBean.getAuctionBuyersId() );
					 * remaningSellerQty=auctionBuyersBean.getBidQuantity()-remaningSellerQty;
					 * System.out.println(remaningSellerQty +" Pending For Buyer " +
					 * auctionBuyersBean.getAuctionBuyersId() ); remaningSellerQty=0;
					 */
					if (remaningSellerQty >= auctionBuyersBean.getBidQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					} else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
					}
					remaningSellerQty = auctionBuyersBean.getBidQuantity() - remaningSellerQty;
					remaningSellerQty = 0;
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					executedBuyersList.add(auctionBuyersBean);
				} else if (SELLER_PARTIAL_ALLOWED && auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
					/*
					 * auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					 * System.out.println(remaningSellerQty +" Assign To " +
					 * auctionBuyersBean.getAuctionBuyersId() );
					 * remaningSellerQty=auctionBuyersBean.getBidQuantity()- remaningSellerQty;
					 * System.out.println(remaningSellerQty +" Pending For Buyer " +
					 * auctionBuyersBean.getAuctionBuyersId() ); remaningSellerQty=0;
					 */
					if (remaningSellerQty >= auctionBuyersBean.getBidQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
						remaningSellerQty = auctionBuyersBean.getBidQuantity() - remaningSellerQty;
					} else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
						remaningSellerQty = 0;
					}
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					executedBuyersList.add(auctionBuyersBean);

				} else {
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					nonExecutedBuyersList.add(auctionBuyersBean);
				}

			} else {
				auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
				nonExecutedBuyersList.add(auctionBuyersBean);

			}

		}
		auctionSellersBean.setAvailableQuantity(remaningSellerQty);

		return new AuctionOfferResult(auctionSellersBean, executedBuyersList, nonExecutedBuyersList);
	}

	private static AuctionBuyersBean allocateQtyToSingleBuyer(AuctionBuyersBean auctionBuyersBean) {
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
		int remaningSellerQty = auctionSellersBean.getAvailableQuantity();
		if (remaningSellerQty > 0) {
			boolean passMinQtyCheck = verifyBuyerMinimumQty(auctionBuyersBean.getMinimumQuantity(), remaningSellerQty);
			boolean passQtyCheck = verifyBuyerQty(auctionBuyersBean.getBidQuantity(), remaningSellerQty, IS_EQUAL_AND_MORE);
			if (remaningSellerQty >= auctionBuyersBean.getBidQuantity()) {
				logger.info(auctionBuyersBean.getBidQuantity() + " Assign To " + auctionBuyersBean.getAuctionBuyersId());
				remaningSellerQty = remaningSellerQty - auctionBuyersBean.getBidQuantity();
				auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
			} else if (!auctionSellersBean.getPartialAllowed() && passQtyCheck && auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
				auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
				logger.info(remaningSellerQty + " Assign To " + auctionBuyersBean.getAuctionBuyersId());
				remaningSellerQty = auctionBuyersBean.getBidQuantity() - remaningSellerQty;
				logger.info(remaningSellerQty + " Pending For Buyer " + auctionBuyersBean.getAuctionBuyersId());
				remaningSellerQty = 0;
			} else if (auctionSellersBean.getPartialAllowed() && auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
				if (remaningSellerQty >= auctionBuyersBean.getBidQuantity()) {
					auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					remaningSellerQty = auctionBuyersBean.getBidQuantity() - remaningSellerQty;
				} else {
					auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
					remaningSellerQty = 0;
				}
			}
		}
		auctionSellersBean.setAvailableQuantity(remaningSellerQty);
		auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
		return auctionBuyersBean;
	}

	public static AuctionOfferResult verifyOffer(AuctionSellersBean auctionSellersBean, List<AuctionBuyersBean> auctionBuyersBeans) {
		boolean isOfferQtyMatch = false;
		boolean SELLER_PARTIAL_ALLOWED = auctionSellersBean.getPartialAllowed();
		float SELLER_OFFER_PRICE = auctionSellersBean.getOfferPrice();
		boolean SELLER_HIGHEST_PRICE = true;
		int SELLER_QTY = auctionSellersBean.getAvailableQuantity();
		List<AuctionBuyersBean> selectedBuyersList = new ArrayList<AuctionBuyersBean>();
		List<AuctionBuyersBean> rejectedBuyersList = new ArrayList<AuctionBuyersBean>();
		List<AuctionBuyersBean> executedQuntityBuyersList = new ArrayList<AuctionBuyersBean>();
		if (SELLER_OFFER_PRICE > 0.0) {
			SELLER_HIGHEST_PRICE = false;
		}
		/*
		 * if(SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) { for (Iterator iterator =
		 * auctionBuyersBeans.iterator(); iterator.hasNext();) { AuctionBuyersBean
		 * auctionBuyersBean = (AuctionBuyersBean) iterator.next(); boolean
		 * result=OrderExecutionLevel1Mock.verifyBuyerExecution(auctionBuyersBean.
		 * getPartialAllowed(),
		 * auctionBuyersBean.getMinimumQuantity(),auctionBuyersBean.getBidQuantity(),
		 * SELLER_QTY);
		 * System.out.println("Case 1 [Seller Partial Allowed with Highest Price]:" +
		 * result); if(result) { selectedBuyersList.add(auctionBuyersBean); }else {
		 * auctionBuyersBean.setExecutedPrice(0.0f);
		 * rejectedBuyersList.add(auctionBuyersBean); } }
		 * 
		 * }else if(!SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
		 * 
		 * //It may be at database level Collections.sort(auctionBuyersBeans,new
		 * Comparator<AuctionBuyersBean>() {
		 * 
		 * @Override public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) { //
		 * TODO Auto-generated method stub return (int) (o2.getBidPrice() -
		 * o1.getBidPrice()); } }); for (Iterator iterator =
		 * auctionBuyersBeans.iterator(); iterator.hasNext();) { AuctionBuyersBean
		 * auctionBuyersBean = (AuctionBuyersBean) iterator.next(); boolean
		 * result=OrderExecutionLevel1Mock.verifyBuyerQty(auctionBuyersBean.
		 * getBidQuantity(),SELLER_QTY,IS_EQUAL_AND_LESS);
		 * System.out.println("Case 2[Partial Not Allowed with Highest Price]:" +
		 * result); if(result) { selectedBuyersList.add(auctionBuyersBean); }else {
		 * auctionBuyersBean.setExecutedPrice(0.0f);
		 * rejectedBuyersList.add(auctionBuyersBean); } } }else
		 */
		if (SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();

				boolean result = OrderExecutionLevel1Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(), auctionBuyersBean.getBidQuantity(),
						auctionBuyersBean.getMinimumQuantity(), auctionBuyersBean.getBidPrice(), SELLER_PARTIAL_ALLOWED, SELLER_QTY, SELLER_OFFER_PRICE);
				logger.info("Case 3[Partial Allowed with Limit Price]:" + result);
				if (result) {
					selectedBuyersList.add(auctionBuyersBean);
					isOfferQtyMatch = true;
				} else {
					auctionBuyersBean.setExecutedPrice(0.0f);
					rejectedBuyersList.add(auctionBuyersBean);
				}
			}
		} else if (!SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {

			int quantitySum = 0;

			/*
			 * for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();)
			 * { AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
			 * boolean result=OrderExecutionLevel1Mock.verifyBuyerExecution(
			 * auctionBuyersBean.getPartialAllowed(), auctionBuyersBean.getBidQuantity(),
			 * auctionBuyersBean.getMinimumQuantity(), auctionBuyersBean.getBidPrice(),
			 * SELLER_PARTIAL_ALLOWED, SELLER_QTY, SELLER_OFFER_PRICE );
			 * logger.info("Case 4[Partial Not Allowed with Limit Price]:" + result);
			 * 
			 * if(result) { selectedBuyersList.add(auctionBuyersBean); } else {
			 * auctionBuyersBean.setExecutedPrice(0.0f);
			 * rejectedBuyersList.add(auctionBuyersBean); } }
			 */
			/** new code */

			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				boolean result = verifyBuyerPrice(auctionBuyersBean.getBidPrice(), SELLER_OFFER_PRICE, IS_EQUAL_AND_MORE);
				if (result) {
					selectedBuyersList.add(auctionBuyersBean);
				} else {
					auctionBuyersBean.setExecutedPrice(0.0f);
					rejectedBuyersList.add(auctionBuyersBean);
				}
			}
		}
		/** new code */
		executedQuntityBuyersList = VerifyBuyerQuntity(auctionSellersBean, selectedBuyersList);
		
		AuctionOfferResult auctionOfferResult = new AuctionOfferResult();
		if (!SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {
			auctionOfferResult = partialAllocateQtyToBuyer(auctionSellersBean, executedQuntityBuyersList, SELLER_QTY, SELLER_PARTIAL_ALLOWED);
			rejectedBuyersList.addAll(rejectedBuyersList(selectedBuyersList,executedQuntityBuyersList));
			auctionOfferResult.setRejectedBuyersBeans(rejectedBuyersList);
		} else {
			// It may be at database level
			Collections.sort(selectedBuyersList, new Comparator<AuctionBuyersBean>() {
				@Override
				public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) {
					// TODO Auto-generated method stub
					return (int) (o2.getBidPrice() - o1.getBidPrice());
				}
			});
			
			auctionOfferResult = partialAllocateQtyToBuyer(auctionSellersBean, selectedBuyersList, SELLER_QTY, SELLER_PARTIAL_ALLOWED);
			List<AuctionBuyersBean> aList = auctionOfferResult.getRejectedBuyersBeans();
			aList.addAll(rejectedBuyersList);
			auctionOfferResult.setRejectedBuyersBeans(aList);
		}

		
		if (auctionOfferResult.getAuctionSellersBean().getAvailableQuantity() == 0) {
			isOfferQtyMatch = true;
		}
		auctionOfferResult.setSellerQtyStatified(isOfferQtyMatch);
		return auctionOfferResult;

	}

	public static AuctionBidResult verifySingleBuyer(AuctionBuyersBean auctionBuyersBean) {
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
		boolean SELLER_PARTIAL_ALLOWED = auctionSellersBean.getPartialAllowed();
		float SELLER_OFFER_PRICE = auctionSellersBean.getOfferPrice();
		boolean SELLER_HIGHEST_PRICE = true;
		int SELLER_QTY = auctionSellersBean.getAvailableQuantity();
		if (SELLER_OFFER_PRICE > 0.0) {
			SELLER_HIGHEST_PRICE = false;
		}
		boolean result = true;
		if (SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {
			result = OrderExecutionLevel1Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(), auctionBuyersBean.getBidQuantity(),
					auctionBuyersBean.getMinimumQuantity(), auctionBuyersBean.getBidPrice(), SELLER_PARTIAL_ALLOWED, SELLER_QTY, SELLER_OFFER_PRICE);
			logger.info("Case 3[Partial Allowed with Limit Price]:" + result);
		} else if (!SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {
			result = OrderExecutionLevel1Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(), auctionBuyersBean.getBidQuantity(),
					auctionBuyersBean.getMinimumQuantity(), auctionBuyersBean.getBidPrice(), SELLER_PARTIAL_ALLOWED, SELLER_QTY, SELLER_OFFER_PRICE);
			logger.info("Case 4[Partial Not Allowed with Limit Price]:" + result);
		}
		if (result) {

			auctionBuyersBean = OrderExecutionLevel1Mock.allocateQtyToSingleBuyer(auctionBuyersBean);
		}
		return new AuctionBidResult(auctionBuyersBean, result);
	}
	
	private static List<AuctionBuyersBean> rejectedBuyersList(List<AuctionBuyersBean> selectedBuyersList,List<AuctionBuyersBean> executedQuntityBuyersList) {
		selectedBuyersList.removeAll(executedQuntityBuyersList);
		return selectedBuyersList;
	}

	/** new code point no 2(issues no#106) */
	private static List<AuctionBuyersBean> VerifyBuyerQuntity(AuctionSellersBean auctionSellersBean, List<AuctionBuyersBean> auctionBuyersBean) {
		int sellerQuantity = auctionSellersBean.getAvailableQuantity();
		int buyerQuantity = 0;
		boolean result = true;
		List<AuctionBuyersBean> selectedBuyersList1 = new ArrayList<AuctionBuyersBean>();
		for (int i = 0; i <= auctionBuyersBean.size() - 1; i++) {
			if (result) {
				List<AuctionBuyersBean> selectedBuyersList = new ArrayList<AuctionBuyersBean>();
				selectedBuyersList.add(auctionBuyersBean.get(i));
				/*System.out.println("main loop " + auctionBuyersBean.get(i).getAuctionBuyersId());*/
				/*System.out.println("q " + auctionBuyersBean.get(i).getMinimumQuantity());*/
				buyerQuantity = auctionBuyersBean.get(i).getBidQuantity();
				boolean minimun = verifyBuyerMinimumQty1(auctionBuyersBean.get(i).getMinimumQuantity(), sellerQuantity);
				if (minimun) {
					boolean buyerQuantitycheck = checkBuyerQuantity(auctionBuyersBean.get(i).getBidQuantity(), sellerQuantity);
					if (!buyerQuantitycheck) {
						buyerQuantity = sellerQuantity;
					}
				}

				boolean checkBidQuentity = checkQuntity(sellerQuantity, buyerQuantity);
				if (checkBidQuentity) {
					return selectedBuyersList;
				}
				for (int j = i + 1; j <= auctionBuyersBean.size() - 1; j++) {
					/*System.out.println("sub  loop Id " + auctionBuyersBean.get(j).getAuctionBuyersId());*/
					boolean minimun1 = verifyBuyerMinimumQty1(auctionBuyersBean.get(j).getMinimumQuantity(), sellerQuantity);
					if (minimun1) {
						boolean buyerQuantitycheck = checkBuyerQuantity(auctionBuyersBean.get(j).getBidQuantity(), sellerQuantity);
						if (!buyerQuantitycheck) {
							int newsellerQuantity = sellerQuantity;
							newsellerQuantity = newsellerQuantity - buyerQuantity;
							buyerQuantity = buyerQuantity + newsellerQuantity;
						} else {
							buyerQuantity = buyerQuantity + auctionBuyersBean.get(j).getBidQuantity();
						}
					} else {
						buyerQuantity = buyerQuantity + auctionBuyersBean.get(j).getBidQuantity();
					}

					boolean checkQuantitySum = checkBuyerQuantity(buyerQuantity, sellerQuantity);
					if (checkQuantitySum) {
					/*	System.out.println("select Buyer " + auctionBuyersBean.get(j).getBidQuantity());*/
						boolean check = checkQuntity(sellerQuantity, buyerQuantity);
						selectedBuyersList.add(auctionBuyersBean.get(j));
						if (check) {
							result = false;
							return selectedBuyersList;
						}
					} else {
						buyerQuantity = 0;
						break;
					}
				}
			}

		}
		return selectedBuyersList1;

	}

	public static boolean checkQuntity(int sellerQuantity, int bidQuentity) {
		if (bidQuentity == sellerQuantity) {
			return true;
		}
		return false;

	}

	public static Integer bidQuantitySum(int Quantity, int BidQuantity) {
		return Quantity + BidQuantity;
	}

	public static boolean checkBuyerQuantity(int buyerTotlaQuaentity, int sellerQuentity) {
		if (buyerTotlaQuaentity <= sellerQuentity) {
			return true;
		}
		return false;

	}

	private static boolean verifyBuyerMinimumQty1(int buyerMinQty, int sellerQty) {
		if (sellerQty >= buyerMinQty) {
			return true;
		}
		return false;
	}

	/**
	 * this method used Sort quantity
	 * 
	 * @param auctionBuyersBidsViewList
	 * @return
	 */
	public static List<AuctionBuyersBean> sortBuyerBidQuantityt(List<AuctionBuyersBean> auctionBuyersBidsList) {
		int i = 0;
		int j = 0;
		List<AuctionBuyersBean> selectedBuyersListSortQuantity = new ArrayList<AuctionBuyersBean>();
		int lastIndex = auctionBuyersBidsList.size() - 1;
		boolean result = true;
		for (i = 0; i <= auctionBuyersBidsList.size() - 1; i++) {
			if (result) {
				List<AuctionBuyersBean> selectedBuyersList = new ArrayList<AuctionBuyersBean>();
				double bidPrice = auctionBuyersBidsList.get(i).getBidPrice();
				for (j = i; j <= auctionBuyersBidsList.size() - 1; j++) {
					if (bidPrice == auctionBuyersBidsList.get(j).getBidPrice()) {
						selectedBuyersList.add(auctionBuyersBidsList.get(j));
						if (lastIndex == j) {
							Collections.sort(selectedBuyersList, new Comparator<AuctionBuyersBean>() {
								@Override
								public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) {
									// TODO Auto-generated method stub
									return (int) (o2.getBidQuantity().compareTo(o1.getBidQuantity()));
								}
							});
							result = false;
							selectedBuyersListSortQuantity.addAll(selectedBuyersList);
							return selectedBuyersListSortQuantity;

						}
					} else {
						Collections.sort(selectedBuyersList, new Comparator<AuctionBuyersBean>() {
							@Override
							public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) {
								// TODO Auto-generated method stub
								return (int) (o2.getBidQuantity().compareTo(o1.getBidQuantity()));
							}
						});
						selectedBuyersListSortQuantity.addAll(selectedBuyersList);
						i = 0;
						j = 0;
						int listSize = selectedBuyersListSortQuantity.size();
						i = i + listSize - 1;
						j = j + listSize - 1;
						break;
					}
				}
			}
		}
		return selectedBuyersListSortQuantity;
	}

	

	public static void main(String[] args) {
		// 1.auctionSellersBean
		AuctionSellersBean auctionSellersBean = new AuctionSellersBean();
		auctionSellersBean.setPartialAllowed(false);
		auctionSellersBean.setOfferPrice(20f);
		auctionSellersBean.setOfferQuantity(600);
		auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
		auctionSellersBean.setAvailableQuantity(600);
		// 2. auctionBuyersBeanList

		List<AuctionBuyersBean> auctionBuyersBeans = new ArrayList<AuctionBuyersBean>();
		AuctionBuyersBean bid1 = new AuctionBuyersBean();
		bid1.setAuctionBuyersId(1);
		bid1.setPartialAllowed(false);
		bid1.setMinimumQuantity(500);
		bid1.setBidPrice(20.0f);
		bid1.setBidQuantity(500);
		bid1.setAuctionSellersBean(auctionSellersBean);
		bid1.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
		auctionBuyersBeans.add(bid1);
		AuctionBuyersBean bid2 = new AuctionBuyersBean();
		bid2.setAuctionBuyersId(2);
		bid2.setPartialAllowed(false);
		bid2.setMinimumQuantity(400);
		bid2.setBidPrice(20.0f);
		bid2.setBidQuantity(400);
		bid2.setAuctionSellersBean(auctionSellersBean);
		bid2.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
		auctionBuyersBeans.add(bid2);
		AuctionBuyersBean bid3 = new AuctionBuyersBean();
		bid3.setAuctionBuyersId(3);
		bid3.setPartialAllowed(true);
		bid3.setBidPrice(20.0f);
		bid3.setBidQuantity(300);
		bid1.setMinimumQuantity(300);
		bid3.setAuctionSellersBean(auctionSellersBean);
		auctionBuyersBeans.add(bid3);
		bid3.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus());
		AuctionBuyersBean bid4 = new AuctionBuyersBean();
		bid4.setAuctionBuyersId(4);
		bid4.setPartialAllowed(true);
		bid4.setMinimumQuantity(250);
		bid4.setBidPrice(20.0f);
		bid4.setBidQuantity(250);
		bid4.setAuctionSellersBean(auctionSellersBean);
		bid4.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus());
		auctionBuyersBeans.add(bid4);
		AuctionBuyersBean bid5 = new AuctionBuyersBean();
		bid5.setAuctionBuyersId(5);
		bid5.setPartialAllowed(false);
		bid5.setMinimumQuantity(225);
		bid5.setBidPrice(20.0f);
		bid5.setBidQuantity(225);
		bid5.setAuctionSellersBean(auctionSellersBean);
		bid5.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus());
		auctionBuyersBeans.add(bid5);
		AuctionBuyersBean bid6 = new AuctionBuyersBean();
		bid6.setAuctionBuyersId(6);
		bid6.setPartialAllowed(false);
		bid6.setMinimumQuantity(125);
		bid6.setBidPrice(20.0f);
		bid6.setBidQuantity(125);
		bid6.setAuctionSellersBean(auctionSellersBean);
		bid6.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.OPEN.getStatus());
		auctionBuyersBeans.add(bid6);

		List<AuctionBuyersBean> auctionBuyersBeans1 = new ArrayList<AuctionBuyersBean>();
		AuctionBuyersBean bid11 = new AuctionBuyersBean();
		bid11.setAuctionBuyersId(1);
		bid11.setPartialAllowed(false);
		bid11.setMinimumQuantity(500);
		bid11.setBidPrice(20.0f);
		bid11.setBidQuantity(500);
		bid11.setAuctionSellersBean(auctionSellersBean);
		bid11.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
		auctionBuyersBeans1.add(bid1);
		AuctionBuyersBean bid21 = new AuctionBuyersBean();
		bid21.setAuctionBuyersId(2);
		bid21.setPartialAllowed(false);
		bid21.setMinimumQuantity(400);
		bid21.setBidPrice(20.0f);
		bid21.setBidQuantity(400);
		bid21.setAuctionSellersBean(auctionSellersBean);
		bid21.setBuyerBidStatus(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
		auctionBuyersBeans1.add(bid2);
		AuctionBuyersBean bid31 = new AuctionBuyersBean();
		bid31.setAuctionBuyersId(3);
		bid31.setPartialAllowed(true);
		bid31.setBidPrice(20.0f);
		bid31.setBidQuantity(300);
		bid31.setMinimumQuantity(300);
		bid31.setAuctionSellersBean(auctionSellersBean);
		auctionBuyersBeans1.add(bid3);
		
		
				OrderExecutionLevel1Mock.rejectedBuyersList(auctionBuyersBeans, auctionBuyersBeans1);
		// Verify Offer Buyer

		// AuctionOfferResult
		// auctionOfferResult=OrderExecutionLevel1Mock.verifyOffer(auctionSellersBean,
		// auctionBuyersBeans);

		/*
		 * for (Iterator iterator =
		 * auctionOfferResult.getSelectedBuyersBeans().iterator(); iterator.hasNext();)
		 * { AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
		 * System.out.println("s-"+auctionBuyersBean.getAuctionBuyersId());
		 * System.out.println("se-"+auctionBuyersBean.getExecutedQuantity()); } for
		 * (Iterator iterator = auctionOfferResult.getRejectedBuyersBeans().iterator();
		 * iterator.hasNext();) { AuctionBuyersBean auctionBuyersBean =
		 * (AuctionBuyersBean) iterator.next();
		 * System.out.println("R-"+auctionBuyersBean.getAuctionBuyersId()); }
		 */

		// Verify Single Buyer
		AuctionBidResult result = OrderExecutionLevel1Mock.verifySingleBuyer(bid1);
		AuctionBuyersBean a1 = OrderExecutionLevel1Mock.allocateQtyToSingleBuyer(bid1);
		/*
		 * System.out.println("Single--"+result.getAuctionBuyersBean().
		 * getAuctionSellersBean().getAvailableQuantity());
		 * System.out.println("Single--"+result.getAuctionBuyersBean().
		 * getExecutedQuantity()); System.out.println("Single--"+result.isResult());
		 * System.out.println("Single--"+a1.getBidQuantity());
		 */

		// System.out.println("test--"+OrderExecutionLevel1Mock.test(75, 25, 100));
	}

}