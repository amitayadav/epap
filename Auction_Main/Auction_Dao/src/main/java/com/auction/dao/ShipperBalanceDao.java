package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.ShipperBalance;

@Repository
public interface ShipperBalanceDao extends GenericDao<ShipperBalance, Integer> {

	@Query(value = "SELECT sb FROM ShipperBalance sb INNER JOIN sb.id.accountProfile ap WHERE ap.accountId = :accountId AND sb.id.transactionDate between :startDate AND :endDate ORDER BY date(sb.id.transactionDate) ASC")
	public List<ShipperBalance> getByBalanceBetweenDate(@Param("accountId") Integer accountId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT * FROM shipper_balance sb where sb.shipper_id= :shipperID ORDER BY sb.transaction_date DESC LIMIT 1", nativeQuery = true)
	public ShipperBalance getShipperBalanceNew(@Param("shipperID") Integer seller_id);

	@Query(value = "SELECT sb.transaction_date,sb.transaction_description,sb.transaction_id,sb.comments,"
			+ " CONCAT(pc.product_group_name,\"-\", pc.product_name,\"-\",pc.product_type_name),"
			+ " st.gross_revenue,st.royalty_amount,st.vat_amount," + " CASE" + "    WHEN sb.debit_credit > 0 THEN 0"
			+ "    WHEN sb.debit_credit < 0 THEN ABS(sb.debit_credit)" + " END as debit," + " CASE"
			+ "    WHEN sb.debit_credit > 0 THEN sb.debit_credit" + "    WHEN sb.debit_credit < 0 THEN 0"
			+ " END as credit," +"sb.advance_balance,"+ " sb.balance" + " FROM shipper_balance sb"
			+ " left join shipper_transactions st ON sb.transaction_id=st.transaction_id"
			+ " left join auction_buyers ab ON ab.auction_buyers_id=st.auction_buyers_id"
			+ " left join daily_auctions da ON ab.daily_auctions_id =da.daily_auctions_id"
			+ " left join product_catalog pc ON pc.product_id=da.product_id"
			+ " WHERE sb.shipper_id = :shipperId AND DATE_FORMAT(sb.transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(sb.transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY sb.transaction_date ASC", nativeQuery = true)
	public List<Object[]> getShipperAccountStatementBetweenDate(@Param("shipperId") Integer shipperId,
			@Param("startDate")  java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
}