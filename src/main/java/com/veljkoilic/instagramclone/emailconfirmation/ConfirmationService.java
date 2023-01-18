package com.veljkoilic.instagramclone.emailconfirmation;

public interface ConfirmationService {
	void saveConfirmationToken(ConfirmationToken token);

	String confirmToken(String token);
}
