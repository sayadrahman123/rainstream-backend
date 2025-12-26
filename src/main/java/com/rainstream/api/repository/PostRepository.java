package com.rainstream.api.repository;

import com.rainstream.api.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Get newest posts first
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findAllByAuthorNameOrderByCreatedAtDesc(String authorName);
}