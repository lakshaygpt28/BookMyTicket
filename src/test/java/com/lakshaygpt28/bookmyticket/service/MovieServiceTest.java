package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.repository.MovieRepository;
import com.lakshaygpt28.bookmyticket.TestData.MovieTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        when(movieRepository.findAll()).thenReturn(MovieTestData.getDummyMoviesList());

        when(movieRepository.findById(1L)).thenReturn(Optional.of(MovieTestData.getDummyMovie1()));
        when(movieRepository.findById(2L)).thenReturn(Optional.of(MovieTestData.getDummyMovie2()));

        when(movieRepository.saveAll(anyList())).thenReturn(MovieTestData.getDummyMoviesList());
    }

    @Test
    public void getAllMovies_ReturnsAllMovies() {
        List<Movie> movies = movieService.getAllMovies();

        assertEquals(2, movies.size());
        assertEquals("Dummy Movie 1", movies.get(0).getName());
        assertEquals("Dummy Movie 2", movies.get(1).getName());
    }

    @Test
    public void getMovieById_ExistingId_ReturnsMovie() {
        Optional<Movie> movie = movieService.getMovieById(1L);

        assertEquals("Dummy Movie 1", movie.get().getName());
    }

    @Test
    public void addMovies_ValidMovies_ReturnsSavedMovies() {
        List<Movie> newMovies = MovieTestData.getDummyMoviesList();

        List<Movie> savedMovies = movieService.addMovies(newMovies);

        assertEquals(2, savedMovies.size());
        assertEquals("Dummy Movie 1", savedMovies.get(0).getName());
        assertEquals("Dummy Movie 2", savedMovies.get(1).getName());
    }
}