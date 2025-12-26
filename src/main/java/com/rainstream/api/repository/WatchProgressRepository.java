package com.rainstream.api.repository;

import com.rainstream.api.model.WatchProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchProgressRepository extends JpaRepository<WatchProgress, Long> {

    Optional<WatchProgress> findByUserEmailAndMovieId(String email, Long movieId);

    List<WatchProgress> findByUserEmailOrderByLastWatchedDesc(String email);
}
