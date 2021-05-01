package com.soodagram.soodagram.feed.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.feed.domain.FeedFileVO;

@Repository
public class FeedFileDAOImpl implements FeedFileDAO {

	private static final String NAMESPACE = "com.soodagram.soodagram.mappers.feed.FeedFileMapper";
	
	private final SqlSession sqlSession;
	
	@Autowired
	public FeedFileDAOImpl (SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void uploadFile(FeedFileVO feedFileVO) throws Exception {
		sqlSession.insert(NAMESPACE + ".uploadFile", feedFileVO);		
	}

	@Override
	public List<FeedFileVO> getFileNames(int feedNo) throws Exception {		
		return sqlSession.selectList(NAMESPACE + ".getFileList", feedNo);
	}

}
