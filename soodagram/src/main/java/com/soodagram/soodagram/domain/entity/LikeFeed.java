package com.soodagram.soodagram.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of= {"account", "feed"})
@Table(name="like_feed")
public class LikeFeed {
	
	@EmbeddedId
	private LikeFeedId id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("accountSeq")
	private Account account;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@MapsId("FeedNo")
	private Feed feed;
	
	@Column
	private LocalDate regDate;
	
	public LikeFeed() {}
	public LikeFeed(Account account, Feed feed) {
		this.account = account;
		this.feed = feed;
		this.id = new LikeFeedId(account.getAccountSeq(), feed.getFeedNo());
	}
	
}
