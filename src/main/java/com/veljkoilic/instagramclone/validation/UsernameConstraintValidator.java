package com.veljkoilic.instagramclone.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

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

public class UsernameConstraintValidator implements ConstraintValidator<Username, String> {

	@SneakyThrows
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {

		Properties props = new Properties();

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");

		try {
			props.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MessageResolver resolver = new PropertiesMessageResolver(props);

		PasswordValidator validator = new PasswordValidator(resolver,
				Arrays.asList(new WhitespaceRule(), new LengthRule(5, 50)));

		RuleResult result = validator.validate(new PasswordData(username));

		if (result.isValid())
			return true;

		String messageTemplate = "Username is invalid";

		context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation()
				.disableDefaultConstraintViolation();

		return false;
	}

}
