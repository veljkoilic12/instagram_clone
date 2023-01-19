package com.veljkoilic.instagramclone.post;

import com.veljkoilic.instagramclone.post.dto.PostCreationDTO;

public interface PostService {

	Post findPostById(int id);

	String savePost(PostCreationDTO postCreationDTO, String token);
}
