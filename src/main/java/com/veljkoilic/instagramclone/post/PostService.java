package com.veljkoilic.instagramclone.post;

import com.veljkoilic.instagramclone.post.dto.PostDTO;
import java.util.List;

public interface PostService {

	List<PostDTO> returnPostsByUserId(int id);
	
}
