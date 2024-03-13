package com.example.moviex;

import com.example.moviex.controller.FavoriteMovieController;
import com.example.moviex.model.FavoriteMovie;
import com.example.moviex.model.Movie;
import com.example.moviex.repository.FavoriteMovieRepository;
import com.example.moviex.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FavoriteMovieController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteMovieRepository favoriteMovieRepository;

    @MockBean
    private MovieRepository movieRepository;

    @InjectMocks
    private FavoriteMovieController favoriteMovieController;

    @Test
    void getFavoriteMovies() throws Exception {
        // Given
        Long userId = 1L;
        List<Movie> movies = Arrays.asList(new Movie(1L, 2.5,"Movie 1","fake url 1",2), new Movie(2L, 3.5,"Movie 2","fake url 2",454));
        when(favoriteMovieRepository.findByUserId(userId)).thenReturn(Arrays.asList(
                new FavoriteMovie(userId, 1L, 5),
                new FavoriteMovie(userId, 2L, 4)
        ));
        when(movieRepository.findAllById(Arrays.asList(1L, 2L))).thenReturn(movies);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/favorites/{userId}", userId))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(movies.size()));
    }

    @Test
    void addToFavoriteList() throws Exception {
        // Given
        Long userId = 1L;
        Long movieId = 1L;
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("movieId", 1);
        requestBody.put("ratingIndex", 5);
        when(favoriteMovieRepository.save(any())).thenReturn(new FavoriteMovie(userId, movieId, 5));

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/favorites/{userId}/add", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"movieId\":1,\"ratingIndex\":5}"))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void rateMovie() throws Exception {
        // Given
        Long userId = 1L;
        Long movieId = 1L;
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("rate", 4);
        when(favoriteMovieRepository.findById(any())).thenReturn(java.util.Optional.of(new FavoriteMovie(userId, movieId, 5)));
        when(favoriteMovieRepository.save(any())).thenReturn(new FavoriteMovie(userId, movieId, 4));

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/favorites/{userId}/rate/{movieId}", userId, movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rate\":4}"))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeFromFavoriteList() throws Exception {
        // Given
        Long userId = 1L;
        Long movieId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/favorites/{userId}/remove/{movieId}", userId, movieId))
                // Then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
