package com.soodagram.soodagram.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.LikeFeed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class FeedDTO {
	
	private Long feedNo;
	private Account writer;
	private String content;
	private FeedFile[] files;
	@Builder.Default
	private List<FeedHashtag> feedHashtags = new ArrayList<>();
	@Builder.Default
	private List<LikeFeed> likes = new ArrayList<>();
	
	public Feed toFeedEntity() {
		List<FeedFile> fileList = new ArrayList<>(Arrays.asList(files));
		Feed build = Feed.builder()
				.feedNo(feedNo)
				.writer(writer)
				.content(content)
				.files(fileList)
				.build();
		return build;
	}	
	
	public static FeedDTO of(Feed feed) {
		FeedFile[] fileArr = feed.getFiles().toArray(new FeedFile[feed.getFiles().size()]);
		
		ModelMapper modelMapper = new ModelMapper();
		final FeedDTO dto = modelMapper.map(feed, FeedDTO.class);
		
		dto.setFiles(fileArr);
		return dto;
	}
	
	public static List<FeedDTO> of(List<Feed> feedList) {
		return feedList.stream()
				.map(FeedDTO::of)
				.collect(Collectors.toList());
	}
	
}
