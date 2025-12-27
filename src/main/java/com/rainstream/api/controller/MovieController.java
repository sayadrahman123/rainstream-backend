package com.rainstream.api.controller;

import com.rainstream.api.model.Movie;
import com.rainstream.api.repository.MovieRepository;
import com.rainstream.api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;
    private final MovieRepository repository;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(service.getAllMovies());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Movie>> getFeaturedMovies() {
        return ResponseEntity.ok(service.getFeaturedMovies());
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(service.addMovie(movie));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(service.searchMovies(query));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Movie>> getUpcomingMovies() {
        return ResponseEntity.ok(service.getUpcomingMovies());
    }

    @GetMapping("/similar/{id}")
    public ResponseEntity<List<Movie>> getSimilarMovies(@PathVariable Long id) {
        return service.getSimilarMovies(id);
    }


    @GetMapping("/featured/random")
    public ResponseEntity<Movie> getRandomFeaturedMovie() {
        List<Movie> featuredMovies = repository.findByIsFeaturedTrue();

        if (featuredMovies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Shuffle and pick the first one
        Collections.shuffle(featuredMovies);
        return ResponseEntity.ok(featuredMovies.get(0));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElseThrow());
    }
}