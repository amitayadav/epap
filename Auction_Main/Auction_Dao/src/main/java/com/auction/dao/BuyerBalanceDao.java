package com.auction.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.BuyerBalance;

@Repository
public interface BuyerBalanceDao extends GenericDao<BuyerBalance, Integer> {
	@Query(value = "SELECT sb FROM BuyerBalance sb INNER JOIN sb.id.accountProfile ap WHERE ap.accountId = :accountId AND sb.id.transactionDate between :startDate AND :endDate ORDER BY date(sb.id.transactionDate) ASC")
	public List<BuyerBalance> getByBalanceBetweenDate(@Param("accountId") Integer accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM buyer_balance bb where bb.buyer_id= :buyerID ORDER BY bb.transaction_date DESC LIMIT 1", nativeQuery = true)
	public BuyerBalance getBuyerBalanceNew(@Param("buyerID") Integer buyer_id);
	
	@Query(value = "SELECT bb.transaction_date,bb.transaction_description,bb.transaction_id,bb.comments," + 
			" CONCAT(pc.product_group_name,\"-\", pc.product_name,\"-\",pc.product_type_name)," + 
			" bt.buy_quantity," + 
			" bt.buy_price,bt.gross_purchase,bt.royalty_percentage," + 
			" bt.vat_percentage,bt.buyer_shipping_charge," + 
			" CASE" + 
			"    WHEN bb.debit_credit > 0 THEN 0" + 
			"    WHEN bb.debit_credit < 0 THEN ABS(bb.debit_credit)" + 
			" END as debit," + 
			" CASE" + 
			"    WHEN bb.debit_credit > 0 THEN bb.debit_credit" + 
			"    WHEN bb.debit_credit < 0 THEN 0" + 
			" END as credit," + "bb.advance_balance,"+
			" bb.balance" + 
			" FROM buyer_balance bb" + 
			" left join buyer_transactions bt ON bb.transaction_id=bt.transaction_id" + 
			" left join auction_buyers ab ON ab.auction_buyers_id=bt.auction_buyers_id" + 
			" left join daily_auctions da ON ab.daily_auctions_id =da.daily_auctions_id" + 
			" left join product_catalog pc ON pc.product_id=da.product_id"+
			" where bb.buyer_id = :buyerId AND DATE_FORMAT(bb.transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(bb.transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY bb.transaction_date ASC", nativeQuery = true)
	public List<Object[]> getBuyerAccountStatementBetweenDate(@Param("buyerId") Integer buyerId,@Param("startDate")  java.sql.Date startDate, @Param("endDate")  java.sql.Date endDate);
	
	@Query(value = "SELECT transaction_id,transaction_date "
			+ " FROM buyer_balance" 
			+ " where buyer_id = :buyerId AND transaction_code= :transactionCode AND DATE_FORMAT(transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY transaction_date ASC", nativeQuery = true)
	public List<Object[]> getBuyerPurchaseInvoicesBetweenDate(@Param("buyerId") Integer buyerId,@Param("startDate")  java.sql.Date startDate, @Param("endDate")  java.sql.Date endDate,@Param("transactionCode") char transactionCode);

	
}