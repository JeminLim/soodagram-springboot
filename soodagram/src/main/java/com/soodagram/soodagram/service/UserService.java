package com.soodagram.soodagram.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Follower;
import com.soodagram.soodagram.domain.entity.Following;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.repository.FollowerRepository;
import com.soodagram.soodagram.domain.repository.FollowingRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;
import com.soodagram.soodagram.dto.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FollowingRepository followingRepository;
	
	@Autowired
	private FollowerRepository followerRepository;
	
	public User registUser(UserDTO user){
		return userRepository.save(user.toEntity());		
	}
	
	public boolean emailCheck(String userEmail) {
		return userRepository.countByUserEmail(userEmail) > 0 ? true : false;
	}
	
	public boolean idCheck(String userId) {
		return userRepository.countByUserId(userId) > 0 ? true : false;
	}
	
	public User getUserInfoById(String userId) {
		return userRepository.findByUserId(userId);
	}
	
	public void following(UserDTO basedUser, UserDTO targetUser) {
		
		followingRepository.save(Following.builder()
				.basedUser(basedUser.toEntity())
				.targetUser(targetUser.toEntity())
				.build());			
		
		followerRepository.save(Follower.builder()
				.basedUser(targetUser.toEntity())
				.targetUser(basedUser.toEntity())
				.build());
	}
	
	public void cancelFollowing(UserDTO basedUser, UserDTO targetUser) {		
		
		
		followingRepository.delete(Following.builder()
									.basedUser(basedUser.toEntity())
									.targetUser(targetUser.toEntity())
									.build());		
	
		
		followerRepository.delete(Follower.builder()
									.basedUser(targetUser.toEntity())
									.targetUser(basedUser.toEntity())
									.build());
	}
	
	public List<User> recommendUser(UserDTO loginUser) {
		List<User> recommendUser = userRepository.findAll();
		
		recommendUser.removeAll(toUserList(loginUser.getFollowings()));
		
		return recommendUser;
		
	}
	
	private List<User> toUserList(List<Following> following) {
		List<User> followingUser = new ArrayList<>();
		for(int i = 0; i < following.size(); i++) {
			followingUser.add(following.get(i).getTargetUser());
		}
		return followingUser;
	}
	
}
