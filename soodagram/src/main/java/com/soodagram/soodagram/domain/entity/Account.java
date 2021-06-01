package com.soodagram.soodagram.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.soodagram.soodagram.commons.util.AuthCodeConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="tbl_account")
@EqualsAndHashCode(of= {"accountSeq"})
public class Account {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountSeq;
	
	@Column(nullable=false)
	private String accountEmail;
	
	@Column(nullable=false)
	private String accountPassword;
	
	@Column(nullable=false)
	private String accountName;
	
	@Column(nullable=false)
	private String accountId;
	
	@Column
	private String phone;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column
	@Builder.Default
	private String profileImg = "/images/non_profile.png";
	
	@CreatedDate
	@Column
	private LocalDate regDate;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Column(nullable=false)
	@Convert(converter = AuthCodeConverter.class)
	private AuthCode authority;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="writer", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Feed> writtenFeed = new ArrayList<>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="account", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<LikeFeed> likeFeeds = new ArrayList<>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="writer", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Reply> writtenReplies = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="parentAccount")
	private Account thisUser;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "thisUser", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<Account> followers = new HashSet<>();
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "thisUser", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<Account> followings = new HashSet<>();
	
	
	public void addLikeFeed(LikeFeed likeFeed) {
		likeFeeds.add(likeFeed);
		likeFeed.getFeed().getLikeAccounts().add(likeFeed);
	}
	
	public void removeLikeFeed(LikeFeed likeFeed) {
		likeFeeds.remove(likeFeed);
		likeFeed.getAccount().getLikeFeeds().remove(likeFeed);
	}

	public void doFollow(Account target) {
		this.followings.add(target);
		target.getFollowers().add(this);
	}
	
	public void cancelFollow(Account target) {
		this.followings.remove(target);
		target.getFollowers().remove(this);
	}
	
	
	public enum AuthCode {
		ROLE_USER, ROLE_ADMIN;
		
		public String toDbValue() {
			return this.name();
		}
		
		public static AuthCode from(String dbData) {
			return AuthCode.valueOf(dbData);
		}
		
	}

	public enum Gender {
		DEFAULT, MALE, FEMALE, BOTH;
		
		public String toDbValue() {
			return this.name();
		}
		
		public static Gender from(String dbData) {
			return Gender.valueOf(dbData);
		}
	}
	
	
	
	@Override
	public String toString() {
		return "Account{" + 
				"accountSeq=" + accountSeq +
				", accountEmail=" + accountEmail +
				", accountPassword=" + accountPassword +
				", accountName=" + accountName +
				", accountId=" + accountId +
				", phone=" + phone +
				", gender=" + gender +
				", profileImg=" + profileImg +
				", regDate=" + regDate +
				", description=" + description +
				", authority=" + authority +
				", writtenFeed=" + writtenFeed.size()  +
				", likeFeeds=" + likeFeeds.size()  +
				", writtenReplies=" + writtenReplies.size() +
				", followers=" + followers.size() +
				", followings=" + followings.size();
				
	}
}
