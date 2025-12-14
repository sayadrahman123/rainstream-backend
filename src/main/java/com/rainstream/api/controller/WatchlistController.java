package com.rainstream.api.controller;

import com.rainstream.api.model.Movie;
import com.rainstream.api.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService service;

    @GetMapping
    public ResponseEntity<Set<Movie>> getWatchlist(Authentication authentication) {
        // 'authentication.getName()' returns the email because of how we set up UserDetails
        return ResponseEntity.ok(service.getWatchlist(authentication.getName()));
    }

    @PostMapping("/add/{movieId}")
    public ResponseEntity<String> addToWatchlist(
            @PathVariable Long movieId,
            Authentication authentication
    ) {
        service.addMovieToWatchlist(authentication.getName(), movieId);
        return ResponseEntity.ok("Movie added to watchlist");
    }

    @DeleteMapping("/remove/{movieId}")
    public ResponseEntity<String> removeFromWatchlist(
            @PathVariable Long movieId,
            Authentication authentication
    ) {
        service.removeMovieFromWatchlist(authentication.getName(), movieId);
        return ResponseEntity.ok("Movie removed from watchlist");
    }
}