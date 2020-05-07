package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.Comments;

@Repository
public interface CommentsDao extends GenericDao<Comments, Integer> {

	List<Comments> findByAccountProfileByCreatedByAccountId(Integer accountId);
	@Query("SELECT cmt FROM Comments cmt WHERE cmt.accountProfileByAccountId.accountId = :accountId or cmt.accountProfileByCreatedBy.accountId = :accountId")
	//@Query("SELECT cmt FROM Comments cmt INNER JOIN cmt.accountProfileByAccountId department WHERE department.accountId = :accountId")
	List<Comments> getByAccountProfileByAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT cmt FROM Comments cmt WHERE cmt.accountProfileByAccountId.accountId = :accountId")
	List<Comments> getByInboxAccountProfileByAccountId(@Param("accountId") Integer accountId);

	@Query("SELECT cmt FROM Comments cmt WHERE cmt.accountProfileByCreatedBy.accountId = :accountId")
	List<Comments> getBySentAccountProfileByAccountId(@Param("accountId") Integer accountId);

}

