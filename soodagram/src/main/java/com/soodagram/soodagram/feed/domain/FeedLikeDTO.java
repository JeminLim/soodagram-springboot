package com.soodagram.soodagram.feed.domain;

import lombok.Data;

@Data
public class FeedLikeDTO {
	int likeNo;
	int feedNo;
	String userEmail;
}
