package com.auction.controller.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.auction.model.bean.AuctionBuyersBean;

public class OrderExecutionLevel1Mock {

	
 
	private static boolean verifyBuyerExecution(boolean buyerPartialAllowed,int buyerMinQty,float buyerPrice,int sellerQty,float sellerPrice) {
		boolean passMinQtyCheck=verifyBuyerMinimumQty(buyerMinQty,sellerQty);
		boolean passBuyerPriceCheck=verifyBuyerPrice(buyerPrice,sellerPrice);
		if(buyerPartialAllowed && passMinQtyCheck && passBuyerPriceCheck ) {
			return true;
		}
		return false;
	}
	private static boolean verifyBuyerExecution(boolean buyerPartialAllowed,int buyerQty,int buyerMinQty,float buyerPrice,boolean sellerPartialAllowed,int sellerQty,float sellerPrice) {
		boolean passMinQtyCheck=verifyBuyerMinimumQty(buyerMinQty,sellerQty);
		boolean passBuyerPriceCheck=verifyBuyerPrice(buyerPrice,sellerPrice);
		boolean passBuyerQtyCheck=verifyBuyerQty(buyerQty, sellerQty);
		if(sellerPartialAllowed) {
			if(buyerPartialAllowed && passBuyerQtyCheck &&  passMinQtyCheck && passBuyerPriceCheck ) {
				return true;
			}
		}else {
			if(passBuyerQtyCheck && passMinQtyCheck && passBuyerPriceCheck ) {
				return true;
			}
		}
		return false;
	}
	private static boolean verifyBuyerExecution(boolean buyerPartialAllowed,int buyerMinQty,int sellerQty) {
		boolean passMinQtyCheck=verifyBuyerMinimumQty(buyerMinQty,sellerQty);		
		if(buyerPartialAllowed && passMinQtyCheck) {
			return true;
		}
		return false;
	}
	private static boolean verifyBuyerMinimumQty(int buyerMinQty,int sellerQty) {
		if(sellerQty >= buyerMinQty) {
			return true;
		}
		return false;
	}
	private static boolean verifyBuyerQty(boolean buyerPartialAllowed,int buyerQty,int sellerQty) {
		if(buyerPartialAllowed && buyerQty >= sellerQty) {
			return true;
		}
		return false;
	}
	private static boolean verifyBuyerQty(int buyerQty,int sellerQty) {
		if(buyerQty >= sellerQty) {
			return true;
		}
		return false;
	}
	private static boolean verifyBuyerPrice(float buyerPrice,float sellerPrice) {
		if(buyerPrice >= sellerPrice) {
			return true;
		}
		return false;
	}
 
	private static int partialAllocateQtyToBuyer(List<AuctionBuyersBean> selectedBuyersList,int sellerQty,boolean SELLER_PARTIAL_ALLOWED) {
		int remaningSellerQty=sellerQty;
		for (Iterator iterator = selectedBuyersList.iterator(); iterator.hasNext();) {
			AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
			if(remaningSellerQty>0) {
				if(remaningSellerQty >=auctionBuyersBean.getBidQuantity()) {
					System.out.println(auctionBuyersBean.getBidQuantity() +" Assign To " + auctionBuyersBean.getAuctionBuyersId() );
					remaningSellerQty=remaningSellerQty-auctionBuyersBean.getBidQuantity();
				}else {
					if(SELLER_PARTIAL_ALLOWED && auctionBuyersBean.getPartialAllowed()) {
						System.out.println(remaningSellerQty +" Assign To " + auctionBuyersBean.getAuctionBuyersId() );
						remaningSellerQty=auctionBuyersBean.getBidQuantity()-remaningSellerQty;
						System.out.println(remaningSellerQty +" Pending For Buyer " + auctionBuyersBean.getAuctionBuyersId() );
						remaningSellerQty=0;
					}
				}
			}			
		}		
		return remaningSellerQty;		
	}
	
	public static void main(String[] args) {
	
		boolean SELLER_PARTIAL_ALLOWED =true;
		float SELLER_OFFER_PRICE=0.0f;
		boolean SELLER_HIGHEST_PRICE=true;
		int SELLER_QTY=1000;		 
		List<AuctionBuyersBean> auctionBuyersBeans = new ArrayList<AuctionBuyersBean>();
		AuctionBuyersBean bid1 = new AuctionBuyersBean();
		bid1.setAuctionBuyersId(1);
		bid1.setPartialAllowed(true);
		bid1.setMinimumQuantity(200);
		bid1.setBidPrice(20f);
		bid1.setBidQuantity(600);
		auctionBuyersBeans.add(bid1);
		AuctionBuyersBean bid2 = new AuctionBuyersBean();
		bid2.setAuctionBuyersId(2);
		bid2.setPartialAllowed(true);
		bid2.setMinimumQuantity(150);
		bid2.setBidPrice(19f);
		bid2.setBidQuantity(300);
		auctionBuyersBeans.add(bid2);
		AuctionBuyersBean bid3 = new AuctionBuyersBean();
		bid3.setAuctionBuyersId(3);
		bid3.setPartialAllowed(false);	 
		bid3.setBidPrice(19f);
		bid3.setBidQuantity(300);
	 	auctionBuyersBeans.add(bid3);
		AuctionBuyersBean bid4 = new AuctionBuyersBean();
		bid4.setAuctionBuyersId(4);
		bid4.setPartialAllowed(true);
		bid4.setMinimumQuantity(0);
		bid4.setBidPrice(18.50f);
		bid4.setBidQuantity(400);
	 	 auctionBuyersBeans.add(bid4);
		Map<Integer,Float> bidExecutionCandidates=new HashMap<Integer,Float>();
		List<AuctionBuyersBean> selectedBuyersList = new ArrayList<AuctionBuyersBean>();
		if(SELLER_OFFER_PRICE > 0.0) {
			SELLER_HIGHEST_PRICE=false;
		}		
		if(SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				boolean result=OrderExecutionLevel1Mock.verifyBuyerExecution(auctionBuyersBean.getPartialAllowed(), auctionBuyersBean.getMinimumQuantity(),SELLER_QTY);
				System.out.println("Case 1 [Seller Partial Allowed with Highest Price]:" + result);
				if(result) { 
					bidExecutionCandidates.put(auctionBuyersBean.getAuctionBuyersId(),auctionBuyersBean.getBidPrice());
					selectedBuyersList.add(auctionBuyersBean);
				}
			}
			
		}else if(!SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				boolean result=OrderExecutionLevel1Mock.verifyBuyerQty(auctionBuyersBean.getPartialAllowed(), auctionBuyersBean.getMinimumQuantity(),SELLER_QTY);
				System.out.println("Case 2[Partial Not Allowed with Highest Price]:" + result);
				if(result) { 
					bidExecutionCandidates.put(auctionBuyersBean.getAuctionBuyersId(),auctionBuyersBean.getBidPrice());
					selectedBuyersList.add(auctionBuyersBean);
				}
			}
		}else if(SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {			
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				boolean result=OrderExecutionLevel1Mock.verifyBuyerExecution(
						auctionBuyersBean.getPartialAllowed(), 
						auctionBuyersBean.getMinimumQuantity(),
						auctionBuyersBean.getBidPrice(),
						SELLER_QTY,
						SELLER_OFFER_PRICE
						);
				System.out.println("Case 3[Partial Allowed with Limit Price]:" + result);
				if(result) { 
					bidExecutionCandidates.put(auctionBuyersBean.getAuctionBuyersId(),auctionBuyersBean.getBidPrice());
					selectedBuyersList.add(auctionBuyersBean);
				}
			}
		} else if(!SELLER_PARTIAL_ALLOWED && !SELLER_HIGHEST_PRICE) {			
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				boolean result=OrderExecutionLevel1Mock.verifyBuyerExecution(
						auctionBuyersBean.getPartialAllowed(),
						auctionBuyersBean.getBidQuantity(),
						auctionBuyersBean.getMinimumQuantity(),
						auctionBuyersBean.getBidPrice(),						
						SELLER_PARTIAL_ALLOWED,
						SELLER_QTY,
						SELLER_OFFER_PRICE
						);
				System.out.println("Case 4[Partial Not Allowed with Limit Price]:" + result);
				if(result) { 
					bidExecutionCandidates.put(auctionBuyersBean.getAuctionBuyersId(),auctionBuyersBean.getBidPrice());
					selectedBuyersList.add(auctionBuyersBean);
				}
			}
		} 
		Collections.sort(selectedBuyersList,new Comparator<AuctionBuyersBean>() {
			@Override
			public int compare(AuctionBuyersBean o1, AuctionBuyersBean o2) {
				// TODO Auto-generated method stub
				 return (int) (o2.getBidPrice() - o1.getBidPrice());
			}
		}); 	
		int s=partialAllocateQtyToBuyer(selectedBuyersList,SELLER_QTY,SELLER_PARTIAL_ALLOWED);
		System.out.println(s);		
		
	}
}
