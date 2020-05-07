package com.auction.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.auction.model.bean.AuctionBuyersBean;

public class OrderExecutionLevel1Mock1 {
	
	
	public static int verifyBuyerExecution1(AuctionBuyersBean auctionBuyersBean,int sellerQty) {
		sellerQty =sellerQty;
		int buyerid=auctionBuyersBean.getAuctionBuyersId();
		if(buyerid == auctionBuyersBean.getAuctionBuyersId() && auctionBuyersBean.getPartialAllowed() == true ) {
			 sellerQty = verifyBuyerMinimumQty(auctionBuyersBean.getMinimumQuantity(),sellerQty);
			 return sellerQty;
		}
		int a=0;
		return sellerQty + a ;
		
	}
		
	
	public static int verifyBuyerMinimumQty(int buyerMinQty,int sellerQty) {
		if(sellerQty >= buyerMinQty) {
			int sellerNewQty = (sellerQty) - (buyerMinQty);
			return sellerNewQty;
		}
		return sellerQty;
		
		
		
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
		/*int  bidquantity =bid1.getMinimumQuantity();
		SELLER_QTY = (SELLER_QTY) -( bidquantity)  ;
		System.out.println("buyer1 nwq Qty"+SELLER_QTY);*/
		AuctionBuyersBean bid2 = new AuctionBuyersBean();
		bid2.setAuctionBuyersId(2);
		bid2.setPartialAllowed(true);
		bid2.setMinimumQuantity(150);
		bid2.setBidPrice(19f);
		bid2.setBidQuantity(300);
		auctionBuyersBeans.add(bid2);
		/*int  bidquantity1 =bid2.getMinimumQuantity();
		SELLER_QTY = (SELLER_QTY) -( bidquantity1)  ;
		System.out.println("buyer 2 nwq Qty"+SELLER_QTY);*/
		AuctionBuyersBean bid3 = new AuctionBuyersBean();
		bid3.setAuctionBuyersId(3);
		bid3.setPartialAllowed(false);	 
		bid3.setBidPrice(19f);
		bid3.setBidQuantity(300);
	 	auctionBuyersBeans.add(bid3);
	 	/*int  bidquantity3 =bid3.getMinimumQuantity();
		SELLER_QTY = (SELLER_QTY) -( bidquantity3)  ;
		System.out.println("buyer 3 nwq Qty"+SELLER_QTY);*/
		AuctionBuyersBean bid4 = new AuctionBuyersBean();
		bid4.setAuctionBuyersId(4);
		bid4.setPartialAllowed(true);
		bid4.setMinimumQuantity(200);
		bid4.setBidPrice(18.50f);
		bid4.setBidQuantity(400);
	 	auctionBuyersBeans.add(bid4);
	 	/*int  bidquantity4 =bid3.getMinimumQuantity();
		SELLER_QTY = (SELLER_QTY) -( bidquantity4)  ;
		System.out.println("buyer 4 nwq Qty"+SELLER_QTY);*/
		Map<Integer,Float> bidExecutionCandidates=new HashMap<Integer,Float>();
		if(SELLER_OFFER_PRICE > 0.0) {
			SELLER_HIGHEST_PRICE=false;
		}		
	
		if(SELLER_PARTIAL_ALLOWED && SELLER_HIGHEST_PRICE) {
			for (Iterator iterator = auctionBuyersBeans.iterator(); iterator.hasNext();) {
				AuctionBuyersBean auctionBuyersBean = (AuctionBuyersBean) iterator.next();
				int result =OrderExecutionLevel1Mock1.verifyBuyerExecution1(auctionBuyersBean,SELLER_QTY);
				SELLER_QTY =result;
				System.out.println("Case 1 [Seller Partial Allowed with Highest Price]:" + result);
				/*if(result) { 
					bidExecutionCandidates.put(auctionBuyersBean.getAuctionBuyersId(),auctionBuyersBean.getBidPrice());
				}*/
			}
			
		}
	
	}
	
}
