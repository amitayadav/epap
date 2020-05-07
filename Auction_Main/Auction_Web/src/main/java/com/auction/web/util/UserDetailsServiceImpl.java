package com.auction.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.commons.enums.ENUM_AccountStatusCodes;
import com.auction.dao.LoginDetailsDao;
import com.auction.model.entity.AccountTypeCodes;
import com.auction.model.entity.LoginDetails;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private LoginDetailsDao loginDetailsDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String loginUserId) throws UsernameNotFoundException {
		LoginDetails loginDetails = loginDetailsDao.findOne(loginUserId);
		if (loginDetails != null) {
			List<GrantedAuthority> authorities = buildUserAuthority(loginDetails.getAccountTypeCodes());
			UserDetails userDetails = buildUserForAuthentication(loginDetails, authorities);
			return userDetails;
		}
		return new User("failed", "failed", false, false, false, false, new ArrayList<GrantedAuthority>());

	}

	// com.auction.model.bean.LoginDetailsBean to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(LoginDetails loginDetails, List<GrantedAuthority> authorities) {
		/*System.out.println("Authority : " + authorities.get(0).getAuthority());*/
		return new User(
				loginDetails.getLoginUserid(), loginDetails.getPassword(), (loginDetails.getAccountStatusCodes()
						.getAccountStatusCode() == ENUM_AccountStatusCodes.ACTIVE.getStatus()),
				true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(AccountTypeCodes accountTypeCodes) {
		/*System.out.println("Role : " + accountTypeCodes.getAccountTypeMeaning());*/
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		result.add(new SimpleGrantedAuthority("ROLE_"+accountTypeCodes.getAccountTypeMeaning().toUpperCase()));
		return result;
	}

}