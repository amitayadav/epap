package com.auction.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AgentOwnerBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DayBegingJob extends QuartzJobBean {

	private AgentOwnerService agentOwnerService;
	
	public AgentOwnerService getAgentOwnerService() {
		return agentOwnerService;
	}

	public void setAgentOwnerService(AgentOwnerService agentOwnerService) {
		this.agentOwnerService = agentOwnerService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	Date date = DateHelper.getAuctionDate(InternetTiming.getInternetDateTime(), false);
	/*System.out.println(date);*/
	
	List<AgentOwnerBean> agentOwnerBeanList=agentOwnerService.findAll();
	if(null != agentOwnerBeanList && agentOwnerBeanList.size() > 0) {
		for(AgentOwnerBean agentOwnerBean : agentOwnerBeanList) {
			if(agentOwnerBean.getLimitSpent().doubleValue() !=0.00) {
				agentOwnerBean.setLimitSpent(new BigDecimal(0.00));
				agentOwnerService.save(agentOwnerBean);
			}
			
		}
	}
	}

}