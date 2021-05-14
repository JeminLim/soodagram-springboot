package com.soodagram.soodagram;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.entity.Follower;
import com.soodagram.soodagram.domain.entity.Following;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.repository.FollowerRepository;
import com.soodagram.soodagram.domain.repository.FollowingRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;
import com.soodagram.soodagram.dto.UserDTO;
import com.soodagram.soodagram.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserRegistTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private FollowerRepository followerRepository;
	
	@Mock
	private FollowingRepository followingRepository;
			
	@Test
	public void userRegistTest() throws Exception {
		//given
		UserDTO userDTO = createUserDTO(1L);
		when(userRepository.save(userDTO.toEntity())).thenReturn(userDTO.toEntity());	
		//when
		User testUser = userService.registUser(userDTO);		
		
		//then
		Assertions.assertEquals(userDTO.getUserSeq(), testUser.getUserSeq());
		Assertions.assertEquals(userDTO.getUserEmail(), testUser.getUserEmail());
		Assertions.assertEquals(userDTO.getUserId(), testUser.getUserId());
		
	}
	
	
	@Test
	public void userDuplicateCheck() throws Exception {
		//given
		final String userEmail = "sooda@mail.com";
		final String userId = "idSooda";
		when(userRepository.countByUserEmail(userEmail)).thenReturn(1);
		when(userRepository.countByUserId(userId)).thenReturn(1);
		
		//when
		boolean emailCheck = userService.emailCheck(userEmail);
		boolean idCheck = userService.idCheck(userId);
		
		//then
		Assertions.assertEquals(true, emailCheck);
		Assertions.assertEquals(true, idCheck);
		
	}
	
	@Test
	public void getUserInfoTest() throws Exception {
		//given
		UserDTO userDTO = createUserDTO(1L);
		when(userRepository.findByUserId(userDTO.getUserId())).thenReturn(userDTO.toEntity());
		
		//when
		User getUser = userService.getUserInfoById(userDTO.getUserId());
		
		//then
		Assertions.assertEquals(userDTO.toEntity(), getUser);
	}
	
	@Test
	public void followingTest() throws Exception {
		//given
		User user = createUserDTO(1L).toEntity();
		Following following = Following.builder()
				.fromUser(user)
				.toUserId("sooda2Id")
				.build();		
		
		Follower follower = Follower.builder()
				.toUser(userRepository.findByUserId("sooda2Id"))
				.fromUserId(user.getUserId())
				.build();
				
		//when
		 userService.following(user, "sooda2@mail.com");
		//then
		
		verify(followingRepository).save(following); 
		verify(followerRepository).save(follower);
	}
	
	@Test
	public void cancelFollowingTest() throws Exception{
		//given
		User user = createUserDTO(1L).toEntity();
		String targetId = "sooda2Id";
		//when
		userService.cancelFollowing(user, targetId);	
		
		//delete
		verify(followingRepository).delete(Following.builder().fromUser(user).toUserId(targetId).build());
		verify(followerRepository).delete(Follower.builder().fromUserId(targetId).toUser(user).build());
	}
	
	private UserDTO createUserDTO(Long fakeUserSeq) {
		return UserDTO.builder()
				.userSeq(fakeUserSeq)
				.userEmail("sooda@mail.com")
				.userId("idSooda")
				.userPw("1111")
				.build();
	}
	
	
}
