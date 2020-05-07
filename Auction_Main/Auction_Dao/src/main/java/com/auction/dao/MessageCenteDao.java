package com.auction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auction.model.entity.MessageCenter;

@Repository
public interface MessageCenteDao extends GenericDao<MessageCenter, Integer> {

	//@Query("FROM MessageCenter WHERE accountProfileByFromAccountId.accountId=:fromAccountId AND accountProfileByToAccountId.accountId=:toAccountId")
	@Query(value ="SELECT * FROM message_center where (from_account_id =:fromAccountId And to_account_id =:toAccountId) Or (from_account_id =:senderAccountId And to_account_id =:receiverAccountId) order by message_timestamp",nativeQuery = true)
	public List<MessageCenter> getAllSenderMessageByAccountId(@Param("fromAccountId") Integer fromAccountId, @Param("toAccountId") Integer toAccountId,@Param("senderAccountId") Integer senderAccountId,@Param("receiverAccountId") Integer receiverAccountId);

}
