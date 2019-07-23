package com.rootech.msolver.common.auth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rootech.msolver.common.dto.CustomUserDetails;
import com.rootech.msolver.common.dto.SecurityMember;
import com.rootech.msolver.dao.UserDao;
import com.rootech.msolver.vo.UserVo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final String ROLE_PREFIX = "ROLE_";

	@Resource(name = "UserDao")
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

			return new SecurityMember(user);
		} else {
			throw new UsernameNotFoundException("User not found.");
		}

//		return builder.build();
	}

	private CustomUserDetails findUserbyUsername(String username) {
		UserVo userVo = new UserVo();
//		if (username.equalsIgnoreCase("admin")) {
		if (StringUtils.isNotBlank(username)) {
			userVo.setUserId(username);
			return userDao.selectUser(userVo);
		}
		return null;
	}

	private static List<GrantedAuthority> makeGrantedAuthority(List<String> roles) {
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role)));
		return list;
	}

}
