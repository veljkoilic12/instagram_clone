package com.veljkoilic.instagramclone.post;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.veljkoilic.instagramclone.config.JwtService;
import com.veljkoilic.instagramclone.exception.NotFoundException;
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
	private JwtService jwtService;

	@Override
	public Post findPostById(int id) {

		Post post = postRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Post with id:" + id + " not found."));

		return post;
	}

	// Converts DTO to post, extracts User from the token and saves the Post with
	// user_id set as extracted User
	@Override
	public String savePost(PostCreationDTO postCreationDTO, String token) {
		Post post = postMapper.toPost(postCreationDTO);

		String jwt = token.substring(7);
		System.out.println(jwt);

		String currentUserUsername = jwtService.extractUsername(jwt);
		User currentUser = userService.findUserByUsername(currentUserUsername);

		post.setUser(currentUser);

		Post savedPost = postRepository.save(post);

		return postMapper.toDto(savedPost).getPostPath();
	}
}
