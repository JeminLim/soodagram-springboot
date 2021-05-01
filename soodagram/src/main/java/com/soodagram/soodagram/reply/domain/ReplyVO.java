package com.soodagram.soodagram.reply.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	
	Integer replyNo;
	String content;
	Date regDate;
	Date updateDate;

	
	//for user
	String userEmail;
	String userId;
	
	//for feed
	Integer feedNo;
}
