package com.soodagram.soodagram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.FeedHashtag;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.UserLikeFeed;
import com.soodagram.soodagram.domain.repository.FeedFileRepository;
import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.LikeRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;
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
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 피드 작성 및 열람
	 */
	public void writeFeed(FeedDTO feedDTO) throws Exception{
		//피드 Id를 위한 저장
		Feed feed = feedRepository.save(feedDTO.toFeedEntity());	
		
		//형태소 분리
		String[] splitedContent = feedDTO.getContent().split(" ");
		//태그 포함된 피드 컨텐츠
		String convertedContent = "";
		
		for(String word : splitedContent) {
			if(word.indexOf("#") == 0) {
				//해시태그 등록
				Hashtag hashtag = Hashtag.builder().content(word).build();	
				hashtagRepository.save(hashtag);				
				
				FeedHashtag feedHashtag = FeedHashtag.builder().hashtag(hashtag).feed(feed).build();
				
				feedHashtagRepository.save(feedHashtag);				
				
				
				hashtag.getFeedHashtag().add(feedHashtag);
				feed.getFeedHashtags().add(feedHashtag);
				

				
				word = "<a th:href=" + "/search/hashtag?hashtag=" + word + ">" + word + "</a>";
			}
			convertedContent += (word + " ");
		}
		
		//파일 등록
		for(FeedFile file: feedDTO.toFeedFileEntity(feed)) 
			feedFileRepository.save(file);
		
		//피드 전체적인 내용 저장
		feed.setContent(convertedContent);
		feedRepository.save(feed);
		
	}
	
	
	public void deleteFeed(FeedDTO feedDTO) throws Exception{
		feedRepository.delete(feedDTO.toFeedEntity());
	}
	
	/**
	 * 피드 소셜 기능 관련
	 */
	
	public void insertLike(FeedDTO feedDTO, User currUser) throws Exception{
		Feed targetFeed = feedDTO.toFeedEntity();
		
		UserLikeFeed likeObj = UserLikeFeed.builder().currUser(currUser).feed(targetFeed).build();
		likeRepository.save(likeObj);
		
		targetFeed.getLikes().add(likeObj);
		currUser.getLikeFeeds().add(likeObj);		
		
		feedRepository.save(targetFeed);
		userRepository.save(currUser);
	}
	
	public void cancelLike(FeedDTO feedDTO, User currUser) throws Exception{
		Feed feed = feedDTO.toFeedEntity();
		UserLikeFeed like = likeRepository.findUserLikeFeedByUserAndFeed(feed, currUser);
		likeRepository.delete(like);
	}
	
}
