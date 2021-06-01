package com.soodagram.soodagram.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.repository.AccountRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(value="dev")
public class AccountRepositoryTest {
	
	@Mock
	private AccountRepository accountRepository;
	
	@Test
	@DisplayName("이메일로 유저 찾기 테스트")
	public void findByAccountEmailTest() throws Exception {
		//given
		Account user = createAccount("test@mail.com", "1111", "testname", "testid");
		when(accountRepository.findByAccountEmail("test@mail.com")).thenReturn(Optional.of(user));
		
		//when
		Account result = accountRepository.findByAccountEmail("test@mail.com").orElseThrow(() -> new NoSuchElementException());
		
		//then
		verify(accountRepository, times(1)).findByAccountEmail("test@mail.com");
		Assertions.assertEquals(user.getAccountEmail(), result.getAccountEmail());
		Assertions.assertEquals(user.getAccountName(), result.getAccountName());
		Assertions.assertEquals(user.getAccountId(), result.getAccountId());
		
	}
	
	
	private Account createAccount(String email, String pw, String name, String id)  {
		return Account.builder()
				.accountEmail(email)
				.accountPassword(pw)
				.accountName(name)
				.accountId(id)
				.build();
	}
}
