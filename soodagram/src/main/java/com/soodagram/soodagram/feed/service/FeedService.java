package com.soodagram.soodagram.feed.service;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.feed.domain.FeedVO;
import com.soodagram.soodagram.user.domain.UserVO;

public interface FeedService {
	
	/**
	 * 피드 작성 및 열람
	 */
	void wrtieFeed(FeedVO feedVO) throws Exception;
	List<FeedVO> getMyFeed(UserVO userVO) throws Exception;
	List<FeedVO> getFollowingFeed(Map<String, Object> input) throws Exception;
	void deleteFeed(int feedNo) throws Exception;
	
	/**
	 * 피드 소셜 기능 관련
	 */
	void insertLike(Map<String, Object> likeInput) throws Exception;
	void cancelLike(Map<String, Object> likeInput) throws Exception;
	int duplicateCheck(Map<String, Object> likeInput) throws Exception;
	int countLikeNo(int feedNo) throws Exception;
	
}
