package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.constant.CommonConstants;
import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.CommonsUtil;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.SalesNoticesDao;
import com.auction.model.bean.SellerSalesNoticeBean;
import com.auction.model.entity.SellerSalesNotice;
import com.auction.model.entity.SellerTransactions;
import com.auction.model.views.SellerSalesNoticesView;
import com.auction.service.SellerAndSellerAgentSalesNoticesService;

@Service
@Transactional
public class SellerAndSellerAgentSalesNoticesServiceImpl implements SellerAndSellerAgentSalesNoticesService {

	@Autowired
	private SalesNoticesDao salesNoticesDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(SellerSalesNoticeBean bean) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call save  method");
		SellerSalesNotice sellerSalesNotice = salesNoticesDao.save(convertBeanToEntity(bean));
		return (null != sellerSalesNotice ? sellerSalesNotice.getSalesNoticeId(): null);
	}

	@Override
	public SellerSalesNoticeBean findById(Integer id) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call findById  method");
		return new SellerSalesNoticeBean(salesNoticesDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call exists  method");
		return salesNoticesDao.exists(id);
	}

	@Override
	public List<SellerSalesNoticeBean> findAll() {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call findAll  method");
		return convertEntityListToBeanList(salesNoticesDao.findAll());
	}

	@Override
	public List<SellerSalesNoticeBean> findAll(Iterable<Integer> ids) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call findAll  method");
		return convertEntityListToBeanList(salesNoticesDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call count  method");
		return salesNoticesDao.count();
	}

	@Override
	public void delete(SellerSalesNoticeBean bean) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call delete  method");
		salesNoticesDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<SellerSalesNoticesView> getSellersalesNoticeRepor(Integer transactionId) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call getSellersalesNoticeRepor  method");
		return convertObjectListToSalesNoticesViewList(salesNoticesDao.getSellersalesNoticeRepor(transactionId));
	}
	
	private List<SellerSalesNoticesView> convertObjectListToSalesNoticesViewList(List<Object[]> objectList) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call convertObjectListToSalesNoticesViewList  method");
		List<SellerSalesNoticesView> sellerSalesNoticesViewList = new ArrayList<SellerSalesNoticesView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				SellerSalesNoticesView sellerSalesNoticesView = new SellerSalesNoticesView(
						TypeConverterUtil.convertObjectToString(row[0]),
						TypeConverterUtil.convertObjectToString(row[1]),
						TypeConverterUtil.convertObjectToString(row[2]),
						TypeConverterUtil.convertObjectToInteger(row[3]),
						TypeConverterUtil.convertObjectToString(row[4]),
						DateHelper.onlydateToString(TypeConverterUtil.convertObjectToDate(row[5])),
						TypeConverterUtil.convertObjectToFloat(row[6]),
						TypeConverterUtil.convertObjectToInteger(row[7]),
						TypeConverterUtil.convertObjectToBigDecimal(row[8]),
						TypeConverterUtil.convertObjectToFloat(row[9]),
						TypeConverterUtil.convertObjectToFloat(row[10]),
						TypeConverterUtil.convertObjectToFloat(row[11]),
						TypeConverterUtil.convertObjectToBigDecimal(row[12]),
						CommonsUtil.generatesalesNoticesNo(TypeConverterUtil.convertObjectToInteger(row[13])));
				sellerSalesNoticesViewList.add(sellerSalesNoticesView);
			}
		}
		return sellerSalesNoticesViewList;
	}
	private SellerSalesNotice convertBeanToEntity(SellerSalesNoticeBean bean) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call convertBeanToEntity  method");
		if (null != bean) { 
			SellerSalesNotice  sellerSalesNotice =new SellerSalesNotice();
			sellerSalesNotice.setSellerTransactions(new SellerTransactions(bean.getSellerTransactionsBean().getTransactionId()));
			sellerSalesNotice.setNoticeNo(bean.getNoticeNo());
			sellerSalesNotice.setNoticeCreation(bean.getNoticeCreation());
			sellerSalesNotice.setSellerPublicName(bean.getSellerPublicName());
			sellerSalesNotice.setBuyerPublicName(bean.getBuyerPublicName());
			sellerSalesNotice.setSellerShipperPublicName(bean.getSellerShipperPublicName());
			return sellerSalesNotice;
		}
		return null;
		}
	
	
	private List<SellerSalesNoticeBean> convertEntityListToBeanList(List<SellerSalesNotice> list) {
		logger.info("SellerAndSellerAgentSalesNoticesServiceImpl call convertEntityListToBeanList  method");
		if (null != list && list.size() > 0) {
			List<SellerSalesNoticeBean> sellerSalesNoticeList = new ArrayList<SellerSalesNoticeBean>(0);
			for (SellerSalesNotice sellerSalesNotice : list) {
				sellerSalesNoticeList.add(new SellerSalesNoticeBean(sellerSalesNotice));			}
			return sellerSalesNoticeList;
		}
		return null;
	}

	
}
