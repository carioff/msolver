package com.rootech.msolver.common.util;

import javax.servlet.http.HttpServletRequest;

import com.rootech.msolver.vo.UserVo;

public class SecurityHolder {

	public static final String SES_USER = "SES_USER";
	public static final String SES_USERID = "SES_USERID";
	
	public static String getUserId(HttpServletRequest request) {
		return getString(request, SES_USERID);
	}

	public static void setUserId(HttpServletRequest request, String userId) {
		request.getSession().setAttribute(SES_USERID, userId);
	}

	public static UserVo getUserInfo(HttpServletRequest request) {
		return getUserInfo(request, SES_USER);
	}

	public static void setUserInfo(HttpServletRequest request, UserVo userVo) {
		request.getSession().setAttribute(SES_USER, userVo);
	}
	
	private static String getString(HttpServletRequest req, String attr) {
		return (String) getAttribute(req, attr);
	}
	
	private static UserVo getUserInfo(HttpServletRequest req, String attr) {
		return (UserVo) getAttribute(req, attr);
	}
	private static Object getAttribute(HttpServletRequest req, String attr) {
		return req.getSession().getAttribute(attr);
	}
}
