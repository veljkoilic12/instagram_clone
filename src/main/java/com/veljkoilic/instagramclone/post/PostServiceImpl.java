package com.veljkoilic.instagramclone.post;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.post.dto.PostDTO;
import com.veljkoilic.instagramclone.post.dto.PostMapper;
import com.veljkoilic.instagramclone.user.User;
import com.veljkoilic.instagramclone.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

	private PostMapper postMapper;
	private UserRepository userRepository;

	@Value("${image.folder.path}")
	private String imageFolderPath;

	@Override
	public List<PostDTO> returnPostsByUserId(int id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with such id"));

		return user.getPosts().stream().map(postMapper::toDto).collect(Collectors.toList());
	}

}
