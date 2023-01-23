package com.veljkoilic.instagramclone.user;

import java.util.List;

import com.veljkoilic.instagramclone.password_reset.PasswordDTO;
import com.veljkoilic.instagramclone.user.dto.UserDTO;
import com.veljkoilic.instagramclone.user.dto.UserUpdateDTO;

public interface UserService {

    List<UserDTO> findAllUsers();

    User findUserById(int id);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    String updateUser(UserUpdateDTO userUpdateDTO);

    String updatePassword(PasswordDTO passwordDTO);

    String deleteUserByUsername(String username, String token);

    void confirmUserEmail(String email);

    User getCurrentUser();

    List<UserDTO> searchUsers(String username);
}
