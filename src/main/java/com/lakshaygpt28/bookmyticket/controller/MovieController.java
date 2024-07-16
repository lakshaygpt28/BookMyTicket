package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.dto.MovieDTO;
import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.mapper.MovieMapper;
import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<ApiResponse<List<MovieDTO>>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        List<MovieDTO> movieDTOS = movies.stream().map(MovieMapper.INSTANCE::toDto).toList();
        ApiResponse<List<MovieDTO>> response = ApiResponse.<List<MovieDTO>>builder()
                .success(true)
                .message("All movies fetched successfully")
                .response(movieDTOS)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/movies")
    public ResponseEntity<ApiResponse<List<MovieDTO>>> addMovies(@RequestBody @Valid List<Movie> newMovies) {
        List<Movie> savedMovies = movieService.addMovies(newMovies);
        List<MovieDTO> movieDTOS = savedMovies.stream().map(MovieMapper.INSTANCE::toDto).toList();
        ApiResponse<List<MovieDTO>> response = ApiResponse.<List<MovieDTO>>builder()
                .success(true)
                .message("All movies fetched successfully")
                .response(movieDTOS)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
