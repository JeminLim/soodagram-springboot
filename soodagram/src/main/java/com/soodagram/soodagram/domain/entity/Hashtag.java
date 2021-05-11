package com.soodagram.soodagram.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class Hashtag {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long hashtagNo;
	
	@Column(nullable=false)
	private String content;
	
	@OneToMany(targetEntity=FeedHashtag.class, mappedBy="feed")
	@Column
	private List<FeedHashtag> feedHashtag;
	
	
}
