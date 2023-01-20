package com.veljkoilic.instagramclone.comment;

import org.springframework.stereotype.Service;

import com.veljkoilic.instagramclone.comment.dto.CommentCreationDTO;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.exception.UnauthorizedException;
import com.veljkoilic.instagramclone.post.Post;
import com.veljkoilic.instagramclone.post.PostService;
import com.veljkoilic.instagramclone.user.User;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

	private PostService postService;
	private UserService userService;
	private CommentRepository commentRepository;

	@Override
	public void saveComment(CommentCreationDTO commentCreationDTO, String postName) {

		Post post = postService.findPostByImageName(postName);
		User user = userService.getCurrentUser();

		Comment comment = Comment.builder().commentText(commentCreationDTO.getComment()).creator(user).post(post)
				.build();
		commentRepository.save(comment);
	}

	@Override
	public void deleteComment(int id) {

		User currentUser = userService.getCurrentUser();

		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Comment with id:" + id + " not found"));

		if (currentUser.getId() != comment.getCreator().getId())
			throw new UnauthorizedException("You are not authorized to delete this comment");

		commentRepository.delete(comment);
	}

}
