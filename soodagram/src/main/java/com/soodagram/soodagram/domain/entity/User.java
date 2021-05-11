package com.soodagram.soodagram.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@EntityListeners(AuditingEntityListener.class)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userSeq;
	
	@Column(nullable=false)
	private String userEmail;
	
	@Column(nullable=false)
	private String userPw;
	@Column(nullable=false)
	private String userName;
	@Column(nullable=false)
	private String userId;
	
	@Column
	private String userPhone;
	@Column
	private String userGender;
	@Column
	private String userImg;
	
	@CreatedDate
	@Column
	private Date userRegDate;
	
	@Column(columnDefinition="TEXT")
	private String userDesc;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private AuthCode authority;
	
	public enum AuthCode {
		ROLE_USER, ROLE_ADMIN
	}
	
	
	@Builder
	public User(String userEmail, String userPw, String userName, String userId, String userPhone, String userGender, String userImg, String userDesc, AuthCode authority) {
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userName = userName;
		this.userId = userId;
		this.userPhone = userPhone;
		this.userGender = userGender;
		this.userImg = userImg;
		this.userDesc = userDesc;
		this.authority = authority;
	}
}
