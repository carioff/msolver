package com.rootech.msolver.common.auth;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rootech.msolver.common.dto.CustomUserDetails;
import com.rootech.msolver.dao.UserDao;
import com.rootech.msolver.vo.UserVo;

public class CustomUserDetailsService implements UserDetailsService {

	@Resource(name="UserDao")
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails user = findUserbyUsername(username);

//		UserBuilder builder = null;
		if (user != null) {
//			builder = org.springframework.security.core.userdetails.User.withUsername(username);
//			user.password(new BCryptPasswordEncoder().encode(user.getPassword()));
//			builder.password(user.getPassword());
//			builder.authorities(user.getAuthorities());
			return user;
		} else {
			throw new UsernameNotFoundException("User not found.");
		}

//		return builder.build();
	}

	private CustomUserDetails findUserbyUsername(String username) {
		UserVo userVo = new UserVo();
//		if (username.equalsIgnoreCase("admin")) {
		if (StringUtils.isNotBlank(username)){
			userVo.setUserId(username);
			return userDao.selectUser(userVo);
		}
		return null;
	}

}
