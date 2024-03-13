package com.example.moviex.model;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteMovieId implements Serializable {
    private Long userId;
    private Long movieId;

    public FavoriteMovieId() {
        // Default constructor
    }

    public FavoriteMovieId(Long userId, Long movieId){
        this.movieId = movieId;
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteMovieId)) return false;
        FavoriteMovieId that = (FavoriteMovieId) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getMovieId(), that.getMovieId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getMovieId());
    }

    // Getters and setters
}
