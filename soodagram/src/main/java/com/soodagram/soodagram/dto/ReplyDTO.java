package com.soodagram.soodagram.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	
	private Long replyNo;
	private String content;
	private Feed feed;
	private Account writer;
	
	public Reply toEntity() {
		return Reply.builder()
				.content(content)
				.feed(feed)
				.writer(writer)
				.build();
	}
	
	public static ReplyDTO of(Reply reply) {
		ModelMapper modelMapper = new ModelMapper();
		final ReplyDTO dto = modelMapper.map(reply, ReplyDTO.class);
		return dto;
	}
	
	public static List<ReplyDTO> of(List<Reply> replyList) {
		return replyList.stream()
				.map(ReplyDTO::of)
				.collect(Collectors.toList());
	}
	
	
}
