package com.veljkoilic.instagramclone.follow;

import com.veljkoilic.instagramclone.follow.FollowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class FollowerController {

    private FollowerService followerService;

    @PostMapping("/follow/{username}")
    public ResponseEntity<String> followUser(@PathVariable String username) {
        return ResponseEntity.ok(followerService.followUser(username));
    }

    @PostMapping("/unfollow/{username}")
    public ResponseEntity<String> unfollowUser(@PathVariable String username) {
        return ResponseEntity.ok(followerService.unfollowUser(username));
    }
}
