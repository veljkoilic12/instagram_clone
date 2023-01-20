package com.veljkoilic.instagramclone.post.dto;

import org.springframework.stereotype.Component;

import com.veljkoilic.instagramclone.post.Post;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PostMapper {

	public PostDTO toDto(Post post) {
		String description = post.getDescription();
		String imageName = post.getImageName();

		return new PostDTO(description, imageName);
	}

	public Post toPost(String description) {
		return new Post(description);
	}
}
