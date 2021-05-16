package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.UserLikeFeed;

public interface LikeRepository extends JpaRepository<UserLikeFeed, Long>{
	UserLikeFeed findUserLikeFeedByUserAndFeed(Feed feed, User user);	
}
