package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.TestData;
import com.lakshaygpt28.bookmyticket.model.City;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.repository.TheatreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TheatreServiceTest {

    @InjectMocks
    private TheatreService theatreService;

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private CityService cityService;

    private City dummyCity;

    @BeforeEach
    public void setUp() {
        dummyCity = TestData.getDummyCity();
    }

    @Test
    public void getAllTheatres_ReturnsAllTheatres() {
        List<Theatre> mockTheatres = TestData.getDummyTheatres();
        when(theatreRepository.findAll()).thenReturn(mockTheatres);

        List<Theatre> theatres = theatreService.getAllTheatres();

        verify(theatreRepository, times(1)).findAll();
        assertEquals(2, theatres.size());
        assertEquals("PVR Cinemas", theatres.get(0).getName());
        assertEquals("INOX", theatres.get(1).getName());
    }

    @Test
    public void getTheatresByCityId_ExistingCityId_ReturnsTheatres() {
        List<Theatre> cityTheatres = TestData.createTheatresForCity(dummyCity);
        when(theatreRepository.findByCityId(dummyCity.getId())).thenReturn(cityTheatres);

        List<Theatre> theatres = theatreService.getTheatresByCityId(dummyCity.getId());

        verify(theatreRepository, times(1)).findByCityId(dummyCity.getId());
        assertEquals(2, theatres.size());
        assertEquals("PVR Cinemas", theatres.get(0).getName());
        assertEquals("INOX", theatres.get(1).getName());
    }

    @Test
    public void addTheatre_ValidTheatre_ReturnsSavedTheatre() {
        Theatre newTheatre = TestData.getDummyTheatre();
        Theatre savedTheatre = TestData.getDummyTheatre();

        when(theatreRepository.save(any(Theatre.class))).thenReturn(savedTheatre);
        when(cityService.getCityById(dummyCity.getId())).thenReturn(Optional.of(dummyCity));
        Theatre returnedTheatre = theatreService.addTheatre(newTheatre);

        verify(theatreRepository, times(1)).save(newTheatre);
        assertEquals(savedTheatre.getId(), returnedTheatre.getId());
        assertEquals(savedTheatre.getName(), returnedTheatre.getName());
    }

    @Test
    public void addTheatresByCityId_ValidCityIdAndTheatres_ReturnsSavedTheatres() {
        List<Theatre> theatresToAdd = TestData.createTheatresForCity(dummyCity);

        when(cityService.getCityById(dummyCity.getId())).thenReturn(Optional.of(dummyCity));
        when(theatreRepository.saveAll(anyList())).thenReturn(theatresToAdd);

        List<Theatre> savedTheatres = theatreService.addTheatresByCityId(dummyCity.getId(), theatresToAdd);

        verify(theatreRepository, times(1)).saveAll(theatresToAdd);
        assertEquals(2, savedTheatres.size());
        assertEquals("PVR Cinemas", savedTheatres.get(0).getName());
        assertEquals("INOX", savedTheatres.get(1).getName());
    }

    @Test
    public void getTheatreById_ExistingTheatreId_ReturnsTheatre() {
        Theatre theatre = TestData.getDummyTheatre();
        when(theatreRepository.findById(1L)).thenReturn(Optional.of(theatre));

        Optional<Theatre> retrievedTheatre = theatreService.getTheatreById(1L);

        verify(theatreRepository, times(1)).findById(1L);
        assertEquals(theatre.getName(), retrievedTheatre.get().getName());
    }
}