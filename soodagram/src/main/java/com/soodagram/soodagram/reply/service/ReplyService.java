package com.soodagram.soodagram.reply.service;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.reply.domain.ReplyVO;

public interface ReplyService {
	void writeReply(ReplyVO replyVO) throws Exception;
	List<ReplyVO> getReply(Map<String, Object> getInput) throws Exception;
}
