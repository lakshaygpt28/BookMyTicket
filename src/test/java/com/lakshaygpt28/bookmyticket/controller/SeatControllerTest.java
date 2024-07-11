package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SeatControllerTest {

    @InjectMocks
    private SeatController seatController;

    private MockMvc mockMvc;

    @Mock
    private SeatService seatService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(seatController).build();
    }

    @Test
    void addSeat_ValidSeat_ShouldReturnCreated() throws Exception {
        Seat seat = new Seat(1L, "A1", null);
        Seat savedSeat = new Seat(1L, "A1", null);

        when(seatService.addSeat(any(Seat.class))).thenReturn(savedSeat);

        mockMvc.perform(post("/api/v1/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(seat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedSeat.getId()))
                .andExpect(jsonPath("$.seatNumber").value(savedSeat.getSeatNumber()));
    }

    @Test
    void getSeatsByScreenId_ValidScreenId_ShouldReturnSeats() throws Exception {
        Long screenId = 1L;
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1L, "A1", null));
        seats.add(new Seat(2L, "A2", null));

        when(seatService.getSeatsByScreenId(screenId)).thenReturn(seats);

        mockMvc.perform(get("/api/v1/screens/{screenId}/seats", screenId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].seatNumber").value(seats.get(0).getSeatNumber()))
                .andExpect(jsonPath("$[1].seatNumber").value(seats.get(1).getSeatNumber()));
    }

    @Test
    void addSeatsByScreenId_ValidScreenIdAndSeats_ShouldReturnCreated() throws Exception {
        Long screenId = 1L;
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1L, "A1", null));
        seats.add(new Seat(2L, "A2", null));
        List<Seat> savedSeats = new ArrayList<>();
        savedSeats.add(new Seat(1L, "A1", null));
        savedSeats.add(new Seat(2L, "A2", null));

        when(seatService.addSeatsByScreenId(screenId, seats)).thenReturn(savedSeats);

        mockMvc.perform(post("/api/v1/screens/{screenId}/seats", screenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(seats)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(savedSeats.get(0).getId()))
                .andExpect(jsonPath("$[0].seatNumber").value(savedSeats.get(0).getSeatNumber()))
                .andExpect(jsonPath("$[1].id").value(savedSeats.get(1).getId()))
                .andExpect(jsonPath("$[1].seatNumber").value(savedSeats.get(1).getSeatNumber()));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}