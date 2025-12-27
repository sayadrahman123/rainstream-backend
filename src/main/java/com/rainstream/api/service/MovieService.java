package com.rainstream.api.service;

import com.rainstream.api.model.Movie;
import com.rainstream.api.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

//    public List<Movie> searchMovies(String query) {
//        return repository.findByTitleContainingIgnoreCase(query);
//    }

    public List<Movie> searchMovies(String query) {
        // Use the new custom query method
        return repository.searchMovies(query);
    }

    public List<Movie> getUpcomingMovies() {
        return repository.findByReleaseDateAfterOrderByReleaseDateAsc(LocalDate.now());
    }


    public ResponseEntity<List<Movie>> getSimilarMovies(Long id) {
        Movie currentMovie = repository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));

        String primaryGenre = currentMovie.getGenre().split(",")[0].trim();

        return ResponseEntity.ok(
                repository.findTop10ByGenreContainingAndIdNot(primaryGenre, id)
        );
    }
}