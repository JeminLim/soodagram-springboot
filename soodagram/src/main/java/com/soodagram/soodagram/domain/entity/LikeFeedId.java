package com.soodagram.soodagram.domain.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;


@Embeddable
@Getter
public class LikeFeedId implements Serializable{
	private static final long serialVersionUID = -252037551312692959L;
	private Long accountId;
	private Long feedId;
	
	public LikeFeedId(Long accountId, Long feedId) {
		this.accountId = accountId;
		this.feedId = feedId;
	}
}
