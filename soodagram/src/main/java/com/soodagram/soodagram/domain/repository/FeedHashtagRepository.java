package com.soodagram.soodagram.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;

public interface FeedHashtagRepository extends JpaRepository<FeedHashtag, Long> {
	List<FeedHashtag> findAllByHashtag(Hashtag hashtag);
}
