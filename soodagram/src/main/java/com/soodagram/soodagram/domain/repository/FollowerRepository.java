package com.soodagram.soodagram.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Follower;
import com.soodagram.soodagram.domain.entity.User;

public interface FollowerRepository extends JpaRepository<Follower, Long>{
}
