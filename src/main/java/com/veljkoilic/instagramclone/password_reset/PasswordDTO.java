package com.veljkoilic.instagramclone.password_reset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.veljkoilic.instagramclone.validation.Password;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordDTO {

	@Password
	@JsonProperty("password_one")
	private String passwordOne;

	@Password
	@JsonProperty("password_two")
	private String passwordTwo;
}
