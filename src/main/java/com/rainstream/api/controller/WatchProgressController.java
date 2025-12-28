package com.rainstream.api.controller;


import com.rainstream.api.model.Movie;
import com.rainstream.api.model.User;
import com.rainstream.api.model.WatchProgress;
import com.rainstream.api.repository.MovieRepository;
import com.rainstream.api.repository.UserRepository;
import com.rainstream.api.repository.WatchProgressRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class WatchProgressController {

    private final WatchProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    @GetMapping
    public ResponseEntity<List<WatchProgress>> getMyProgress() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(progressRepository.findByUserEmailOrderByLastWatchedDesc(email));
    }

    @GetMapping("/{movieId}") // <--- Changed to PathVariable for cleaner URL: /api/progress/55
    public ResponseEntity<?> getProgressByMovieId(@PathVariable Long movieId) {

        // 1. Get current user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Query DB
        Optional<WatchProgress> progress = progressRepository.findByUserEmailAndMovieId(email, movieId);

        // 3. Handle result logic
        if (progress.isPresent()) {
            return ResponseEntity.ok(progress.get());
        } else {
            // CRITICAL FIX: Don't throw error!
            // Return an empty object or a default "0 progress" object.
            // This lets the frontend know it's safe to start at 0:00.
            WatchProgress newProgress = new WatchProgress();
            newProgress.setMovie(movieRepository.findById(movieId).orElseThrow());
            newProgress.setProgressSeconds(0D); // Start at 0
            newProgress.setTotalDurationSeconds(0D);
            return ResponseEntity.ok(newProgress);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveProgress(@RequestBody ProgressRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow();


        WatchProgress progress = progressRepository.findByUserEmailAndMovieId(email, request.getMovieId())
                .orElse(WatchProgress.builder()
                        .user(user)
                        .movie(movie)
                        .build());

        progress.setProgressSeconds(request.getProgress());
        progress.setTotalDurationSeconds(request.getTotalDuration());

        progressRepository.save(progress);
        return ResponseEntity.ok().build();
    }



    @Data
    static class ProgressRequest {
        private Long movieId;
        private Double progress;
        private Double totalDuration;
    }
}
