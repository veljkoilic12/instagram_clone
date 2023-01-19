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
		String path = this.getImageFolderPath() + post.getId();

		return new PostDTO(description, path);
	}

	public Post toPost(PostCreationDTO postDTO) {
		return new Post(postDTO.getDescription());
	}
}
