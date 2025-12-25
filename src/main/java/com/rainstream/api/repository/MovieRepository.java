package com.rainstream.api.repository;

import com.rainstream.api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Custom query to find featured movies for the Hero Slider
    List<Movie> findByIsFeaturedTrue();


    List<Movie> findByTitleContainingIgnoreCase(String title);


    List<Movie> findByGenreContainingIgnoreCase(String genre);

    @Query("SELECT m FROM Movie m WHERE " +
            "LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(m.genre) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Movie> searchMovies(@Param("query") String query);

    List<Movie> findByReleaseDateAfterOrderByReleaseDateAsc(LocalDate date);
}