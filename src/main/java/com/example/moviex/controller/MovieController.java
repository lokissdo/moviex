package com.example.moviex.controller;

import com.example.moviex.model.Movie;
import com.example.moviex.repository.MovieRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie) {
        System.out.println(movie.getName());
        return movieRepository.save(movie);
    }
}
