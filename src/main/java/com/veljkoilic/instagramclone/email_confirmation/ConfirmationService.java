package com.veljkoilic.instagramclone.email_confirmation;

public interface ConfirmationService {
	void saveConfirmationToken(ConfirmationToken token);

	void confirmToken(String token);
}
