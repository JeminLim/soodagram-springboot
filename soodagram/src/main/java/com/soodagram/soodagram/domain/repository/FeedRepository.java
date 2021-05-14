package com.soodagram.soodagram.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.User;

public interface FeedRepository extends JpaRepository<Feed, Integer>{
	Optional<Feed> findByWriter(User writer);
	int countAllFeedByWriter(User writer);
}
