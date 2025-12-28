package com.rainstream.api.controller;

import com.rainstream.api.model.PostComment;
import com.rainstream.api.service.PostCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostCommentController {

    private final PostCommentService service;

    public PostCommentController(PostCommentService service) {
        this.service = service;
    }

    // FIX: Added /v1 to the path
    @GetMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<List<PostComment>> getAllComments(@PathVariable Long postId){
        return ResponseEntity.ok(service.getAllComments(postId));
    }

    // FIX: Added /v1 to the path
    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity<PostComment> addComment(@PathVariable Long postId, @RequestBody PostComment comment){
        return ResponseEntity.ok(service.addComment(postId, comment));
    }
}