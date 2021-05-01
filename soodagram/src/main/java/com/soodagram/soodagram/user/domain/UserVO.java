package com.soodagram.soodagram.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private String userEmail;
	private String userPw;
	private String userName;
	private String userId;
	private String userPhone;
	private String userGender;
	private String userImg;
	private Date userRegDate;
	private String userDesc;
	private String authority;
}
