package com.rainstream.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String content;

    private String authorName;
    private String movieTitle;

    // Initialize to 0 to prevent NullPointerExceptions
    private Integer likes = 0;
    private Integer commentsCount = 0;

    @Transient // Correct! This field is NOT stored in the DB
    private boolean isLiked;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (authorName == null) authorName = "Anonymous";

        // Safety check: ensure these are never null
        if (likes == null) likes = 0;
        if (commentsCount == null) commentsCount = 0;

    }


}