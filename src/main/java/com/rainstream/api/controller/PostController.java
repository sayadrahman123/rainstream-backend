package com.rainstream.api.controller;

import com.rainstream.api.model.Post;
import com.rainstream.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;

    // 1. Get All Posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(repository.findAllByOrderByCreatedAtDesc());
    }

    // 2. Create New Post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        // Simple initialization
        post.setLikes(0);
        post.setCommentsCount(0);
        return ResponseEntity.ok(repository.save(post));
    }
}