package com.soodagram.soodagram.dto;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedFileDTO {
	
	private Long fileId;
	private String fileName;
	private String filePath;
	private Feed feed;
	
	public FeedFile toEntity() {
		FeedFile build = FeedFile.builder()
				.fileId(fileId)
				.fileName(fileName)
				.filePath(filePath)
				.feed(feed)
				.build();
		return build;
	}
	
	@Builder
	public FeedFileDTO(Long fileId, String fileName, String filePath, Feed feed) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.feed = feed;
	}
	
}
