package com.soodagram.soodagram.commons.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.service.UserService;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession httpSession = request.getSession();
		Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
		
		if(loginCookie != null) {
			User userVO = userService.checkLoginBefore(loginCookie.getValue());
			if(userVO != null) {
				httpSession.setAttribute("login", userVO);
			}
		}
		
		if(httpSession.getAttribute("login") == null) {
			logger.info("current user is not logged");
			response.sendRedirect("/login");
			return false;
		} 		
		
		return true;		
	}	
	
	
}
