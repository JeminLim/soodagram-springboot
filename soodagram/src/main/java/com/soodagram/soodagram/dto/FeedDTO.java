package com.soodagram.soodagram.dto;

import java.util.Date;

import lombok.Data;

@Data
public class FeedDTO {
	
	private Integer feedNo;
	private Long userSeq;
	private String content;
	private Date regDate;
	private Date updateDate;
	
	
}
