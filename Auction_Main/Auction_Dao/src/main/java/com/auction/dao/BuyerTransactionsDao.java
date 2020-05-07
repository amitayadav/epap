package com.auction.dao;

import org.springframework.stereotype.Repository;

import com.auction.model.entity.BuyerTransactions;

@Repository
public interface BuyerTransactionsDao extends GenericDao<BuyerTransactions, Integer> {

}