package com.veljkoilic.instagramclone.validation;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

	@SneakyThrows
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		// customizing validation messages

		Properties props = new Properties();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");

		props.load(inputStream);

		MessageResolver resolver = new PropertiesMessageResolver(props);

		PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(
				// length between 8 and 16 characters
				new LengthRule(8, 16),

				// at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 1),

				// at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 1),

				// at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 1),

				// at least one symbol (special character)
				new CharacterRule(EnglishCharacterData.Special, 1),

				// no whitespace
				new WhitespaceRule()));

		RuleResult result = validator.validate(new PasswordData(password));

		if (result.isValid())
			return true;

		List<String> messages = validator.getMessages(result);

		String messageTemplate = String.join(",", messages);

		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();

		return false;
	}

}
