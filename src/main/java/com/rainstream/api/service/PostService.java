package com.rainstream.api.service;

import com.rainstream.api.model.Post;
import com.rainstream.api.model.PostLike;
import com.rainstream.api.model.User;
import com.rainstream.api.repository.PostLikeRepository;
import com.rainstream.api.repository.PostRepository;
import com.rainstream.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public PostService(PostRepository repository, UserRepository userRepository, PostLikeRepository postLikeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public List<Post> getAllPosts() {
        List<Post> posts = repository.findAllByOrderByCreatedAtDesc();

        // Get current logged-in user email/username
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByEmail(email);

        // If user is logged in, check which posts they liked
        if (currentUser.isPresent()) {
            for (Post post : posts) {
                boolean liked = postLikeRepository.existsByUserAndPost(currentUser.get(), post);
                post.setLiked(liked); // Set the Transient field
            }
        }

        return posts;
    }

    public Post createPost(Post post) {

        return repository.save(post);
    }

    public List<Post> getUserPosts(String authorName) {
        return repository.findAllByAuthorNameOrderByCreatedAtDesc(authorName);
    }

    public Post likePost(Long postId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = repository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Check if like exists
        Optional<PostLike> existingLike = postLikeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            // UNLIKE: Remove the record and decrease count
            postLikeRepository.delete(existingLike.get());
            post.setLikes(Math.max(0, post.getLikes() - 1)); // Prevent negative
            post.setLiked(false);
        } else {
            // LIKE: Create record and increase count
            PostLike newLike = new PostLike(user, post);
            postLikeRepository.save(newLike);
            post.setLikes(post.getLikes() + 1);
            post.setLiked(true);
        }

        return repository.save(post);
    }
}
