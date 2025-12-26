package com.rainstream.api.controller;

import com.rainstream.api.model.User;
import com.rainstream.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile() {
        User currentUser = getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(@RequestBody User updatedData) {
        User currentUser = getCurrentUser();

        if (updatedData.getNickname() != null) {
            currentUser.setNickname(updatedData.getNickname());
        }
        if (updatedData.getBio() != null) {
            currentUser.setBio(updatedData.getBio());
        }
        if (updatedData.getLocation() != null) {
            currentUser.setLocation(updatedData.getLocation());
        }
        if (updatedData.getAvatarUrl() != null) {
            currentUser.setAvatarUrl(updatedData.getAvatarUrl());
        }

        return ResponseEntity.ok(repository.save(currentUser));
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
