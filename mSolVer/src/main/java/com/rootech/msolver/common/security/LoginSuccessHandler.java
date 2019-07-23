package com.rootech.msolver.common.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//    public LoginSuccessHandler(String defaultTargetUrl) {
//        setDefaultTargetUrl(defaultTargetUrl);
//    }

	private static int TIME = 60 * 60; // 1 hour

//	@Autowired
//	private UserService userService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		request.getSession().setMaxInactiveInterval(TIME);
		handle(request, response, authentication);
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//		SecurityMember.setIp(getClientIp(request));
//		((SecurityMember) authentication.getPrincipal()).setIp(getClientIp(request));

//		HttpSession session = request.getSession();
//        if (session != null) {
//            String redirectUrl = (String) session.getAttribute("prevPage");
//            if (redirectUrl != null) {
//                session.removeAttribute("prevPage");
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            } else {
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        } else {
//            super.onAuthenticationSuccess(request, response, authentication);
//        }

//		int ret = userService.updateLoginDateBy(((User) auth.getPrincipal()).getUsername());
//		System.out.println(((User) auth.getPrincipal()).getUsername());
//		super.onAuthenticationSuccess(request, response, authentication);
	}

//	public static String getClientIp(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//             ip = request.getHeader("Proxy-Client-IP");
//         }
//         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//             ip = request.getHeader("WL-Proxy-Client-IP");
//         }
//         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//             ip = request.getHeader("HTTP_CLIENT_IP");
//         }
//         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//             ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//         }
//         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//             ip = request.getRemoteAddr();
//         }
//         return ip;
//    }

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				isUser = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			}
		}

		if (isUser) {
			return "/main";
		} else if (isAdmin) {
			return "/main";
		} else {
			throw new IllegalStateException();
		}
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
