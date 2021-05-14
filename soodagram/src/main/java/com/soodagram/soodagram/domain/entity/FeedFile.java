package com.soodagram.soodagram.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Entity
@Table(name="tbl_feed_file")
public class FeedFile{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fileId;
	
	@Column(nullable = false)
	private String fileName;
	
	@Column(nullable = false)
	private String filePath;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@Builder
	public FeedFile(Long fileId, String fileName, String filePath, Feed feed) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.feed = feed;
	}
}
