package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.CommentsDao;
import com.auction.model.bean.CommentsBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.Comments;
import com.auction.service.CommentsService;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private CommentsDao commentsDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(CommentsBean bean) {
		logger.info("CommentsServiceImpl  call save method");
		Comments complaints = commentsDao.save(convertBeanToEntity(bean));
		return (null != complaints ? complaints.getCommentId() : null);
	}

	@Override
	public CommentsBean findById(Integer id) {
		logger.info("CommentsServiceImpl  call findById method");
		return new CommentsBean(commentsDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("CommentsServiceImpl  call exists method");
		return commentsDao.exists(id);
	}

	@Override
	public List<CommentsBean> findAll() {
		logger.info("CommentsServiceImpl  call findAll method");
		return convertEntityListToBeanList(commentsDao.findAll());
	}

	@Override
	public List<CommentsBean> findAll(Iterable<Integer> ids) {
		logger.info("CommentsServiceImpl  call findAll method");
		return convertEntityListToBeanList(commentsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("CommentsServiceImpl  call count method");
		return commentsDao.count();
	}

	@Override
	public void delete(CommentsBean bean) {
		logger.info("CommentsServiceImpl  call delete method");
		commentsDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<CommentsBean> findByAccountProfileByCreatedByAccountId(Integer accountId) {
		logger.info("CommentsServiceImpl  call findByAccountProfileByCreatedByAccountId method");
		return convertEntityListToBeanList(commentsDao.findByAccountProfileByCreatedByAccountId(accountId));
	}

	@Override
	public List<CommentsBean> getByAccountProfileByAccountId(Integer accountId) {
		logger.info("CommentsServiceImpl  call getByAccountProfileByAccountId method");
		return convertEntityListToBeanList(commentsDao.getByAccountProfileByAccountId(accountId));
	}

	private Comments convertBeanToEntity(CommentsBean bean) {
		logger.info("CommentsServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			Comments entity = new Comments();
			entity.setCommentId(bean.getCommentId());

			if (null != bean.getAccountProfileBeanByAccountId() && null != bean.getAccountProfileBeanByAccountId().getAccountId())
				entity.setAccountProfileByAccountId(new AccountProfile(bean.getAccountProfileBeanByAccountId().getAccountId()));

			if (null != bean.getAccountProfileBeanByCreatedBy() && null != bean.getAccountProfileBeanByCreatedBy().getAccountId())
				entity.setAccountProfileByCreatedBy(new AccountProfile(bean.getAccountProfileBeanByCreatedBy().getAccountId()));

			if (null != bean.getAccountProfileBeanByUpdatedBy() && null != bean.getAccountProfileBeanByUpdatedBy().getAccountId())
				entity.setAccountProfileByUpdatedBy(new AccountProfile(bean.getAccountProfileBeanByUpdatedBy().getAccountId()));

			entity.setCommentStatus(bean.getCommentStatus());
			entity.setCommentText(bean.getCommentText());
			entity.setCreatedTimestamp(bean.getCreatedTimestamp());
			entity.setFeedback(bean.getFeedback());
			entity.setReserved(bean.getReserved());
			entity.setUpdatedTimestamp(bean.getUpdatedTimestamp());
			return entity;
		}
		return null;
	}

	private List<CommentsBean> convertEntityListToBeanList(List<Comments> list) {
		logger.info("CommentsServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<CommentsBean> commentsBeansList = new ArrayList<CommentsBean>(0);
			for (Comments comment : list) {
				commentsBeansList.add(new CommentsBean(comment));
			}
			return commentsBeansList;
		}
		return null;
	}

	@Override
	public List<CommentsBean> getByInboxAccountProfileByAccountId(Integer accountId) {
		logger.info("CommentsServiceImpl  call getByInboxAccountProfileByAccountId method");
		return convertEntityListToBeanList(commentsDao.getByInboxAccountProfileByAccountId(accountId));
	}

	@Override
	public List<CommentsBean> getBySentAccountProfileByAccountId(Integer accountId) {
		logger.info("CommentsServiceImpl  call getBySentAccountProfileByAccountId method");
		return convertEntityListToBeanList(commentsDao.getBySentAccountProfileByAccountId(accountId));
	}

}