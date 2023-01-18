package com.veljkoilic.instagramclone.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.veljkoilic.instagramclone.post.Post;
import com.veljkoilic.instagramclone.user.User;

@Component
public class UserMapper {

	public UserDTO toDto(User user) {
		String username = user.getUsername();
		String email = user.getEmail();
		List<String> posts = user.getPosts().stream().map(Post::getDescription).collect(Collectors.toList());
		return new UserDTO(username, email, posts);
	}
}
