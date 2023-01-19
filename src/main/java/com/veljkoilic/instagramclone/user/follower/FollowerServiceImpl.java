package com.veljkoilic.instagramclone.user.follower;

import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.user.User;
import com.veljkoilic.instagramclone.user.UserRepository;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {

	private UserService userService;
	private UserRepository userRepository;

	public void followUser(String username) {
		User currentUser = userService.getCurrentUser();
		User userToFollow = userService.findUserByUsername(username);

		userToFollow.getFollowers().add(currentUser);
		userRepository.save(userToFollow);
	}
}
