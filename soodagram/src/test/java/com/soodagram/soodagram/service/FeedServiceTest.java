package com.soodagram.soodagram.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.domain.repository.AccountRepository;
import com.soodagram.soodagram.domain.repository.FeedFileRepository;
import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.LikeFeedRepository;
import com.soodagram.soodagram.dto.FeedDTO;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {
	@InjectMocks
	private FeedService feedService;
	
	@Mock
	private FeedRepository feedRepository;
	
	@Mock
	private FeedFileRepository feedFileRepository;
	
	@Mock
	private FeedHashtagRepository feedhashtagRepository;
	
	@Mock
	private HashtagRepository hashtagRepository;	
	
	@Mock
	private AccountRepository accountRepository;
	
	@Test
	public void writeFeedTest() throws Exception {
		//given
		FeedDTO feedDTO = createFeedDTO(1L);
		when(feedRepository.save(any())).thenReturn(feedDTO.toFeedEntity());		
		when(feedFileRepository.saveAll(any())).thenReturn(feedDTO.toFeedEntity().getFiles());		
		when(hashtagRepository.save(any())).thenReturn(Hashtag.builder().content("#sample").build());
		
		//when
		feedService.writeFeed(feedDTO.toFeedEntity());
		//then
		verify(feedRepository, times(2)).save(any());
		verify(feedFileRepository).saveAll(any());
		verify(feedhashtagRepository).save(any());
		verify(hashtagRepository).save(any());
	}
	
	@Test
	public void deleteFeedTest() throws Exception {
		//given
		FeedDTO feedDTO = createFeedDTO(1L);
		//when
		feedService.deleteFeed(feedDTO.toFeedEntity());
		//then
		verify(feedRepository).delete(feedDTO.toFeedEntity());
	}
	
	
	
	private FeedDTO createFeedDTO(Long feedNo) {
		FeedFile[] files = {FeedFile.builder().fileId(1L).fileName("test.png").filePath("/images").build()};
		
		return FeedDTO.builder()
				.feedNo(feedNo)
				.content("this is sample feed #sample")
				.writer(Account.builder().accountSeq(1L).accountEmail("sooda@mail.com").accountId("soodaId").build())
				.files(files)
				.build();				
	}
	
	
	
	
}
