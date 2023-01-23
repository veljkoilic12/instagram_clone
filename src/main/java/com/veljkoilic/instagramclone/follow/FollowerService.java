package com.veljkoilic.instagramclone.follow;

public interface FollowerService {
	String followUser(String username);

	String unfollowUser(String username);
}
