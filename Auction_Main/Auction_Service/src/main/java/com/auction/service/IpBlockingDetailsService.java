package com.auction.service;

import com.auction.model.bean.IpBlockingDetailsBean;
import com.auction.service.generic.GenericService;

public interface IpBlockingDetailsService extends GenericService<IpBlockingDetailsBean, String> {

	public void loginFail(String id);

	public boolean isBlocked(String ip, Integer maxFailCount);

	public void deleteBlockedIp(String ip);

}