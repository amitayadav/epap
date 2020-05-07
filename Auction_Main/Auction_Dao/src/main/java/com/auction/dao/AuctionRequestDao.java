package com.auction.dao;

import org.springframework.stereotype.Repository;

import com.auction.model.entity.AuctionRequest;

@Repository
public interface AuctionRequestDao extends GenericDao<AuctionRequest, Integer> {

}
