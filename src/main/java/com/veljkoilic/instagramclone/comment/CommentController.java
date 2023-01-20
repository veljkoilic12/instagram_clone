package com.veljkoilic.instagramclone.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.veljkoilic.instagramclone.comment.dto.CommentCreationDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CommentController {

	private CommentService commentService;

	@PostMapping("/posts/{name}/comments")
	public ResponseEntity<String> postComment(@RequestBody CommentCreationDTO commentCreationDTO, @PathVariable String name) {
		commentService.saveComment(commentCreationDTO, name);
		return ResponseEntity.ok("Comment successfully posted");
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
		commentService.deleteComment(id);
		return ResponseEntity.ok("Comment successfully deleted");
	}

}
