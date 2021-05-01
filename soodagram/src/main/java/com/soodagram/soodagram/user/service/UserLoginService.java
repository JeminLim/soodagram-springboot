package com.soodagram.soodagram.user.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.user.domain.UserVO;

@Service
public class UserLoginService implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		UserVO userVO = new UserVO();
		try {
			userVO = userService.login(userEmail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		if (userVO != null)
			return new User(userVO.getUserEmail(), userVO.getUserPw(), getAuthorities(userVO));	
		else
			return null;
	}
	
	
	private Collection<? extends GrantedAuthority> getAuthorities(UserVO userVO) {
		String userRole = userVO.getAuthority();
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole);
		return authorities;
	}
	
	
}
