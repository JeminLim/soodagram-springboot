package com.soodagram.soodagram.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Table(name="tbl_reply")
public class Reply{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long replyNo;
	
	@Column(nullable=false)
	private String content;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@CreatedDate
	@Column
	private LocalDateTime regDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="userSeq")
	private Account writer;
	
	@Builder
	public Reply(Long replyNo, String content, Feed feed, Account writer) {
		this.replyNo = replyNo;
		this.content = content;
		this.feed = feed;
		this.writer = writer;
	}
	
	
	public void addReply(Feed feed, Account writer) {
		this.feed = feed;
		this.writer = writer;
		feed.getReplies().add(this);
		writer.getWrittenReplies().add(this);
	}
	
	public void removeReply(Feed feed, Account account) {
		feed.getReplies().remove(this);
		account.getWrittenReplies().remove(this);
	}
	
}
