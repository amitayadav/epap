package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.EventsMeaningDao;
import com.auction.model.bean.EventsMeaningBean;
import com.auction.model.entity.EventsMeaning;
import com.auction.service.EventsMeaningService;

@Service
@Transactional
public class EventsMeaningServiceImpl implements EventsMeaningService {

	@Autowired
	EventsMeaningDao eventsMeaningDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(EventsMeaningBean bean) {
		logger.info("EventsMeaningServiceImpl  call save method");
		EventsMeaning eventsMeaning = eventsMeaningDao.save(convertBeanToEntity(bean));
		return (null != eventsMeaning ? eventsMeaning.getEventId() : null);
	}

	@Override
	public EventsMeaningBean findById(Integer id) {
		logger.info("EventsMeaningServiceImpl  call findById method");
		return convertEntityToBean(eventsMeaningDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("EventsMeaningServiceImpl  call exists method");
		return eventsMeaningDao.exists(id);
	}

	@Override
	public List<EventsMeaningBean> findAll() {
		logger.info("EventsMeaningServiceImpl  call findAll method");
		return convertEntityListToBeanList(eventsMeaningDao.findAll());
	}

	@Override
	public List<EventsMeaningBean> findAll(Iterable<Integer> ids) {
		logger.info("EventsMeaningServiceImpl  call findAll method");
		return convertEntityListToBeanList(eventsMeaningDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("EventsMeaningServiceImpl  call count method");
		return eventsMeaningDao.count();
	}

	@Override
	public void delete(EventsMeaningBean bean) {
		logger.info("EventsMeaningServiceImpl  call delete method");
		eventsMeaningDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("EventsMeaningServiceImpl  call refresh method");
		EventsMeaning eventsMeaning = eventsMeaningDao.findOne(id);
		return (null != eventsMeaning ? eventsMeaning.getEventId() : null);
	}

	private EventsMeaning convertBeanToEntity(EventsMeaningBean bean) {
		logger.info("EventsMeaningServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			EventsMeaning entity = new EventsMeaning();
			entity.setEventId(bean.getEventId());
			entity.setEventMeaning(bean.getEventMeaning());
			return entity;
		}
		return null;
	}

	private List<EventsMeaningBean> convertEntityListToBeanList(List<EventsMeaning> list) {
		logger.info("EventsMeaningServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<EventsMeaningBean> eventsMeaningBeanList = new ArrayList<EventsMeaningBean>(0);
			for (EventsMeaning eventsMeaning : list) {
				EventsMeaningBean eventsMeaningBean = convertEntityToBean(eventsMeaning);
				eventsMeaningBeanList.add(eventsMeaningBean);
			}
			return eventsMeaningBeanList;
		}
		return null;
	}

	private EventsMeaningBean convertEntityToBean(EventsMeaning entity) {
		logger.info("EventsMeaningServiceImpl  call convertEntityToBean method");
		if (null != entity) {
			EventsMeaningBean bean = new EventsMeaningBean();
			bean.setEventId(entity.getEventId());
			bean.setEventMeaning(entity.getEventMeaning());
			return bean;
		}
		return null;
	}

}
