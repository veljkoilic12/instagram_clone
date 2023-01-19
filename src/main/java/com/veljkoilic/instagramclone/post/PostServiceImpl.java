package com.veljkoilic.instagramclone.post;

import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.exception.UnauthorizedException;
import com.veljkoilic.instagramclone.post.dto.PostCreationDTO;
import com.veljkoilic.instagramclone.post.dto.PostMapper;
import com.veljkoilic.instagramclone.user.User;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private PostMapper postMapper;
	private UserService userService;

	@Override
	public Post findPostById(int id) {

		Post post = postRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Post with id:" + id + " not found."));

		return post;
	}

	// Converts DTO to post, extracts User from the token and saves the Post with
	// user_id set as extracted User
	@Override
	public String savePost(PostCreationDTO postCreationDTO) {
		Post post = postMapper.toPost(postCreationDTO);
		User currentUser = userService.getCurrentUser();

		post.setUser(currentUser);

		Post savedPost = postRepository.save(post);

		return postMapper.toDto(savedPost).getPostPath();
	}

	@Override
	public String deletePost(Integer id) {

		Post post = this.findPostById(id);
		User currentUser = userService.getCurrentUser();

		int postCreatorId = post.getUser().getId();
		int currentUserId = currentUser.getId();

		if (postCreatorId != currentUserId) {
			throw new UnauthorizedException("You are not authorized to delete this post!");
		}

		postRepository.deleteById(id);

		return "Post successfully deleted";
	}

}
