package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soodagram.soodagram.domain.entity.FeedFile;

public interface FeedFileRepository extends JpaRepository<FeedFile, Long>{
	
}
