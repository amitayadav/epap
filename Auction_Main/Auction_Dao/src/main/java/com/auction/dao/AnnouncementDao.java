package com.auction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.bean.AnnouncementBean;
import com.auction.model.entity.Announcement;

@Repository
public interface AnnouncementDao extends GenericDao<Announcement, Integer> {

	@Query("SELECT an FROM Announcement an WHERE an. modifiedimestamp between :startDate AND  :endDate    ORDER BY  an.modifiedimestamp  DESC")
	public List<Announcement> getAnnouncementsBetweenDate(@Param("startDate") Date startDate ,@Param("endDate")Date endDate);

	
	@Modifying
	@Query("UPDATE Announcement SET modifiedimestamp = :modifiedimestamp WHERE announcementId = :announcementId")
	public void updateAnnouncementmodifiedimestampBYId(@Param("modifiedimestamp") Date modifiedimestamp,@Param("announcementId")Integer announcementId); 
}
