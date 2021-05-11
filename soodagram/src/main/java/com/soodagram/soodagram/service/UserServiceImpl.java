package com.soodagram.soodagram.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.dto.LoginDTO;
import com.soodagram.soodagram.user.repository.UserDAO;

/**
 * 유저 서비스 구현체
 * 회원가입, 로그인, 프로필, 정보열람, 유저관계에 관련된 클래스
 * @author jeminLim * 
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;
	
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * 입력된 유저 정보를 등록
	 */
	@Transactional
	@Override
	public void register(User userVO) throws Exception {
		userDAO.register(userVO);
		userDAO.registAuth(userVO.getUserEmail());
	}

	/**
	 * 이메일 중복 체크
	 */
	@Override
	public int duplicateEmail(String userEmail) throws Exception {
		return userDAO.duplicateEmail(userEmail);
	}

	/**
	 * 아이디 중복 체크
	 */
	@Override
	public int duplicateId(String userId) throws Exception {
		return userDAO.duplicateId(userId);
	}

	/**
	 * 유저 로그인
	 */
	@Override
	public LoginDTO login(String userEmail) throws Exception {
		return userDAO.login(userEmail);
	}

	/**
	 * 유저 로그인 정보 세션 저장
	 */
	@Override
	public void keepLogin(String userEmail, String sessionId, Date next) throws Exception {
		userDAO.keepLogin(userEmail, sessionId, next);
	}

	/**
	 * 세션 키 정보로 로그인 유저 불러오기
	 */
	@Override
	public User checkLoginBefore(String value) throws Exception {
		
		return userDAO.checkUserWithSessionKey(value);
	}

	/**
	 * 유저 프로필 사진 업로드
	 */
	@Override
	public void uploadUserImg(User userVO) throws Exception {
		userDAO.uploadUserImg(userVO);
	}

	/**
	 * 유저 개인정보 업데이트
	 */
	@Override
	public void updateUserInfo(User userVO) throws Exception {
		userDAO.updateUserInfo(userVO);
		
	}	

	/**
	 * 이메일로 유저 정보 열람
	 * @param userEmail
	 */
	@Override
	public User getUserInfo(String userEmail) throws Exception {
		return userDAO.getUserInfo(userEmail);
	}
	
	/**
	 * 아이디로 유저 정보 열람
	 * @param userId
	 */
	@Override
	public User getUserInfoById(String userId) throws Exception {
		return userDAO.getUserInfoById(userId);
	}
	
	/**
	 * 유저 팔로잉 추천 리스트
	 */
	@Override
	public List<User> getRecommendUserList(User userVO) throws Exception {
		return userDAO.getRecommendUserList(userVO);
		
	}

	/**
	 * 팔로워 유저 정보 리스트
	 */
	@Override
	public List<User> getFollowerList(User userVO) throws Exception {
		return userDAO.getFollowerList(userVO);
	}

	/**
	 * 팔로잉 유저 정보 리스트
	 */
	@Override
	public List<User> getFollowingList(User userVO) throws Exception {
		return userDAO.getFollowingList(userVO);
	}
	
	/**
	 * 팔로우 DB에 타겟 추가
	 */
	@Override
	public void follow(Map<String, Object> input) throws Exception {
		userDAO.follow(input);		
	}

	/**
	 * 팔로우 DB에 타겟 삭제
	 */
	@Override
	public void cancelFollow(Map<String, Object> userInput) throws Exception {
		userDAO.cancelFollow(userInput);
	}

	/**
	 * 타겟 유저 팔로우 여부 확인
	 */
	@Override
	public Integer checkFollow(Map<String, Object> userInput) throws Exception {
		return userDAO.checkFollow(userInput);
	}

	@Override
	public User getLoginUser(String userEmail) throws Exception {
		return userDAO.getLoginUser(userEmail);
	}
	
	

}
