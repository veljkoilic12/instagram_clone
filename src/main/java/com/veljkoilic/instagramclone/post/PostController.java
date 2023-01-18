package com.veljkoilic.instagramclone.post;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veljkoilic.instagramclone.post.dto.PostDTO;

import lombok.AllArgsConstructor;

@RequestMapping("/posts")
@RestController
@AllArgsConstructor
public class PostController {

	@GetMapping("/{id}")
	public List<PostDTO> getAllUserPosts(@PathVariable Integer id) {
		return null;
	}

}
