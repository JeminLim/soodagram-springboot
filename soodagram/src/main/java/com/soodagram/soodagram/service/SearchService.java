package com.soodagram.soodagram.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.AccountRepository;

@Service
public class SearchService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private FeedHashtagRepository feedHashtagRepository;
	
	@Autowired
	private HashtagRepository hashtagRepository;
	
	public Map<String, Object> search(String keyword) throws Exception{
		Map<String, Object> result = new HashMap<>();
		
		if(keyword.charAt(0) == '@') {
			result.put("resultUser", searchUser(keyword));
			return result;
		} else if(keyword.charAt(0) == '#') {
			result.put("resultHashtag", searchHashtag(keyword));
			return result;
		} else {
			result.put("resultUser", searchUser(keyword));
			result.put("resultHashtag", searchHashtag(keyword));
			return result;
		}
		
	}
	
	public List<FeedHashtag> getHashtagFeed(String content) {
		Hashtag getHashtag = hashtagRepository.findByContent(content);
		return feedHashtagRepository.findAllByHashtag(getHashtag);
	}
	
	private List<Account> searchUser(String keyword) throws Exception {
		return accountRepository.findAllByAccountIdContains(keyword);
	}
	
	private List<FeedHashtag> searchHashtag(String keyword) throws Exception {
		List<Hashtag> hashtag = hashtagRepository.findByContentContains(keyword);
		return feedHashtagRepository.findAllByHashtagIn(hashtag);
	}
	
}
