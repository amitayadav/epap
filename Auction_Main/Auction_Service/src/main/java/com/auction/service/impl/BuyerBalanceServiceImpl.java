package com.auction.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.BuyerBalanceDao;
import com.auction.model.bean.BuyerBalanceBean;
import com.auction.model.entity.AccountProfile;
import com.auction.model.entity.BuyerBalance;
import com.auction.model.entity.BuyerBalanceId;
import com.auction.model.entity.TransactionCode;
import com.auction.model.views.AccountStatementsView;
import com.auction.model.views.PurchaseInvoicesView;
import com.auction.service.BuyerBalanceService;

@Service
@Transactional
public class BuyerBalanceServiceImpl implements BuyerBalanceService {

	@Autowired
	private BuyerBalanceDao buyerBalanceDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(BuyerBalanceBean bean) {
		logger.info("BuyerBalanceServiceImpl  call save method");
		BuyerBalance buyerBalance = buyerBalanceDao.save(convertBeanToEntity(bean));
		return (null != buyerBalance ? buyerBalance.getId().getAccountProfile().getAccountId() : null);
	}

	@Override
	public BuyerBalanceBean findById(Integer id) {
		logger.info("BuyerBalanceServiceImpl  call findById method");
		return new BuyerBalanceBean(buyerBalanceDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("BuyerBalanceServiceImpl  call exists method");
		return buyerBalanceDao.exists(id);
	}

	@Override
	public List<BuyerBalanceBean> findAll() {
		logger.info("BuyerBalanceServiceImpl  call findAll method");
		return convertEntityListToBeanList(buyerBalanceDao.findAll());
	}

	@Override
	public List<BuyerBalanceBean> findAll(Iterable<Integer> ids) {
		logger.info("BuyerBalanceServiceImpl  call findAll method");
		return convertEntityListToBeanList(buyerBalanceDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("BuyerBalanceServiceImpl  call count method");
		return buyerBalanceDao.count();
	}

	@Override
	public void delete(BuyerBalanceBean bean) {
		logger.info("BuyerBalanceServiceImpl  call delete method");
		buyerBalanceDao.delete(convertBeanToEntity(bean));
	}

	@Override
	public Integer refresh(Integer id) {
		return null;
	}

	@Override
	public List<BuyerBalanceBean> getByBalanceBetweenDate(Integer accountId, Date startDate, Date endDate) {
		logger.info("BuyerBalanceServiceImpl  call getByBalanceBetweenDate method");
		return convertEntityListToBeanList(buyerBalanceDao.getByBalanceBetweenDate(accountId, startDate, endDate));
	}
	
	@Override
	public BuyerBalanceBean getBuyerBalanceNew(Integer buyer_id) {
		logger.info("BuyerBalanceServiceImpl  call getBuyerBalanceNew method");
		return new BuyerBalanceBean(buyerBalanceDao.getBuyerBalanceNew(buyer_id));
	}

	private BuyerBalance convertBeanToEntity(BuyerBalanceBean bean) {
		logger.info("BuyerBalanceServiceImpl  call convertBeanToEntity method");
		if (null != bean) {

			BuyerBalanceId buyerBalanceId = new BuyerBalanceId();
			buyerBalanceId.setAccountProfile(new AccountProfile(bean.getId().getAccountProfileBean().getAccountId()));
			buyerBalanceId.setTransactionDate(bean.getId().getTransactionDate());

			BuyerBalance buyerBalance = new BuyerBalance(buyerBalanceId);
			buyerBalance.setBalance(bean.getBalance());
			buyerBalance.setDebitCredit(bean.getDebitCredit());
			buyerBalance.setAdvanceBalance(bean.getAdvanceBalance());
			buyerBalance.setTransactionId(bean.getTransactionId());
			buyerBalance.setTransactionCode(new TransactionCode(bean.getTransactionCodeBean().getTransactionCode()));
			buyerBalance.setTransactionDescription(bean.getTransactionDescription());
			buyerBalance.setReserved1(bean.getReserved1());
			buyerBalance.setReserved2(bean.getReserved2());
			buyerBalance.setComments(bean.getComments());
			buyerBalance.setEpapTransactionId(bean.getEpapTransactionId());
			return buyerBalance;
		}
		return null;
	}

	private List<BuyerBalanceBean> convertEntityListToBeanList(List<BuyerBalance> list) {
		logger.info("BuyerBalanceServiceImpl  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<BuyerBalanceBean> balanceBeansList = new ArrayList<BuyerBalanceBean>(0);
			for (BuyerBalance buyerBalance : list) {
				balanceBeansList.add(new BuyerBalanceBean(buyerBalance));
			}
			return balanceBeansList;
		}
		return null;
	}
	
	@Override
	public List<AccountStatementsView> getBuyerAccountStatementBetweenDate(Integer buyerId,java.sql.Date startDate,java.sql.Date endDate) {
		logger.info("BuyerBalanceServiceImpl  call getBuyerAccountStatementBetweenDate method");
		return convertObjectListToViewList(buyerBalanceDao.getBuyerAccountStatementBetweenDate(buyerId,startDate,endDate));
	}
	
	@Override
	public List<PurchaseInvoicesView> getBuyerPurchaseInvoicesBetweenDate(Integer buyerId,java.sql.Date startDate,java.sql.Date endDate,char p) {
		logger.info("BuyerBalanceServiceImpl  call getBuyerPurchaseInvoicesBetweenDate method");
		return convertObjectListToPurchaseInvoicesViewList(buyerBalanceDao.getBuyerPurchaseInvoicesBetweenDate(buyerId,startDate,endDate, p));
	}
	
	
	
	private List<PurchaseInvoicesView> convertObjectListToPurchaseInvoicesViewList(List<Object[]> objectList) {
		logger.info("BuyerBalanceServiceImpl  call convertObjectListToPurchaseInvoicesViewList method");
		List<PurchaseInvoicesView> purchaseInvoicesViewList = new ArrayList<PurchaseInvoicesView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				PurchaseInvoicesView purchaseInvoicesView = new PurchaseInvoicesView(
				TypeConverterUtil.convertObjectToInteger(row[0]),
				DateHelper.dateToStringAmPm(TypeConverterUtil.convertObjectToDate(row[1])));
				purchaseInvoicesViewList.add(purchaseInvoicesView);
			}
			}
		
		return purchaseInvoicesViewList;
	}
	
	 
	private List<AccountStatementsView> convertObjectListToViewList(List<Object[]> objectList) {
		logger.info("BuyerBalanceServiceImpl  call convertObjectListToViewList method");
		List<AccountStatementsView> accountStatementsViews = new ArrayList<AccountStatementsView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				AccountStatementsView accountStatementsView = new AccountStatementsView(
						TypeConverterUtil.convertObjectToDate(row[0]),
						TypeConverterUtil.convertObjectToString(row[1]), 
						TypeConverterUtil.convertObjectToInteger(row[2]),
						TypeConverterUtil.convertObjectToString(row[3]),
						TypeConverterUtil.convertObjectToString(row[4]),
						TypeConverterUtil.convertObjectToInteger(row[5]), 
						TypeConverterUtil.convertObjectToFloat(row[6]), 
						TypeConverterUtil.convertObjectToBigDecimal(row[7]), 
						TypeConverterUtil.convertObjectToFloat(row[8]),
						TypeConverterUtil.convertObjectToFloat(row[9]), 
						TypeConverterUtil.convertObjectToFloat(row[10]), 
						TypeConverterUtil.convertObjectToBigDecimal(row[11]),
						TypeConverterUtil.convertObjectToBigDecimal(row[12]),
						TypeConverterUtil.convertObjectToBigDecimal(row[13]),
						TypeConverterUtil.convertObjectToBigDecimal(row[14]));
						
				accountStatementsViews.add(accountStatementsView);
			}

		}
		return accountStatementsViews;
	}

}