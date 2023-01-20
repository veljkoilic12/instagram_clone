package com.veljkoilic.instagramclone.user.follower;

public interface FollowerService {
	void followUser(String username);

	void unfollowUser(String username);
}
