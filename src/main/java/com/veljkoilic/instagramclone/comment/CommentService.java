package com.veljkoilic.instagramclone.comment;

import com.veljkoilic.instagramclone.comment.dto.CommentCreationDTO;

public interface CommentService {

	void saveComment(CommentCreationDTO commentCreationDTO, String name);

	void deleteComment(int id);
}
