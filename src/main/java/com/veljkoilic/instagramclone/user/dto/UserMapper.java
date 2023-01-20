package com.veljkoilic.instagramclone.user.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.veljkoilic.instagramclone.post.dto.PostDTO;
import com.veljkoilic.instagramclone.post.dto.PostMapper;
import com.veljkoilic.instagramclone.user.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserMapper {

	private PostMapper postMapper;

	public UserDTO toDto(User user) {
		String username = user.getUsername();
		String email = user.getEmail();
		List<PostDTO> posts = user.getPosts().stream().map(postMapper::toDto).collect(Collectors.toList());
		return new UserDTO(username, email, posts);
	}
}
