package com.veljkoilic.instagramclone.user;

import com.veljkoilic.instagramclone.user.dto.UserDTO;
import com.veljkoilic.instagramclone.user.dto.UserMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.util.List;

@RequestMapping("/user")
@RestController
@AllArgsConstructor
public class UserController {

	private UserService userService;
	private UserMapper userMapper;

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userList = userService.returnAllUsers();
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
		UserDTO user = userMapper.toDto(userService.findUserById(id));
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable String username) {
		userService.deleteUserByUsername(username);
		return ResponseEntity.ok("User successfully deleted");
	}
}
