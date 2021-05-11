package com.soodagram.soodagram.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class Reply{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long replyNo;
	
	@Column(nullable=false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="feed")
	private Feed feed;
	
	@CreatedDate
	@Column
	private LocalDateTime regDate;
	
	@ManyToOne
	@JoinColumn(name="userSeq")
	private User user;
	
}
