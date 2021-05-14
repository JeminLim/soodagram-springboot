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
@Table(name="tbl_following")
public class Following {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long followingSeq;
	
	@Column
	private String toUserId;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="userSeq")
	private User fromUser;
	
	@Builder
	public Following(User fromUser, String toUserId) {
		this.fromUser = fromUser;
		this.toUserId = toUserId;
	}
	
}
