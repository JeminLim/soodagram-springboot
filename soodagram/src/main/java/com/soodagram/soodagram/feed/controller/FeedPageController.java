package com.soodagram.soodagram.feed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soodagram.soodagram.feed.domain.FeedVO;
import com.soodagram.soodagram.feed.service.FeedService;
import com.soodagram.soodagram.user.domain.UserVO;
import com.soodagram.soodagram.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 피드 열람 페이지 컨트롤러
 * @author jeminLim
 * @version 1.0
 */
@Slf4j
@Controller
@RequestMapping("/")
public class FeedPageController {
	
	private final FeedService feedService;
	private final UserService userService;
	
	@Autowired
	public FeedPageController(FeedService feedService, UserService userService) {
		this.feedService = feedService;
		this.userService = userService;
	}	
	
	/**
	 * 피드 열람 페이지 유저 관련 정보(추천 유저, 팔로잉 유저) 데이터 수신
	 * @param model
	 * @param request
	 * @return feed page
	 * @throws Exception
	 */
	@GetMapping("")
	public String feedPageGET(Model model, HttpServletRequest request) throws Exception {
		// 현재 로그인 유저 정보		
		HttpSession httpSession = request.getSession();
		UserVO loginUser = (UserVO) httpSession.getAttribute("login");	
		
		log.info("===========login user info==============");
		log.info(loginUser.toString());
		
		// 다른 유저 추천 리스트
		List<UserVO> recommendList = userService.getRecommendUserList(loginUser);
		model.addAttribute("recommendList", recommendList);
		
		// 팔로잉 리스트
		List<UserVO> followingList = userService.getFollowingList(loginUser);
		model.addAttribute("followingList", followingList);
		log.info("login user image = " + loginUser.getUserImg());
		log.info("===============================================");
		
		return "feed/feed";
	}
	
	/**
	 * 팔로잉 유저의 피드 열람
	 * @param model
	 * @param request
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/feeds/{page}")
	@ResponseBody
	public Map<String, Object> getFeed(Model model, HttpServletRequest request, @PathVariable("page") int page) throws Exception {
		// 현재 로그인 유저 정보		
		HttpSession httpSession = request.getSession();
		UserVO loginUser = (UserVO) httpSession.getAttribute("login");	
		// 팔로잉 유저 피드
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("page", page);
		input.put("userVO", loginUser);
		
		
		List<FeedVO> followingFeed = feedService.getFollowingFeed(input);	
		
		Map<String, Object> result = new HashMap<>();
		result.put("followingFeed", followingFeed);
		result.put("code", "success");
		return result;
	}
	
	/**
	 * 해당 피드 좋아요 갯수
	 * @param feedNo
	 * @return number of feed like
	 * @throws Exception
	 */
	@GetMapping("/feeds/like/{feedNo}")
	@ResponseBody
	public int getCountFeedLike(@PathVariable("feedNo") int feedNo) throws Exception {			
		return feedService.countLikeNo(feedNo);
	}
}
