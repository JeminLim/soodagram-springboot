package com.soodagram.soodagram.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.domain.repository.FeedFileRepository;
import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.dto.FeedDTO;

@Service
public class FeedService {
	
	@Autowired
	private FeedRepository feedRepository;
	
	@Autowired
	private FeedFileRepository feedFileRepository;
	
	@Autowired
	private FeedHashtagRepository feedHashtagRepository;
	
	@Autowired
	private HashtagRepository hashtagRepository;
	
	/**
	 * 피드 작성 및 열람
	 */
	public Feed getFeed(Long feedNo) throws Exception {
		return feedRepository.findByFeedNo(feedNo).orElseThrow(() -> new NotFoundException());
	}
	
	public void writeFeed(Feed feed) throws Exception{
		//피드 Id를 위한 저장
		Feed savedFeed = feedRepository.save(feed);	
		//형태소 분리
		List<String> splitedContent = new ArrayList<>(Arrays.asList(savedFeed.getContent().split(" ")));
		
		List<Hashtag> hashtagList = splitedContent.stream()
			.filter(word -> word.indexOf("#") == 0)
			.map(hashtag -> {Hashtag filtered = Hashtag.builder().content(hashtag).build();
							 hashtagRepository.save(filtered);
							 return filtered;})
			.collect(Collectors.toList());
		
		for(Hashtag tag: hashtagList) {
			FeedHashtag feedHashtag = new FeedHashtag(savedFeed, tag);
			feedHashtagRepository.save(feedHashtag);
			savedFeed.addHashtag(feedHashtag);
		}
		
		//태그 포함된 피드 컨텐츠
		String convertedContent = splitedContent.stream().map(content -> {
			if(content.indexOf("#") == 0) { 
				content = "<a th:href=" + "/search/hashtag?hashtag=" + content + ">" + content + "</a>";}
			return content;})
			.collect(Collectors.joining(" "));
		
		savedFeed.getFiles().forEach(file -> file.setFeed(savedFeed));
		feedFileRepository.saveAll(savedFeed.getFiles());
		
		
		//피드 전체적인 내용 저장
		feed.setContent(convertedContent);
		feedRepository.save(feed);
		
	}
	
	public List<FeedDTO> getFollowingFeed(Account loginUser, int page) throws Exception {
		List<Account> userList = new ArrayList<>(loginUser.getFollowings());
		return FeedDTO.of(feedRepository.findByWriterIn(userList, PageRequest.of(page,  5, Sort.Direction.DESC)));		
		
	}
	
	
	public void deleteFeed(Long feedNo) throws Exception{
		feedRepository.deleteByFeedNo(feedNo); 
	}
	 
	public void deleteFeed(Feed feed) throws Exception {
		feedRepository.delete(feed);
	}
}
