package com.soodagram.soodagram.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.user.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	private UserDAO userDAO;
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		// 로그인 된 유저 정보
		User userVO = userDAO.getLoginUser(authentication.getName());
		// 세션에 유저 정보 저장
		session.setAttribute("login", userVO);
		// 로그인 쿠키 생성, Remember me 활용으로 대체 예정
		log.info("Create cookie");
		Cookie loginCookie = new Cookie("loginCookie", session.getId());
		int amount = 60 * 60 * 24 * 7;
		loginCookie.setMaxAge(amount);
		Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
		userDAO.keepLogin(userVO.getUserEmail(), session.getId(), sessionLimit);
		response.addCookie(loginCookie);
		response.sendRedirect("/");
	}
	
	

	
}
