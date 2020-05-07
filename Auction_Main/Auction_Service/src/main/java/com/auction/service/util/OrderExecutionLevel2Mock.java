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

public class OrderExecutionLevel2Mock {

	private static AuctionLogger logger = new AuctionLogger(OrderExecutionLevel2Mock.class);

	private static int IS_EQUAL_AND_LESS=0;
	private static int IS_EQUAL_AND_MORE=1;
  

	private static boolean verifyBuyerExecution(boolean buyerPartialAllowed,int buyerQty,int buyerMinQty,boolean sellerPartialAllowed,int sellerQty) {
		boolean passMinQtyCheck=verifyBuyerMinimumQty(buyerMinQty,sellerQty);
		
		if(sellerPartialAllowed) {
			boolean passBuyerQtyCheck=verifyBuyerQty(buyerQty, sellerQty,buyerMinQty,IS_EQUAL_AND_LESS);
			boolean passBuyerQtyMoreCheck=verifyBuyerQty(buyerQty, sellerQty,buyerMinQty,IS_EQUAL_AND_MORE);
			if(buyerPartialAllowed && passBuyerQtyCheck &&  passMinQtyCheck  ) {
				return true;
			}else if(buyerPartialAllowed && !passBuyerQtyCheck && passBuyerQtyMoreCheck && passMinQtyCheck ) {
				return true;
			}else if(!buyerPartialAllowed && passBuyerQtyCheck &&  passMinQtyCheck  ) {
				return true;
			}
		}else {
			boolean passBuyerQtyCheck=verifyBuyerQty(buyerQty, sellerQty,buyerMinQty,IS_EQUAL_AND_LESS);			
			if(passBuyerQtyCheck && passMinQtyCheck  ) {
				return true;
			}
		}
		return false;
	}

	private static boolean verifyBuyerMinimumQty(int buyerMinQty,int sellerQty) {
		if(sellerQty >= buyerMinQty) {
			return true;
		}
		return false;
	}
	private static boolean verifyBuyerQty(int buyerQty,int sellerQty,int minimumQuantity,int checkType) {
		if(checkType==IS_EQUAL_AND_LESS) {
			if(buyerQty <= sellerQty) {
				return true;
			}else if(minimumQuantity <=sellerQty) {
				return true;
			}
			return false;
		}else {
			if(buyerQty >= sellerQty && verifyBuyerMinimumQty(minimumQuantity,sellerQty)  ) {
				return true;
			}
			return false;
		}		
	}
	 

	private static AuctionOfferResult partialAllocateQtyToBuyer(AuctionSellersBean auctionSellersBean,List<AuctionBuyersBean> selectedBuyersList,int sellerQty,boolean SELLER_PARTIAL_ALLOWED) {
		int remaningSellerQty=sellerQty;
		List<AuctionBuyersBean> executedBuyersList = new ArrayList<AuctionBuyersBean>();
		List<AuctionBuyersBean> nonExecutedBuyersList = new ArrayList<AuctionBuyersBean>();
		for (Iterator iterator = selectedBuyersList.iterator(); iterator.hasNext();) {
			AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
			if (remaningSellerQty == 0) {
				auctionBuyersBean.getAuctionSellersBean().setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.SETTLING.getStatus());
			}
			if(remaningSellerQty>0) {
				boolean passMinQtyCheck=verifyBuyerMinimumQty(auctionBuyersBean.getMinimumQuantity(),remaningSellerQty);
				boolean passQtyCheck=verifyBuyerQty(auctionBuyersBean.getBidQuantity(),remaningSellerQty,auctionBuyersBean.getMinimumQuantity(),IS_EQUAL_AND_MORE);
				if(remaningSellerQty >=auctionBuyersBean.getBidQuantity()) {
					remaningSellerQty=remaningSellerQty-auctionBuyersBean.getBidQuantity();
					auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					executedBuyersList.add(auctionBuyersBean);
				}else if(!SELLER_PARTIAL_ALLOWED && passQtyCheck && auctionBuyersBean.getPartialAllowed()&& passMinQtyCheck) {
					if(remaningSellerQty>=auctionBuyersBean.getBidQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					}else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
					}					
					remaningSellerQty=auctionBuyersBean.getBidQuantity()-remaningSellerQty;
					remaningSellerQty=0;
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					executedBuyersList.add(auctionBuyersBean);
				}else if(SELLER_PARTIAL_ALLOWED && auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
					if(remaningSellerQty>=auctionBuyersBean.getBidQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
						remaningSellerQty=auctionBuyersBean.getBidQuantity()- remaningSellerQty;
					}else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
						remaningSellerQty=0;
					}
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
						executedBuyersList.add(auctionBuyersBean);
						
						
					
				}else {
					auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
					nonExecutedBuyersList.add(auctionBuyersBean);
				}
				
				
			}else {
				auctionBuyersBean.getAuctionSellersBean().setAvailableQuantity(remaningSellerQty);
				nonExecutedBuyersList.add(auctionBuyersBean);
				
			}
	
			
		}	
		auctionSellersBean.setAvailableQuantity(remaningSellerQty);
		
		return new AuctionOfferResult(auctionSellersBean, executedBuyersList,nonExecutedBuyersList);		
	}
	
	private static AuctionBuyersBean allocateQtyToSingleBuyer(AuctionBuyersBean  auctionBuyersBean) {        
		AuctionSellersBean auctionSellersBean =auctionBuyersBean.getAuctionSellersBean();
		int remaningSellerQty=auctionSellersBean.getAvailableQuantity(); 
			if(remaningSellerQty>0) {
				boolean passMinQtyCheck=verifyBuyerMinimumQty(auctionBuyersBean.getMinimumQuantity(),remaningSellerQty);
				boolean passQtyCheck=verifyBuyerQty(auctionBuyersBean.getBidQuantity(),remaningSellerQty,auctionBuyersBean.getMinimumQuantity(),IS_EQUAL_AND_LESS);
	
				if(remaningSellerQty >=auctionBuyersBean.getBidQuantity()) {				
					remaningSellerQty=remaningSellerQty-auctionBuyersBean.getBidQuantity();
					auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity()); 
				}else if(!auctionSellersBean.getPartialAllowed() && passQtyCheck && auctionBuyersBean.getPartialAllowed()&& passMinQtyCheck) {
					if(remaningSellerQty>=auctionBuyersBean.getBidQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
					}else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
					}					
					remaningSellerQty=auctionBuyersBean.getBidQuantity()-remaningSellerQty;
					remaningSellerQty=0;
				}else if(auctionSellersBean.getPartialAllowed() && auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
					if(remaningSellerQty>=auctionBuyersBean.getBidQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getBidQuantity());
						remaningSellerQty=auctionBuyersBean.getBidQuantity()- remaningSellerQty;
					}else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
						remaningSellerQty=0;
					}
					
						 
				} else if(!auctionSellersBean.getPartialAllowed() && !passQtyCheck &&auctionBuyersBean.getPartialAllowed() && passMinQtyCheck) {
					if(remaningSellerQty>=auctionBuyersBean.getMinimumQuantity()) {
						auctionBuyersBean.setExecutedQuantity(auctionBuyersBean.getMinimumQuantity());
						remaningSellerQty=remaningSellerQty-auctionBuyersBean.getMinimumQuantity() ;
						}else {
						auctionBuyersBean.setExecutedQuantity(remaningSellerQty);
						remaningSellerQty=0;
					}
					
						 
				} 
			}		 	
		auctionSellersBean.setAvailableQuantity(remaningSellerQty);
		auctionBuyersBean.setAuctionSellersBean(auctionSellersBean);
		return auctionBuyersBean;
	}
	
	public static AuctionOfferResult verifyOffer(AuctionSellersBean auctionSellersBean,List<AuctionBuyersBean> auctionBuyersBeans) {
		boolean isOfferQtyMatch =false;
		boolean SELLER_PARTIAL_ALLOWED =auctionSellersBean.getPartialAllowed();
		float SELLER_OFFER_PRICE=auctionSellersBean.getOfferPrice();
		boolean SELLER_HIGHEST_PRICE=true;
		int SELLER_QTY=auctionSellersBean.getAvailableQuantity();
		List<AuctionBuyersBean> selectedBuyersList = new ArrayList<AuctionBuyersBean>();
		List<AuctionBuyersBean> rejectedBuyersList = new ArrayList<AuctionBuyersBean>();
		if(SELLER_OFFER_PRICE > 0.0) {
			SELLER_HIGHEST_PRICE=false;
		}	
	
		if(SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				boolean result=OrderExecutionLevel2Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(),auctionBuyersBean.getBidQuantity(), auctionBuyersBean.getMinimumQuantity(),SELLER_PARTIAL_ALLOWED,SELLER_QTY);
				//System.out.println("Case 1 [Seller Partial Allowed with Highest Price]:" + result);
				 logger.info("Case 1 [Seller Partial Allowed with Highest Price]:" + result);
				if(result) {					
					selectedBuyersList.add(auctionBuyersBean);
				}else {
					auctionBuyersBean.setExecutedPrice(0.0f);
					rejectedBuyersList.add(auctionBuyersBean);
				}
			}
			isOfferQtyMatch=true;
			
		}else if(!SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			
			//It may be at database level 
			
			 Collections.sort(auctionBuyersBeans,new Comparator<AuctionBuyersBean>() {
				@Override
				public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) {
					// TODO Auto-generated method stub
					 return (int) (o2.getBidPrice() - o1.getBidPrice());
				}
			}); 	
			 for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				
				 AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				//boolean result=OrderExecutionLevel2Mock.verifyBuyerQty(auctionBuyersBean.getBidQuantity(),SELLER_QTY,IS_EQUAL_AND_LESS);
				 boolean result=OrderExecutionLevel2Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(),auctionBuyersBean.getBidQuantity(), auctionBuyersBean.getMinimumQuantity(),SELLER_PARTIAL_ALLOWED,SELLER_QTY);
				 //System.out.println("Case 2[Partial Not Allowed with Highest Price]:" + result);
				 logger.info("Case 2[Partial Not Allowed with Highest Price]:" + result);
				if(result) {					
					selectedBuyersList.add(auctionBuyersBean);
				}else {
					auctionBuyersBean.setExecutedPrice(0.0f);
					rejectedBuyersList.add(auctionBuyersBean);
				}
			 }
		}
		//It may be at database level
	
		 Collections.sort(selectedBuyersList,new Comparator<AuctionBuyersBean>() {
			@Override
			public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) {
				// TODO Auto-generated method stub
				 return (int) (o2.getBidPrice() - o1.getBidPrice());
			}
		}); 
		
	 	AuctionOfferResult auctionOfferResult = new AuctionOfferResult(auctionSellersBean, selectedBuyersList, rejectedBuyersList) ;
	 	if(!SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			AuctionOfferResult auctionOfferResult1 =partialAllocateQtyToBuyer(auctionSellersBean,selectedBuyersList,SELLER_QTY,SELLER_PARTIAL_ALLOWED);
			if(auctionOfferResult1.getAuctionSellersBean().getAvailableQuantity()  == 0) {
				isOfferQtyMatch=true;
			}
		}
	 
	
	auctionOfferResult.setSellerQtyStatified(isOfferQtyMatch);
		return auctionOfferResult;		
	}
	
	 
	 
	public static AuctionBidResult verifySingleBuyer(AuctionBuyersBean auctionBuyersBean) {
		AuctionSellersBean auctionSellersBean = auctionBuyersBean.getAuctionSellersBean();
		boolean SELLER_PARTIAL_ALLOWED =auctionSellersBean.getPartialAllowed();
		float SELLER_OFFER_PRICE=auctionSellersBean.getOfferPrice();
		boolean SELLER_HIGHEST_PRICE=true;
		int SELLER_QTY=auctionSellersBean.getAvailableQuantity();		 
		if(SELLER_OFFER_PRICE > 0.0) {
			SELLER_HIGHEST_PRICE=false;
		}
		boolean result = true;
		if(SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {		 
			 result=OrderExecutionLevel2Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(),auctionBuyersBean.getBidQuantity(), auctionBuyersBean.getMinimumQuantity(),SELLER_PARTIAL_ALLOWED,SELLER_QTY);
				//System.out.println("Case 1 [Seller Partial Allowed with Highest Price]:" + result);	
			 logger.info("Case 1 [Seller Partial Allowed with Highest Price]:" + result);
		}else if(!SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			 result=OrderExecutionLevel2Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(),auctionBuyersBean.getBidQuantity(), auctionBuyersBean.getMinimumQuantity(),SELLER_PARTIAL_ALLOWED,SELLER_QTY);
			//System.out.println("Case 2[Partial Not Allowed with Highest Price]:" + result);		
			 logger.info("Case 2[Partial Not Allowed with Highest Price]:" + result);
		}		 
		if(result) {		 
			auctionBuyersBean=OrderExecutionLevel2Mock.allocateQtyToSingleBuyer(auctionBuyersBean);
		}else {
			auctionBuyersBean.setExecutedQuantity(0);			
		}
		return new AuctionBidResult(auctionBuyersBean, result) ;
	}
	
	
	
	
 
	/*public static void main(String[] args) {
	
		//1.auctionSellersBean
		AuctionSellersBean auctionSellersBean = new AuctionSellersBean();
		auctionSellersBean.setPartialAllowed(false);
		auctionSellersBean.setOfferPrice(0.00f);
		auctionSellersBean.setOfferQuantity(100);
		auctionSellersBean.setSellerOfferStatus(ENUM_AuctionSellerOfferStatusCodes.RUNNING.getStatus());
		auctionSellersBean.setAvailableQuantity(100);
		//2. auctionBuyersBeanList
		
		List<AuctionBuyersBean> auctionBuyersBeans = new ArrayList<AuctionBuyersBean>();
		AuctionBuyersBean bid1 = new AuctionBuyersBean();
		bid1.setAuctionBuyersId(1);
		bid1.setPartialAllowed(false);
		bid1.setMinimumQuantity(0);
		bid1.setBidPrice(20f);
		bid1.setBidQuantity(25);
		bid1.setAuctionSellersBean(auctionSellersBean);
 	   auctionBuyersBeans.add(bid1);
		AuctionBuyersBean bid2 = new AuctionBuyersBean();
		bid2.setAuctionBuyersId(2);
		bid2.setPartialAllowed(false);
		bid2.setMinimumQuantity(0);
		bid2.setBidPrice(20f);
		bid2.setBidQuantity(100);
		bid2.setAuctionSellersBean(auctionSellersBean);
		   auctionBuyersBeans.add(bid2);
		AuctionBuyersBean bid3 = new AuctionBuyersBean();
		bid3.setAuctionBuyersId(3);
		bid3.setPartialAllowed(true);	 
		bid3.setBidPrice(20f);
		bid3.setBidQuantity(100);
		bid3.setMinimumQuantity(10);
		bid3.setAuctionSellersBean(auctionSellersBean);
	   //auctionBuyersBeans.add(bid3);
		AuctionBuyersBean bid4 = new AuctionBuyersBean();
		bid4.setAuctionBuyersId(4);
		bid4.setPartialAllowed(true);
		bid4.setMinimumQuantity(100);
		bid4.setBidPrice(18.50f);
		bid4.setBidQuantity(400);
		bid4.setAuctionSellersBean(auctionSellersBean);
	   //auctionBuyersBeans.add(bid4);
		AuctionBuyersBean bid5 = new AuctionBuyersBean();
		bid5.setAuctionBuyersId(1);
		bid5.setPartialAllowed(false);
		bid5.setMinimumQuantity(0);
		bid5.setBidPrice(20f);
		bid5.setBidQuantity(100);
		bid5.setAuctionSellersBean(auctionSellersBean);
		 //auctionBuyersBeans.add(bid5);
		
		//Verify Offer Buyer
	 
		
		AuctionBidResult result1=OrderExecutionLevel2Mock.verifySingleBuyer(bid1);
		
		AuctionOfferResult auctionOfferResult=OrderExecutionLevel2Mock.verifyOffer(auctionSellersBean, auctionBuyersBeans);
		for (Iterator iterator = auctionOfferResult.getSelectedBuyersBeans().iterator(); iterator.hasNext();) {
			AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
			//System.out.println("Executed Buyer-"+auctionBuyersBean.getAuctionBuyersId());
		 
		}
		for (Iterator iterator = auctionOfferResult.getRejectedBuyersBeans().iterator(); iterator.hasNext();) {
			AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
			//System.out.println("Rejected Buyer Id-"+auctionBuyersBean.getAuctionBuyersId());			
		}
		 
		//Verify Single Buyer
		if(auctionOfferResult.getSelectedBuyersBeans().size() > 0) { 
			AuctionBidResult result=OrderExecutionLevel2Mock.verifySingleBuyer(auctionOfferResult.getSelectedBuyersBeans().get(0));
			//System.out.println("Single Seller Available Qty --"+result.getAuctionBuyersBean().getAuctionSellersBean().getAvailableQuantity());
			//System.out.println("Single Buyer Executed Qty--"+result.getAuctionBuyersBean().getExecutedQuantity());
			//System.out.println("Single Result--"+result.isResult());
		}else {
			AuctionBidResult result=OrderExecutionLevel2Mock.verifySingleBuyer(auctionOfferResult.getRejectedBuyersBeans().get(0));
			//System.out.println("Single Seller Available Qty --"+result.getAuctionBuyersBean().getAuctionSellersBean().getAvailableQuantity());
			//System.out.println("Single Buyer Executed Qty--"+result.getAuctionBuyersBean().getExecutedQuantity());
			//System.out.println("Single Result--"+result.isResult());
		}
		
		 
		 
	}*/
}
