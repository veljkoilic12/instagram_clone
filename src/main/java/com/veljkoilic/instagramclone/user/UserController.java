package com.veljkoilic.instagramclone.user;

import com.veljkoilic.instagramclone.password_reset.PasswordDTO;
import com.veljkoilic.instagramclone.user.dto.UserDTO;
import com.veljkoilic.instagramclone.user.dto.UserMapper;
import com.veljkoilic.instagramclone.user.dto.UserUpdateDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        List<UserDTO> userList = userService.findAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userMapper.toDto(userService.findUserByUsername(username));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username,
                                             @RequestHeader(name = "Authorization") String token) {
        userService.deleteUserByUsername(username, token);
        return ResponseEntity.ok("User successfully deleted");
    }

    @PutMapping
    public ResponseEntity<String> updateUserEmail(@RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(userUpdateDTO);
        return ResponseEntity.ok("User successfully updated");
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        userService.updatePassword(passwordDTO);
        return ResponseEntity.ok("Password successfully updated");
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserDTO>> searchForUsersWithUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }
}
