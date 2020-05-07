package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.BuyerPurchaseInvoice;

@Repository
public interface PurchaseInvoiceDao extends GenericDao<BuyerPurchaseInvoice, Integer>  {
	@Query(value = " Select pi.transaction_id,pi.invoice_creation,"+
				" CONCAT(pc.product_group_name, '-', pc.product_name, '-', pc.product_type_name) as item,"+
				" pi.seller_public_name,pi.buyer_public_name,pi.buyer_shipper_public_name,"+
				" bt.buy_price,bt.buy_quantity,bt.gross_purchase,bt.royalty_amount,"+
				" bt.buyer_shipping_charge,bt.vat_amount,bt.net_payment ,pi.purchase_invoice_id"+
				" FROM purchase_invoice pi"+
				" INNER JOIN buyer_transactions bt ON bt.transaction_id = pi.transaction_id"+
				" INNER JOIN auction_buyers abs ON abs.auction_buyers_id = bt.auction_buyers_id"+
				" INNER JOIN daily_auctions das ON das.daily_auctions_id = abs.daily_auctions_id"+
				" INNER JOIN product_catalog pc ON pc.product_id = das.product_id"+
				" where pi.transaction_id = :transactionId AND  DATE_FORMAT(pi.invoice_creation,'%d-%m-%Y')" , nativeQuery = true)
	public List<Object[]> getBuyerPurchaseInvoicesReport(@Param("transactionId") Integer transactionId);
}
