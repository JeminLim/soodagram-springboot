package com.soodagram.soodagram.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.user.domain.LoginDTO;
import com.soodagram.soodagram.user.domain.UserVO;

public interface UserService {
	
	/**
	 * 회원가입 관련
	 */
	void register(UserVO userVO) throws Exception;
	int duplicateEmail(String userEmail) throws Exception;
	int duplicateId(String userId) throws Exception;
	
	/**
	 * 유저 로그인 관련
	 */
	LoginDTO login(String userEmail) throws Exception;
	UserVO getLoginUser(String userEmail) throws Exception;
	void keepLogin(String userEmail, String sessionId, Date next) throws Exception;
	UserVO checkLoginBefore(String value) throws Exception;
	
	/**
	 * 유저 프로필 관련
	 */
	void uploadUserImg(UserVO userVO) throws Exception;
	void updateUserInfo(UserVO userVO) throws Exception;

	/**
	 * 유저 정보열람 관련
	 */
	UserVO getUserInfo(String userEmail) throws Exception;
	UserVO getUserInfoById(String userId) throws Exception;	
	List<UserVO> getRecommendUserList(UserVO userVO) throws Exception;
	List<UserVO> getFollowerList(UserVO userVO) throws Exception; 
	List<UserVO> getFollowingList(UserVO userVO) throws Exception;
	
	
	/**
	 * 유저 관계(좋아요, 팔로잉) 관련
	 */
	void follow(Map<String, Object> input) throws Exception;
	void cancelFollow(Map<String, Object> userInput) throws Exception;
	Integer checkFollow(Map<String, Object> userInput) throws Exception;
	
	
}
