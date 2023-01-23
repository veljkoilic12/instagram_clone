package com.veljkoilic.instagramclone.email_confirmation;

import com.veljkoilic.instagramclone.user.User;

public interface ConfirmationService {
    void saveConfirmationToken(ConfirmationToken token);

    String confirmToken(String token);

    void sendConfirmationToken(User user);
}
