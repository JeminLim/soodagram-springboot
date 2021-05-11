package com.soodagram.soodagram.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.feed.repository.FeedDAO;
import com.soodagram.soodagram.search.repository.SearchDAO;

/**
 * 검색 서비스 구현 객체
 * 키워드 검색 및 해시태그 검색 결과 열람
 * @author wer08
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	private final SearchDAO searchDAO;
	private final FeedDAO feedDAO;
	
	@Autowired
	public SearchServiceImpl(SearchDAO searchDAO, FeedDAO feedDAO) {
		this.searchDAO = searchDAO;
		this.feedDAO = feedDAO;
	}
	
	/**
	 * 검색 기능 구현
	 * keyword에 따른 검색 분리
	 */
	@Override
	public Map<String, Object> search(String keyword) throws Exception {
		
		// 검색 키워드 종류 태그 판별
		char indicator = keyword.charAt(0);
		Map<String, Object> result = new HashMap<>();
		
		
		if(indicator == '@') { 
			// 유저 검색
			List<User> resultUser = searchDAO.searchUser(keyword.substring(1));
			result.put("resultUser", resultUser);
		} else if(indicator == '#') {
			// 해시 태그 검색
			List<FeedHashtag> resultHashtag = searchDAO.searchHashtag(keyword.substring(1));
			result.put("resultHashtag", resultHashtag);
		} else {
			// 미지정 검색
			List<User> resultUser = searchDAO.searchUser(keyword);
			result.put("resultUser", resultUser);
			List<FeedHashtag> resultHashtag = searchDAO.searchHashtag(keyword);
			result.put("resultHashtag", resultHashtag);
			
		}
		
		return result;
	}

	/**
	 * 해시태그 검색 태그 결과 클릭시, 관련 피드 리스트 수신
	 */
	@Override
	public List<Feed> getHashtagFeed(String hashtagName) throws Exception {		
		return feedDAO.getHashtagFeed(hashtagName);
	}

}
