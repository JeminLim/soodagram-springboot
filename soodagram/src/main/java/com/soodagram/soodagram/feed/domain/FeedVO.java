package com.soodagram.soodagram.feed.domain;

import java.util.Date;
import java.util.List;

import com.soodagram.soodagram.user.domain.UserVO;

import lombok.Data;

@Data
public class FeedVO {
	private Integer feedNo;
	private String content;
	private Date regDate;
	private Date updateDate;
	private String userEmail;
	//업로드시
	private String files[];
	
	//피드 GET
	private List<FeedFileVO> fileVO;
	private UserVO userVO;
	
	//좋아요 개수
	private int totalLike;
	private boolean isLike;
	
	
	//댓글 개수
	private int totalReplies;
}
