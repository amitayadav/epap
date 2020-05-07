package com.auction.service;

import java.sql.Blob;
import java.util.List;
import com.auction.model.bean.AccountProfileBean;
import com.auction.service.generic.GenericService;

public interface AccountProfileService extends GenericService<AccountProfileBean, Integer> {

	public AccountProfileBean findOwnerAccountProfileByAgentAccountId(Integer accountId);

	public Integer findOwnerAccountIdByAgentAccountId(Integer accountId);

	public String findOwnerPublicNameByAgentAccountId(Integer accountId);

	public List<AccountProfileBean> findByAccountTypeCodes(Character[] accountTypeCodes);

	public Blob getByEditProfileImage(String profileImage, Integer accountId);

	public void updateRatingByAccountProflieId(Float rating, Integer ratingTotal, Integer numOfRatings, Integer accountId);

	public Boolean checkValidateOtpByAccountProfiledId(Integer accountId, Integer otp);
}