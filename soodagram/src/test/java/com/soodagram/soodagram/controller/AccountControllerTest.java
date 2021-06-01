package com.soodagram.soodagram.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.soodagram.soodagram.config.security.CustomLoginFailureHandler;
import com.soodagram.soodagram.config.security.CustomLoginSuccessHandler;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.repository.AccountRepository;
import com.soodagram.soodagram.dto.AccountDTO;
import com.soodagram.soodagram.service.AccountService;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {
	
	@InjectMocks
	private AccountController accountController;
	
	@Autowired
	private MockMvc mockMvc;
		
	@MockBean(name = "UserService")
	private AccountService accountService;
	
	@MockBean
	private CustomLoginSuccessHandler successHandler;
	
	@MockBean
	private CustomLoginFailureHandler failureHandler;
	
	@MockBean
	private AccountRepository accountRepository;	

	private Account loginUser;
	protected MockHttpSession session;
	
	@BeforeEach
	void setUp() throws Exception {
		loginUser = createUser("user@mail.com", "1111");
		session = new MockHttpSession();
		session.setAttribute("login", loginUser);	
	}
	
	@Test
	@DisplayName("로그인 페이지 로딩")
	void loginPageGetTest() throws Exception {
		mockMvc.perform(get("/login").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk());
	}
	@Test
	@WithMockUser
	@DisplayName("회원가입 페이지 로딩")
	void registerPageGetTest() throws Exception {
		mockMvc.perform(get("/regist").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	@DisplayName("회원가입 신청")
	void userRegistTest() throws Exception {
		//given
		AccountDTO.Regist registForm = AccountDTO.Regist.builder()
				.accountEmail("sooda@mail.com")
				.accountName("kimsooda")
				.accountId("sooda")
				.accountPassword("1111")
				.build();
		
		//when
		final ResultActions actions = mockMvc.perform(post("/user/regist")
				.param("userEmail", registForm.getAccountEmail())
				.param("userName", registForm.getAccountName())
				.param("userId", registForm.getAccountId())
				.param("userPw", registForm.getAccountPassword())
				.with(csrf()));
		//then
		actions.andExpect(redirectedUrl("/user/login"))
			   .andDo(print());
	}
	
	@Test
	@WithAnonymousUser
	@DisplayName("로그인 성공 테스트")
	void userLoginTest() throws Exception {
		//given
		Account account = this.createUser("sooda@mail.com", "1111");
		
		//when & then
		mockMvc.perform(formLogin().user(account.getAccountEmail()).password("1111"))
				.andExpect(authenticated());
	}
	
	@Test
	@WithAnonymousUser
	@DisplayName("로그인 실패 테스트")
	void userLoginFailTest() throws Exception {
		//given
		Account account = this.createUser("sooda@mail.com", "1111");
		
		//when & then
		mockMvc.perform(formLogin().user(account.getAccountEmail()).password("1234"))
			.andExpect(unauthenticated());
	}
	
	@Test
	@WithMockUser(username="user@mail.com",password="1111",roles="USER")
	@DisplayName("로그인 유저 페이지 로딩 성공")
	void accountGetPageTest() throws Exception {
		when(accountService.getAccountInfoByEmail(any())).thenReturn(AccountDTO.AccountBasicInfo.of(loginUser));
		when(accountService.getAccountInfoById(any())).thenReturn(AccountDTO.AccountBasicInfo.of(loginUser));

		//then		
		mockMvc.perform(get("/user/{userId}", loginUser.getAccountId()).session(session))
			.andExpect(view().name("profile/profile"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username="user@mail.com",password="1111",roles="USER")
	@DisplayName("타 유저 페이지 로딩 성공")
	void accountGetOtherUserPageTest() throws Exception {
		//given
		AccountDTO.AccountBasicInfo targetUser = AccountDTO.AccountBasicInfo.builder().accountEmail("sooda2@mail.com")
								.accountId("otherid")
								.accountName("othername")
								.profileImg("/images/non_profile.png")
								.build();
		
		when(accountService.getAccountInfoByEmail(any())).thenReturn(AccountDTO.AccountBasicInfo.of(loginUser));
		when(accountService.getAccountInfoById(any())).thenReturn(targetUser);

		//then
		mockMvc.perform(get("/user/{userId}", targetUser.getAccountId()).session(session))
		.andExpect(model().attribute("targetUser", targetUser))
		.andExpect(view().name("feed/userFeed"))
		.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	@DisplayName("프로필 변경 성공")
	void patchProfileTest() throws Exception {		
		AccountDTO.AccountProfileInfo changedProfile = AccountDTO.AccountProfileInfo.builder()
									.accountName("changedName")
									.phone("010-0000-0000")
									.build();
		
		mockMvc.perform(patch("/user/profile")
				.param("accountName", changedProfile.getAccountName())
				.param("phone", changedProfile.getPhone())
				.with(csrf()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/profile"));
		
	}
	
	@Test
	@WithMockUser
	@DisplayName("프로필 이미지 변경 성공")
	void changeProfileImgTest() throws Exception {
		
		InputStream ins = new FileInputStream("C:\\Users\\wer08\\Desktop\\sample_profile.png");
		byte[] image = IOUtils.toByteArray(ins);
		
		MockMultipartFile file = new MockMultipartFile("file", "sample_profile.png", MediaType.IMAGE_PNG_VALUE, image);
		
		mockMvc.perform(multipart("/user/profile/img").file(file).with(csrf()).session(session))
			.andExpect(status().is2xxSuccessful());
	}
	
	
	private Account createUser(String userEmail, String password) {
		Account account = Account.builder()
				.accountEmail(userEmail)
				.accountPassword(password)
				.accountName("sooda")
				.accountId("soosooda")
				.profileImg("/images/non_profile.png")
				.build();
		accountService.registUser(account);
		return account;
	}
}
