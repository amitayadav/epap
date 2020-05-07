package com.auction.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.CommonsUtil;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.TypeConverterUtil;
import com.auction.dao.BuyerBalanceDao;
import com.auction.dao.PurchaseInvoiceDao;
import com.auction.model.bean.BuyerPurchaseInvoiceBean;
import com.auction.model.bean.VatBalanceBean;
import com.auction.model.entity.BuyerPurchaseInvoice;
import com.auction.model.entity.BuyerTransactions;
import com.auction.model.entity.VatBalance;
import com.auction.model.views.PurchaseInvoicesView;
import com.auction.service.BuyerAndBuyerAgentInvoiceService;

@Service
@Transactional
public class BuyerAndBuyerAgentInvoiceServiceImp implements BuyerAndBuyerAgentInvoiceService {

	@Autowired
	private PurchaseInvoiceDao purchaseInvoiceDao;
	
	private AuctionLogger logger = new AuctionLogger(this.getClass());

	@Override
	public Integer save(BuyerPurchaseInvoiceBean bean) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call save method");
		BuyerPurchaseInvoice buyerPurchaseInvoice = purchaseInvoiceDao.save(convertBeanToEntity(bean));
		return (null != buyerPurchaseInvoice ? buyerPurchaseInvoice.getPurchaseInvoiceId(): null);
		
	}

	@Override
	public BuyerPurchaseInvoiceBean findById(Integer id) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call findById method");
		return new BuyerPurchaseInvoiceBean(purchaseInvoiceDao.findOne(id));
	}

	@Override
	public boolean exists(Integer id) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call exists method");
		return purchaseInvoiceDao.exists(id);
	}

	@Override
	public List<BuyerPurchaseInvoiceBean> findAll() {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call findAll method");
		return convertEntityListToBeanList(purchaseInvoiceDao.findAll());
	}

	@Override
	public List<BuyerPurchaseInvoiceBean> findAll(Iterable<Integer> ids) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call findAll method");
		return convertEntityListToBeanList(purchaseInvoiceDao.findAll(ids));
	}

	@Override
	public long count() {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call count method");
		return purchaseInvoiceDao.count();
	}

	@Override
	public void delete(BuyerPurchaseInvoiceBean bean) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call delete method");
		purchaseInvoiceDao.delete(convertBeanToEntity(bean));

	}

	@Override
	public Integer refresh(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	private BuyerPurchaseInvoice convertBeanToEntity(BuyerPurchaseInvoiceBean bean) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call convertBeanToEntity method");
		if (null != bean) { 
			BuyerPurchaseInvoice  buyerPurchaseInvoice =new BuyerPurchaseInvoice();
			buyerPurchaseInvoice.setBuyerTransactions(new  BuyerTransactions(bean.getBuyerTransactionsBean().getTransactionId()));
			buyerPurchaseInvoice.setInvoiceNo(bean.getInvoiceNo());
			buyerPurchaseInvoice.setInvoiceCreation(bean.getInvoiceCreation());
			buyerPurchaseInvoice.setSellerPublicName(bean.getSellerPublicName());
			buyerPurchaseInvoice.setBuyerPublicName(bean.getBuyerPublicName());
			buyerPurchaseInvoice.setBuyerShipperPublicName(bean.getBuyerShipperPublicName());
			return buyerPurchaseInvoice;
		}
		return null;
		}
	
	
	private List<BuyerPurchaseInvoiceBean> convertEntityListToBeanList(List<BuyerPurchaseInvoice> list) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call convertEntityListToBeanList method");
		if (null != list && list.size() > 0) {
			List<BuyerPurchaseInvoiceBean> buyerPurchaseInvoiceList = new ArrayList<BuyerPurchaseInvoiceBean>(0);
			for (BuyerPurchaseInvoice buyerPurchaseInvoice : list) {
				buyerPurchaseInvoiceList.add(new BuyerPurchaseInvoiceBean(buyerPurchaseInvoice));			}
			return buyerPurchaseInvoiceList;
		}
		return null;
	}
	@Override
	public List<PurchaseInvoicesView> getBuyerPurchaseInvoicesReport(Integer transactionId) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call getBuyerPurchaseInvoicesReport method");
		return convertObjectListToPurchaseInvoicesViewList(purchaseInvoiceDao.getBuyerPurchaseInvoicesReport(transactionId));

	}

	private List<PurchaseInvoicesView> convertObjectListToPurchaseInvoicesViewList(List<Object[]> objectList) {
		logger.info("BuyerAndBuyerAgentInvoiceServiceImp  call convertObjectListToPurchaseInvoicesViewList method");
		List<PurchaseInvoicesView> purchaseInvoicesViewList = new ArrayList<PurchaseInvoicesView>();
		if (null != objectList && objectList.size() > 0) {
			for (Object[] row : objectList) {
				PurchaseInvoicesView purchaseInvoicesView = new PurchaseInvoicesView(
						TypeConverterUtil.convertObjectToInteger(row[0]),
						DateHelper.onlydateToString(TypeConverterUtil.convertObjectToDate(row[1])), 
						TypeConverterUtil.convertObjectToString(row[2]), 
						TypeConverterUtil.convertObjectToString(row[3]),
						TypeConverterUtil.convertObjectToString(row[4]),
						TypeConverterUtil.convertObjectToString(row[5]), 
						TypeConverterUtil.convertObjectToFloat(row[6]),
						TypeConverterUtil.convertObjectToInteger(row[7]),
						TypeConverterUtil.convertObjectToBigDecimal(row[8]), 
						TypeConverterUtil.convertObjectToFloat(row[9]),
						TypeConverterUtil.convertObjectToFloat(row[10]), 
						TypeConverterUtil.convertObjectToFloat(row[11]), 
						TypeConverterUtil.convertObjectToBigDecimal(row[12]),
						CommonsUtil.generatePurchaseInvoices(TypeConverterUtil.convertObjectToInteger(row[13])));

				purchaseInvoicesViewList.add(purchaseInvoicesView);
			}
		}

		return purchaseInvoicesViewList;
	}

}
