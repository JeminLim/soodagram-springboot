package com.soodagram.soodagram.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Reply;
import com.soodagram.soodagram.reply.repository.ReplyDAO;

/**
 * 댓글 서비스 구현 객체
 * 댓글 등록 및 댓글 열람
 * @author jeminLim
 * @version 1.0
 */
@Service
public class ReplyServiceImpl implements ReplyService {

	private final ReplyDAO replyDAO;
	
	@Autowired
	public ReplyServiceImpl (ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}
	
	/**
	 * 댓글 등록
	 */
	@Override
	public void writeReply(Reply replyVO) throws Exception {
		replyDAO.writeReply(replyVO);
	}

	/**
	 * 댓글 열람
	 */
	@Override
	public List<Reply> getReply(Map<String, Object> getInput) throws Exception {		
		return replyDAO.getReply(getInput);
	}

}
