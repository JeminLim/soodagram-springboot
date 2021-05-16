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
	
	@ManyToOne(targetEntity=Feed.class, cascade = CascadeType.ALL)
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@CreatedDate
	@Column
	private LocalDateTime regDate;
	
	@ManyToOne(targetEntity=User.class, cascade = CascadeType.ALL)
	@JoinColumn(name="userSeq")
	private User writer;
	
	@Builder
	public Reply(Long replyNo, String content, Feed feed, User writer) {
		this.replyNo = replyNo;
		this.content = content;
		this.feed = feed;
		this.writer = writer;
	}
	
	
}
