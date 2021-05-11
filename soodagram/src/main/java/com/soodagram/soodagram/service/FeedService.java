package com.soodagram.soodagram.service;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.User;

public interface FeedService {
	
	/**
	 * 피드 작성 및 열람
	 */
	void wrtieFeed(Feed feedVO) throws Exception;
	List<Feed> getMyFeed(User userVO) throws Exception;
	List<Feed> getFollowingFeed(Map<String, Object> input) throws Exception;
	void deleteFeed(int feedNo) throws Exception;
	
	/**
	 * 피드 소셜 기능 관련
	 */
	void insertLike(Map<String, Object> likeInput) throws Exception;
	void cancelLike(Map<String, Object> likeInput) throws Exception;
	int duplicateCheck(Map<String, Object> likeInput) throws Exception;
	int countLikeNo(int feedNo) throws Exception;
	
}
