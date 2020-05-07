package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.dao.AgentOwnerDao;
import com.auction.model.bean.AgentOwnerBean;
import com.auction.model.bean.LoginDetailsBean;
import com.auction.model.entity.AgentOwner;
import com.auction.model.entity.LoginDetails;
import com.auction.service.AgentOwnerService;

@Service
@Transactional
public class AgentOwnerServiceImpl implements AgentOwnerService {

	@Autowired
	private AgentOwnerDao agentOwnerDao;

	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(AgentOwnerBean bean) {
		logger.info("AgentOwnerServiceImpl call save method");
		AgentOwner agentOwner = convertBeanToEntity(bean);
		agentOwnerDao.save(agentOwner);
		if (null != agentOwner && null != agentOwner.getAgentOwnerId()) {
			return agentOwner.getAgentOwnerId();
		}
		return null;
	}

	@Override
	public AgentOwnerBean findById(Integer id) {
		logger.info("AgentOwnerServiceImpl call findById method");
		return convertEntityToBean(agentOwnerDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("AgentOwnerServiceImpl call exists method");
		return agentOwnerDao.exists(id);
	}

	@Override
	public List<AgentOwnerBean> findAll() {
		logger.info("AgentOwnerServiceImpl call findAll method");
		return convertEntityListToBeanList(agentOwnerDao.findAll());
	}

	@Override
	public List<AgentOwnerBean> findAll(Iterable<Integer> ids) {
		logger.info("AgentOwnerServiceImpl call findAll method");
		return convertEntityListToBeanList(agentOwnerDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("AgentOwnerServiceImpl call count method");
		return agentOwnerDao.count();
	}

	@Override
	public void delete(AgentOwnerBean bean) {
		logger.info("AgentOwnerServiceImpl call delete method");
		agentOwnerDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		agentOwnerDao.refresh(new AgentOwner(id));
		return id;
	}

	@Override
	public AgentOwnerBean getAgentOwnerByLoginUserId(String loginUserid) {
		logger.info("AgentOwnerServiceImpl call getAgentOwnerByLoginUserId method");
		return convertEntityToBean(agentOwnerDao.getAgentOwnerByLoginUserId(loginUserid));
	}

	@Override
	public List<AgentOwnerBean> getAgentByOwnerLoginUserId(String loginUserid) {
		logger.info("AgentOwnerServiceImpl call getAgentByOwnerLoginUserId method");
		return convertEntityListToBeanList(agentOwnerDao.getAgentByOwnerLoginUserId(loginUserid));
	}

	@Override
	public AgentOwnerBean getPurchaseLimitOfAgentByAccountId(String loginUserid) {
		logger.info("AgentOwnerServiceImpl call getPurchaseLimitOfAgentByAccountId method");
		return convertEntityToBean(agentOwnerDao.getPurchaseLimitOfAgentByAccountId(loginUserid));
	}

	@Override
	public Boolean checkAgentByPrivilegeAndLoginUserId(String loginUserid, Short privileges) {
		logger.info("AgentOwnerServiceImpl call checkAgentByPrivilegeAndLoginUserId method");
		return agentOwnerDao.checkAgentByPrivilegeAndLoginUserId(loginUserid, privileges);
	}

	private AgentOwner convertBeanToEntity(AgentOwnerBean bean) {
		logger.info("AgentOwnerServiceImpl call convertBeanToEntity method");
		if (null != bean) {
			AgentOwner entity = new AgentOwner();
			if (null != bean.getAgentOwnerId())
				entity.setAgentOwnerId(bean.getAgentOwnerId());

			entity.setComments(bean.getComments());
			entity.setPrivileges(bean.getPrivileges());
			entity.setPurchaseLimit(bean.getPurchaseLimit());
			entity.setLimitSpent(bean.getLimitSpent());
			if (null != bean.getLoginDetailsByAgentLoginUserid())
				entity.setLoginDetailsByAgentLoginUserid(new LoginDetails(bean.getLoginDetailsByAgentLoginUserid().getLoginUserid()));

			if (null != bean.getLoginDetailsByOwnerLoginUserid())
				entity.setLoginDetailsByOwnerLoginUserid(new LoginDetails(bean.getLoginDetailsByOwnerLoginUserid().getLoginUserid()));

			return entity;
		}
		return null;
	}

	private List<AgentOwnerBean> convertEntityListToBeanList(List<AgentOwner> list) {
		logger.info("AgentOwnerServiceImpl call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<AgentOwnerBean> agentOwnerBeanList = new ArrayList<AgentOwnerBean>(0);
			for (AgentOwner agentOwner : list) {
				AgentOwnerBean agentOwnerBean = convertEntityToBean(agentOwner);
				agentOwnerBeanList.add(agentOwnerBean);
			}
			return agentOwnerBeanList;
		}
		return null;
	}

	private AgentOwnerBean convertEntityToBean(AgentOwner entity) {
		logger.info("AgentOwnerServiceImpl call convertEntityToBean method");
		if (null != entity) {
			AgentOwnerBean bean = new AgentOwnerBean();
			bean.setAgentOwnerId(entity.getAgentOwnerId());
			bean.setComments(entity.getComments());
			bean.setPrivileges(entity.getPrivileges());
			bean.setPurchaseLimit(entity.getPurchaseLimit());
			bean.setLimitSpent(entity.getLimitSpent());
			if (null != entity.getLoginDetailsByAgentLoginUserid())
				bean.setLoginDetailsByAgentLoginUserid(new LoginDetailsBean(entity.getLoginDetailsByAgentLoginUserid()));

			if (null != entity.getLoginDetailsByOwnerLoginUserid())
				bean.setLoginDetailsByOwnerLoginUserid(new LoginDetailsBean(entity.getLoginDetailsByOwnerLoginUserid()));

			return bean;
		}
		return null;
	}

}