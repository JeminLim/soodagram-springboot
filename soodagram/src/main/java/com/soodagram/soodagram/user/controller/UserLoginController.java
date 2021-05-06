package com.soodagram.soodagram.user.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.soodagram.soodagram.user.domain.LoginDTO;
import com.soodagram.soodagram.user.domain.UserVO;
import com.soodagram.soodagram.user.service.UserService;

/**
 * 유저 로그인 컨트롤러
 * 로그인 및 로그아웃
 * @author jeminLim
 * @version 1.0
 */
@Controller
@RequestMapping("")
public class UserLoginController {
	
	private final UserService userService;
	
	@Autowired
	public UserLoginController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loginGET(@ModelAttribute("loginDTO") LoginDTO loginDTO) {
		return "/user/login";
	}
	
	/**
	 * 로그인 실패 처리 요청
	 */
	@PostMapping("/login")
	public String loginPOST() throws Exception {
		return "/user/login";		
	}
	
	/**
	 * 로그아웃 처리
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {
		
		Object object = httpSession.getAttribute("login");
		// 로그인 쿠키 정보 삭제
		if(object != null) {
			UserVO userVO = (UserVO) object;
			httpSession.removeAttribute("login");
			httpSession.invalidate();
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
			userService.keepLogin(userVO.getUserEmail(), "none", new Date());			
		}		
		
		return "/user/logout";		
	}
	
}
