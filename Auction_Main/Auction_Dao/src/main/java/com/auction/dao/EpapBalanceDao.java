package com.auction.dao;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auction.model.entity.EpapBalance;


@Repository
public interface EpapBalanceDao extends GenericDao<EpapBalance, Integer> {
	
	@Query(value = "SELECT balance from  EpapBalance where epapBalanceId = (select Max(epapBalanceId) from EpapBalance)")
	public BigDecimal getLastEpapBalance();
	
	@Query(value = "SELECT * FROM epap_balance bb where  DATE_FORMAT(bb.transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(bb.transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY bb.transaction_date ASC ", nativeQuery = true)
	List<EpapBalance> findByTransactionDateBetween(@Param("startDate") java.sql.Date startDate , @Param("endDate")   java.sql.Date endDate);
}