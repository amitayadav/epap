package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.EventsProfileDao;
import com.auction.model.bean.EventsProfileBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.EventsProfile;
import com.auction.service.EventsProfileService;

@Service
@Transactional
public class EventsProfileServiceImpl implements EventsProfileService {

	@Autowired
	EventsProfileDao eventsProfileDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(EventsProfileBean bean) {
		logger.info("EventsProfileServiceImpl  call save method");
		EventsProfile eventsProfile = eventsProfileDao.save(convertBeanToEntity(bean));
		return (null != eventsProfile ? eventsProfile.getAccountId() : null);
	}

	@Override
	public EventsProfileBean findById(Integer id) {
		logger.info("EventsProfileServiceImpl  call findById method");
		return new EventsProfileBean(eventsProfileDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("EventsProfileServiceImpl  call exists method");
		return eventsProfileDao.exists(id);
	}

	@Override
	public List<EventsProfileBean> findAll() {
		logger.info("EventsProfileServiceImpl  call findAll method");
		return convertEntityListToBeanList(eventsProfileDao.findAll());
	}

	@Override
	public List<EventsProfileBean> findAll(Iterable<Integer> ids) {
		logger.info("EventsProfileServiceImpl  call findAll method");
		return convertEntityListToBeanList(eventsProfileDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("EventsProfileServiceImpl  call count method");
		return eventsProfileDao.count();
	}

	@Override
	public void delete(EventsProfileBean bean) {
		logger.info("EventsProfileServiceImpl  call delete method");
		eventsProfileDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}
	private EventsProfile convertBeanToEntity(EventsProfileBean bean) {
		logger.info("EventsProfileServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			EventsProfile entity = new EventsProfile();
			entity.setAccountId(bean.getAccountId());
			entity.setEventMap(bean.getEventMap());
			entity.setAccountProfile(new AccountProfile(bean.getAccountProfilebean().getAccountId()));
			return entity;
		}
		return null;
	}
	private List<EventsProfileBean> convertEntityListToBeanList(List<EventsProfile> list) {
		logger.info("EventsProfileServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {

			List<EventsProfileBean> eventsProfileBeansList = new ArrayList<EventsProfileBean>(0);
			for (EventsProfile profile : list) {
				eventsProfileBeansList.add(new EventsProfileBean(profile));
			}
			return eventsProfileBeansList;
		}
		return null;
	}
}
