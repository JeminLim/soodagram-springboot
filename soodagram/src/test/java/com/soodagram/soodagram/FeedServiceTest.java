package com.soodagram.soodagram;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.entity.Feed;
import com.soodagram.soodagram.domain.entity.FeedFile;
import com.soodagram.soodagram.domain.entity.Hashtag;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.User.AuthCode;
import com.soodagram.soodagram.domain.entity.UserLikeFeed;
import com.soodagram.soodagram.domain.repository.FeedFileRepository;
import com.soodagram.soodagram.domain.repository.FeedHashtagRepository;
import com.soodagram.soodagram.domain.repository.FeedRepository;
import com.soodagram.soodagram.domain.repository.HashtagRepository;
import com.soodagram.soodagram.domain.repository.LikeRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;
import com.soodagram.soodagram.dto.FeedDTO;
import com.soodagram.soodagram.service.FeedService;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {
	@InjectMocks
	private FeedService feedService;
	
	@Mock
	private FeedRepository feedRepository;
	
	@Mock
	private FeedFileRepository feedFileRepository;
	
	@Mock
	private FeedHashtagRepository feedhashtagRepository;
	
	@Mock
	private HashtagRepository hashtagRepository;
	
	@Mock
	private LikeRepository likeRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	public void writeFeedTest() throws Exception {
		//given
		FeedDTO feedDTO = createFeedDTO(1L);
		when(feedRepository.save(any())).thenReturn(feedDTO.toFeedEntity());		
		when(feedFileRepository.save(any())).thenReturn(feedDTO.toFeedFileEntity(feedDTO.toFeedEntity()).get(0));		
		when(hashtagRepository.save(any())).thenReturn(Hashtag.builder().content("#sample").build());
		
		//when
		feedService.writeFeed(feedDTO);
		//then
		verify(feedRepository).save(feedDTO.toFeedEntity());
		verify(feedFileRepository).save(any());
		verify(feedhashtagRepository).save(any());
		verify(hashtagRepository).save(any());
		
	}
	
	@Test
	public void deleteFeedTest() throws Exception {
		//given
		FeedDTO feedDTO = createFeedDTO(1L);
		//when
		feedService.deleteFeed(feedDTO);
		//then
		verify(feedRepository).delete(feedDTO.toFeedEntity());
	}
	
	@Test
	public void feedLikeTest() throws Exception {
		//given
		FeedDTO feedDTO = createFeedDTO(1L);
		User user = User.builder().userSeq(2L).userEmail("tester2@mail.com").userId("testerId").userName("tester").authority(AuthCode.ROLE_USER).build();
		UserLikeFeed likeObj = UserLikeFeed.builder().likeSeq(1L).currUser(user).feed(feedDTO.toFeedEntity()).build();	
		when(likeRepository.save(any())).thenReturn(likeObj);

		Feed afterLikeFeed = feedDTO.toFeedEntity();
		afterLikeFeed.getLikes().add(likeObj);
		User afterLikeUser = user;
		afterLikeUser.getLikeFeeds().add(likeObj);
		
		when(feedRepository.save(any())).thenReturn(afterLikeFeed);
		when(userRepository.save(any())).thenReturn(afterLikeUser);
		
		//when
		feedService.insertLike(feedDTO, user);
		
		//then		
		verify(likeRepository).save(any());
		verify(feedRepository).save(any());
		verify(userRepository).save(any());
		
	}
	
	@Test
	public void cancelLikeTest() throws Exception{
		//given
		FeedDTO feedDTO = createFeedDTO(1L);
		User user = User.builder().userSeq(2L).userEmail("tester2@mail.com").userId("testerId").userName("tester").authority(AuthCode.ROLE_USER).build();
		//when
		feedService.cancelLike(feedDTO, user);
		//then
		verify(likeRepository).delete(any());
	}
	
	private FeedDTO createFeedDTO(Long feedNo) {
		FeedFile[] files = {FeedFile.builder().fileId(1L).fileName("test.png").filePath("/images").build()};
		
		return FeedDTO.builder()
				.feedNo(feedNo)
				.content("this is sample feed #sample")
				.writer(User.builder().userSeq(1L).userEmail("sooda@mail.com").userId("soodaId").build())
				.files(files)
				.build();				
	}
	
	
	
	
}
