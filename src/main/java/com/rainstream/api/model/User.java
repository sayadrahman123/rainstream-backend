package com.rainstream.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates Getters, Setters, toString, etc.
@Builder // Helps in creating objects easily
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all args
@Entity // Tells Hibernate: "Make a table out of this class"
@Table(name = "users") // Name the table 'users' in MySQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImage; // URL to the avatar image

    // We will start with a simple role. Later we can make this more complex.
    // e.g., "ROLE_USER", "ROLE_ADMIN"
    private String role;
}