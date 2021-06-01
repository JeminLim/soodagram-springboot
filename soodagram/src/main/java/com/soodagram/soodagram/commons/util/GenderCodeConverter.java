package com.soodagram.soodagram.commons.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.soodagram.soodagram.domain.entity.Account;
import com.soodagram.soodagram.domain.entity.Account.Gender;

@Converter
public class GenderCodeConverter implements AttributeConverter<Gender, String>{

	@Override
	public String convertToDatabaseColumn(Gender attribute) {
		return attribute.toDbValue();
	}

	@Override
	public Gender convertToEntityAttribute(String dbData) {
		return Account.Gender.from(dbData);
	}

	
}
