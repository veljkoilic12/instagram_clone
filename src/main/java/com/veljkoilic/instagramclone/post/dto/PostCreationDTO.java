package com.veljkoilic.instagramclone.post.dto;

import com.veljkoilic.instagramclone.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreationDTO {

	private String description;
	private User user;
}
