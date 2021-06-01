package com.soodagram.soodagram.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.dto.FeedDTO;
import com.soodagram.soodagram.dto.ReplyDTO;
import com.soodagram.soodagram.service.AccountService;
import com.soodagram.soodagram.service.FeedService;
import com.soodagram.soodagram.service.ReplyService;

@WebMvcTest(ReplyController.class)
@AutoConfigureMockMvc
public class ReplyControllerTest {

	@InjectMocks
	private ReplyController replyController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean(name = "UserService")
	private AccountService accountService;
	
	@MockBean
	private CustomLoginSuccessHandler successHandler;
	
	@MockBean
	private CustomLoginFailureHandler failureHandler;
	
	@MockBean
	private FeedService feedService;
	
	@MockBean
	private ReplyService replyService;
	
	private Account loginUser;
	protected MockHttpSession session;
	private FeedDTO mockFeed;
	
	@BeforeEach
	public void setUp() throws Exception {
		loginUser = createUser("user@mail.com", "1111", "soodaName", "soodaId");
		session = new MockHttpSession();
		session.setAttribute("login", loginUser);
		mockFeed = createFeedDTO();		
	}
	
	@Test
	@WithMockUser
	@DisplayName("댓글 작성 성공")
	public void writeReplyTest() throws Exception {
		ReplyDTO reply = ReplyDTO.builder().content("reply test").build();
		
		when(feedService.getFeed(any())).thenReturn(mockFeed.toFeedEntity());
		
		
		mockMvc.perform(post("/reply/{feedNo}", 1L)
				.flashAttr("ReplyDTO", reply)
				.session(session)
				.with(csrf()))
				.andDo(print());
		
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
	
	private FeedDTO createFeedDTO() {
		FeedFile[] files = {FeedFile.builder().fileId(1L).fileName("test file").filePath("/path/file/").build(),
							FeedFile.builder().fileId(2L).fileName("test file2").filePath("/path/file/").build()};
		
		
		return FeedDTO.builder()
				.content("test content")
				.files(files)
				.build();
	}
}
