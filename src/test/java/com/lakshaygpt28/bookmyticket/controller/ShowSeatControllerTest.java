package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.ShowSeat;
import com.lakshaygpt28.bookmyticket.service.ShowSeatService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class ShowSeatControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShowSeatService showSeatService;

    @InjectMocks
    private ShowSeatController showSeatController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(showSeatController).build();
    }

    @Test
    void getAvailableShowSeats_ValidShowId_ShouldReturnAvailableSeats() throws Exception {
        Long showId = 1L;
        List<ShowSeat> availableSeats = new ArrayList<>();
        availableSeats.add(new ShowSeat(1L, null, null, true, null));
        availableSeats.add(new ShowSeat(2L, null, null, true, null));

        when(showSeatService.getAvailableShowSeats(showId)).thenReturn(availableSeats);

        mockMvc.perform(get("/api/v1/shows/{showId}/seats/available", showId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void getShowSeats_ValidShowId_ShouldReturnAllSeats() throws Exception {
        Long showId = 1L;
        List<ShowSeat> showSeats = new ArrayList<>();
        showSeats.add(new ShowSeat(1L, null, null, true, null));
        showSeats.add(new ShowSeat(2L, null, null, true, null));

        when(showSeatService.getShowSeats(showId)).thenReturn(showSeats);

        mockMvc.perform(get("/api/v1/shows/{showId}/seats", showId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}