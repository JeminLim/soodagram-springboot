package com.soodagram.soodagram.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.LikeFeed;
import com.soodagram.soodagram.domain.repository.AccountRepository;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.LikeFeedRepository;
import com.soodagram.soodagram.dto.AccountDTO;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	
	@InjectMocks
	private AccountService accountService;
	
	@Mock
	private AccountRepository accountRepository;	
	
	@Mock
	private LikeFeedRepository likeFeedRepository;
	
	@Mock
	private FeedRepository feedRepository;
			
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void userRegistTest() throws Exception {
		//given		
		AccountDTO.Regist regist = AccountDTO.Regist.builder().accountEmail("user@mail.com").accountPassword("111").accountName("name").accountId("id").build();
		when(accountRepository.save(any())).thenReturn(regist.toEntity());	
		//when
		Account testUser = accountService.registUser(regist.toEntity());
		//then
		Assertions.assertEquals(regist.getAccountEmail(), testUser.getAccountEmail());
		Assertions.assertEquals(regist.getAccountId(), testUser.getAccountId());
		
	}
	
	
	@Test
	public void userDuplicateCheck() throws Exception {
		//given
		final String userEmail = "sooda@mail.com";
		final String userId = "idSooda";
		when(accountRepository.countByAccountEmail(userEmail)).thenReturn(1);
		when(accountRepository.countByAccountId(userId)).thenReturn(1);
		
		//when
		boolean emailCheck = accountService.emailCheck(userEmail);
		boolean idCheck = accountService.idCheck(userId);
		
		//then
		Assertions.assertEquals(true, emailCheck);
		Assertions.assertEquals(true, idCheck);
		
	}
	
	@Test
	public void getUserInfoTest() throws Exception {
		//given
		AccountDTO.AccountBasicInfo accountDTO = AccountDTO.AccountBasicInfo.builder().accountEmail("sooda@mail.com").accountName("name").accountId("id").build();
		when(accountRepository.findByAccountId(accountDTO.getAccountId())).thenReturn(Optional.of(accountDTO.toEntity()));
		
		//when
		AccountDTO.AccountBasicInfo getUser = accountService.getAccountInfoById(accountDTO.getAccountId());
		
		//then
		Assertions.assertEquals(accountDTO.getAccountEmail(), getUser.getAccountEmail());
	}
	
	@Test
	public void userFollowingTest() throws Exception {
		//given
		Account user = Account.builder().accountEmail("sooda@mail.com").accountName("name").accountId("id").build();
		
		Account targetUser = Account.builder()
				.accountEmail("tester@mail.com")
				.accountId("sooda2Id")
				.build();
		when(accountRepository.findByAccountEmail(any())).thenReturn(Optional.of(targetUser));
		
		//when
		accountService.userFollow(user, targetUser.getAccountEmail());
		//then
		Assertions.assertEquals(1, user.getFollowings().size());
		Assertions.assertEquals(1, targetUser.getFollowers().size());
	
		accountService.userFollow(user, targetUser.getAccountEmail());
		Assertions.assertEquals(0, user.getFollowings().size());
		Assertions.assertEquals(0, targetUser.getFollowers().size());
		
	}
	
	@Test
	public void recommendUserTest() throws Exception {
		//given
		Account user = Account.builder().accountEmail("sooda@mail.com").accountName("name").accountId("id").build();
		
		Account dummy = Account.builder().build();
		Account dummy2 = Account.builder().build();
		Account dummy3 = Account.builder().build();
		List<Account> allUser = new ArrayList<>();
		allUser.add(user);
		allUser.add(dummy);
		allUser.add(dummy2);
		allUser.add(dummy3);
		
		when(accountRepository.findAll()).thenReturn(allUser);
		//when
		List<AccountDTO.AccountBasicInfo> result = accountService.recommendUser(user);	
		
		//then
		Assertions.assertEquals(3, result.size());
		
	}

	@Test
	public void feedLikeTest() throws Exception {
		//given
		Feed feed = Feed.builder().feedNo(1L).build();
		Account account = Account.builder().accountSeq(2L).accountEmail("tester2@mail.com").accountId("testerId").accountName("tester").build();
		LikeFeed likeObj = new LikeFeed(account, feed);	
		when(feedRepository.findByFeedNo(any())).thenReturn(Optional.of(feed));
		//when
		int result = accountService.likeFeed(account, feed.getFeedNo());
		
		//then
		Assertions.assertEquals(1, result);
		Assertions.assertEquals(1, account.getLikeFeeds().size());
		
		
	}	
}
