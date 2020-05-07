package com.auction.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.VatBalance;

@Repository
public interface VatBalanceDao extends GenericDao<VatBalance, Integer> {
	
	@Query(value = "SELECT balance from  VatBalance where vatBalanceId = (select Max(vatBalanceId) from VatBalance)")
	public BigDecimal getLastVatBalance();
	
	@Query(value = "SELECT * FROM vat_balance bb where  DATE_FORMAT(bb.transaction_date,'%Y-%m-%d') >= DATE_FORMAT(:startDate,'%Y-%m-%d') and DATE_FORMAT(bb.transaction_date,'%Y-%m-%d')  <= DATE_FORMAT(:endDate,'%Y-%m-%d') ORDER BY bb.transaction_date ASC ", nativeQuery = true)
	List<VatBalance>  findByTransactionDateBetween(@Param("startDate") java.sql.Date startDate , @Param("endDate")   java.sql.Date endDate);
}