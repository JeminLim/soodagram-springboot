package com.soodagram.soodagram.reply.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.reply.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {

	private static final String NAMESPACE = "com.soodagram.soodagram.mappers.reply.ReplyMapper";
	
	private final SqlSession sqlSession;
	
	@Autowired
	public ReplyDAOImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void writeReply(ReplyVO replyVO) throws Exception {
		sqlSession.insert(NAMESPACE + ".writeReply", replyVO);

	}

	@Override
	public List<ReplyVO> getReply(Map<String, Object> getInput) throws Exception {		
		return sqlSession.selectList(NAMESPACE + ".getReply", getInput);
	}

	
	
}
