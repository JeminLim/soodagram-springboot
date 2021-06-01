package com.soodagram.soodagram.dto;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Account.AuthCode;
import com.soodagram.soodagram.domain.entity.Account.Gender;
import com.soodagram.soodagram.domain.entity.Feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AccountDTO {	
	@Getter
	@Setter
	@AllArgsConstructor
	@Builder	
	public static class Login implements UserDetails{
		private static final long serialVersionUID = 1L;
		private String accountEmail;
		private String accountPassword;
		private Collection<? extends GrantedAuthority> authroities;		
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));}
		@Override
		public String getPassword() {return this.accountPassword;}
		@Override
		public String getUsername() {return this.accountEmail;}
		@Override
		public boolean isAccountNonExpired() {return true;}
		@Override
		public boolean isAccountNonLocked() {return true;}
		@Override
		public boolean isCredentialsNonExpired() {return true;}
		@Override
		public boolean isEnabled() {return true;}	
		
		public static Login of(Account account) {
			ModelMapper modelMapper = new ModelMapper();
			final Login dto = modelMapper.map(account, Login.class);
			return dto;
		}
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@Builder
	public static class Regist {
		
		private String accountEmail;
		private String accountPassword;
		private String accountName;
		private String accountId;
		@Builder.Default
		private AuthCode authority = AuthCode.ROLE_USER;
		
		public Account toEntity() {
			return Account.builder()
					.accountEmail(accountEmail)
					.accountPassword(accountPassword)
					.accountName(accountName)
					.accountId(accountId)
					.build();
		}		
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class AccountBasicInfo {
		private String accountEmail;
		private String accountName;
		private String accountId;
		private String profileImg;
		private String description;
		@Builder.Default
		private List<Feed> writtenFeed = new ArrayList<>();
		@Builder.Default
		private Set<Account> followers = new HashSet<>();
		@Builder.Default
		private Set<Account> followings = new HashSet<>();
		
		public Account toEntity() {
			return Account.builder()
					.accountEmail(accountEmail)
					.accountName(accountName)
					.accountId(accountId)
					.profileImg(profileImg)
					.writtenFeed(writtenFeed)
					.followers(followers)
					.followings(followings)
					.description(description)
					.build();
		}
		
		public static AccountBasicInfo of(Account account) {
			ModelMapper modelMapper = new ModelMapper();
			final AccountBasicInfo dto = modelMapper.map(account, AccountBasicInfo.class);
			return dto;
		}
		
		public static List<AccountBasicInfo> of(List<Account> userList) {
			return userList.stream()
					.map(AccountBasicInfo::of)
					.collect(Collectors.toList());
		}
		
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@Builder
	public static class AccountProfileInfo {
		private String accountEmail;
		private String accountName;
		private String accountId;
		private String phone;
		private Gender gender;
		private String profileImg;
		private String description;
		
		public Account toEntity() {
			return Account.builder()
					.accountEmail(accountEmail)
					.accountName(accountName)
					.accountId(accountId)
					.phone(phone)
					.gender(gender)
					.profileImg(profileImg)
					.description(description)
					.build();
		}
	}
	
}
