package com.veljkoilic.instagramclone.auth;

import com.veljkoilic.instagramclone.validation.Password;
import com.veljkoilic.instagramclone.validation.Username;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	@Username
	private String username;

	@Password
	private String password;

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}
}
