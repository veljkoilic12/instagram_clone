package com.veljkoilic.instagramclone.post.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.veljkoilic.instagramclone.post.Post;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PostMapper {

	@Value("${image.folder.path}")
	private String imageFolderPath;

	public PostDTO toDto(Post post) {
		String description = post.getDescription();
		String imageName = post.getImageName();

		return new PostDTO(description, imageName);
	}

	public Post toPost(PostCreationDTO postDTO) {
		return new Post(postDTO.getDescription());
	}
}
