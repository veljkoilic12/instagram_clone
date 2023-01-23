package com.veljkoilic.instagramclone.email_confirmation;

import java.time.LocalDateTime;
import java.util.UUID;

import com.veljkoilic.instagramclone.email.EmailService;
import com.veljkoilic.instagramclone.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.exception.BadRequestException;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.user.UserService;

@Service
@RequiredArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService {

    @Value("${localhost.link}")
    private String siteUrl;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final UserService userService;
    private final EmailService emailService;

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
            sendConfirmationToken(confirmationToken.getUser());
            throw new BadRequestException("Your token has expired, new activation link has been sent to your email address");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        userService.confirmUserEmail(confirmationToken.getUser().getEmail());

        return "Your account has been successfully confirmed!";
    }

    public void sendConfirmationToken(User user) {

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30), user);

        saveConfirmationToken(confirmationToken);

        final String link = siteUrl + "/auth/confirm?token=" + confirmationToken.getToken();
        emailService.send(user.getEmail(), link, "Confirm your Email");
    }
}
