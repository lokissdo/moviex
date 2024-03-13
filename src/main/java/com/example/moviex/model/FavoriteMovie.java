package com.example.moviex.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(FavoriteMovieId.class)
@Getter
@Setter
@NoArgsConstructor
public class FavoriteMovie {
    @Id
    private Long userId;
    @Id
    private Long movieId;
    private int ratingIndex;

    public FavoriteMovie(Long userId, Long movieId, int ratingIndex) {
        this.userId = userId;
        this.movieId = movieId;
        this.ratingIndex = ratingIndex;
    }
}


