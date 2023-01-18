package com.veljkoilic.instagramclone.emailconfirmation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

	Optional<ConfirmationToken> findByToken(String token);
}
