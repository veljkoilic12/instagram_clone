package com.veljkoilic.instagramclone.auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.config.JwtServiceImpl;
import com.veljkoilic.instagramclone.email_confirmation.ConfirmationService;
import com.veljkoilic.instagramclone.exception.BadRequestException;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.user.UserRepository;
import com.veljkoilic.instagramclone.user.Role;
import com.veljkoilic.instagramclone.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationService confirmationService;

    public String register(RegisterRequest request) {

        // Check if username or email already exist
        Optional<User> userFromDBFoundByUsername = userRepository.findByUsername(request.getUsername());
        Optional<User> userFromDBFoundByEmail = userRepository.findByEmail(request.getEmail());

        if (!userFromDBFoundByEmail.isEmpty() || !userFromDBFoundByUsername.isEmpty()) {
            throw new BadRequestException("User with the same username or email already exists");
        }

        // Check if password and repeatedPassword match
        if (!request.getPassword().equals(request.getRepeatedPassword())) {
            throw new BadRequestException("Two passwords are not the same");
        }

        // Build our user using parameters passed in the request and save it.
        var user = User.builder().username(request.getUsername()).email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
        userRepository.save(user);

        // Generate token using UserDetails (can pass the user, because it extends
        // UserDetails).
        var jwtToken = jwtService.generateToken(user);

        confirmationService.sendConfirmationToken(user);

        // Build and return response.
        return "Your account has been created. You should receive an activation email shortly.";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        String username = request.getUsername();

        // If we got to this point, user is authenticated
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username: " + username + " not found."));

        // In case authentication fails, exception will be thrown
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

        // Generate token using UserDetails (can pass the user, because it extends
        // UserDetails).
        var jwtToken = jwtService.generateToken(user);

        // Build and return response.
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
