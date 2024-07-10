package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieTestData {
    public static Movie getDummyMovie1() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Dummy Movie 1");
        movie.setDescription("This is the first dummy movie");
        return movie;
    }

    public static Movie getDummyMovie2() {
        Movie movie = new Movie();
        movie.setId(2L);
        movie.setName("Dummy Movie 2");
        movie.setDescription("This is the second dummy movie");
        return movie;
    }

    public static List<Movie> getDummyMoviesList() {
        List<Movie> movies = new ArrayList<>();
        movies.add(getDummyMovie1());
        movies.add(getDummyMovie2());
        return movies;
    }
}
