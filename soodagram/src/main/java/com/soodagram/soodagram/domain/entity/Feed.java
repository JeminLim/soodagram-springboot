package com.soodagram.soodagram.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Table(name="tbl_feed")
public class Feed {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long feedNo;

	@ManyToOne(targetEntity=User.class, cascade = CascadeType.MERGE)
	@JoinColumn(name="userSeq")
	private User writer;

	@Column(columnDefinition="TEXT")
	private String content;
	
	@OneToMany(targetEntity=FeedFile.class, mappedBy="feed")
	@Builder.Default
	private List<FeedFile> files = new ArrayList<>();
	
	@OneToMany(targetEntity=FeedHashtag.class, mappedBy="hashtag")
	@Builder.Default
	private List<FeedHashtag> feedHashtags = new ArrayList<>();
	
	@OneToMany(targetEntity=Reply.class, mappedBy="feed")
	@Builder.Default
	private List<Reply> replies = new ArrayList<>();
	
	@OneToMany(targetEntity=UserLikeFeed.class, mappedBy="feed")
	@Builder.Default
	private List<UserLikeFeed> likes = new ArrayList<>();
	
	@CreatedDate
	@Column
	private LocalDateTime regDate;
	
	@LastModifiedDate
	private LocalDateTime updateDate;	
	
}
