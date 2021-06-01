package com.soodagram.soodagram.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Account;

public interface FeedRepository extends JpaRepository<Feed, Long>{
	Optional<Feed> findByFeedNo(Long id);
	Feed findByWriter(Account writer);
	int countAllFeedByWriter(Account writer);
	List<Feed> findByWriterIn(List<Account> writer, Pageable pageable);
	void deleteByFeedNo(Long feedNo);
}
