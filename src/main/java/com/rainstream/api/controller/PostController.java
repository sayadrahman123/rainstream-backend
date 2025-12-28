package com.rainstream.api.controller;

import com.rainstream.api.model.Post;
import com.rainstream.api.repository.PostRepository;
import com.rainstream.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;
    private final PostService postService;

    // 1. Get All Posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // 2. Create New Post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        System.out.println(post.isLiked());
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping("/user/{authorName}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable String authorName) {
        return ResponseEntity.ok(postService.getUserPosts(authorName));
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.likePost(postId));
    }
}