package com.veljkoilic.instagramclone.password_reset;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.veljkoilic.instagramclone.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PasswordResetToken {

	@Id
	@GeneratedValue
	private int id;

	private String token;

	@OneToOne(targetEntity = User.class)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private LocalDateTime expiryDate;

	public PasswordResetToken(String token, User user, LocalDateTime expiryDate) {
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}
}
