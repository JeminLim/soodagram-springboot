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
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Table(name="tbl_user_like_feed")
@ToString(exclude= {"currUser", "feed"})
public class UserLikeFeed {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long likeSeq;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="userSeq")
	private User currUser;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@Builder
	public UserLikeFeed(Long likeSeq, User currUser, Feed feed) {
		this.likeSeq = likeSeq;
		this.currUser = currUser;
		this.feed = feed;
	}
	

}
