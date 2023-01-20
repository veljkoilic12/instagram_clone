package com.veljkoilic.instagramclone.user.follower;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class FollowerController {

	private FollowerService followerService;

	@PostMapping("/follow/{username}")
	public ResponseEntity<String> followUser(@PathVariable String username) {
		followerService.followUser(username);
		return ResponseEntity.ok("Successfully followed " + username);
	}

	@PostMapping("/unfollow/{username}")
	public ResponseEntity<String> unfollowUser(@PathVariable String username) {
		return ResponseEntity.ok("Successfully unfollowed " + username);
	}
}
