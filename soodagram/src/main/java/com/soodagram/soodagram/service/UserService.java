package com.soodagram.soodagram.service;

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
	
	public void following(User fromUser, String toUserId) {
		
		User target = userRepository.findByUserId(toUserId);
		
		Following following = Following.builder()
				.fromUser(fromUser)
				.toUserId(toUserId)
				.build();
		
		followingRepository.save(following);	
		
		
		
		Follower follower = Follower.builder()
				.toUser(target)
				.fromUserId(fromUser.getUserId())
				.build();
		
		followerRepository.save(follower);
	}
	
	public void cancelFollowing(User fromUser, String toUserId) {		
		
		User target = userRepository.findByUserId(toUserId);
		
		followingRepository.delete(Following.builder().fromUser(fromUser).toUserId(toUserId).build());
		followerRepository.delete(Follower.builder().fromUserId(target.getUserId()).toUser(fromUser).build());
	}
	
}
