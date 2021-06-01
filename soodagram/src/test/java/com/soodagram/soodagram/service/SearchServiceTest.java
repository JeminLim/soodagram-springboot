package com.soodagram.soodagram.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

	@InjectMocks
	private SearchService searchService;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private FeedHashtagRepository feedHashtagRepository;
	
	@Mock
	private HashtagRepository hashtagRepository;
	
	@Test
	public void searchUserTest() throws Exception {
		//given
		String keyword = "user";
		//when
		Map<String, Object> result = searchService.search(keyword);
		//then
		verify(accountRepository).findAllByAccountIdContains(any());
	}
	
	@Test
	public void searchHashtagTest() throws Exception {
		//given
		String keyword = "#sample";
		//when
		Map<String, Object> result = searchService.search(keyword);
		//then
		verify(hashtagRepository).findByContentContains(keyword);
		verify(feedHashtagRepository).findAllByHashtagIn(any());
	}
	
	
}
