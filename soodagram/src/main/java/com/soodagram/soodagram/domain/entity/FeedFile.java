package com.soodagram.soodagram.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Entity
public class FeedFile{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fileId;
	
	@Column(nullable = false)
	private String fileName;
	
	@Column(nullable = false)
	private String filePath;
	
	@ManyToOne
	@JoinColumn(name="feedNo")
	private Feed feed;
	
	@Builder
	public FeedFile(Long fileId, String fileName, String filePath) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
	}
}
