package com.example.moviex.controller;

import com.example.moviex.model.FavoriteMovie;
import com.example.moviex.model.FavoriteMovieId;
import com.example.moviex.model.Movie;
import com.example.moviex.repository.FavoriteMovieRepository;
import com.example.moviex.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
public class FavoriteMovieController {

    @Autowired
    private FavoriteMovieRepository favoriteMovieRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{userId}")
    public List<Movie> getFavoriteMovies(@PathVariable Long userId) {
        List<FavoriteMovie> favoriteMovies = favoriteMovieRepository.findByUserId(userId);

        // Extract movieIds from favoriteMovies
        List<Long> movieIds = favoriteMovies.stream()
                .map(FavoriteMovie::getMovieId)
                .collect(Collectors.toList());

        // Retrieve movies corresponding to the extracted movieIds
        List<Movie> movies = movieRepository.findAllById(movieIds);
        return movies;
    }

    @PostMapping("/{userId}/add")
    public FavoriteMovie addToFavoriteList(@PathVariable Long userId, @RequestBody Map<String, Integer> requestBody) {
        Long movieId = Long.valueOf(requestBody.get("movieId"));
        int ratingIndex = requestBody.get("ratingIndex");

        // Create a new FavoriteMovie object
        FavoriteMovie favoriteMovie = new FavoriteMovie(userId, movieId, ratingIndex);

        // Save the favorite movie to the repository
        return favoriteMovieRepository.save(favoriteMovie);
    }

    // This is concurrency collection ???
//    @PostMapping("/{userId}/add")
//    public CompletableFuture<FavoriteMovie> addToFavoriteList(@PathVariable Long userId, @RequestBody Map<String, Integer> requestBody) {
//        return CompletableFuture.supplyAsync(() -> {
//            Long movieId = Long.valueOf(requestBody.get("movieId"));
//            int ratingIndex = requestBody.get("ratingIndex");
//
//            FavoriteMovie favoriteMovie = new FavoriteMovie(userId, movieId, ratingIndex);
//
//            return favoriteMovieRepository.save(favoriteMovie);
//        });
//    }


    @PutMapping("/{userId}/rate/{movieId}")
    public FavoriteMovie rateMovie(@PathVariable Long userId, @PathVariable Long movieId, @RequestBody Map<String, Integer> requestBody) {
        int ratingIndex = requestBody.get("rate");
        Optional<FavoriteMovie> optionalFavoriteMovie = favoriteMovieRepository.findById(new FavoriteMovieId(userId, movieId));
        if (optionalFavoriteMovie.isPresent()) {
            // Update the ratingIndex
            FavoriteMovie favoriteMovie = optionalFavoriteMovie.get();
            favoriteMovie.setRatingIndex(ratingIndex);

            // Save the updated favorite movie to the repository
            return favoriteMovieRepository.save(favoriteMovie);
        } else {
            // Handle the case where the favorite movie is not found
            throw new RuntimeException("Favorite movie not found for user ID: " + userId + " and movie ID: " + movieId);
        }
    }

    @DeleteMapping("/{userId}/remove/{movieId}")
    public void removeFromFavoriteList(@PathVariable Long userId, @PathVariable Long movieId) {
        try {
            favoriteMovieRepository.deleteById(new FavoriteMovieId(userId,movieId));
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
