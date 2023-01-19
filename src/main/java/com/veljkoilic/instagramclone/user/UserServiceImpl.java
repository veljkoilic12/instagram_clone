package com.veljkoilic.instagramclone.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.exception.BadRequestException;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.exception.UnauthorizedException;
import com.veljkoilic.instagramclone.password_reset.PasswordDTO;
import com.veljkoilic.instagramclone.user.dto.UserDTO;
import com.veljkoilic.instagramclone.user.dto.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserDTO> findAllUsers() {
		return userRepository.findAllActiveUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public User findUserById(int id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User with id: " + id + " not found."));

		return user;
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with email" + email + "does not exist."));

		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User with username: " + username + " does not exist."));

		return user;
	}

	@Override
	public void updateUser(String email, String username) {

		if (email != null) {
			User currentUser = getCurrentUser();

			if (userRepository.findByEmail(email) == null)
				throw new BadRequestException("Username with " + email + " email already exists");

			currentUser.setEmail(email);
			userRepository.save(currentUser);
		}

		if (username != null) {
			if (userRepository.findByUsername(username) == null)
				throw new BadRequestException("Username with " + username + " username already exists");

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User userDetails = (User) authentication.getPrincipal();
			userDetails.setUsername(username);
			userRepository.save(userDetails);
		}

		if (username == null && email == null) {
			throw new BadRequestException("Provide email or username to change");
		}
	}

	@Override
	public void updatePassword(PasswordDTO passwordDTO) {

		String password = passwordDTO.getPasswordOne();
		String repeatedPassword = passwordDTO.getPasswordTwo();

		if (!password.equals(repeatedPassword))
			throw new BadRequestException("Passwords do not match");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userDetails = (User) authentication.getPrincipal();
		userDetails.setPassword(passwordEncoder.encode(password));

		userRepository.save(userDetails);
	}

	@Override
	public void deleteUserByUsername(String username, String token) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User with username: " + username + " does not exist."));

		User currentUser = getCurrentUser();

		if (!currentUser.equals(user)) {
			throw new UnauthorizedException("You are not authorized to delete " + username);
		}

		user.setUserDeleted(true);

		userRepository.save(user);
	}

	@Override
	public void confirmUserEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with email: " + email + " does not exist."));

		user.setUserEnabled(true);
		userRepository.save(user);
	}

	@Override
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserUsername = authentication.getName();
		return this.findUserByUsername(currentUserUsername);
	}

	@Override
	public List<UserDTO> searchUsers(String username) {
		username = username.toLowerCase();
		return userRepository.search(username).stream().map(userMapper::toDto).collect(Collectors.toList());
	}
}
