package com.soodagram.soodagram.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Account.AuthCode;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.LikeFeed;
import com.soodagram.soodagram.domain.repository.AccountRepository;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.LikeFeedRepository;
import com.soodagram.soodagram.dto.AccountDTO;

import javassist.NotFoundException;

@Service
public class AccountService implements UserDetailsService{
	
	@Autowired
	private AccountRepository accountRepository;	
	
	@Autowired
	private LikeFeedRepository likeFeedRepository;
	
	@Autowired
	private FeedRepository feedRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Account registUser(Account registForm){				
		registForm.setAccountPassword(passwordEncoder.encode(registForm.getAccountPassword()));
		registForm.setAuthority(AuthCode.ROLE_USER);		
		return accountRepository.save(registForm);		
	}
	
	public Account updateUser(Account updateInfo) {
		return accountRepository.save(updateInfo);
	}
	
	public boolean emailCheck(String userEmail) {
		return accountRepository.countByAccountEmail(userEmail) > 0 ? true : false;
	}
	
	public boolean idCheck(String userId) {
		return accountRepository.countByAccountId(userId) > 0 ? true : false;
	}
	
	public AccountDTO.AccountBasicInfo getAccountInfoById(String userId) {
		return AccountDTO.AccountBasicInfo.of(accountRepository.findByAccountId(userId).orElseThrow(()-> new NoSuchElementException()));
	}
	
	public AccountDTO.AccountBasicInfo getAccountInfoByEmail(String userEmail) {
		return AccountDTO.AccountBasicInfo.of(accountRepository.findByAccountEmail(userEmail).orElseThrow(()-> new NoSuchElementException()));
	}
	
	public int likeFeed(Account activeUser, Long feedNo) throws NotFoundException {
		Feed feed = feedRepository.findByFeedNo(feedNo).orElseThrow(() -> new NotFoundException("유효하지 않는 게시물 입니다"));
		LikeFeed likeFeed = new LikeFeed(activeUser, feed);
		
		if(likeFeedRepository.countLikeFeedByAccountAndFeed(activeUser, feed) == 0) {
			likeFeedRepository.save(likeFeed);
			activeUser.addLikeFeed(likeFeed);
			feedRepository.save(feed);
			accountRepository.save(activeUser);
			return 1;
			
		} else {
			likeFeedRepository.delete(likeFeed);
			activeUser.removeLikeFeed(likeFeed);
			feedRepository.save(feed);
			accountRepository.save(activeUser);
			return 0;
		}		
	}
	
	public List<AccountDTO.AccountBasicInfo> recommendUser(Account loginUser) {
		List<Account> recommendUser = accountRepository.findAll();
		
		recommendUser.removeAll(loginUser.getFollowings());
		recommendUser.remove(loginUser);
		
		return AccountDTO.AccountBasicInfo.of(recommendUser);
		
	}
	
	public int userFollow(Account basedUser, String targetEmail) {
		
		Account targetUser = accountRepository.findByAccountEmail(targetEmail).orElseThrow(()-> new NoSuchElementException());
		
		if(basedUser.getFollowings().contains(targetUser)) {	
			basedUser.cancelFollow(targetUser);
			accountRepository.save(basedUser);
			accountRepository.save(targetUser);			
			return 0;
		} else {
			basedUser.doFollow(targetUser);
			accountRepository.save(basedUser);
			accountRepository.save(targetUser);
			return 1;
		}
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Account account = accountRepository.findByAccountEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		
		return AccountDTO.Login.builder()
					.accountEmail(account.getAccountEmail())
					.accountPassword(account.getAccountPassword())
					.authroities(authorities)
					.build();
	}
}
