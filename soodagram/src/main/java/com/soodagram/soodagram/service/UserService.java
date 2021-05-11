package com.soodagram.soodagram.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.dto.LoginDTO;

public interface UserService {
	
	/**
	 * 회원가입 관련
	 */
	void register(User userVO) throws Exception;
	int duplicateEmail(String userEmail) throws Exception;
	int duplicateId(String userId) throws Exception;
	
	/**
	 * 유저 로그인 관련
	 */
	LoginDTO login(String userEmail) throws Exception;
	User getLoginUser(String userEmail) throws Exception;
	void keepLogin(String userEmail, String sessionId, Date next) throws Exception;
	User checkLoginBefore(String value) throws Exception;
	
	/**
	 * 유저 프로필 관련
	 */
	void uploadUserImg(User userVO) throws Exception;
	void updateUserInfo(User userVO) throws Exception;

	/**
	 * 유저 정보열람 관련
	 */
	User getUserInfo(String userEmail) throws Exception;
	User getUserInfoById(String userId) throws Exception;	
	List<User> getRecommendUserList(User userVO) throws Exception;
	List<User> getFollowerList(User userVO) throws Exception; 
	List<User> getFollowingList(User userVO) throws Exception;
	
	
	/**
	 * 유저 관계(좋아요, 팔로잉) 관련
	 */
	void follow(Map<String, Object> input) throws Exception;
	void cancelFollow(Map<String, Object> userInput) throws Exception;
	Integer checkFollow(Map<String, Object> userInput) throws Exception;
	
	
}
