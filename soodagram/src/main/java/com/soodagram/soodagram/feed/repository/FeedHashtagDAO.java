package com.soodagram.soodagram.feed.repository;

import com.soodagram.soodagram.feed.domain.FeedHashtagVO;

public interface FeedHashtagDAO {
	void writeHashtag(FeedHashtagVO hashtagVO) throws Exception;
}
