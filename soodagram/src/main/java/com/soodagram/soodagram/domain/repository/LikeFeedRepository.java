package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.LikeFeed;

public interface LikeFeedRepository extends JpaRepository<LikeFeed, Long>{
	int countLikeFeedByAccountAndFeed(Account account, Feed feed);
}
