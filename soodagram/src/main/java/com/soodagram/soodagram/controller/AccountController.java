package com.soodagram.soodagram.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soodagram.soodagram.commons.util.UploadFileUtils;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.dto.AccountDTO;
import com.soodagram.soodagram.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class AccountController {
	
	private final AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	

	@GetMapping("")
	public String mainPage(Model model, HttpServletRequest request) {
		
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account) httpSession.getAttribute("login");
		
		List<AccountDTO.AccountBasicInfo> recommendList = accountService.recommendUser(loginUser);
		model.addAttribute("recommendList", recommendList);
		
		return "/feed/feed";
	}
	
	/*
	 * 특정 유저 상세 페이지
	 */
	@GetMapping("/user/{userId}")
	public String accountGET(@PathVariable("userId") String userId, Model model, HttpServletRequest request, Principal principal) throws Exception {
		// 로그인 계정
		AccountDTO.AccountBasicInfo loginUser = accountService.getAccountInfoByEmail(principal.getName());
	
		// 열람할 타겟 유저
		AccountDTO.AccountBasicInfo targetUser = (loginUser.getAccountId().equals(userId)) ? loginUser : accountService.getAccountInfoById(userId);		
		
		
		log.info("===================Browse User=======================");
		log.info("login user(" + loginUser.getAccountId() + ") browse target user(" + loginUser.getAccountId()+")");
		log.info(targetUser.getAccountId() + " account browse");
		log.info(targetUser.getProfileImg());
		log.info("number of writed feed = " + targetUser.getWrittenFeed().size());
		log.info("number of follower = " + targetUser.getFollowers().size());
		log.info("number of following user = " + targetUser.getFollowings());
		
		log.info("=====================================================");
		
		// 타겟 == 로그인 사용자 경우, 본인 프로필 계정으로 이동
		if(loginUser.equals(targetUser)) {
			List<AccountDTO.AccountBasicInfo> recommendList = accountService.recommendUser(targetUser.toEntity());
			model.addAttribute("recommendList", recommendList);
			return "profile/profile";
		} 	
		// 타인 계정 탐방
		model.addAttribute("targetUser", targetUser);
		return "feed/userFeed";

	}	
	
	@PatchMapping("/user/profile")
	public String updateProfile(@ModelAttribute AccountDTO.AccountProfileInfo profile, RedirectAttributes redirectAttributes) throws Exception {
		
		log.info("received user data = " + profile.toString());
		
		accountService.updateUser(profile.toEntity());
				
		return "redirect:/profile";
	}

	@PostMapping(value="/user/profile/img", produces="text/plain;charset=UTF-8")
	@ResponseBody 
	public ResponseEntity<String> uploadUserImg(MultipartFile file, HttpServletRequest request, @AuthenticationPrincipal AccountDTO.Login login) throws Exception {
		
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account)httpSession.getAttribute("login");
		
		ResponseEntity<String> entity = null;
		try {
			String savedFilePath = UploadFileUtils.uploadFile(file, request);
			loginUser.setProfileImg("/resources/dist/upload/media" + savedFilePath);
			accountService.updateUser(loginUser);			
			entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	@PostMapping("/user/regist")
	public String registerPOST(@ModelAttribute("regist") AccountDTO.Regist regist, Model model, RedirectAttributes redirectAttributes) throws Exception {
		log.info(regist.getAccountEmail() + " " + regist.getAccountPassword());
		accountService.registUser(regist.toEntity());		
		return "redirect:/user/login";
	}	
		
	@PostMapping("/regist/check/email")
	@ResponseBody 
	public boolean duplicateEmail(String userEmail){		
		try {			
			boolean result = accountService.emailCheck(userEmail);
			return result;			
		} catch (Exception e) {			
			e.printStackTrace();
			return false;			
		}		
	}
	
	@PostMapping("/user/regist/check/id")
	@ResponseBody 
	public boolean duplicateId(String userId){		
		try {			
			boolean result = accountService.idCheck(userId);
			return result;			
		} catch (Exception e) {			
			e.printStackTrace();
			return false;			
		}		
	}
	
	@PostMapping("/user/relation/like")
	@ResponseBody
	public int likeFeed(HttpServletRequest request, @RequestParam("feedNo") Long feedNo) throws Exception {
		
		// 현재 로그인 유저 정보		
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account) httpSession.getAttribute("login");				
		
		return accountService.likeFeed(loginUser, feedNo);		
	}

	@PostMapping("/user/relation/follow")
	@ResponseBody
	public int followUser(String targetEmail, Model model, HttpServletRequest request) throws Exception{
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account) httpSession.getAttribute("login");
		
		log.info("=======================Follow relation======================");
		log.info("based user email = " + loginUser.getAccountEmail());
		log.info("target user email = " + targetEmail);
		log.info("============================================================");
				
		return accountService.userFollow(loginUser, targetEmail);
	}
	
	
}
