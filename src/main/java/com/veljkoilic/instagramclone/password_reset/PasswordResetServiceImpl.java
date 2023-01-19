package com.veljkoilic.instagramclone.password_reset;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.email.EmailService;
import com.veljkoilic.instagramclone.exception.BadRequestException;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.user.User;
import com.veljkoilic.instagramclone.user.UserRepository;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

	private final UserService userService;
	private final PasswordResetTokenRepository passTokenRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Value("${localhost.link}")
	private String siteUrl;

	// Create Password Reset Token and save it to the Database
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken resetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(2));
		passTokenRepository.save(resetToken);
	}

	// Generate reset token and send the reset link to the user
	public String sendToken(String email) {
		User user = userService.findUserByEmail(email);
		String token = UUID.randomUUID().toString();

		createPasswordResetTokenForUser(user, token);

		final String link = siteUrl + "/auth/changePassword?token=" + token;
		emailService.send(user.getEmail(), link, "Password reset");

		return "Reset Email successfully sent!";
	}

	// Check for Token expiration date
	public boolean isTokenExpired(PasswordResetToken token) {
		return token.getExpiryDate().isBefore(LocalDateTime.now());
	}

	// Check for password match in passed DTO
	public boolean arePasswordsMatching(PasswordDTO passwordDTO) {
		return passwordDTO.getPasswordOne().equals(passwordDTO.getPasswordTwo());
	}

	// Validate Token and password matching
	public PasswordResetToken getPasswordToken(String token, PasswordDTO passwordDTO) {

		if (!arePasswordsMatching(passwordDTO)) {
			throw new BadRequestException("Passwords do not match");
		}

		PasswordResetToken resetToken = passTokenRepository.findByToken(token)
				.orElseThrow(() -> new NotFoundException("Token not found"));

		if (isTokenExpired(resetToken)) {
			throw new BadRequestException("Password reset token expired.");
		}

		return resetToken;
	}

	public String changeUserPassword(String token, PasswordDTO passwordDTO) {

		PasswordResetToken resetToken = getPasswordToken(token, passwordDTO);

		User user = userService.findUserById(resetToken.getUser().getId());

		user.setPassword(passwordEncoder.encode(passwordDTO.getPasswordOne()));
		userRepository.save(user);

		return "Successfully changed password!";
	}

}
