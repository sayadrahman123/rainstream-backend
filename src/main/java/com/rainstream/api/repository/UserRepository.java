package com.rainstream.api.repository;

import com.rainstream.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically generates the SQL for this!
    Optional<User> findByEmail(String email);

    // We might need this for the "Create Profile" check later
    Optional<User> findByUsername(String username);
}