package com.soodagram.soodagram.search.service;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.feed.domain.FeedVO;

public interface SearchService {
	
	Map<String, Object> search(String keyword) throws Exception;
	List<FeedVO> getHashtagFeed(String hashtagName) throws Exception;
}
