package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserEmail(String userEmail);
	User findByUserId(String userId);
	int countByUserEmail(String userEmail);
	int countByUserId(String userId);
}
