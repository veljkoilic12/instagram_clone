package com.veljkoilic.instagramclone.auth;

import com.veljkoilic.instagramclone.password_reset.PasswordDTO;
import com.veljkoilic.instagramclone.password_reset.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veljkoilic.instagramclone.email_confirmation.ConfirmationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ConfirmationService confirmationService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(confirmationService.confirmToken(token));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "email") String userEmail) {
        return ResponseEntity.ok(passwordResetService.sendToken(userEmail));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam(name = "token") String token,
                                                 @RequestBody PasswordDTO passwordDTO) {
        return ResponseEntity.ok(passwordResetService.changeUserPassword(token, passwordDTO));
    }
}
