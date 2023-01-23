package com.veljkoilic.instagramclone.follow;

import com.veljkoilic.instagramclone.exception.BadRequestException;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.user.User;
import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.user.UserRepository;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private UserService userService;

    private UserRepository userRepository;
    private FollowerRepository followerRepository;

    public String followUser(String username) {

        User currentUser = userService.getCurrentUser();
        User userToFollow = userService.findUserByUsername(username);

        if (userToFollow == null)
            throw new NotFoundException("User with username " + username + "not found");

        if (userToFollow.getId() == currentUser.getId())
            throw new BadRequestException("You can't follow yourself");

        Optional<Follow> followExistsCheck = followerRepository.findFollowByFollowerIdAndFollowingId(currentUser.getId(), userToFollow.getId());

        if (followExistsCheck.isPresent())
            throw new BadRequestException("You are already following this user");

        Follow follow = new Follow(currentUser, userToFollow);
        followerRepository.save(follow);

        return "Successfully followed user: " + username;
    }

    @Override
    public String unfollowUser(String username) {
        return "Successfully unfollowed user: " + username;
    }
}
