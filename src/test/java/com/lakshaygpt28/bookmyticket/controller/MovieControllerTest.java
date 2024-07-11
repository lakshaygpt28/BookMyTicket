package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    void getAllMovies_ShouldReturnAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1L, "Inception", "Sci-Fi"));
        movies.add(new Movie(2L, "The Dark Knight", "Action"));

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addMovies_ValidRequest_ShouldReturnAddedMovies() throws Exception {
        List<Movie> newMovies = new ArrayList<>();
        newMovies.add(new Movie(null, "Interstellar", "Sci-Fi"));
        newMovies.add(new Movie(null, "The Shawshank Redemption", "Drama"));

        List<Movie> savedMovies = new ArrayList<>();
        savedMovies.add(new Movie(1L, "Interstellar", "Sci-Fi"));
        savedMovies.add(new Movie(2L, "The Shawshank Redemption", "Drama"));

        when(movieService.addMovies(newMovies)).thenReturn(savedMovies);

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newMovies)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Interstellar"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("The Shawshank Redemption"));
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}