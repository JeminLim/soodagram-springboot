package com.soodagram.soodagram.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soodagram.soodagram.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByAccountEmail(String accountEmail);
	Optional<Account> findByAccountId(String accountId);
	int countByAccountEmail(String accountEmail);
	int countByAccountId(String accountId);
	List<Account> findAllByAccountIdContains(String accountId);
}
