package com.soodagram.soodagram.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.service.SearchService;

/**
 * 검색 관련 컨트롤러
 * @author jeminLim
 * @version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	private final SearchService searchService;
	
	@Autowired
	public SearchController (SearchService searchService) {
		this.searchService = searchService;
	}
	
	/**
	 * 주어진 키워드로 유저 또는 해시태그 검색
	 * @param keyword
	 * @return search result
	 * @throws Exception
	 */
	@PostMapping("")
	@ResponseBody
	public Map<String, Object> search(@RequestParam("keyword") String keyword) throws Exception {
		return searchService.search(keyword);		
	}
	
	/**
	 * 해시태그에 관련 있는 피드 열람
	 * @param hashtag
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{hashtag}")
	public String getHashtagFeed(@PathVariable("hashtag") String content, Model model) throws Exception {
		
		content = URLDecoder.decode(content, "UTF-8");
		
		List<FeedHashtag> feedHashtag = searchService.getHashtagFeed(content);
		model.addAttribute("feedHashtag", feedHashtag);
		
		
		return "/feed/hashtagFeed";
	}
	
	
}
