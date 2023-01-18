package com.veljkoilic.instagramclone.post.dto;

import org.springframework.stereotype.Component;

import com.veljkoilic.instagramclone.post.Post;

@Component
public class PostMapper {

	public PostDTO toDto(Post post) {
		String description = post.getDescription();

		return new PostDTO(description);
	}

	public Post toPost(PostCreationDTO postDTO) {
		return new Post(postDTO.getDescription(), postDTO.getUser());
	}
}
