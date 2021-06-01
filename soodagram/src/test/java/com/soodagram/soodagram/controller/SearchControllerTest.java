package com.soodagram.soodagram.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.soodagram.soodagram.config.security.CustomLoginFailureHandler;
import com.soodagram.soodagram.config.security.CustomLoginSuccessHandler;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.service.AccountService;
import com.soodagram.soodagram.service.SearchService;

@WebMvcTest(SearchController.class)
@AutoConfigureMockMvc
public class SearchControllerTest {
	
	@InjectMocks
	private SearchController searchController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean(name="SearchService")
	private SearchService searchService;

	@MockBean(name = "UserService")
	private AccountService accountService;
	
	@MockBean
	private CustomLoginSuccessHandler successHandler;
	
	@MockBean
	private CustomLoginFailureHandler failureHandler;
	
	private Account loginUser;
	protected MockHttpSession session;
	private Feed mockFeed;
	
	@BeforeEach
	public void setUp() throws Exception {
		loginUser = createUser("user@mail.com", "1111", "soodaName", "soodaId");
		session = new MockHttpSession();
		session.setAttribute("login", loginUser);
		mockFeed = createFeed();
	}
	
	@Test
	@WithMockUser
	@DisplayName("키워드 검색 성공")
	public void searchTest() throws Exception {
		mockMvc.perform(post("/search").param("keyword", "sooda").with(csrf()))
			.andDo(print())
			.andReturn();		
		
	}
	
	@Test
	@WithMockUser
	@DisplayName("해시태그 페이지로 이동 성공")
	public void getHashtagPageTest() throws Exception {
		//given
		Hashtag hashtag = Hashtag.builder().content("#content").build();
		FeedHashtag feedHashtag = new FeedHashtag(mockFeed, hashtag);
		
		List<FeedHashtag> list = new ArrayList<>();		
		list.add(feedHashtag);		
		
		when(searchService.getHashtagFeed(any())).thenReturn(list);
		
		
		mockMvc.perform(get("/search/{hashtag}", "#content").session(session).with(csrf()))
			.andExpect(view().name("/feed/hashtagFeed"))
			.andExpect(model().attributeExists("feedHashtag"))
			.andExpect(status().isOk());
	}
	
	private Account createUser(String userEmail, String password, String name, String id) {
		Account account = Account.builder()
				.accountEmail(userEmail)
				.accountPassword(password)
				.accountName(name)
				.accountId(id)
				.profileImg("/images/non_profile.png")
				.build();
		return account;
	}
	
	private Feed createFeed() {
		List<FeedFile> files = new ArrayList<>();
		files.add(FeedFile.builder().fileId(1L).fileName("testfile").filePath("/path/file/").build());
		files.add(FeedFile.builder().fileId(2L).fileName("testfile2").filePath("/path/file/").build());
		
		
		return Feed.builder()
				.content("test #content")
				.files(files)
				.build();
	}
}
