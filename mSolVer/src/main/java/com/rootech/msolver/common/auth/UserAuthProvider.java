package com.rootech.msolver.common.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rootech.msolver.common.dto.CustomUserDetails;

public class UserAuthProvider extends DaoAuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthProvider.class);
	
	@Autowired 
	private CustomUserDetailsService userAuthService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		return super.authenticate(authentication);

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		logger.debug("::::::: input username: {}  password: {}" , username, password);
		
		User user = (User) userAuthService.loadUserByUsername(username);

		logger.debug("loadUserByUsername ::::::: 2");
		
		if(!user.isEnabled()) {
			logger.debug("isEnabled or isCredentialsNonExpired :::::::: false!");
			throw new AuthenticationCredentialsNotFoundException(username);
		}
		
		// TODO need add Credentials checks 190617
//		if(!user.isCredentialsNonExpired()) {
//			logger.debug("isEnabled or isCredentialsNonExpired :::::::: false!");
//			throw new AuthenticationCredentialsNotFoundException(username);
//		}
		
		if (!passwordEncoder.matches(password, user.getPassword())) {
			logger.debug("matchPassword :::::::: false!");
			throw new BadCredentialsException(username);
		}

		return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
