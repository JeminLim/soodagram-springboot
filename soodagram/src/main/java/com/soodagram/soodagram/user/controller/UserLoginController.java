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
@RequestMapping("/user")
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
	 * 로그인 처리 함수
	 * @param loginDTO
	 * @param httpSession
	 * @param model
	 * @param redirectAttributes
	 * @throws Exception
	 */
	@PostMapping("/login")
	public String loginPOST(LoginDTO loginDTO, HttpSession httpSession, Model model, RedirectAttributes redirectAttributes) throws Exception {
		
		UserVO userVO = userService.login(loginDTO.getUserEmail());
		
		if(userVO == null || !BCrypt.checkpw(loginDTO.getUserPw(), userVO.getUserPw())) {
			return "/user/loginPost";
		}
		
		httpSession.setAttribute("login", userVO);
		model.addAttribute("user", userVO);
		
		// 로그인 쿠키 등록
		int amount = 60 * 60 * 24 * 7;
		Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));
		userService.keepLogin(userVO.getUserEmail(), httpSession.getId(), sessionLimit);
		
		return "redirect:/";
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
			loginCookie.setPath("/");
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
			userService.keepLogin(userVO.getUserEmail(), "none", new Date());			
		}		
		
		return "/user/logout";		
	}
	
}
