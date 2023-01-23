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
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userMapper.toDto(userService.findUserByUsername(username)));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username,
                                             @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(userService.deleteUserByUsername(username, token));
    }

    @PutMapping
    public ResponseEntity<String> updateUserEmail(@RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(userUpdateDTO));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        return ResponseEntity.ok(userService.updatePassword(passwordDTO));
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<UserDTO>> searchForUsersWithUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.searchUsers(username));
    }
}
