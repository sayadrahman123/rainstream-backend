package com.rainstream.api.controller;

import com.rainstream.api.model.Movie;
import com.rainstream.api.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService service;

    @GetMapping
    public ResponseEntity<Set<Movie>> getLikedMovies(Authentication authentication) {
        return ResponseEntity.ok(service.getLikedMovies(authentication.getName()));
    }

    @PostMapping("/add/{movieId}")
    public ResponseEntity<String> likeMovie(
            @PathVariable Long movieId,
            Authentication authentication
    ) {
        service.likeMovie(authentication.getName(), movieId);
        return ResponseEntity.ok("Movie liked");
    }

    @DeleteMapping("/remove/{movieId}")
    public ResponseEntity<String> unlikeMovie(
            @PathVariable Long movieId,
            Authentication authentication
    ) {
        service.unlikeMovie(authentication.getName(), movieId);
        return ResponseEntity.ok("Like removed");
    }
}