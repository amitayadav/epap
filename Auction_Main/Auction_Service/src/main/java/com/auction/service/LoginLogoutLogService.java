package com.auction.service;

import java.util.Date;
import java.util.List;
import com.auction.model.bean.LoginLogoutLogBean;
import com.auction.service.generic.GenericService;

public interface LoginLogoutLogService extends GenericService<LoginLogoutLogBean, String> {

	public List<LoginLogoutLogBean> getByAccountIdAndLoginTimestamp(String loginUserid, Date loginTimestamp);

	public void updateLogoutTime(String logoutTimestamp, String loginUserid, String loginTimestamp);

	public LoginLogoutLogBean getLogoutTimestampByAccountId(String loginUserid);
}