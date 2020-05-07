package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AnnouncementDao;
import com.auction.model.bean.AnnouncementBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.Announcement;
import com.auction.service.AnnouncementService;

@Service
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

	@Autowired
	private AnnouncementDao announcementDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AnnouncementBean bean) {
		logger.info("announcementServiceImpl call save method");
		Announcement announcement = announcementDao.save(convertBeanToEntity(bean));
		return (null != announcement ? announcement.getAnnouncementId() : null);
			}
	
	  @Override public AnnouncementBean findById(Integer id) {
		  logger.info("announcementServiceImpl call findById method"); 
		  return new AnnouncementBean(announcementDao.findOne(id)); }
	 

	@Override
	public boolean exists(Integer id) {
		logger.info("announcementServiceImpl call exists method");
		return announcementDao.exists(id);
	}

	@Override
	public List<AnnouncementBean> findAll() {
		logger.info("announcementServiceImpl call findAll method");
		return convertEntityListToBeanList(announcementDao.findAll());
	}

	
	@Override
	public List<AnnouncementBean> findAll(Iterable<Integer> ids) {
		logger.info("announcementServiceImpl call findAll method");
		return null;
	}

	@Override
	public long count() {
		logger.info("announcementServiceImpl call count method");
		return announcementDao.count();
	}

	@Override
	public void delete(AnnouncementBean bean) {
		logger.info("announcementServiceImpl call delete method");
		announcementDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("announcementServiceImpl call refresh method");
		Announcement announcement = announcementDao.findOne(id);
		return (null != announcement ? announcement.getAnnouncementId() : null);
	}
	

	private Announcement convertBeanToEntity(AnnouncementBean bean) {
		logger.info("announcementServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			Announcement entity = new Announcement();
			entity.setAnnouncementId(bean.getAnnouncementId());
			if(null != bean.getAddedBy() && null != bean.getAddedBy().getAccountId() ) 
			entity.setAddedBy(new AccountProfile(bean.getAddedBy().getAccountId()));
			if(null != bean.getModifiedBy() && null != bean.getModifiedBy().getAccountId() ) {
				entity.setModifiedBy(new AccountProfile(bean.getModifiedBy().getAccountId()));
			}
			entity.setAnnouncement(bean.getAnnouncement());
			entity.setAddedtimestamp(bean.getAddedtimestamp());
			entity.setModifiedimestamp(bean.getModifiedimestamp());
			return entity;
		}
		return null;
	}

	private List<AnnouncementBean> convertEntityListToBeanList(List<Announcement> list) {
		logger.info("announcementServiceImpl call convertEntityListToBeanList method");
		List<AnnouncementBean> announcementBeanList = new ArrayList<AnnouncementBean>(0);
		if (null != list && list.size() > 0) {
			
			for (Announcement announcement : list) {
				announcementBeanList.add(new AnnouncementBean(announcement));
			}
			return announcementBeanList;
		}
		return announcementBeanList;

	}

	@Override
	public List<AnnouncementBean> getAnnouncementsBetweenDate(Date startDate, Date endDate) {
		logger.info("announcementServiceImpl call getAnnouncementsBetweenDate method");
		return convertEntityListToBeanList(announcementDao.getAnnouncementsBetweenDate(startDate, endDate));
	}
	

	@Override
	public void updateAnnouncementmodifiedimestampBYId(Integer announcementId, Date modifiedimestamp) {
		logger.info("announcementServiceImpl call updateAnnouncementmodifiedimestampBYId method");
		announcementDao.updateAnnouncementmodifiedimestampBYId(modifiedimestamp, announcementId);
		
	}

	
	


}
