package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Feed;

public interface FeedRepository extends JpaRepository<Feed, Integer>{
	
}
