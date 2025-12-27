package com.rainstream.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "cast_members")
public class Cast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // e.g., "Pedro Pascal"
    private String roleName;  // e.g., "Joel Miller"
    private String imageUrl;  // URL to their face

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonBackReference // Prevents infinite recursion loop
    private Movie movie;
}