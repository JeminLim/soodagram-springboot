package com.soodagram.soodagram.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
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
	
	@OneToMany(targetEntity=Feed.class, mappedBy="writer", cascade = CascadeType.REMOVE)
	@Builder.Default
	private List<Feed> feeds = new ArrayList<>();
	
	@OneToMany(targetEntity=Follower.class, mappedBy="toUser", cascade = CascadeType.REMOVE)
	@Builder.Default
	private List<Follower> followers = new ArrayList<>();
	
	@OneToMany(targetEntity=Following.class, mappedBy="fromUser", cascade = CascadeType.REMOVE)
	@Builder.Default
	private List<Following> followings = new ArrayList<>();
	
	@OneToMany(targetEntity=UserLikeFeed.class, mappedBy="currUser", cascade = CascadeType.REMOVE)
	@Builder.Default
	private List<UserLikeFeed> likeFeeds = new ArrayList<>();
	
	
	public enum AuthCode {
		ROLE_USER, ROLE_ADMIN
	}
	
	public enum Gender {
		DEFAULT, MALE, FEMALE, BOTH
	}
}
