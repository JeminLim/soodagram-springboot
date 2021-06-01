package com.soodagram.soodagram.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	List<Reply> findByFeed(Feed feed, Pageable pageable);
}
