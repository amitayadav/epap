package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.dao.IpBlockingDetailsDao;
import com.auction.model.bean.IpBlockingDetailsBean;
import com.auction.model.entity.IpBlockingDetails;
import com.auction.service.IpBlockingDetailsService;

@Service
@Transactional
public class IpBlockingDetailsServiceImpl implements IpBlockingDetailsService {

	@Autowired
	IpBlockingDetailsDao ipBlockingDetailsDao;

	@Value("${ip.blocking.hours}")
	private int ipBlockingHours;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public String save(IpBlockingDetailsBean bean) {
		logger.info("IpBlockingDetailsServiceImpl  call save method");
		IpBlockingDetails entity = ipBlockingDetailsDao.save(convertBeanToEntity(bean));
		return ((null != entity && null != entity.getIp()) ? entity.getIp() : null);
	}

	@Override
	public IpBlockingDetailsBean findById(String id) {
		logger.info("IpBlockingDetailsServiceImpl  call findById method");
		return new IpBlockingDetailsBean(ipBlockingDetailsDao.findOne(id));
	}

	@Override
	public boolean exists(String id) {
		logger.info("IpBlockingDetailsServiceImpl  call exists method");
		return ipBlockingDetailsDao.exists(id);
	}

	@Override
	public List<IpBlockingDetailsBean> findAll() {
		logger.info("IpBlockingDetailsServiceImpl  call findAll method");
		return convertEntityListToBeanList(ipBlockingDetailsDao.findAll());
	}

	@Override
	public List<IpBlockingDetailsBean> findAll(Iterable<String> ids) {
		logger.info("IpBlockingDetailsServiceImpl  call findAll method");
		return convertEntityListToBeanList(ipBlockingDetailsDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("IpBlockingDetailsServiceImpl  call count method");
		return ipBlockingDetailsDao.count();
	}

	@Override
	public void delete(IpBlockingDetailsBean bean) {
		logger.info("IpBlockingDetailsServiceImpl  call delete method");
		if (null != bean && null != bean.getIp() && exists(bean.getIp())) {
			ipBlockingDetailsDao.delete(convertBeanToEntity(bean));
		}
	}

	@Override
	public String refresh(String id) {
		logger.info("IpBlockingDetailsServiceImpl  call refresh method");
		IpBlockingDetails entity = ipBlockingDetailsDao.findOne(id);
		return ((null != entity && null != entity.getIp()) ? entity.getIp() : null);
	}

	@Override
	public void loginFail(String id) {
		logger.info("IpBlockingDetailsServiceImpl  call loginFail method");
		if (exists(id)) {
			IpBlockingDetailsBean bean = findById(id);
			bean.setNoOfBlocking((bean.getNoOfBlocking() + 1));
			save(bean);
		} else {
			IpBlockingDetailsBean bean = new IpBlockingDetailsBean();
			bean.setIp(id);
			bean.setNoOfBlocking(1);
			bean.setBlockingTimestamp(InternetTiming.getInternetDateTime());
			save(bean);
		}
	}

	@Override
	public boolean isBlocked(String ip, Integer maxFailCount) {
		logger.info("IpBlockingDetailsServiceImpl  call isBlocked method");
		IpBlockingDetails ipBlockingDetails = ipBlockingDetailsDao.isBlocked(ip, maxFailCount);
		if (null != ipBlockingDetails && null != ipBlockingDetails.getIp()) {
			long currentTime = System.currentTimeMillis();
			long exprtyTime = DateHelper.getDateAfterAddedHoursInMiliSeconds(ipBlockingDetails.getBlockingTimestamp(), ipBlockingHours);
			return (exprtyTime >= currentTime);
		}
		return false;
	}

	@Override
	public void deleteBlockedIp(String ip) {
		logger.info("IpBlockingDetailsServiceImpl  call deleteBlockedIp method");
		if (exists(ip)) {
			delete(new IpBlockingDetailsBean(ip));
		}
	}

	private IpBlockingDetails convertBeanToEntity(IpBlockingDetailsBean bean) {
		logger.info("IpBlockingDetailsServiceImpl  call convertBeanToEntity method");
		if (null != bean) {
			IpBlockingDetails entity = new IpBlockingDetails();
			entity.setIp(bean.getIp());
			entity.setBlockingTimestamp(bean.getBlockingTimestamp());
			entity.setNoOfBlocking(bean.getNoOfBlocking());
			return entity;
		}
		return null;
	}

	private List<IpBlockingDetailsBean> convertEntityListToBeanList(List<IpBlockingDetails> list) {
		logger.info("IpBlockingDetailsServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<IpBlockingDetailsBean> ipBlockingDetailsBeanList = new ArrayList<IpBlockingDetailsBean>(0);
			for (IpBlockingDetails ipBlockingDetails : list) {
				ipBlockingDetailsBeanList.add(new IpBlockingDetailsBean(ipBlockingDetails));
			}
			return ipBlockingDetailsBeanList;
		}
		return null;
	}

}