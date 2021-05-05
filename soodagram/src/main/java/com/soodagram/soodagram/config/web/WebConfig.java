package com.soodagram.soodagram.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.soodagram.soodagram.commons.interceptor.LoginAfterInterceptor;
import com.soodagram.soodagram.commons.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Autowired
	private LoginAfterInterceptor loginAfterInterceptor;	
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024);
		return multipartResolver;
	}
	
	@Bean
	public HiddenHttpMethodFilter httpMethodFilter() {
		HiddenHttpMethodFilter hiddenFilter = new HiddenHttpMethodFilter();
		return hiddenFilter;
	}
	
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/feed/feed");
	}
	
	@Override 
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/*").excludePathPatterns("/login", "/register");
		registry.addInterceptor(loginAfterInterceptor).addPathPatterns("/login", "/register"); 
	 }
	

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/", "classpath:/templates/");
	}
	
	
}
