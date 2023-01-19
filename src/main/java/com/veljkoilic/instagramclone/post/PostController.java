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

	@GetMapping("/{imageName}")
	public ResponseEntity<PostDTO> get(@PathVariable String imageName) {
		PostDTO postDto = postMapper.toDto(postService.findPostByImageName(imageName));
		return ResponseEntity.ok(postDto);
	}

	@PostMapping
	public ResponseEntity<String> savePost(@RequestBody PostCreationDTO postCreationDTO) {
		postService.savePost(postCreationDTO);
		return ResponseEntity.ok("Post successfully saved");
	}

	@DeleteMapping("/{imageName}")
	public ResponseEntity<String> savePost(@PathVariable String imageName) {
		postService.deletePost(imageName);
		return ResponseEntity.ok("Post successfully deleted");
	}
}
