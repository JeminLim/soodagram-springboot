package com.soodagram.soodagram.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;

@Service
public class SearchService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FeedHashtagRepository feedHashtagRepository;
	
	@Autowired
	private HashtagRepository hashtagRepository;
	
	public List<User> searchUser(String keyword) throws Exception {
		return userRepository.findAllByUserId(keyword);
	}
	
	public List<FeedHashtag> searchHashtag(String keyword) throws Exception {
		Hashtag hashtag = hashtagRepository.findByContent(keyword);
		return feedHashtagRepository.findAllByHashtag(hashtag);
	}
	
}
