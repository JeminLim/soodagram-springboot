package com.soodagram.soodagram.feed.repository;

import java.util.Map;

public interface FeedLikeDAO {
	void insertLike(Map<String, Object> likeInput) throws Exception;
	void cancelLike(Map<String, Object> likeInput) throws Exception;
	int duplicateCheck(Map<String, Object> likeInput) throws Exception;
	int countLikeNo(int feedNo) throws Exception;
}
