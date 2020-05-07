package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.SellerBalanceDao;
import com.auction.model.bean.SellerBalanceBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.SellerBalance;
import com.auction.model.entity.SellerBalanceId;
import com.auction.model.entity.TransactionCode;
import com.auction.model.views.AccountStatementsView;
import com.auction.model.views.SellerSalesNoticesView;
import com.auction.service.SellerBalanceService;

@Service
@Transactional
public class SellerBalanceServiceImpl implements SellerBalanceService {

	@Autowired
	private SellerBalanceDao sellerBalanceDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(SellerBalanceBean bean) {
		logger.info("SellerBalanceServiceImpl call save  method");
		SellerBalance sellerBalance = sellerBalanceDao.save(convertBeanToEntity(bean));
		return ((null != sellerBalance && null != sellerBalance.getId().getAccountProfile().getAccountId())
				? sellerBalance.getId().getAccountProfile().getAccountId()
				: null);
	}

	@Override
	public SellerBalanceBean findById(Integer id) {
		logger.info("SellerBalanceServiceImpl call findById  method");
		return new SellerBalanceBean(sellerBalanceDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerBalanceServiceImpl call exists  method");
		return sellerBalanceDao.exists(id);
	}

	@Override
	public List<SellerBalanceBean> findAll() {
		logger.info("SellerBalanceServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerBalanceDao.findAll());
	}

	@Override
	public List<SellerBalanceBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerBalanceServiceImpl call findAll  method");
		return convertEntityListToBeanList(sellerBalanceDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("SellerBalanceServiceImpl call findAll  method");
		return sellerBalanceDao.count();
	}

	@Override
	public void delete(SellerBalanceBean bean) {
		logger.info("SellerBalanceServiceImpl call delete  method");
		sellerBalanceDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<SellerBalanceBean> getByAccountProfile(Integer accountId) {
		logger.info("SellerBalanceServiceImpl call getByAccountProfile  method");
		return convertEntityListToBeanList(sellerBalanceDao.getByAccountProfile(accountId));
	}

	@Override
	public List<SellerBalanceBean> getByBalanceByAccountProfile(Integer accountId) {
		logger.info("SellerBalanceServiceImpl call getByBalanceByAccountProfile  method");
		return convertEntityPageToBeanPage(
				sellerBalanceDao.getByBalanceByAccountProfile(accountId, new PageRequest(0, 1)));
	}

	@Override
	public List<SellerBalanceBean> getByBalanceBetweenDate(Integer accountId, Date startDate, Date endDate) {
		logger.info("SellerBalanceServiceImpl call getByBalanceBetweenDate  method");
		return convertEntityListToBeanList(sellerBalanceDao.getByBalanceBetweenDate(accountId, startDate, endDate));
	}

	@Override
	public SellerBalanceBean getSellerBalanceNew(Integer seller_id) {
		logger.info("SellerBalanceServiceImpl call getSellerBalanceNew  method");
		return new SellerBalanceBean(sellerBalanceDao.getSellerBalanceNew(seller_id));
	}

	private SellerBalance convertBeanToEntity(SellerBalanceBean bean) {
		logger.info("SellerBalanceServiceImpl call convertBeanToEntity  method");
		if (null != bean) {
			SellerBalanceId sellerBalanceId = new SellerBalanceId();
			sellerBalanceId.setAccountProfile(new AccountProfile(bean.getId().getAccountProfileBean().getAccountId()));
			sellerBalanceId.setTransactionDate(bean.getId().getTransactionDate());

			SellerBalance sellerBalance = new SellerBalance(sellerBalanceId);
			sellerBalance.setTransactionId(bean.getTransactionId());
			sellerBalance.setBalance(bean.getBalance());
			sellerBalance.setDebitCredit(bean.getDebitCredit());
			sellerBalance.setAdvanceBalance(bean.getAdvanceBalance());
			sellerBalance.setTransactionCode(new TransactionCode(bean.getTransactionCodeBean().getTransactionCode()));
			sellerBalance.setTransactionDescription(bean.getTransactionDescription());
			sellerBalance.setReserved1(bean.getReserved1());
			sellerBalance.setReserved2(bean.getReserved2());
			sellerBalance.setComments(bean.getComments());
			sellerBalance.setEpapTransactionId(bean.getEpapTransactionId());
			return sellerBalance;
		}
		return null;
	}

	private List<SellerBalanceBean> convertEntityListToBeanList(List<SellerBalance> list) {
		logger.info("SellerBalanceServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerBalanceBean> sellerBalanceBeansList = new ArrayList<SellerBalanceBean>(0);
			for (SellerBalance balance : list) {
				sellerBalanceBeansList.add(new SellerBalanceBean(balance));
			}
			return sellerBalanceBeansList;
		}
		return null;
	}

	private List<SellerBalanceBean> convertEntityPageToBeanPage(Page<SellerBalance> sellerBalancePages) {
		logger.info("SellerBalanceServiceImpl call convertEntityPageToBeanPage  method");
		if (null != sellerBalancePages && sellerBalancePages.getSize() > 0) {
			if (null != sellerBalancePages.getContent() && sellerBalancePages.getContent().size() > 0) {
				return convertEntityListToBeanList(sellerBalancePages.getContent());
			}
		}
		return null;
	}

	@Override
	public List<AccountStatementsView> getSellerAccountStatementBetweenDate(Integer sellerId, java.sql.Date startDate,
			java.sql.Date endDate) {
		logger.info("SellerBalanceServiceImpl call getSellerAccountStatementBetweenDate  method");
		return convertObjectListToViewList(
				sellerBalanceDao.getSellerAccountStatementBetweenDate(sellerId, startDate, endDate));
	}
	
	
	@Override
	public List<SellerSalesNoticesView> getSellerSalesNoticesBetweenDate(Integer sellerId, char transactionCode,java.sql.Date startDate,
			java.sql.Date endDate) {
		logger.info("SellerBalanceServiceImpl call getSellerSalesNoticesBetweenDate  method");
		return convertObjectListToSalesNoticesViewList(
				sellerBalanceDao.getSellerSalesNoticesBetweenDate(sellerId,transactionCode, startDate, endDate));
	}
	
	
	private List<SellerSalesNoticesView> convertObjectListToSalesNoticesViewList(List<Object[]> objectList) {
		logger.info("SellerBalanceServiceImpl call convertObjectListToSalesNoticesViewList  method");
		List<SellerSalesNoticesView> sellerSalesNoticesViewList = new ArrayList<SellerSalesNoticesView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				SellerSalesNoticesView sellerSalesNoticesView = new SellerSalesNoticesView(
				TypeConverterUtil.convertObjectToInteger(row[0]),
				DateHelper.dateToStringAmPm(TypeConverterUtil.convertObjectToDate(row[1])));
				sellerSalesNoticesViewList.add(sellerSalesNoticesView);
			}
		}
		return sellerSalesNoticesViewList;
	}
	
	
	
	

	private List<AccountStatementsView> convertObjectListToViewList(List<Object[]> objectList) {
		logger.info("SellerBalanceServiceImpl call convertObjectListToViewList  method");
		List<AccountStatementsView> sellerAccountStatementsViews = new ArrayList<AccountStatementsView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				AccountStatementsView sellerAccountStatementsView = new AccountStatementsView(
						TypeConverterUtil.convertObjectToDate(row[0]), TypeConverterUtil.convertObjectToString(row[1]),
						TypeConverterUtil.convertObjectToInteger(row[2]),
						TypeConverterUtil.convertObjectToString(row[3]),
						TypeConverterUtil.convertObjectToString(row[4]),
						TypeConverterUtil.convertObjectToInteger(row[5]),
						TypeConverterUtil.convertObjectToFloat(row[6]),
						TypeConverterUtil.convertObjectToBigDecimal(row[7]),
						TypeConverterUtil.convertObjectToFloat(row[8]), TypeConverterUtil.convertObjectToFloat(row[9]),
						TypeConverterUtil.convertObjectToFloat(row[10]),
						TypeConverterUtil.convertObjectToBigDecimal(row[11]),
						TypeConverterUtil.convertObjectToBigDecimal(row[12]),
						TypeConverterUtil.convertObjectToBigDecimal(row[13]),
						TypeConverterUtil.convertObjectToBigDecimal(row[14]));
				sellerAccountStatementsViews.add(sellerAccountStatementsView);
			}

		}
		return sellerAccountStatementsViews;
	}

}