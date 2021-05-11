package com.soodagram.soodagram.service;

import java.util.List;
import java.util.Map;

import com.soodagram.soodagram.domain.entity.Feed;

public interface SearchService {
	
	Map<String, Object> search(String keyword) throws Exception;
	List<Feed> getHashtagFeed(String hashtagName) throws Exception;
}
