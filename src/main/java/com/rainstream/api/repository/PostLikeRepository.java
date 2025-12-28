package com.rainstream.api.repository;

import com.rainstream.api.model.Post;
import com.rainstream.api.model.PostLike;
import com.rainstream.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);

    boolean existsByUserAndPost(User user, Post post);
}
