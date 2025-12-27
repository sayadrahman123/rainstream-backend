package com.rainstream.api.repository;

import com.rainstream.api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Get all reviews for a specific movie, newest first
    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);
}