package com.soodagram.soodagram;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;
import com.soodagram.soodagram.service.SearchService;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

	@InjectMocks
	private SearchService searchService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private FeedHashtagRepository feedHashtagRepository;
	
	@Mock
	private HashtagRepository hashtagRepository;
	
	@Test
	public void searchUserTest() throws Exception {
		//given
		String keyword = "user";
		//when
		searchService.searchUser(keyword);
		//then
		verify(userRepository).findAllByUserId(any());
	}
	
	@Test
	public void searchHashtagTest() throws Exception {
		//given
		String keyword = "#sample";
		//when
		searchService.searchHashtag(keyword);
		//then
		verify(feedHashtagRepository).findAllByHashtag(any());
	}
	
	
}
