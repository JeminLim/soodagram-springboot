package com.soodagram.soodagram.dto;

import java.util.ArrayList;
import java.util.List;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Reply;
import com.soodagram.soodagram.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedDTO {
	
	private Long feedNo;
	private User writer;
	private String content;
	private FeedFile[] files;
	@Builder.Default
	private List<FeedHashtag> feedHashtags = new ArrayList<>();
	@Builder.Default
	private List<Reply> replies = new ArrayList<>();
	
	public Feed toFeedEntity() {
		Feed build = Feed.builder()
				.feedNo(feedNo)
				.writer(writer)
				.content(content)
				.build();
		return build;
	}
	
	public List<FeedFile> toFeedFileEntity(Feed feed) {
		List<FeedFile> toList = new ArrayList<>();
		for(FeedFile file: files) {
			FeedFile build = FeedFile.builder()
					.fileName(file.getFileName())
					.filePath(file.getFilePath())
					.feed(feed)
					.build();
			toList.add(build);
		}
				
		return toList;
	}
	
	
}
