package com.veljkoilic.instagramclone.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.user.dto.UserDTO;
import com.veljkoilic.instagramclone.user.dto.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;

	public List<UserDTO> returnAllUsers() {
		return userRepository.findAllActiveUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	public User findUserById(int id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User with id: " + id + " not found."));

		return user;
	}

	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with email" + email + "does not exist."));

		return user;
	}

	public void deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("User with username: " + username + " does not exist."));

		user.setUserDeleted(true);

		userRepository.save(user);
	}

	public void confirmUserEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("User with email: " + email + " does not exist."));

		user.setUserEnabled(true);
		userRepository.save(user);
	}
}
