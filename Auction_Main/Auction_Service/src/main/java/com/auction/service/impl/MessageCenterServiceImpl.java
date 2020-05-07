package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.MessageCenteDao;
import com.auction.model.bean.MessageCenterBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.MessageCenter;
import com.auction.model.views.MessageCenterView;
import com.auction.service.MessageCenterService;

@Service
@Transactional
public class MessageCenterServiceImpl implements MessageCenterService {
	@Autowired
	private MessageCenteDao messageCenteDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(MessageCenterBean bean) {
		logger.info("MessageCenterServiceImpl call save method");
		MessageCenter messageCenter = messageCenteDao.save(convertBeanToEntity(bean));
		return (null != messageCenter ? messageCenter.getMessageId() : null);
	}

	@Override
	public MessageCenterBean findById(Integer id) {
		logger.info("MessageCenterServiceImpl call findById method");
		return new MessageCenterBean(messageCenteDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("MessageCenterServiceImpl call exists method");
		return messageCenteDao.exists(id);
	}

	@Override
	public List<MessageCenterBean> findAll() {
		logger.info("MessageCenterServiceImpl call findAll method");
		return convertEntityListToBeanList(messageCenteDao.findAll());
	}

	@Override
	public List<MessageCenterBean> findAll(Iterable<Integer> ids) {
		logger.info("MessageCenterServiceImpl call findAll method");
		return convertEntityListToBeanList(messageCenteDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("MessageCenterServiceImpl call count method");
		return messageCenteDao.count();
	}

	@Override
	public void delete(MessageCenterBean bean) {
		logger.info("MessageCenterServiceImpl call delete method");
		messageCenteDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<MessageCenterView> getAllSenderMessageByAccountId(Integer fromAccountId, Integer toAccountId,Integer senderAccountId,Integer receiverAccountId) {
		logger.info("MessageCenterServiceImpl call getAllSenderMessageByAccountId method");
		return convertBeanListToViewList(messageCenteDao.getAllSenderMessageByAccountId(fromAccountId, toAccountId,senderAccountId,receiverAccountId));
	}
	
	public MessageCenter  convertBeanToEntity(MessageCenterBean bean) {
		logger.info("MessageCenterServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			MessageCenter entity = new MessageCenter();
			entity.setMessageId(bean.getMessageId());
			entity.setAccountProfileByFromAccountId(new AccountProfile(bean.getAccountProfileByFromAccountId().getAccountId()));
			entity.setAccountProfileByToAccountId(new AccountProfile(bean.getAccountProfileByToAccountId().getAccountId()));
			entity.setMessageTimestamp(bean.getMessageTimestamp());
			entity.setMessage(bean.getMessage());
			entity.setAttachment(bean.getAttachment());
			entity.setAttachmentType(bean.getAttachmentType());
			entity.setAttachment(bean.getAttachment());
			entity.setReserved(bean.getReserved());
			return entity;
		}
		return null;
	}
	public List<MessageCenterBean>  convertEntityListToBeanList(List<MessageCenter> list){
		logger.info("MessageCenterServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<MessageCenterBean> messageCenterBeanList = new ArrayList<MessageCenterBean>();
			for (MessageCenter messageCenter : list) {
				messageCenterBeanList.add(new MessageCenterBean(messageCenter));
			}
			return messageCenterBeanList;
		}
		return null;
	}
	private List<MessageCenterView> convertBeanListToViewList(List<MessageCenter> list) {
		logger.info("MessageCenterServiceImpl call convertBeanListToViewList method");
		List<MessageCenterView> views = new ArrayList<MessageCenterView>();
		if (null != list && list.size() > 0) {
			for (MessageCenter message : list) {
				views.add(new MessageCenterView(message));
			}
		}
		return views;
	}
	
}
