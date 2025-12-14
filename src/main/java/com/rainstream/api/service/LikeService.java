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
public class LikeService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    // Helper to find user safely
    private User getUser(String identifier) {
        return userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByUsername(identifier))
                .orElseThrow(() -> new RuntimeException("User not found: " + identifier));
    }

    public Set<Movie> getLikedMovies(String identifier) {
        return getUser(identifier).getLikedMovies();
    }

    public void likeMovie(String identifier, Long movieId) {
        User user = getUser(identifier);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        user.getLikedMovies().add(movie);
        userRepository.save(user);
    }

    public void unlikeMovie(String identifier, Long movieId) {
        User user = getUser(identifier);
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        user.getLikedMovies().remove(movie);
        userRepository.save(user);
    }
}