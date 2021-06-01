package com.soodagram.soodagram.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Reply;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.repository.ReplyRepository;
import com.soodagram.soodagram.dto.ReplyDTO;
import com.soodagram.soodagram.service.ReplyService;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {
	
	@InjectMocks
	private ReplyService replyService;
	
	@Mock
	private ReplyRepository replyRepository;
	
	@Test
	public void writeReplyTest() throws Exception {
		//given
		Feed feed = Feed.builder()
				.feedNo(1L)
				.content("content")
				.build();
		Account account = Account.builder()
				.accountSeq(1L)
				.accountId("user")
				.build();
		ReplyDTO replyDTO = createReply(1L, feed, account);
		when(replyRepository.save(any())).thenReturn(replyDTO.toEntity());
		//when
		Reply savedReply = replyService.writeReply(replyDTO.toEntity());		
		
		//then
		Assertions.assertEquals(replyDTO.getContent(), savedReply.getContent());
		Assertions.assertEquals(replyDTO.getFeed(), savedReply.getFeed());
		Assertions.assertEquals(replyDTO.getWriter(), savedReply.getWriter());
		
	}
	
	private ReplyDTO createReply(Long fakeId, Feed feed, Account writer) {
		return ReplyDTO.builder()
				.replyNo(fakeId)
				.content("reply")
				.feed(feed)
				.writer(writer)
				.build();
	}
}
