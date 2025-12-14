package com.rainstream.api.service;

import com.rainstream.api.model.Movie;
import com.rainstream.api.model.User;
import com.rainstream.api.repository.MovieRepository;
import com.rainstream.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public Set<Movie> getWatchlist(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return user.getWatchlist();
    }

    public void addMovieToWatchlist(String email, Long movieId) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        user.getWatchlist().add(movie);
        userRepository.save(user);
    }

    public void removeMovieFromWatchlist(String email, Long movieId) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        user.getWatchlist().remove(movie);
        userRepository.save(user);
    }
}