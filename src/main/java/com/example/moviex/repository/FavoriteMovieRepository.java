package com.example.moviex.repository;

import com.example.moviex.model.FavoriteMovie;
import com.example.moviex.model.FavoriteMovieId;
import com.example.moviex.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, FavoriteMovieId> {
    List<FavoriteMovie> findByUserId(Long userId);
    FavoriteMovie findByUserIdAndMovieId(Long userId, Long movieId);

  //  Optional<FavoriteMovie> findById(FavoriteMovieId favoriteMovieId);
}
