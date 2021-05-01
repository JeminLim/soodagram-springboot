package com.soodagram.soodagram.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserAuthCode {
	private long authCodeSeq;
	private String authority;
	private Date regDate;
}
