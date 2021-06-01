package com.soodagram.soodagram.domain.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@EqualsAndHashCode(of= {"hashtag", "feed"})
@Table(name="tbl_feed_hashtag")
public class FeedHashtag{
	
	@EmbeddedId
	private FeedHashtagId id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hashtagNo")
	private Hashtag hashtag;	
	
	
	public FeedHashtag() {}
	public FeedHashtag(Feed feed, Hashtag hashtag) {
		this.hashtag = hashtag;
		this.feed = feed;
		this.id = new FeedHashtagId(hashtag.getHashtagNo(), feed.getFeedNo());
	}
	
}
