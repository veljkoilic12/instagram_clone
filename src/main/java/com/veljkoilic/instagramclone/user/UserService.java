package com.veljkoilic.instagramclone.user;

import java.util.List;

import com.veljkoilic.instagramclone.post.dto.PostDTO;
import com.veljkoilic.instagramclone.user.dto.UserDTO;

public interface UserService {

	List<UserDTO> findAllUsers();

	User findUserById(int id);

	User findUserByEmail(String email);

	User findUserByUsername(String username);

	void deleteUserByUsername(String username);

	void confirmUserEmail(String email);
}
