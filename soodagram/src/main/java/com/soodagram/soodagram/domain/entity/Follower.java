package com.soodagram.soodagram.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Table(name="tbl_follower")
public class Follower {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long followerSeq;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="userSeq")
	private User basedUser;
	
	@Column
	private User targetUser;
	
	@Builder
	public Follower(User basedUser, User targetUser) {
		this.basedUser = basedUser;
		this.targetUser = targetUser;
	}
	
}