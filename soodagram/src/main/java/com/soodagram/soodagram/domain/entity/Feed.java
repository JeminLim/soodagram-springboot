package com.soodagram.soodagram.domain.entity;

import java.time.LocalDateTime;
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
	@Column
	private List<FeedFile> files;
	
	@OneToMany(targetEntity=FeedHashtag.class, mappedBy="hashtag")
	@Column
	private List<FeedHashtag> feedHashtag;
	
	@OneToMany(targetEntity=Reply.class, mappedBy="feed")
	@Column
	private List<Reply> replies;
	
	@CreatedDate
	@Column
	private LocalDateTime regDate;
	
	@LastModifiedDate
	private LocalDateTime updateDate;
	
	@Builder
	public Feed(Long feedNo, User writer, String content, List<FeedFile> files, List<FeedHashtag> feedHashtag, List<Reply> replies) {
		this.feedNo = feedNo;
		this.writer = writer;
		this.content = content;
		this.files = files;
		this.feedHashtag = feedHashtag;
		this.replies = replies;
	}
	
}
