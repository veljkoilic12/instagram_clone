package com.veljkoilic.instagramclone.post;

import com.veljkoilic.instagramclone.post.dto.PostCreationDTO;

public interface PostService {

	Post findPostByImageName(String id);

	void savePost(PostCreationDTO postCreationDTO);

	void deletePost(String imageName);
}
