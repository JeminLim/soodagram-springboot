package com.soodagram.soodagram.commons.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Account.AuthCode;

@Converter
public class AuthCodeConverter implements AttributeConverter<AuthCode, String>{

	@Override
	public String convertToDatabaseColumn(AuthCode attribute) {
		return attribute.toDbValue();
	}

	@Override
	public AuthCode convertToEntityAttribute(String dbData) {
		return Account.AuthCode.from(dbData);
	}

}
