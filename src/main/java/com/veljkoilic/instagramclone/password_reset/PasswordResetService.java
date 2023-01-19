package com.veljkoilic.instagramclone.password_reset;

import com.veljkoilic.instagramclone.user.User;

public interface PasswordResetService {

	void createPasswordResetTokenForUser(User user, String token);

	String sendToken(String email);

	boolean isTokenExpired(PasswordResetToken token);

	boolean arePasswordsMatching(PasswordDTO passwordDTO);

	PasswordResetToken getPasswordToken(String token, PasswordDTO passwordDTO);

	String changeUserPassword(String token, PasswordDTO passwordDTO);
}
