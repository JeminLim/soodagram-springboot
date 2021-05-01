package com.soodagram.soodagram.user.domain;

import lombok.Data;

@Data
public class FollowDTO {
	private Integer follow_no;
	private String basedUser;
	private String targetUser;
}
