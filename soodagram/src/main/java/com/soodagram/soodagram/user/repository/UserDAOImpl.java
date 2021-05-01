package com.soodagram.soodagram.user.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.user.domain.LoginDTO;
import com.soodagram.soodagram.user.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final String NAMESPACE = "com.soodagram.soodagram.mappers.user.UserMapper";
	
	private final SqlSession sqlSession;
	
	@Autowired
	public UserDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void register(UserVO userVO) throws Exception {
		sqlSession.insert(NAMESPACE + ".register", userVO);
	}

	@Override
	public int duplicateEmail(String userEmail) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".duplicateEmail", userEmail);
	}

	@Override
	public int duplicateId(String userId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".duplicateId", userId);
	}

	@Override
	public UserVO login(String userEmail) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".login", userEmail);
	}

	@Override
	public void uploadUserImg(UserVO userVO) throws Exception {
		sqlSession.insert(NAMESPACE + ".uploadUserImg", userVO);
		
	}

	@Override
	public void follow(Map<String, Object> follow) throws Exception {
		sqlSession.insert(NAMESPACE + ".userFollow", follow);		
	}

	@Override
	public List<UserVO> getFollowerList(UserVO userVO) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getFollowerList", userVO);
	}

	@Override
	public List<UserVO> getFollowingList(UserVO userVO) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getFollowingList", userVO);
	}

	@Override
	public List<UserVO> getRecommendUserList(UserVO userVO) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getUserList", userVO);
	}

	@Override
	public UserVO getUserInfo(String userEmail) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getUserInfo", userEmail);
	}

	@Override
	public void updateUserInfo(UserVO userVO) throws Exception {
		sqlSession.update(NAMESPACE + ".updateUserInfo", userVO);
		
	}

	@Override
	public void cancelFollow(Map<String, Object> userInput) throws Exception {
		sqlSession.delete(NAMESPACE + ".cancelFollow", userInput);
	}

	@Override
	public Integer checkFollow(Map<String, Object> userInput) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".followCheck", userInput);
	}

	@Override
	public UserVO getUserInfoById(String userId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getUserInfoById", userId);
	}

	@Override
	public void keepLogin(String userEmail, String sessionId, Date sessionLimit) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userEmail", userEmail);
		paramMap.put("sessionId", sessionId);
		paramMap.put("sessionLimit", sessionLimit);
		
		sqlSession.update(NAMESPACE + ".keepLogin", paramMap);
		
	}

	@Override
	public UserVO checkUserWithSessionKey(String value) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".checkUserWithSessionKey", value);
	}

	@Override
	public void registAuth(String userEmail) throws Exception {
		sqlSession.insert(NAMESPACE + ".registerAuth", userEmail);		
	}

}
