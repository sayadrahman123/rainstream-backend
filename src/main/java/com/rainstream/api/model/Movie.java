package com.rainstream.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000) // Allow longer descriptions
    private String description;

    private String posterUrl;      // Vertical image (for sliders)
    private String backdropUrl;    // Horizontal image (for hero section)
    private String videoUrl;       // Link to the actual video file/stream

    private String genre;          // e.g., "Action, Sci-Fi"
    private String releaseDate;    // e.g., "2023" or "2023-12-25"
    private Double rating;         // e.g., 8.5
    private String duration;       // e.g., "2h 15m"

    private boolean isFeatured;    // To show in the Hero Slider
}