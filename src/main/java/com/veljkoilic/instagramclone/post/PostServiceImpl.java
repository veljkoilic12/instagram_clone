package com.veljkoilic.instagramclone.post;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.veljkoilic.instagramclone.InstagramcloneApplication;
import com.veljkoilic.instagramclone.exception.NotFoundException;
import com.veljkoilic.instagramclone.exception.UnauthorizedException;
import com.veljkoilic.instagramclone.user.User;
import com.veljkoilic.instagramclone.user.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PostServiceImpl implements PostService {

	@Value("${image.folder.path}")
	private String imageFolderPath;

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserService userService;

	@Override
	public Post findPostByImageName(String imageName) {

		Post post = postRepository.findByImageName(imageName)
				.orElseThrow(() -> new NotFoundException("Image with name: " + imageName + " not found."));

		return post;
	}

	// Converts DTO to post, extracts User from the token and saves the Post with
	// user_id set as extracted User
	@Override
	public void savePost(String description, MultipartFile image) {
		Post post = new Post(description);
		User currentUser = userService.getCurrentUser();

		String imageName = UUID.randomUUID().toString();

		Optional<Post> checkForImageNamePost = postRepository.findByImageName(imageName);

		while (!checkForImageNamePost.isEmpty()) {
			imageName = UUID.randomUUID().toString();
			checkForImageNamePost = postRepository.findByImageName(imageName);
		}

		this.saveImage(image, imageName);

		post.setImageName(imageName);
		post.setUser(currentUser);

		postRepository.save(post);
	}

	@Override
	public void deletePost(String imageName) {

		Post post = this.findPostByImageName(imageName);
		User currentUser = userService.getCurrentUser();

		int postCreatorId = post.getUser().getId();
		int currentUserId = currentUser.getId();

		if (postCreatorId != currentUserId) {
			throw new UnauthorizedException("You are not authorized to delete this post!");
		}

		postRepository.deleteByImageName(imageName);
	}

	@Override
	public void saveImage(MultipartFile file, String imageName) {

		String path = imageFolderPath + imageName + ".jpg";

		try {
			File newFile = new File(path);
			newFile.createNewFile();
			FileOutputStream fileStream = new FileOutputStream(newFile);

			fileStream.write(file.getBytes());
			fileStream.close();
			System.out.println("FILE SAVED");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
