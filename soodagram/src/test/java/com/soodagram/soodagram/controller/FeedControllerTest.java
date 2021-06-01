package com.soodagram.soodagram.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.soodagram.soodagram.config.security.CustomLoginFailureHandler;
import com.soodagram.soodagram.config.security.CustomLoginSuccessHandler;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.dto.FeedDTO;
import com.soodagram.soodagram.service.AccountService;
import com.soodagram.soodagram.service.FeedService;

@WebMvcTest(FeedController.class)
@AutoConfigureMockMvc
public class FeedControllerTest {
	
	@InjectMocks
	private FeedController feedController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean(name = "FeedService")
	private FeedService feedService;	
	
	@MockBean(name = "UserService")
	private AccountService accountService;
	
	@MockBean
	private CustomLoginSuccessHandler successHandler;
	
	@MockBean
	private CustomLoginFailureHandler failureHandler;
	
	private Account loginUser;
	//private Account otherUser;
	protected MockHttpSession session;
	private FeedDTO mockFeed;
	
	@BeforeEach
	public void setUp() throws Exception {
		loginUser = createUser("user@mail.com", "1111", "soodaName", "soodaId");
		//otherUser = createUser("test@mail.com", "1312", "testName", "testId");
		session = new MockHttpSession();
		session.setAttribute("login", loginUser);
		mockFeed = createFeedDTO();		
	}
	
	@Test
	@WithMockUser
	@DisplayName("피드 포스팅 성공")
	public void uploadFeedTest() throws Exception {		
		mockMvc.perform(post("/feed/post").session(session)
				.flashAttr("feedDTO", mockFeed)
				.with(csrf()))
				.andExpect(redirectedUrl("/user/" + loginUser.getAccountId()));
		
	}
	
	@Test
	@WithMockUser
	@DisplayName("이미지 업로드 성공")
	public void uploadImgTest() throws Exception {
		
		InputStream ins = new FileInputStream("C:\\Users\\wer08\\Desktop\\sample_profile.png");
		byte[] image = IOUtils.toByteArray(ins);
		MockMultipartFile file = new MockMultipartFile("file", "sample_profile.png", MediaType.IMAGE_PNG_VALUE, image);
		
		mockMvc.perform(multipart("/feed/post/img").file(file).with(csrf()).session(session))
		.andExpect(status().is2xxSuccessful());
		
	}
	
	@Test
	@WithMockUser
	@DisplayName("썸네일 생성 성공")
	public void createThumbnailImgTest() throws Exception {
		mockMvc.perform(get("/feed/thumbnail")
				.param("fileName", "C:\\Users\\wer08\\Desktop\\sample_profile.png")
				.with(csrf()))
			.andExpect(status().isCreated());
	}
	
	@Test
	@WithMockUser
	@DisplayName("피드 삭제 성공")
	public void deleteFeedTest() throws Exception {		
		mockMvc.perform(delete("/feed/{feedNo}", 1L).with(csrf()))
		.andExpect(status().isOk());
	}
	

	
	@Test
	@WithMockUser
	@DisplayName("팔로잉 유저 피드 수신 성공")
	public void getFollowingFeedTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/feed/{page}", 1).session(session).with(csrf())).andReturn();
		
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
