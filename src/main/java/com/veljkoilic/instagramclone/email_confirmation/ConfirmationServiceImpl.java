package com.veljkoilic.instagramclone.email_confirmation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.exception.BadRequestException;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new BadRequestException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        userService.confirmUserEmail(confirmationToken.getUser().getEmail());

        return "Your account has been successfully confirmed!";
    }
}
