package com.soodagram.soodagram.feed.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FeedLikeDAOImpl implements FeedLikeDAO {

	private static final String NAMESPACE = "com.soodagram.soodagram.mappers.feed.FeedLikeMapper";
	
	private final SqlSession sqlSession;
	
	@Autowired
	public FeedLikeDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void insertLike(Map<String, Object> likeInput) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertLike", likeInput);
	}

	@Override
	public void cancelLike(Map<String, Object> likeInput) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteLike", likeInput);
	}

	@Override
	public int duplicateCheck(Map<String, Object> likeInput) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".isCancel", likeInput);
	}

	@Override
	public int countLikeNo(int feedNo) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".countLikeNo", feedNo);
	}
	
	

}
