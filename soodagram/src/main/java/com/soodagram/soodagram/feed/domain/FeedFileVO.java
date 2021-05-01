package com.soodagram.soodagram.feed.domain;

import java.util.Date;

import lombok.Data;

@Data
public class FeedFileVO {
	private String fileName;
	private Integer feedNo;
	private Date regDate;	
}
