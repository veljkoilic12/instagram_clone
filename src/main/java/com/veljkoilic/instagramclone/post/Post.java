package com.veljkoilic.instagramclone.post;

import com.veljkoilic.instagramclone.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "posts")
public class Post {

	@Id
	@GeneratedValue
	private Integer id;

	private String description;

	private String imagePath;

	@ManyToOne
	private User user;

	public Post(String description, User user) {
		this.description = description;
		this.user = user;
	}
}
