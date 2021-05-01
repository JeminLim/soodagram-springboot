package com.soodagram.soodagram.search.repository;

import java.util.List;

import com.soodagram.soodagram.feed.domain.FeedHashtagVO;
import com.soodagram.soodagram.user.domain.UserVO;

public interface SearchDAO {
	
	List<UserVO> searchUser(String keyword) throws Exception;
	List<FeedHashtagVO> searchHashtag(String keyword) throws Exception;
	
}
