package com.soodagram.soodagram.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soodagram.soodagram.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserEmail(String userEmail);
	int countByUserEmail(String userEmail);
	int countByUserId(String userId);
}
