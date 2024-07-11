package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.service.ShowService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ShowControllerTest {

    @InjectMocks
    private ShowController showController;

    private MockMvc mockMvc;

    @Mock
    private ShowService showService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(showController).build();
    }

    @Test
    void getShowById_ValidId_ShouldReturnShow() throws Exception {
        Long showId = 1L;
        Show show = new Show();
        show.setId(showId);
        when(showService.getShowById(showId)).thenReturn(Optional.of(show));

        mockMvc.perform(get("/api/v1/shows/{id}", showId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(showId));
    }

    @Test
    void addShows_ValidShows_ShouldReturnCreated() throws Exception {
        List<Show> newShows = new ArrayList<>();
        newShows.add(new Show());
        newShows.add(new Show());

        List<Show> savedShows = new ArrayList<>();
        savedShows.add(new Show());
        savedShows.add(new Show());

        when(showService.addShows(any(List.class))).thenReturn(savedShows);

        mockMvc.perform(post("/api/v1/shows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newShows)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllShows_ShouldReturnAllShows() throws Exception {
        List<Show> shows = new ArrayList<>();
        shows.add(new Show(1L, null, null, null, null, null));
        shows.add(new Show(2L, null, null, null, null, null));

        when(showService.getAllShows()).thenReturn(shows);

        mockMvc.perform(get("/api/v1/shows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void getShowsByTheatreId_ValidTheatreId_ShouldReturnShows() throws Exception {
        Long theatreId = 1L;
        List<Show> shows = new ArrayList<>();
        shows.add(new Show(1L, null, null, null, null, null));
        shows.add(new Show(2L, null, null, null, null, null));


        when(showService.getShowsByTheatreId(theatreId)).thenReturn(shows);

        mockMvc.perform(get("/api/v1/theatres/{theatreId}/shows", theatreId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
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