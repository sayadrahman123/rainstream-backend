package com.rainstream.api.service;

import com.rainstream.api.model.Movie;
import com.rainstream.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return repository.save(movie);
    }

    public List<Movie> getFeaturedMovies() {
        return repository.findByIsFeaturedTrue();
    }
}