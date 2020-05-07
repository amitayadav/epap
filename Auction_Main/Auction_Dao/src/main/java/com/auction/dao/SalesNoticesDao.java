package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auction.model.entity.SellerSalesNotice;

@Repository
public interface SalesNoticesDao extends GenericDao<SellerSalesNotice, Integer>{
	
	@Query(value = " Select sn.seller_public_name,sn.buyer_public_name,sn.seller_shipper_public_name, "+ 
			" sn.transaction_id,CONCAT(pc.product_group_name, '-', pc.product_name, '-', pc.product_type_name) as item,"+
			" sn.notice_creation,st.sell_price, st.sell_quantity, st.gross_sale,"+
			" st.royalty_amount, st.seller_shipping_charge, st.vat_amount, st.net_sales, sn.sales_notice_id"+
			" FROM sales_notice sn"+
			" INNER JOIN seller_transactions st ON st.transaction_id = sn.transaction_id"+
			" INNER JOIN auction_buyers abs ON abs.auction_buyers_id = st.auction_buyers_id"+
			" INNER JOIN daily_auctions das ON das.daily_auctions_id = abs.daily_auctions_id"+
			" INNER JOIN product_catalog pc ON pc.product_id = das.product_id"+
			" WHERE sn.transaction_id = :transactionId AND DATE_FORMAT(sn.notice_creation,'%d-%m-%Y')", nativeQuery = true)
	public List<Object[]> getSellersalesNoticeRepor(@Param("transactionId") Integer transactionId);
}
