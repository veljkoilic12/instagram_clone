package com.veljkoilic.instagramclone.auth;

public interface AuthenticationService {

	String register(RegisterRequest request);

	AuthenticationResponse authenticate(AuthenticationRequest request);
}
