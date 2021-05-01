package com.soodagram.soodagram.user.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.user.domain.LoginDTO;
import com.soodagram.soodagram.user.domain.UserVO;

public interface UserDAO {
	
	// 회원가입
	void register(UserVO userVO) throws Exception;
	void registAuth(String userEmail) throws Exception;
	int duplicateEmail(String userEmail) throws Exception;
	int duplicateId(String userId) throws Exception;
	
	// 정보갱신
	void uploadUserImg(UserVO userVO) throws Exception;
	void updateUserInfo(UserVO userVO) throws Exception;
	// 로그인
	UserVO login(String userEmail) throws Exception;
	
	// 팔로잉
	void follow(Map<String, Object> follow) throws Exception;
	void cancelFollow(Map<String, Object> userInput) throws Exception;
	Integer checkFollow(Map<String, Object> userInput) throws Exception;
	
	// 팔로잉 팔로워 리스트 GET
	List<UserVO> getFollowerList(UserVO userVO) throws Exception;
	List<UserVO> getFollowingList(UserVO userVO) throws Exception;
	
	// 정보열람
	List<UserVO> getRecommendUserList(UserVO userVO) throws Exception;
	
	// 유저 한명 정보
	UserVO getUserInfo(String userEmail) throws Exception;
	UserVO getUserInfoById(String userId) throws Exception;
	
	// 로그인 유지 처리
	void keepLogin(String userEmail, String sessionId, Date sessionLimit) throws Exception;
	// 세션키 검증
	UserVO checkUserWithSessionKey(String value) throws Exception;
}
