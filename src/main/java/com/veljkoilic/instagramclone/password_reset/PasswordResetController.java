package com.veljkoilic.instagramclone.password_reset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class PasswordResetController {

	@Autowired
	private PasswordResetService passwordResetService;

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestParam(name = "email") String userEmail) {
		return ResponseEntity.ok(passwordResetService.sendToken(userEmail));
	}

	@GetMapping("/changePassword")
	public String changePasswordPage(@RequestParam(name = "token") String token) {
		// TODO return change password page
		return token;
	}

	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestParam(name = "token") String token,
			@RequestBody PasswordDTO passwordDTO) {
		return ResponseEntity.ok(passwordResetService.changeUserPassword(token, passwordDTO));
	}
}
