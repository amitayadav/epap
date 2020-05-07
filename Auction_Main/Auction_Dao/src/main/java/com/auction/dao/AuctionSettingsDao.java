package com.auction.dao;

import org.springframework.stereotype.Repository;

import com.auction.model.entity.AuctionSettings;

@Repository
public interface AuctionSettingsDao extends GenericDao<AuctionSettings, Integer> {

}