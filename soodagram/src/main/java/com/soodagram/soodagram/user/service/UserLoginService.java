package com.soodagram.soodagram.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soodagram.soodagram.user.domain.LoginDTO;
import com.soodagram.soodagram.user.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Service
public class UserLoginService implements UserDetailsService{
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		
		LoginDTO loginUser = userDAO.login(userEmail);
		
		if (loginUser != null) {
			return new User(loginUser.getUserEmail(), loginUser.getUserPw(), loginUser.getAuthorities());
		} throw new UsernameNotFoundException("User '" + userEmail + "' not found");
		
	}
	
	
}
