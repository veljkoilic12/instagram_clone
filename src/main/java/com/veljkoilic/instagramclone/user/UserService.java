package com.veljkoilic.instagramclone.user;

import java.util.List;

import com.veljkoilic.instagramclone.user.dto.UserDTO;

public interface UserService {

	List<UserDTO> returnAllUsers();

	User findUserById(int id);

	User findUserByEmail(String email);

	void deleteUserByUsername(String username);

	void confirmUserEmail(String email);
}
