package com.soodagram.soodagram;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.soodagram.soodagram.domain.entity.User;
import com.soodagram.soodagram.domain.entity.User.AuthCode;
import com.soodagram.soodagram.domain.entity.UserAuthCode.Authority;
import com.soodagram.soodagram.domain.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(value = "dev")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testRegister() {
		User user = User.builder()
				.userEmail("abc@mail.com")
				.userPw("password")
				.userName("sooda")
				.userId("idSooda")
				.authority(AuthCode.ROLE_USER)
				.build();
		User savedUser = userRepository.save(user);
		System.out.println(savedUser.toString());
		Assertions.assertEquals(user.getUserEmail(), savedUser.getUserEmail(), "savedUserTest");
	}
	
	@Test
	public void testUserDuplicate() {
		User user = User.builder()
				.userEmail("abc@mail.com")
				.userPw("password")
				.userName("sooda")
				.userId("idSooda")
				.authority(AuthCode.ROLE_USER)
				.build();
		userRepository.save(user);
		Assertions.assertEquals(1, userRepository.countByUserEmail("abc@mail.com"));
		Assertions.assertEquals(1, userRepository.countByUserId("idSooda"));
	}
	
	@Test
	public void testGetUserByUserEmail() {
		User user = User.builder()
				.userEmail("abc@mail.com")
				.userPw("password")
				.userName("sooda")
				.userId("idSooda")
				.authority(AuthCode.ROLE_USER)
				.build();
		User savedUser = userRepository.save(user);
		
		Optional<User> userFindByEmail = userRepository.findByUserEmail(user.getUserEmail());
		userFindByEmail.ifPresent(value -> Assertions.assertEquals(savedUser.getUserEmail(), value.getUserEmail()));
	}
	
	@Test
	public void testUpdateUser() {
		User user = User.builder()
				.userEmail("abc@mail.com")
				.userPw("password")
				.userName("sooda")
				.userId("idSooda")
				.authority(AuthCode.ROLE_USER)
				.build();
		userRepository.save(user);
		
		Optional<User> userFindByEmail = userRepository.findByUserEmail(user.getUserEmail());
		userFindByEmail.ifPresent(selectUser -> {
			selectUser.setUserName("notsooda");
			selectUser.setUserId("notSoodaid");
			userRepository.save(selectUser);
			Assertions.assertEquals(user.getUserName(), selectUser.getUserName());
			Assertions.assertEquals(user.getUserId(), selectUser.getUserId());
		});	
	}
	
	
}
