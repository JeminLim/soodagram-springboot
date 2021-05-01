package com.soodagram.soodagram.reply.repository;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.reply.domain.ReplyVO;

public interface ReplyDAO {
	
	void writeReply(ReplyVO replyVO) throws Exception;
	List<ReplyVO> getReply(Map<String, Object> getInput) throws Exception;
}
