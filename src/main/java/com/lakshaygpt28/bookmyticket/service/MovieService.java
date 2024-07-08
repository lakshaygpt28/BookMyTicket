package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        LOG.info("Fetching all movies");
        List<Movie> movies = movieRepository.findAll();
        LOG.info("All movies fetched successfully");
        return movies;
    }

    public Optional<Movie> getMovieById(Long id) {
        LOG.info("Fetching movie with id: {}", id);
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            LOG.info("Movie found: {}", movie.get().getId());
        } else {
            LOG.info("Movie not found with id: {}", id);
        }
        return movie;
    }


    public List<Movie> addMovies(List<Movie> newMovies) {
        LOG.info("Adding {} movies", newMovies.size());
        List<Movie> savedMovies = movieRepository.saveAll(newMovies);
        LOG.info("{} movies added successfully", savedMovies.size());
        return savedMovies;
    }
}
