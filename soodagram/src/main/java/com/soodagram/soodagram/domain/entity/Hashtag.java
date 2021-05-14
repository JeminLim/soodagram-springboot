package com.soodagram.soodagram.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Table(name="tbl_hashtag")
public class Hashtag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long hashtagNo;
	
	@Column(nullable=false)
	private String content;
	
	@OneToMany(targetEntity=FeedHashtag.class, mappedBy="feed")
	@Column
	private List<FeedHashtag> feedHashtag;
	
	@Builder
	public Hashtag (Long hashtagNo, String content, List<FeedHashtag> feedHashtag) {
		this.hashtagNo = hashtagNo;
		this.content = content;
		this.feedHashtag = feedHashtag;
	}
}
