package Mn.learning.SpringStarter.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Mn.learning.SpringStarter.models.Post;
import Mn.learning.SpringStarter.repositories.PostRepository;

@Service

public class PostService {
	private PostRepository postRepository;

	public PostService(PostRepository postRepository) {
			this.postRepository = postRepository;
	}
	public Optional<Post> getById(Long id) {
		return postRepository.findById(id);
	}

	public List<Post> getAll() {
		return postRepository.findAll();
	}

	public void delete(Post post) {
		postRepository.delete(post);
	}

	public Post save(Post post) {
		if (post.getId() == null) {
			post.setCreatedAt(LocalDateTime.now());
		}
		post.setUpdatedAt(LocalDateTime.now());
		return postRepository.save(post);
	}



}
