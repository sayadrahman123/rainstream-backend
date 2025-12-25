package com.rainstream.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // <--- Import this

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

    @Column(length = 1000)
    private String description;

    private String posterUrl;
    private String backdropUrl;
    private String videoUrl;

    private String genre;

    // CHANGED FROM String TO LocalDate
    private LocalDate releaseDate;

    private Double rating;
    private String duration;

    private boolean isFeatured;
}