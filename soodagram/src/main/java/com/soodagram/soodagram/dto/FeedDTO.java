package com.soodagram.soodagram.dto;

import java.util.List;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Reply;
import com.soodagram.soodagram.domain.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedDTO {
	
	private Long feedNo;
	private User writer;
	private String content;
	private List<FeedFile> files;
	private List<FeedHashtag> feedHashtag;
	private List<Reply> replies;
	
	public Feed toEntity() {
		Feed build = Feed.builder()
				.feedNo(feedNo)
				.writer(writer)
				.content(content)
				.files(files)
				.feedHashtag(feedHashtag)
				.replies(replies)
				.build();
		return build;
	}
	
	@Builder
	public FeedDTO(Long feedNo, User writer, String content, List<FeedFile> files, List<FeedHashtag> feedHashtag, List<Reply> replies) {
		this.feedNo = feedNo;
		this.writer = writer;
		this.content = content;
		this.files = files;
		this.feedHashtag = feedHashtag;
		this.replies = replies;
	}
	
}
