package com.rootech.msolver.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.rootech.msolver.service.UserService;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static int TIME = 60 * 60; // 1 hour

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		request.getSession().setMaxInactiveInterval(TIME);
		
//		int ret = userService.updateLoginDateBy(((User) auth.getPrincipal()).getUsername());
//		System.out.println(((User) auth.getPrincipal()).getUsername());
		super.onAuthenticationSuccess(request, response, auth);
	}

}
