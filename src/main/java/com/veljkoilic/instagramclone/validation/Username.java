package com.veljkoilic.instagramclone.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UsernameConstraintValidator.class)
public @interface Username {

	String message() default "Invalid Username";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
