package com.soodagram.soodagram.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name="tbl_user")
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
	@Enumerated(EnumType.STRING)
	private Gender userGender;
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
	
	@OneToMany(targetEntity=Feed.class, mappedBy="writer")
	@Column
	private List<Feed> feeds;
	
	@OneToMany(targetEntity=Follower.class, mappedBy="toUser")
	@Column
	private List<Follower> followers;
	
	@OneToMany(targetEntity=Following.class, mappedBy="fromUser")
	@Column
	private List<Following> following;
	
	
	public enum AuthCode {
		ROLE_USER, ROLE_ADMIN
	}
	
	public enum Gender {
		DEFAULT, MALE, FEMALE, BOTH
	}
	
	@Builder
	public User(Long userSeq, String userEmail, String userPw, String userName, String userId, String userPhone, Gender userGender, String userImg, String userDesc, AuthCode authority,List<Feed> feeds, List<Follower> followers, List<Following> following) {
		this.userSeq = userSeq;
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userName = userName;
		this.userId = userId;
		this.userPhone = userPhone;
		this.userGender = userGender;
		this.userImg = userImg;
		this.userDesc = userDesc;
		this.authority = authority;
		this.followers = followers;
		this.following = following;
		this.feeds = feeds;
	}
}
