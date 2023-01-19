package com.veljkoilic.instagramclone.post;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

	Optional<Post> findByImageName(String imageName);

	void deleteByImageName(String imageName);
}
