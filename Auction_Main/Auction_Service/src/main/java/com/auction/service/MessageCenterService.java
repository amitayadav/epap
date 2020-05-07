package com.auction.service;

import java.util.List;

import com.auction.model.bean.MessageCenterBean;
import com.auction.model.views.MessageCenterView;
import com.auction.service.generic.GenericService;

public interface MessageCenterService extends GenericService<MessageCenterBean, Integer> {

	public List<MessageCenterView> getAllSenderMessageByAccountId(Integer fromAccountId, Integer toAccountId,Integer senderAccountId,Integer receiverAccountId);
}
