package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.TestData;
import com.lakshaygpt28.bookmyticket.exception.CityNotFoundException;
import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Test
    public void addCity_ValidCity_ReturnsSavedCity() {
        City city = TestData.getDummyCity1();

        when(cityRepository.save(any(City.class))).thenReturn(city);
        City savedCity = cityService.addCity(city);

        verify(cityRepository, times(1)).save(city);
        assertEquals(city.getName(), savedCity.getName());
    }

    @Test
    public void addCity_NullCity_ThrowsNullPointerException() {
        City city = null;

        assertThrows(NullPointerException.class, () -> cityService.addCity(city));

        verify(cityRepository, never()).save(any());
    }

    @Test
    public void getAllCities_NoCities_ReturnsEmptyList() {
        List<City> mockCities = new ArrayList<>();

        when(cityRepository.findAll()).thenReturn(mockCities);
        List<City> cities = cityService.getAllCities();

        verify(cityRepository, times(1)).findAll();
        assertTrue(cities.isEmpty());
    }

    @Test
    public void getAllCities_MultipleCities_ReturnsListOfCities() {
        List<City> mockCities = Arrays.asList(TestData.getDummyCity1(), TestData.getDummyCity2());

        when(cityRepository.findAll()).thenReturn(mockCities);
        List<City> cities = cityService.getAllCities();

        verify(cityRepository, times(1)).findAll();
        assertEquals(2, cities.size());
        assertEquals(TestData.getDummyCity1().getName(), cities.get(0).getName());
        assertEquals(TestData.getDummyCity2().getName(), cities.get(1).getName());
    }

    @Test
    public void getCityById_ExistingCityId_ReturnsCity() {
        City city = TestData.getDummyCity1();

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        City optionalCity = cityService.getCityById(1L);

        verify(cityRepository, times(1)).findById(1L);
        assertEquals(city.getName(), optionalCity.getName());
    }

    @Test
    public void getCityById_NonExistingCityId_ThrowsException() {
        Long nonExistingId = 999L;

        when(cityRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(CityNotFoundException.class, () -> cityService.getCityById(nonExistingId));

        verify(cityRepository, times(1)).findById(nonExistingId);
    }
}