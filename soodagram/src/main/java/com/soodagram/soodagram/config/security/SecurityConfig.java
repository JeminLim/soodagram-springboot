package com.soodagram.soodagram.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.soodagram.soodagram.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;
	
	@Autowired
	private CustomLoginFailureHandler customLoginFailureHandler;
	
	@Autowired
	private AccountService userSerivce;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/css/**", "/dist/**", "/fonts/**", "/images/**", "/js/**").permitAll()
								.antMatchers("/login","/regist", "/user/regist/**", "/login/**").permitAll()
								.anyRequest().authenticated()
								.and()
								.formLogin().loginPage("/login").loginProcessingUrl("/doLogin").usernameParameter("userEmail").passwordParameter("userPw")
								.successHandler(customLoginSuccessHandler)
								.failureHandler(customLoginFailureHandler)
								.permitAll()
								.and().logout().logoutSuccessUrl("/login")
								.and().httpBasic();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSerivce).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.httpFirewall(defaultHttpFirewall());
	}
	
	
	
}
