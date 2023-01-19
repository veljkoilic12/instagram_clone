package com.veljkoilic.instagramclone.post;

import java.util.Optional;
import java.util.UUID;

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
	public Post findPostByImageName(String imageName) {

		Post post = postRepository.findByImageName(imageName)
				.orElseThrow(() -> new NotFoundException("Image with name: " + imageName + " not found."));

		return post;
	}

	// Converts DTO to post, extracts User from the token and saves the Post with
	// user_id set as extracted User
	@Override
	public void savePost(PostCreationDTO postCreationDTO) {
		Post post = postMapper.toPost(postCreationDTO);
		User currentUser = userService.getCurrentUser();

		String imageName = UUID.randomUUID().toString();

		Optional<Post> checkForImageNamePost = postRepository.findByImageName(imageName);

		while (!checkForImageNamePost.isEmpty()) {
			imageName = UUID.randomUUID().toString();
			checkForImageNamePost = postRepository.findByImageName(imageName);
		}

		post.setImageName(imageName);
		post.setUser(currentUser);

		postRepository.save(post);
	}

	@Override
	public void deletePost(String imageName) {

		Post post = this.findPostByImageName(imageName);
		User currentUser = userService.getCurrentUser();

		int postCreatorId = post.getUser().getId();
		int currentUserId = currentUser.getId();

		if (postCreatorId != currentUserId) {
			throw new UnauthorizedException("You are not authorized to delete this post!");
		}

		postRepository.deleteByImageName(imageName);
	}

}
