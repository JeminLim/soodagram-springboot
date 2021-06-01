package com.soodagram.soodagram.domain.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class FeedHashtagId implements Serializable{
	private static final long serialVersionUID = 3750899313153190636L;
	private Long feedId;
	private Long hashtagId;
	
	public FeedHashtagId(Long feedId, Long hashtagId) {
		this.feedId = feedId;
		this.hashtagId = hashtagId;
	}
	
	
}
