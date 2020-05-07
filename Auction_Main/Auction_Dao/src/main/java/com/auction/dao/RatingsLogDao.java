package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.RatingsLog;

@Repository
public interface RatingsLogDao  extends GenericDao<RatingsLog, Integer> {
	
	@Query(value = "SELECT avg(rating_value) As rating  ,sum(rating_value) As ratingTotal ,count(rating_value) As numOfRatings  FROM ratings_log where rated_id =:ratedId", nativeQuery = true)
	public List<Object[]> calculateAverageRatingByRatedId(@Param("ratedId") Integer ratedId);

	@Query("SELECT (count(rat.logId) > 0)  FROM RatingsLog  rat WHERE rat.accountProfileByRatorId.accountId =:accountProfileByRatorId AND rat.accountProfileByRatedId.accountId =:accountProfileByRatedId AND rat.dailyAuctions.dailyAuctionsId =:dailyAuctionsId")
	public boolean getRatingCountByRatedId(@Param("accountProfileByRatorId") Integer accountProfileByRatorId,@Param("accountProfileByRatedId") Integer accountProfileByRatedId,@Param("dailyAuctionsId") Integer dailyAuctionsId);

	@Query("FROM RatingsLog  rat WHERE rat.accountProfileByRatorId.accountId =:accountProfileByRatorId AND rat.accountProfileByRatedId.accountId =:accountProfileByRatedId AND rat.dailyAuctions.dailyAuctionsId =:dailyAuctionsId")
	public RatingsLog getRatingByRatedId(@Param("accountProfileByRatorId") Integer accountProfileByRatorId,@Param("accountProfileByRatedId") Integer accountProfileByRatedId,@Param("dailyAuctionsId") Integer dailyAuctionsId);
}
