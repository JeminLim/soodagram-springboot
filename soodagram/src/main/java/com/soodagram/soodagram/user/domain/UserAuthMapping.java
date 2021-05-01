package com.soodagram.soodagram.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserAuthMapping {
	private long authMappingSeq;
	private String userEmail;
	private long authCode;
	private Date regDate;
}
