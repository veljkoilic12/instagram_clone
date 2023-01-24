package com.veljkoilic.instagramclone.password_reset;

import com.veljkoilic.instagramclone.user.User;

public interface PasswordResetService {

    void createPasswordResetTokenForUser(User user, String token);

    void sendToken(String email);

    boolean isTokenExpired(PasswordResetToken token);

    boolean arePasswordsMatching(PasswordDTO passwordDTO);

    String confirmPasswordToken(String token, PasswordDTO passwordDTO);
}
