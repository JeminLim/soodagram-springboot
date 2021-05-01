package com.soodagram.soodagram.feed.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.feed.domain.FeedVO;
import com.soodagram.soodagram.user.domain.UserVO;

@Repository
public class FeedDAOImpl implements FeedDAO {

	private static final String NAMESPACE = "com.soodagram.soodagram.mappers.feed.FeedMapper";
	
	private final SqlSession sqlSession;
	
	@Autowired
	public FeedDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void writeFeed(FeedVO feedVO) throws Exception {
		sqlSession.insert(NAMESPACE + ".writeFeed", feedVO);
	}

	@Override
	public List<FeedVO> getMyFeed(UserVO userVO) throws Exception {		
		return sqlSession.selectList(NAMESPACE + ".getMyFeed", userVO);
	}
	
	@Override
	public List<FeedVO> getFollowingFeed(Map<String, Object> input) throws Exception {		
		return sqlSession.selectList(NAMESPACE + ".getFollowingFeed", input);
	}

	@Override
	public void deleteFeed(int feedNo) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteFeed", feedNo);
	}

	@Override
	public List<FeedVO> getHashtagFeed(String hashtagName) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getHashtagFeed", hashtagName);
	}



	
	
}
