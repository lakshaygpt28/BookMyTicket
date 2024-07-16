package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.service.ScreenService;
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
class ScreenControllerTest {

    @Mock
    private ScreenService screenService;

    @InjectMocks
    private ScreenController screenController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(screenController).build();
    }

    @Test
    void addScreen_ValidRequest_ShouldReturnCreated() throws Exception {
        Screen newScreen = new Screen(null, "Screen 1", null, new ArrayList<>());
        Screen savedScreen = new Screen(1L, "Screen 1", null, new ArrayList<>());

        when(screenService.addScreen(any(Screen.class))).thenReturn(savedScreen);

        mockMvc.perform(post("/api/v1/screens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newScreen)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Screen 1"));
    }

    @Test
    void getAllScreens_ShouldReturnAllScreens() throws Exception {
        Theatre theatre = new Theatre(1L, "Theatre A", null, new ArrayList<>());
        List<Screen> screens = new ArrayList<>();
        screens.add(new Screen(1L, "Screen A", theatre, new ArrayList<>()));
        screens.add(new Screen(2L, "Screen B", theatre, new ArrayList<>()));

        when(screenService.getAllScreens()).thenReturn(screens);

        mockMvc.perform(get("/api/v1/screens")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Screen A"))
                .andExpect(jsonPath("$[0].theatre.id").value(1L))
                .andExpect(jsonPath("$[0].theatre.name").value("Theatre A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Screen B"))
                .andExpect(jsonPath("$[1].theatre.id").value(1L))
                .andExpect(jsonPath("$[1].theatre.name").value("Theatre A"));
    }

    @Test
    void addScreensByTheatreId_ValidRequest_ShouldReturnCreated() throws Exception {
        Long theatreId = 1L;
        Long cityId = 1L;
        Theatre theatre = new Theatre(theatreId, "Theatre A", null, new ArrayList<>());
        List<Screen> newScreens = new ArrayList<>();
        newScreens.add(new Screen(null, "Screen A", theatre, new ArrayList<>()));
        newScreens.add(new Screen(null, "Screen B", theatre, new ArrayList<>()));

        List<Screen> savedScreens = new ArrayList<>();
        savedScreens.add(new Screen(1L, "Screen A", theatre, new ArrayList<>()));
        savedScreens.add(new Screen(2L, "Screen B", theatre, new ArrayList<>()));

        when(screenService.addScreensByTheatreIdAndCityId(any(), eq(theatreId), anyList())).thenReturn(savedScreens);

        mockMvc.perform(post("/api/v1/cities/{cityId}/theatres/{theatreId}/screens", cityId, theatreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newScreens)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response[0].id").value(1L))
                .andExpect(jsonPath("$.response[0].name").value("Screen A"));
    }

    @Test
    void getScreensByTheatreId_ShouldReturnScreensByTheatreId() throws Exception {
        Long theatreId = 1L;
        Long cityId = 1L;
        Theatre theatre = new Theatre(theatreId, "Theatre A", null, new ArrayList<>());
        List<Screen> screens = new ArrayList<>();
        screens.add(new Screen(1L, "Screen A", theatre, new ArrayList<>()));
        screens.add(new Screen(2L, "Screen B", theatre, new ArrayList<>()));

        when(screenService.getScreensByTheatreIdAndCityId(cityId, theatreId)).thenReturn(screens);

        mockMvc.perform(get("/api/v1/cities/{cityId}/theatres/{theatreId}/screens", cityId, theatreId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response[0].id").value(1L))
                .andExpect(jsonPath("$.response[0].name").value("Screen A"))
                .andExpect(jsonPath("$.response[1].id").value(2L))
                .andExpect(jsonPath("$.response[1].name").value("Screen B"));
    }

    @Test
    void getScreenById_ShouldReturnScreenById() throws Exception {
        Long screenId = 1L;
        Theatre theatre = new Theatre(1L, "Theatre A", null, new ArrayList<>());
        Long cityId = 1L;
        Long theatreId = 1L;
        Screen screen = new Screen(screenId, "Screen A", theatre, new ArrayList<>());

        when(screenService.getScreenByIdAndTheatreIdAndCityId(cityId, theatreId, screenId)).thenReturn(screen);

        mockMvc.perform(get("/api/v1/cities/{cityId}/theatres/{theatreId}/screens/{id}", cityId, theatreId, screenId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.response.id").value(screenId))
                .andExpect(jsonPath("$.response.name").value("Screen A"));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}