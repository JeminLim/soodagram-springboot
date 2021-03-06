package com.soodagram.soodagram.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;

public interface FeedHashtagRepository extends JpaRepository<FeedHashtag, Long> {
	List<FeedHashtag> findAllByHashtagIn(List<Hashtag> hashtags);
	List<FeedHashtag> findAllByHashtag(Hashtag hashtag);
}
