package com.veljkoilic.instagramclone.user;

import java.util.List;

import com.veljkoilic.instagramclone.password_reset.PasswordDTO;
import com.veljkoilic.instagramclone.user.dto.UserDTO;

public interface UserService {

	List<UserDTO> findAllUsers();

	User findUserById(int id);

	User findUserByEmail(String email);

	User findUserByUsername(String username);

	void updateUser(String email, String username);

	void updatePassword(PasswordDTO passwordDTO);

	void deleteUserByUsername(String username, String token);

	void confirmUserEmail(String email);

	User getCurrentUser();
}
