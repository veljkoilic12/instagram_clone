package com.veljkoilic.instagramclone.auth;

import com.veljkoilic.instagramclone.email_confirmation.ConfirmationToken;
import com.veljkoilic.instagramclone.user.User;

public interface AuthenticationService {

    String register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
