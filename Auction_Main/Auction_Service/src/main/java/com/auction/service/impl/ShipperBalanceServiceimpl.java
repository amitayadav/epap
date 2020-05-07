package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.ShipperBalanceDao;
import com.auction.model.bean.ShipperBalanceBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.ShipperBalance;
import com.auction.model.entity.ShipperBalanceId;
import com.auction.model.entity.ShipperTransactions;
import com.auction.model.entity.TransactionCode;
import com.auction.model.views.AccountStatementsView;
import com.auction.service.ShipperBalanceService;

@Service
@Transactional
public class ShipperBalanceServiceimpl implements ShipperBalanceService {

	@Autowired
	private ShipperBalanceDao shipperBalanceDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(ShipperBalanceBean bean) {
		logger.info("ShipperBalanceServiceimpl call save  method");
		ShipperBalance shipperBalance = shipperBalanceDao.save(convertBeanToEntity(bean));
		return ((null != shipperBalance && null != shipperBalance.getId().getAccountProfile().getAccountId())
				? shipperBalance.getId().getAccountProfile().getAccountId()
				: null);
	}

	@Override
	public ShipperBalanceBean findById(Integer id) {
		logger.info("ShipperBalanceServiceimpl call findById  method");
		return new ShipperBalanceBean(shipperBalanceDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("ShipperBalanceServiceimpl call exists  method");
		return shipperBalanceDao.exists(id);
	}

	@Override
	public List<ShipperBalanceBean> findAll() {
		logger.info("ShipperBalanceServiceimpl call findAll  method");
		return convertEntityListToBeanList(shipperBalanceDao.findAll());
	}

	@Override
	public List<ShipperBalanceBean> findAll(Iterable<Integer> ids) {
		logger.info("ShipperBalanceServiceimpl call findAll  method");
		return convertEntityListToBeanList(shipperBalanceDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("ShipperBalanceServiceimpl call count  method");
		return shipperBalanceDao.count();
	}

	@Override
	public void delete(ShipperBalanceBean bean) {
		logger.info("ShipperBalanceServiceimpl call delete  method");
		shipperBalanceDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		logger.info("ShipperBalanceServiceimpl call refresh  method");
		return id;
	}

	@Override
	public List<ShipperBalanceBean> getByBalanceBetweenDate(Integer accountId, Date startDate, Date endDate) {
		logger.info("ShipperBalanceServiceimpl call getByBalanceBetweenDate  method");
		return convertEntityListToBeanList(shipperBalanceDao.getByBalanceBetweenDate(accountId, startDate, endDate));
	}

	@Override
	public ShipperBalanceBean getShipperBalanceNew(Integer shipper_id) {
		logger.info("ShipperBalanceServiceimpl call getShipperBalanceNew  method");
		return new ShipperBalanceBean(shipperBalanceDao.getShipperBalanceNew(shipper_id));
	}

	private ShipperBalance convertBeanToEntity(ShipperBalanceBean bean) {
		logger.info("ShipperBalanceServiceimpl call convertBeanToEntity  method");
		if (null != bean) {
			ShipperBalanceId shipperBalanceId = new ShipperBalanceId();
			shipperBalanceId.setAccountProfile(new AccountProfile(bean.getId().getAccountProfileBean().getAccountId()));
			shipperBalanceId.setTransactionDate(bean.getId().getTransactionDate());

			ShipperBalance shipperBalance = new ShipperBalance(shipperBalanceId);
			shipperBalance.setBalance(bean.getBalance());
			shipperBalance.setComments(bean.getComments());
			shipperBalance
					.setCreatedAccountProfile(new AccountProfile(bean.getCreatedAccountProfileBean().getAccountId()));
			shipperBalance.setDebitCredit(bean.getDebitCredit());
			shipperBalance.setAdvanceBalance(bean.getAdvanceBalance());
			shipperBalance.setTransactionCode(new TransactionCode(bean.getTransactionCodeBean().getTransactionCode()));
			shipperBalance.setTransactionDescription(bean.getTransactionDescription());
			shipperBalance.setEpapTransactionId(bean.getEpapTransactionId());
			if (null != bean.getShipperTransactionsBean()
					&& null != bean.getShipperTransactionsBean().getTransactionId()
					&& bean.getShipperTransactionsBean().getTransactionId() > 0) {
				shipperBalance.setShipperTransactions(
						new ShipperTransactions(bean.getShipperTransactionsBean().getTransactionId()));
			}

			if (null != bean.getUpdatedAccountProfileBean()
					&& null != bean.getUpdatedAccountProfileBean().getAccountId()
					&& bean.getUpdatedAccountProfileBean().getAccountId() > 0) {
				shipperBalance.setUpdatedAccountProfile(
						new AccountProfile(bean.getUpdatedAccountProfileBean().getAccountId()));
			}
			return shipperBalance;
		}
		return null;
	}

	private List<ShipperBalanceBean> convertEntityListToBeanList(List<ShipperBalance> list) {
		logger.info("ShipperBalanceServiceimpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<ShipperBalanceBean> shipperBalanceBeansList = new ArrayList<ShipperBalanceBean>(0);
			for (ShipperBalance balance : list) {
				shipperBalanceBeansList.add(new ShipperBalanceBean(balance));
			}
			return shipperBalanceBeansList;
		}
		return null;
	}

	@Override
	public List<AccountStatementsView> getShipperAccountStatementBetweenDate(Integer shipperId, java.sql.Date startDate,java.sql.Date endDate) {
		logger.info("ShipperBalanceServiceimpl call getShipperAccountStatementBetweenDate  method");
		return convertObjectListToViewList(
				shipperBalanceDao.getShipperAccountStatementBetweenDate(shipperId, startDate, endDate));
	}

	private List<AccountStatementsView> convertObjectListToViewList(List<Object[]> objectList) {
		logger.info("ShipperBalanceServiceimpl call convertObjectListToViewList  method");
		List<AccountStatementsView> accountStatementsViews = new ArrayList<AccountStatementsView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				AccountStatementsView accountStatementsView = new AccountStatementsView(
						TypeConverterUtil.convertObjectToDate(row[0]), TypeConverterUtil.convertObjectToString(row[1]),
						TypeConverterUtil.convertObjectToInteger(row[2]),
						TypeConverterUtil.convertObjectToString(row[3]),
						TypeConverterUtil.convertObjectToString(row[4]),
						TypeConverterUtil.convertObjectToBigDecimal(row[5]),
						TypeConverterUtil.convertObjectToFloat(row[6]), TypeConverterUtil.convertObjectToFloat(row[7]),
						TypeConverterUtil.convertObjectToBigDecimal(row[8]),
						TypeConverterUtil.convertObjectToBigDecimal(row[9]),
						TypeConverterUtil.convertObjectToBigDecimal(row[10]),
						TypeConverterUtil.convertObjectToBigDecimal(row[11]));
				accountStatementsViews.add(accountStatementsView);
			}

		}
		return accountStatementsViews;
	}

}