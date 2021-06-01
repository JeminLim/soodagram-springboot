package com.soodagram.soodagram.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Reply;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.ReplyRepository;
import com.soodagram.soodagram.dto.ReplyDTO;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private FeedRepository feedRepository;
	
	public Reply writeReply(Reply writtenReply) throws Exception {			
		Reply reply = replyRepository.save(writtenReply);
		reply.getFeed().getReplies().add(reply);
		return reply;
	}
	
	public List<ReplyDTO> getReplies(Long feedNo, int page) throws Exception {
		Feed feed = feedRepository.findByFeedNo(feedNo).orElseThrow(() -> new NotFoundException());
		return ReplyDTO.of(replyRepository.findByFeed(feed, PageRequest.of(page, 3, Sort.Direction.DESC)));
	}
}
