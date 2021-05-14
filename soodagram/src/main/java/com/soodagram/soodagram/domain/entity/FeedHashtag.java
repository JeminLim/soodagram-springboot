package com.soodagram.soodagram.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Table(name="tbl_feed_hashtag")
public class FeedHashtag{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long feedHashtagSeq;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="hashtagNo")
	private Hashtag hashtag;	
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@Builder
	public FeedHashtag(Long feedHashtagSeq, Hashtag hashtag, Feed feed) {
		this.feedHashtagSeq = feedHashtagSeq;
		this.hashtag = hashtag;
		this.feed = feed;
	}
	
}
