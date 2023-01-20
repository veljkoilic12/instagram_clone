package com.veljkoilic.instagramclone.post;

import java.util.List;

import com.veljkoilic.instagramclone.comment.Comment;
import com.veljkoilic.instagramclone.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@ManyToOne
	private User user;

	@Column(name = "image_name")
	private String imageName;

	@OneToMany(mappedBy = "post")
	List<Comment> comments;

	public Post(String description) {
		this.description = description;
	}
}
