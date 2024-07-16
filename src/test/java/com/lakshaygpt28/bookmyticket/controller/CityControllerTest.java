package com.lakshaygpt28.bookmyticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.service.CityService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }

    @Test
    void addCity_ValidCity_ShouldReturnCreated() throws Exception {
        City city = new City(1L, "New York", null);

        when(cityService.addCity(city)).thenReturn(city);

        mockMvc.perform(post("/api/v1/cities")
                        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(city))).andExpect(status().isCreated());
    }

    @Test
    void getAllCities_ShouldReturnAllCities() throws Exception {
        List<City> cities = new ArrayList<>();
        cities.add(new City(1L, "New York", null));
        cities.add(new City(2L, "Los Angeles", null));

        when(cityService.getAllCities()).thenReturn(cities);

        mockMvc.perform(get("/api/v1/cities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCityById_ShouldReturnCity() throws Exception {
        City city = new City(1L, "New York", null);
        Long cityId = 1L;
        when(cityService.getCityById(cityId)).thenReturn(city);

        mockMvc.perform(get("/api/v1/cities/{cityId}", cityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(cityId));
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}