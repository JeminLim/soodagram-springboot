package com.soodagram.soodagram;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soodagram.soodagram.domain.entity.Follower;
import com.soodagram.soodagram.domain.entity.Following;
import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.User.AuthCode;
import com.soodagram.soodagram.domain.repository.FollowerRepository;
import com.soodagram.soodagram.domain.repository.FollowingRepository;
import com.soodagram.soodagram.domain.repository.UserRepository;
import com.soodagram.soodagram.dto.UserDTO;
import com.soodagram.soodagram.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
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
		UserDTO user = createUserDTO(1L);
		
		UserDTO targetUser = UserDTO.builder()
				.userSeq(2L)
				.userId("sooda2Id")
				.build();
		
		Following following = Following.builder()
				.basedUser(user.toEntity())
				.targetUser(targetUser.toEntity())
				.build();		
		
		
		Follower follower = Follower.builder()
				.basedUser(targetUser.toEntity())
				.targetUser(user.toEntity())
				.build();
				
		//when
		 userService.following(user, targetUser);
		//then
		
		verify(followingRepository).save(following); 
		verify(followerRepository).save(follower);
	}
	
	@Test
	public void cancelFollowingTest() throws Exception{
		//given
		UserDTO user = createUserDTO(1L);
		UserDTO targetUser = UserDTO.builder()
				.userSeq(2L)
				.userId("sooda2Id")
				.build();
		
		//when
		userService.cancelFollowing(user, targetUser);	
		
		//then
		verify(followingRepository).delete(Following.builder().basedUser(user.toEntity()).targetUser(targetUser.toEntity()).build());
		verify(followerRepository).delete(Follower.builder().basedUser(targetUser.toEntity()).targetUser(user.toEntity()).build());
	}
	
	@Test
	public void recommendUserTest() throws Exception {
		//given
		UserDTO user = createUserDTO(1L);
		
		UserDTO targetUser = UserDTO.builder()
				.userSeq(2L)
				.userId("sooda2Id")
				.build();
		
		Following following = Following.builder()
				.basedUser(user.toEntity())
				.targetUser(targetUser.toEntity())
				.build();
		user.getFollowings().add(following);
		
		List<User> userList = new ArrayList<>();
		userList.add(user.toEntity());
		for(Long i = 2L; i < 5; i++) {
			userList.add(createUserDTO(i).toEntity());
		}
		when(userRepository.findAll()).thenReturn(userList);
		
		//when
		List<User> recommendUser = userService.recommendUser(user);
		
		//then
		Assertions.assertTrue(recommendUser.size() > 0);
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
