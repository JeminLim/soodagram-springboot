package com.soodagram.soodagram.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.dto.ReplyDTO;
import com.soodagram.soodagram.service.FeedService;
import com.soodagram.soodagram.service.ReplyService;

/**
 * 댓글 관련 컨트롤러
 * 댓글 등록 및 열람
 * @author jeminLim
 * @version 1.0
 */
@Controller
@RequestMapping("/reply")
public class ReplyController {
	
	private final ReplyService replyService;
	private final FeedService feedService;
	
	@Autowired
	public ReplyController(ReplyService replyService, FeedService feedService) {
		this.replyService = replyService;
		this.feedService = feedService;
	}
	
	/**
	 * 해당 피드에 뎃글 등록
	 * @param feedNo
	 * @param replyVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{feedNo}")
	@ResponseBody
	public ReplyDTO writeReply(@PathVariable("feedNo") Long feedNo, @ModelAttribute("ReplyDTO") ReplyDTO replyDTO, HttpServletRequest request) throws Exception{
		
		// 이용자 식별
		HttpSession httpSession = request.getSession();
		Account loginUser = (Account) httpSession.getAttribute("login");
		replyDTO.setWriter(loginUser);
		replyDTO.setFeed(feedService.getFeed(feedNo));
		
		replyService.writeReply(replyDTO.toEntity());
		
		return replyDTO;
		
			
	}
	
	/**
	 * 해당 피드 댓글 열람
	 * @param feedNo
	 * @param loadNum
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{feedNo}")
	@ResponseBody
	public List<ReplyDTO> getReply(@PathVariable("feedNo") Long feedNo, @RequestParam("curPage") int curPage) throws Exception {						
		return replyService.getReplies(feedNo, curPage);
	}
	
}
