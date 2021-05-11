package com.soodagram.soodagram.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class FeedHashtag{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long feedHashtagSeq;
	
	@ManyToOne
	@JoinColumn(name="hashtagNo")
	private Hashtag hashtag;	
	
	@ManyToOne
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	
}
