package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.MovieTestData;
import com.lakshaygpt28.bookmyticket.TestData.ScreenTestData;
import com.lakshaygpt28.bookmyticket.TestData.ShowSeatTestData;
import com.lakshaygpt28.bookmyticket.TestData.ShowTestData;
import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.model.ShowSeat;
import com.lakshaygpt28.bookmyticket.repository.ShowSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowSeatServiceTest {

    @InjectMocks
    private ShowSeatService showSeatService;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private SeatService seatService;

    @BeforeEach
    public void setUp() {
        reset(showSeatRepository, seatService);
    }

    @Test
    public void saveShowSeats_ShouldSaveAllShowSeats() {
        List<ShowSeat> showSeatsToSave = ShowSeatTestData.getDummyShowSeats();
        when(showSeatRepository.saveAll(anyList())).thenReturn(showSeatsToSave);
        showSeatService.saveShowSeats(showSeatsToSave);
        verify(showSeatRepository, times(1)).saveAll(showSeatsToSave);
    }

    @Test
    public void getShowSeatsByShowIdAndSeatIds_ShouldReturnShowSeatsForExistingShowIdAndSeatIds() {
        Long showId = 1L;
        List<Long> seatIds = List.of(1L, 2L);
        List<ShowSeat> expectedShowSeats = ShowSeatTestData.getDummyShowSeats();
        when(showSeatRepository.findByShowIdAndSeatIdIn(showId, seatIds)).thenReturn(expectedShowSeats);
        List<ShowSeat> actualShowSeats = showSeatService.getShowSeatsBySeatIds(showId, seatIds);
        assertEquals(expectedShowSeats.size(), actualShowSeats.size());
        verify(showSeatRepository, times(1)).findByShowIdAndSeatIdIn(showId, seatIds);
    }

    @Test
    public void createShowSeats_ShouldCreateShowSeatsForGivenShowsAndSeats() {
        Show dummyShow = ShowTestData.getDummyShow1(ScreenTestData.getDummyScreen1(), MovieTestData.getDummyMovie1());
        Seat dummySeat = new Seat();
        List<Show> shows = List.of(dummyShow);
        List<Seat> seats = List.of(dummySeat);
        List<ShowSeat> expectedShowSeats = ShowSeatTestData.createShowSeats(shows, seats);

        when(seatService.getSeatsByScreenId(anyLong())).thenReturn(seats);
        when(showSeatRepository.saveAll(anyList())).thenReturn(expectedShowSeats);

        showSeatService.createShowSeats(shows);
        verify(showSeatRepository, times(1)).saveAll(expectedShowSeats);
    }

    @Test
    public void getAvailableShowSeats_ShouldFetchAvailableShowSeatsForGivenShowId() {
        Long showId = 1L;
        List<ShowSeat> expectedShowSeats = ShowSeatTestData.createAvailableShowSeats(new ArrayList<>());
        when(showSeatRepository.findByShowIdAndIsAvailableTrue(showId)).thenReturn(expectedShowSeats);
        List<ShowSeat> actualShowSeats = showSeatService.getAvailableShowSeats(showId);
        assertEquals(expectedShowSeats.size(), actualShowSeats.size());
        verify(showSeatRepository, times(1)).findByShowIdAndIsAvailableTrue(showId);
    }

    @Test
    public void getShowSeats_ShouldFetchAllShowSeatsForGivenShowId() {
        Long showId = 1L;
        List<ShowSeat> expectedShowSeats = ShowSeatTestData.createShowSeats(new ArrayList<>(), new ArrayList<>());
        when(showSeatRepository.findByShowId(showId)).thenReturn(expectedShowSeats);
        List<ShowSeat> actualShowSeats = showSeatService.getShowSeats(showId);
        assertEquals(expectedShowSeats.size(), actualShowSeats.size());
        verify(showSeatRepository, times(1)).findByShowId(showId);
    }
}