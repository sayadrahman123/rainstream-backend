package com.rainstream.api.service;

import com.rainstream.api.model.Post;
import com.rainstream.api.model.PostComment;
import com.rainstream.api.model.User;
import com.rainstream.api.repository.PostCommentRepository;
import com.rainstream.api.repository.PostRepository;
import com.rainstream.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostCommentService {

    private final PostCommentRepository repository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostCommentService(PostCommentRepository repository, UserRepository userRepository, PostRepository postRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<PostComment> getAllComments(Long postId) {
        return repository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    public PostComment addComment(Long postId, PostComment comment) {
        // 1. Get the current user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get the post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // 3. Set relationships
        comment.setPost(post);
        comment.setUser(user);

        // 4. Save the comment first
        PostComment savedComment = repository.save(comment);

        // 5. UPDATE THE POST COUNT
        // Check for null to avoid NullPointerException if it's the first comment
        int currentCount = post.getCommentsCount() == null ? 0 : post.getCommentsCount();
        post.setCommentsCount(currentCount + 1);

        // 6. Save the updated post
        postRepository.save(post);

        return savedComment;
    }
}
