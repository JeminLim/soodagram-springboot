package com.soodagram.soodagram.dto;


import java.util.ArrayList;
import java.util.List;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Follower;
import com.soodagram.soodagram.domain.entity.Following;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.User.AuthCode;
import com.soodagram.soodagram.domain.entity.User.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	@Builder.Default
	private List<Feed> feeds = new ArrayList<>();
	@Builder.Default
	private List<Follower> followers = new ArrayList<>();
	@Builder.Default
	private List<Following> followings = new ArrayList<>();
	
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
				.followings(followings)
				.build();
		return build;
	}	
}
