package com.rainstream.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails { // <--- IMPORTS UserDetails interface

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profileImage;

    // Use an Enum for roles (Optional: String is fine for now, let's stick to String)
    private String role;

    private String nickname;
    private String bio;
    private String location;
    private String avatarUrl;
    private LocalDate joinDate;

    @ManyToMany(fetch = FetchType.EAGER) // Load watchlist immediately when user logs in
    @JoinTable(
            name = "user_watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> watchlist = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> likedMovies = new HashSet<>();

    // --- UserDetails Methods ---

    @PrePersist
    protected void onCreate() {
        joinDate = LocalDate.now();
        if (avatarUrl == null) {
            // Default random avatar based on email/randomness
            avatarUrl = "https://api.dicebear.com/7.x/avataaars/svg?seed=" + (Math.random() * 1000);
        }
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Returns the user's role/permissions
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email; // We will use EMAIL as the username for login
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}