package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.SellerBalance;

@Repository
public interface SellerBalanceDao extends GenericDao<SellerBalance, Integer> {

	@Query(value = "SELECT sb FROM SellerBalance sb INNER JOIN sb.id.accountProfile ap WHERE ap.accountId = :accountId ORDER BY date(sb.id.transactionDate) ASC")
	public List<SellerBalance> getByAccountProfile(@Param("accountId") Integer accountId);

	@Query(value = "SELECT sb FROM SellerBalance sb INNER JOIN sb.id.accountProfile ap WHERE ap.accountId = :accountId ORDER BY date(sb.id.transactionDate) ASC")
	public Page<SellerBalance> getByBalanceByAccountProfile(@Param("accountId") Integer accountId, Pageable pageable);

	@Query(value = "SELECT sb FROM SellerBalance sb INNER JOIN sb.id.accountProfile ap WHERE ap.accountId = :accountId AND sb.id.transactionDate between :startDate AND :endDate ORDER BY date(sb.id.transactionDate) ASC")
	public List<SellerBalance> getByBalanceBetweenDate(@Param("accountId") Integer accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT * FROM seller_balance sb where sb.seller_id= :sellerID ORDER BY sb.transaction_date DESC LIMIT 1", nativeQuery = true)
	public SellerBalance getSellerBalanceNew(@Param("sellerID") Integer seller_id);
	
	 
	
	
	
	@Query(value = "SELECT sb.transaction_date,sb.transaction_description,sb.transaction_id,sb.comments,"+
			" CONCAT(pc.product_group_name,\"-\", pc.product_name,\"-\",pc.product_type_name),"+ 
			" st.sell_quantity,st.sell_price,st.gross_sale,st.royalty_percentage," + 
			" st.vat_percentage,st.seller_shipping_charge," + 
			" CASE" + 
			"    WHEN sb.debit_credit > 0 THEN 0 " + 
			"    WHEN sb.debit_credit < 0 THEN ABS(sb.debit_credit)" + 
			" END as debit," + 
			" CASE " + 
			"    WHEN sb.debit_credit > 0 THEN sb.debit_credit" + 
			"    WHEN sb.debit_credit < 0 THEN 0" +  
			" END as credit," + "sb.advance_balance,"+
			" sb.balance" + 
			" FROM seller_balance sb" + 
			" left join seller_transactions st ON sb.transaction_id=st.transaction_id" + 
			" left join auction_buyers ab ON ab.auction_buyers_id=st.auction_buyers_id" + 
			" left join daily_auctions da ON ab.daily_auctions_id =da.daily_auctions_id" + 
			" left join product_catalog pc ON pc.product_id=da.product_id"+	
			" where sb.seller_id = :sellerId AND DATE_FORMAT(sb.transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(sb.transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY sb.transaction_date ASC ", nativeQuery = true)
	public List<Object[]> getSellerAccountStatementBetweenDate(@Param("sellerId") Integer sellerId, @Param("startDate")   java.sql.Date startDate, @Param("endDate")   java.sql.Date endDate);

	
	@Query(value = "SELECT transaction_id,transaction_date "
			+ " FROM seller_balance" 
			+ " where seller_id = :sellerId AND transaction_code= :transactionCode AND DATE_FORMAT(transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY transaction_date ASC", nativeQuery = true)
	public List<Object[]> getSellerSalesNoticesBetweenDate(@Param("sellerId") Integer sellerId, @Param("transactionCode") char transactionCode,@Param("startDate")   java.sql.Date startDate, @Param("endDate")   java.sql.Date endDate);
	
}