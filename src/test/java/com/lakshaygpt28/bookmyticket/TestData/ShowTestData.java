package com.lakshaygpt28.bookmyticket.TestData;

import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Show;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowTestData {

    public static Show getDummyShow1(Screen screen, Movie movie) {
        Show show = new Show();
        show.setId(1L);
        show.setScreen(screen);
        show.setMovie(movie);
        show.setTicketPrice(BigDecimal.valueOf(250));
        show.setStartTime(LocalDateTime.of(2024, 8, 1, 18, 0));
        show.setEndTime(LocalDateTime.of(2024, 8, 1, 21, 0));
        return show;
    }

    public static Show getDummyShow2(Screen screen, Movie movie) {
        Show show = new Show();
        show.setId(2L);
        show.setScreen(screen);
        show.setMovie(movie);
        show.setTicketPrice(BigDecimal.valueOf(300));
        show.setStartTime(LocalDateTime.of(2024, 8, 2, 14, 0));
        show.setEndTime(LocalDateTime.of(2024, 8, 2, 17, 0));
        return show;
    }

    public static List<Show> createShows(Screen screen, Movie movie) {
        List<Show> shows = new ArrayList<>();
        shows.add(getDummyShow1(screen, movie));
        shows.add(getDummyShow2(screen, movie));
        return shows;
    }
}