package com.auction.service;



import java.util.List;
import com.auction.model.bean.RatingsLogBean;
import com.auction.service.generic.GenericService;

public interface RatingsLogService  extends GenericService<RatingsLogBean , Integer>{

	public List<RatingsLogBean> calculateAverageRatingByRatedId(Integer ratedId);
	
	public boolean getRatingCountByRatedId( Integer accountProfileByRatorId, Integer accountProfileByRatedId, Integer dailyAuctionsId);

	public RatingsLogBean getRatingByRatedId( Integer accountProfileByRatorId, Integer accountProfileByRatedId, Integer dailyAuctionsId);
}
