package com.soodagram.soodagram.dto;


import java.util.List;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Follower;
import com.soodagram.soodagram.domain.entity.Following;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.User.AuthCode;
import com.soodagram.soodagram.domain.entity.User.Gender;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDTO {

	private Long userSeq;
	private String userEmail;
	private String userPw;
	private String userName;
	private String userId;
	private String userPhone;
	private Gender userGender;
	private String userImg;
	private String userDesc;
	private AuthCode authority;
	private List<Feed> feeds;
	private List<Follower> followers;
	private List<Following> following;
	
	public User toEntity() {
		User build = User.builder()
				.userSeq(userSeq)
				.userEmail(userEmail)
				.userPw(userPw)
				.userName(userName)
				.userId(userId)
				.userPhone(userPhone)
				.userGender(userGender)
				.userImg(userImg)
				.userDesc(userDesc)
				.authority(authority)
				.followers(followers)
				.following(following)
				.build();
		return build;
	}
	
	@Builder
	public UserDTO(Long userSeq, String userEmail, String userPw, String userName, 
			String userId, String userPhone, Gender userGender, 
			String userImg, String userDesc, AuthCode authority, 
			List<Follower> followers, List<Following> following,
			List<Feed> feeds) {
		this.userSeq = userSeq;
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userName = userName;
		this.userId = userId;
		this.userPhone = userPhone;
		this.userGender = userGender;
		this.userImg = userImg;
		this.userDesc = userDesc;
		this.authority = authority;
		this.followers = followers;
		this.following = following;
		this.feeds = feeds;
	}
	
}
