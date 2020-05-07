package com.auction.service;

import java.util.Date;
import java.util.List;

import com.auction.model.bean.AnnouncementBean;

import com.auction.service.generic.GenericService;

public interface AnnouncementService extends GenericService<AnnouncementBean, Integer> {

	public List<AnnouncementBean> getAnnouncementsBetweenDate(Date startDate, Date endDate);

	
	public void  updateAnnouncementmodifiedimestampBYId(Integer announcementId,Date  modifiedimestamp);

	


}