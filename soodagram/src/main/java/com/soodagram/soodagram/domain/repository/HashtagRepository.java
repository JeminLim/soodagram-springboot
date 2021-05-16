package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>{
	Hashtag findByContent(String content);
}
