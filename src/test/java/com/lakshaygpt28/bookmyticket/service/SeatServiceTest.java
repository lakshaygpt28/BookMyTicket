package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.ScreenTestData;
import com.lakshaygpt28.bookmyticket.TestData.SeatTestData;
import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScreenService screenService;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void getSeatsByIds_ReturnsSeats() {
        List<Long> seatIds = List.of(1L, 2L);
        List<Seat> expectedSeats = SeatTestData.dummySeats(seatIds);
        when(seatRepository.findAllById(seatIds)).thenReturn(expectedSeats);

        List<Seat> actualSeats = seatService.getSeatsByIds(seatIds);

        assertEquals(expectedSeats.size(), actualSeats.size());
        assertEquals(expectedSeats.get(0).getId(), actualSeats.get(0).getId());
        assertEquals(expectedSeats.get(1).getId(), actualSeats.get(1).getId());
        verify(seatRepository, times(1)).findAllById(seatIds);
    }

    @Test
    public void getSeatsByScreenId_ReturnsSeats() {
        Long screenId = 1L;
        List<Seat> expectedSeats = SeatTestData.dummySeats();
        when(seatRepository.findByScreenId(screenId)).thenReturn(expectedSeats);

        List<Seat> actualSeats = seatService.getSeatsByScreenId(screenId);

        assertEquals(expectedSeats.size(), actualSeats.size());
        assertEquals(expectedSeats.get(0).getId(), actualSeats.get(0).getId());
        verify(seatRepository, times(1)).findByScreenId(screenId);
    }

    @Test
    public void addSeat_ReturnsSavedSeat() {
        Seat newSeat = SeatTestData.getDummySeat();
        Seat savedSeat = SeatTestData.getDummySeat();
        savedSeat.setId(1L);
        when(seatRepository.save(any(Seat.class))).thenReturn(savedSeat);

        Seat returnedSeat = seatService.addSeat(newSeat);

        assertEquals(savedSeat.getId(), returnedSeat.getId());
        verify(seatRepository, times(1)).save(newSeat);
    }

    @Test
    public void addSeatsByScreenId_ReturnsSavedSeats() {
        Long screenId = 1L;
        Screen screen = ScreenTestData.getDummyScreen1();
        List<Seat> newSeats = SeatTestData.dummySeats();
        List<Seat> savedSeats = SeatTestData.dummySeats();
        savedSeats.forEach(seat -> seat.setId(1L));
        when(screenService.getScreenById(screenId)).thenReturn(Optional.of(screen));
        when(seatRepository.saveAll(anyList())).thenReturn(savedSeats);

        List<Seat> returnedSeats = seatService.addSeatsByScreenId(screenId, newSeats);

        assertEquals(savedSeats.size(), returnedSeats.size());
        assertEquals(savedSeats.get(0).getId(), returnedSeats.get(0).getId());
        assertEquals(savedSeats.get(1).getId(), returnedSeats.get(1).getId());
        verify(seatRepository, times(1)).saveAll(newSeats);
    }
}