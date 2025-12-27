package com.rainstream.api.controller;

import com.rainstream.api.model.Movie;
import com.rainstream.api.model.Review;
import com.rainstream.api.model.User;
import com.rainstream.api.repository.MovieRepository;
import com.rainstream.api.repository.ReviewRepository;
import com.rainstream.api.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    // 1. Get Reviews for a Movie
    @GetMapping("/{movieId}")
    public ResponseEntity<List<Review>> getMovieReviews(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId));
    }

    // 2. Add a Review
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody ReviewRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .user(user)
                .movie(movie)
                .build();

        return ResponseEntity.ok(reviewRepository.save(review));
    }

    @Data
    static class ReviewRequest {
        private Long movieId;
        private String content;
        private Integer rating;
    }
}