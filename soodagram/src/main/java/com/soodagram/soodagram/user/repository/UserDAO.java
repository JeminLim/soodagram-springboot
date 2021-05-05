package com.soodagram.soodagram.user.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.user.domain.LoginDTO;
import com.soodagram.soodagram.user.domain.UserVO;

public interface UserDAO {
	
	// 회원가입
	void register(UserVO userVO);
	void registAuth(String userEmail);
	int duplicateEmail(String userEmail);
	int duplicateId(String userId);
	
	// 정보갱신
	void uploadUserImg(UserVO userVO);
	void updateUserInfo(UserVO userVO);
	// 로그인
	LoginDTO login(String userEmail);
	UserVO getLoginUser(String userEmail);
	
	// 팔로잉
	void follow(Map<String, Object> follow);
	void cancelFollow(Map<String, Object> userInput);
	Integer checkFollow(Map<String, Object> userInput);
	
	// 팔로잉 팔로워 리스트 GET
	List<UserVO> getFollowerList(UserVO userVO);
	List<UserVO> getFollowingList(UserVO userVO);
	
	// 정보열람
	List<UserVO> getRecommendUserList(UserVO userVO);
	
	// 유저 한명 정보
	UserVO getUserInfo(String userEmail);
	UserVO getUserInfoById(String userId);
	
	// 로그인 유지 처리
	void keepLogin(String userEmail, String sessionId, Date sessionLimit);
	// 세션키 검증
	UserVO checkUserWithSessionKey(String value);
}
