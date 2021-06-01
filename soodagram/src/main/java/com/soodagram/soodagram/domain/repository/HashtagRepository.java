package com.soodagram.soodagram.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.domain.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long>{
	List<Hashtag> findByContentContains(String content);
	Hashtag findByContent(String content);
}
