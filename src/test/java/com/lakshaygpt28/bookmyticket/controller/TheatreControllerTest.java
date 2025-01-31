package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.service.TheatreService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TheatreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TheatreService theatreService;

    @InjectMocks
    private TheatreController theatreController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(theatreController).build();
    }

    @Test
    void getTheatresByCityId_ValidCityId_ShouldReturnTheatres() throws Exception {
        Long cityId = 1L;
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1L, "Theatre A", null, null));
        theatres.add(new Theatre(2L, "Theatre B", null, null));

        when(theatreService.getTheatresByCityId(cityId)).thenReturn(theatres);

        mockMvc.perform(get("/api/v1/cities/{cityId}/theatres", cityId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].name").value("Theatre A"))
                .andExpect(jsonPath("$.response[1].id").value(2))
                .andExpect(jsonPath("$.response[1].name").value("Theatre B"));
    }

    @Test
    void addTheatresByCityId_ValidCityIdAndTheatres_ShouldReturnCreated() throws Exception {
        Long cityId = 1L;
        List<Theatre> theatresToAdd = new ArrayList<>();
        theatresToAdd.add(new Theatre(null, "Theatre X", null, null));
        theatresToAdd.add(new Theatre(null, "Theatre Y", null, null));

        List<Theatre> savedTheatres = new ArrayList<>();
        savedTheatres.add(new Theatre(1L, "Theatre X", null, null));
        savedTheatres.add(new Theatre(2L, "Theatre Y", null, null));

        when(theatreService.addTheatresByCityId(eq(cityId), anyList())).thenReturn(savedTheatres);

        mockMvc.perform(post("/api/v1/cities/{cityId}/theatres", cityId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(theatresToAdd)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Theatre X"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Theatre Y"));
    }

    @Test
    void getAllTheatres_ShouldReturnAllTheatres() throws Exception {
        Long cityId = 1L;
        List<Theatre> theatres = new ArrayList<>();
        theatres.add(new Theatre(1L, "Theatre A", null, null));
        theatres.add(new Theatre(2L, "Theatre B", null, null));

        when(theatreService.getTheatresByCityId(cityId)).thenReturn(theatres);

        mockMvc.perform(get("/api/v1/cities/{cityId}/theatres", cityId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response[0].id").value(1))
                .andExpect(jsonPath("$.response[0].name").value("Theatre A"))
                .andExpect(jsonPath("$.response[1].id").value(2))
                .andExpect(jsonPath("$.response[1].name").value("Theatre B"));
    }

    @Test
    void getTheatreById_ValidTheatreId_ShouldReturnTheatre() throws Exception {
        Long theatreId = 1L;
        Long cityId = 1L;
        Theatre theatre = new Theatre(theatreId, "Theatre A", null, null);

        when(theatreService.getTheatreByCityAndId(cityId, theatreId)).thenReturn(theatre);

        mockMvc.perform(get("/api/v1/cities/{cityId}/theatres/{id}", cityId, theatreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.id").value(1))
                .andExpect(jsonPath("$.response.name").value("Theatre A"));
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