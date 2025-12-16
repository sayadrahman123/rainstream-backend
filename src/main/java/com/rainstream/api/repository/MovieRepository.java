package com.rainstream.api.repository;

import com.rainstream.api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Custom query to find featured movies for the Hero Slider
    List<Movie> findByIsFeaturedTrue();


    List<Movie> findByTitleContainingIgnoreCase(String title);


    List<Movie> findByGenreContainingIgnoreCase(String genre);
}