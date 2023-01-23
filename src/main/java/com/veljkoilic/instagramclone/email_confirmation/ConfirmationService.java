package com.veljkoilic.instagramclone.email_confirmation;

public interface ConfirmationService {
    void saveConfirmationToken(ConfirmationToken token);

    String confirmToken(String token);
}
