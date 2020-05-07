package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.RatingsLogDao;
import com.auction.model.bean.RatingsLogBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.DailyAuctions;
import com.auction.model.entity.RatingsLog;
import com.auction.service.RatingsLogService;

@Service
@Transactional
public class RatingsLogServiceImpl implements RatingsLogService{

	@Autowired
	private RatingsLogDao  ratingsLogDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());
	
	@Override
	public Integer save(RatingsLogBean bean) {
		logger.info("RatingsLogServiceImpl call save method");
		RatingsLog ratingsLog = ratingsLogDao.save(convertBeanToEntity(bean));
		return (null != ratingsLog ? ratingsLog.getLogId() : null);
	}

	@Override
	public RatingsLogBean findById(Integer id) {
		logger.info("RatingsLogServiceImpl call findById method");
		return new RatingsLogBean(ratingsLogDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("RatingsLogServiceImpl call exists method");
		return ratingsLogDao.exists(id);
	}

	@Override
	public List<RatingsLogBean> findAll() {
		logger.info("RatingsLogServiceImpl call findAll method");
		return convertEntityListToBeanList(ratingsLogDao.findAll());
	}

	@Override
	public List<RatingsLogBean> findAll(Iterable<Integer> ids) {
		logger.info("RatingsLogServiceImpl call findAll method");
		return convertEntityListToBeanList(ratingsLogDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("RatingsLogServiceImpl call count method");
		return ratingsLogDao.count();
	}

	@Override
	public void delete(RatingsLogBean bean) {
		logger.info("RatingsLogServiceImpl call delete method");
		ratingsLogDao.delete(convertBeanToEntity(bean));
		
	}
	
	@Override
	public Integer refresh(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<RatingsLogBean> calculateAverageRatingByRatedId(Integer ratedId) {
		logger.info("RatingsLogServiceImpl call calculateAverageRatingByRatedId method");
		return convertObjectListToViewList(ratingsLogDao.calculateAverageRatingByRatedId(ratedId));
	}
	
	@Override
	public boolean getRatingCountByRatedId(Integer accountProfileByRatorId, Integer accountProfileByRatedId, Integer dailyAuctionsId) {
		logger.info("RatingsLogServiceImpl call getRatingCountByRatedId method");
		return ratingsLogDao.getRatingCountByRatedId(accountProfileByRatorId, accountProfileByRatedId, dailyAuctionsId);
	}
	
	@Override
	public RatingsLogBean getRatingByRatedId(Integer accountProfileByRatorId, Integer accountProfileByRatedId, Integer dailyAuctionsId) {
		logger.info("RatingsLogServiceImpl call getRatingByRatedId method");
		return new RatingsLogBean(ratingsLogDao.getRatingByRatedId(accountProfileByRatorId, accountProfileByRatedId, dailyAuctionsId));
	}
	
	/**
	 * This method is used to convert RatingsLogBean to RatingsLog
	 * 
	 * @param bean
	 * @return entity
	 */
	private RatingsLog convertBeanToEntity(RatingsLogBean bean) {
		logger.info("RatingsLogServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			RatingsLog  entity = new  RatingsLog();
			entity.setLogId(bean.getLogId());
			entity.setAccountProfileByRatedId(new AccountProfile(bean.getAccountProfileByRatedId().getAccountId()));
			entity.setAccountProfileByRatorId(new AccountProfile(bean.getAccountProfileByRatorId().getAccountId()));
			entity.setDailyAuctions(new DailyAuctions(bean.getDailyAuctionsBean().getDailyAuctionsId()));
			entity.setRatingValue(bean.getRatingValue());
			entity.setComments(bean.getComments());
			entity.setRatingTimestamp(bean.getRatingTimestamp());
			return entity;
		}
		return null;
	}

	/**
	 * This method is used to convert PickupTicke List to RatingsLogBean
	 * List
	 * 
	 * @param List<RatingsLog>
	 *            list
	 * @return List<RatingsLogBean> list
	 */
	private List<RatingsLogBean> convertEntityListToBeanList(List<RatingsLog> list) {
		logger.info("RatingsLogServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {

			List<RatingsLogBean> ratingsLogBeansList = new ArrayList<RatingsLogBean>(0);

			for (RatingsLog ratings : list) {
				ratingsLogBeansList.add(new RatingsLogBean(ratings));
			}
			return ratingsLogBeansList;
		}
		return null;
	}

	private List<RatingsLogBean> convertObjectListToViewList(List<Object[]> objectList) {
		logger.info("RatingsLogServiceImpl call convertObjectListToViewList method");
		List<RatingsLogBean> ratingsLogBeanList = new ArrayList<RatingsLogBean>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				RatingsLogBean ratingsLogBean = new RatingsLogBean(TypeConverterUtil.convertObjectToFloat(row[0]),
						TypeConverterUtil.convertObjectToInteger(row[1]),
						TypeConverterUtil.convertObjectToInteger(row[2]));
						ratingsLogBeanList.add(ratingsLogBean);
			}
		}
		return ratingsLogBeanList;
	}

	


}
