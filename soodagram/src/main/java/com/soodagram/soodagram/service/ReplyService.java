package com.soodagram.soodagram.service;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.domain.entity.Reply;

public interface ReplyService {
	void writeReply(Reply replyVO) throws Exception;
	List<Reply> getReply(Map<String, Object> getInput) throws Exception;
}
