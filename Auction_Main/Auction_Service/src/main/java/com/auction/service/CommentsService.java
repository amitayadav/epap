package com.auction.service;

import java.util.List;

import com.auction.model.bean.CommentsBean;
import com.auction.service.generic.GenericService;

public interface CommentsService extends GenericService<CommentsBean, Integer> {

	List<CommentsBean> findByAccountProfileByCreatedByAccountId(Integer accountId);

	List<CommentsBean> getByAccountProfileByAccountId(Integer accountId);

	List<CommentsBean> getByInboxAccountProfileByAccountId(Integer accountId);
	
	List<CommentsBean> getBySentAccountProfileByAccountId (Integer accountId);
}