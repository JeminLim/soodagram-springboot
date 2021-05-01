package com.soodagram.soodagram.user.domain;

import lombok.Data;

@Data
public class LoginDTO {
	
	private String userEmail;
	private String userPw;
	private boolean useCookie;	
}
