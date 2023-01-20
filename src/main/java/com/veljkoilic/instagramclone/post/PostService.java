package com.veljkoilic.instagramclone.post;

import org.springframework.web.multipart.MultipartFile;

import com.veljkoilic.instagramclone.post.dto.PostCreationDTO;

public interface PostService {

	Post findPostByImageName(String id);

	void savePost(String description, MultipartFile image);

	void deletePost(String imageName);

	void saveImage(MultipartFile file, String imageName);
}
