package com.veljkoilic.instagramclone.password_reset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.veljkoilic.instagramclone.validation.Password;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordDTO {

	@Password
	@JsonProperty("new_password")
	private String newPassword;

	@Password
	@JsonProperty("repeated_password")
	private String repeatedNewPassword;
}
