package com.veljkoilic.instagramclone.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veljkoilic.instagramclone.post.dto.PostCreationDTO;
import com.veljkoilic.instagramclone.post.dto.PostDTO;
import com.veljkoilic.instagramclone.post.dto.PostMapper;

import lombok.AllArgsConstructor;

@RequestMapping("/posts")
@RestController
@AllArgsConstructor
public class PostController {

	private PostMapper postMapper;
	private PostService postService;

	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> get(@PathVariable Integer id) {
		PostDTO postDto = postMapper.toDto(postService.findPostById(id));
		return ResponseEntity.ok(postDto);
	}

	@PostMapping
	public ResponseEntity<String> savePost(@RequestBody PostCreationDTO postCreationDTO) {
		return ResponseEntity.ok(postService.savePost(postCreationDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> savePost(@PathVariable Integer id) {
		return ResponseEntity.ok(postService.deletePost(id));
	}
}
