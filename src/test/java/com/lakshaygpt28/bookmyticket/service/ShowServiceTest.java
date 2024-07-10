package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.MovieTestData;
import com.lakshaygpt28.bookmyticket.TestData.ScreenTestData;
import com.lakshaygpt28.bookmyticket.TestData.ShowTestData;
import com.lakshaygpt28.bookmyticket.model.Movie;
import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {

    @InjectMocks
    private ShowService showService;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private ScreenService screenService;

    @Mock
    private ShowSeatService showSeatService;

    private Screen dummyScreen;
    private List<Show> dummyShows;

    @BeforeEach
    public void setUp() {
        dummyScreen = ScreenTestData.createScreens().get(0);
        Movie dummyMovie = MovieTestData.getDummyMovie1();
        dummyShows = ShowTestData.createShows(dummyScreen, dummyMovie);
    }

    @Test
    public void getShowById_ExistingShowId_ReturnsShow() {
        Long showId = dummyShows.get(0).getId();
        when(showRepository.findById(showId)).thenReturn(Optional.of(dummyShows.get(0)));

        Optional<Show> result = showService.getShowById(showId);

        verify(showRepository, times(1)).findById(showId);
        assertEquals(dummyShows.get(0).getId(), result.get().getId());
    }

    @Test
    public void addShows_ValidShows_ReturnsSavedShows() {
        when(showRepository.saveAll(anyList())).thenReturn(dummyShows);

        List<Show> savedShows = showService.addShows(dummyShows);

        verify(showRepository, times(1)).saveAll(dummyShows);
        verify(showSeatService, times(1)).createShowSeats(dummyShows);
        assertEquals(dummyShows.size(), savedShows.size());
    }

    @Test
    public void getAllShows_ReturnsAllShows() {
        when(showRepository.findAll()).thenReturn(dummyShows);

        List<Show> result = showService.getAllShows();

        verify(showRepository, times(1)).findAll();
        assertEquals(dummyShows.size(), result.size());
    }

    @Test
    public void getShowsByTheatreId_ExistingTheatreId_ReturnsShows() {
        Long theatreId = dummyScreen.getTheatre().getId();
        List<Screen> screens = new ArrayList<>();
        screens.add(dummyScreen);

        when(screenService.getScreensByTheatreId(theatreId)).thenReturn(screens);
        when(showRepository.findByScreenId(dummyScreen.getId())).thenReturn(dummyShows);

        List<Show> result = showService.getShowsByTheatreId(theatreId);

        verify(screenService, times(1)).getScreensByTheatreId(theatreId);
        verify(showRepository, times(1)).findByScreenId(dummyScreen.getId());
        assertEquals(dummyShows.size(), result.size());
    }

    @Test
    public void getShowsByScreenId_ExistingScreenId_ReturnsShows() {
        Long screenId = dummyScreen.getId();

        when(showRepository.findByScreenId(screenId)).thenReturn(dummyShows);

        List<Show> result = showService.getShowsByScreenId(screenId);

        verify(showRepository, times(1)).findByScreenId(screenId);
        assertEquals(dummyShows.size(), result.size());
    }
}