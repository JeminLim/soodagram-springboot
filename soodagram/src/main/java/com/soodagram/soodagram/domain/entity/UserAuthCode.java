package com.soodagram.soodagram.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Table(name="tbl_user_auth_code")
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
public class UserAuthCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private final long authCodeSeq;
	private final Authority authority;
	
	public static enum Authority{
		ROLE_USER, ROLE_ADMIN
	}
}
