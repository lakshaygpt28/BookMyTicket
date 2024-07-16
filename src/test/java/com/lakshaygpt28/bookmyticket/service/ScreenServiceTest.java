package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.ScreenTestData;
import com.lakshaygpt28.bookmyticket.exception.ScreenNotFoundException;
import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.repository.ScreenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScreenServiceTest {

    @InjectMocks
    private ScreenService screenService;

    @Mock
    private ScreenRepository screenRepository;

    @Mock
    private TheatreService theatreService;

    private Theatre dummyTheatre;
    private List<Screen> dummyScreens;

    @BeforeEach
    public void setUp() {
        dummyTheatre = ScreenTestData.getDummyTheatre();
        dummyScreens = ScreenTestData.createScreens();
    }

    @Test
    public void addScreen_ValidScreen_ReturnsSavedScreen() {
        Screen newScreen = ScreenTestData.getDummyScreen(dummyTheatre);
        Screen savedScreen = ScreenTestData.getDummyScreen(dummyTheatre);

        when(screenRepository.save(any(Screen.class))).thenReturn(savedScreen);
        Screen returnedScreen = screenService.addScreen(newScreen);

        verify(screenRepository, times(1)).save(newScreen);
        assertEquals(savedScreen.getId(), returnedScreen.getId());
        assertEquals(savedScreen.getName(), returnedScreen.getName());
    }

    @Test
    public void getScreenById_ExistingScreenId_ReturnsScreen() {
        Screen screen = ScreenTestData.getDummyScreen(dummyTheatre);
        when(screenRepository.findById(1L)).thenReturn(Optional.of(screen));

        Screen retrievedScreen = screenService.getScreenById(1L);

        verify(screenRepository, times(1)).findById(1L);
        assertEquals(screen.getName(), retrievedScreen.getName());
    }


    @Test
    public void getScreenById_NonExistingScreenId_ThrowsException() {
        when(screenRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ScreenNotFoundException.class, () -> screenService.getScreenById(1L));

        verify(screenRepository, times(1)).findById(1L);
    }

    @Test
    public void getAllScreens_NoScreens_ReturnsEmptyList() {
        List<Screen> mockScreens = ScreenTestData.createScreens();
        when(screenRepository.findAll()).thenReturn(mockScreens);

        List<Screen> screens = screenService.getAllScreens();

        verify(screenRepository, times(1)).findAll();
        assertEquals(2, screens.size());
        assertEquals("Screen 1", screens.get(0).getName());
        assertEquals("Screen 2", screens.get(1).getName());
    }

    @Test
    public void getScreensByTheatreId_ValidTheatreId_ReturnsScreens() {
        Long theatreId = dummyTheatre.getId();
        when(screenRepository.findByTheatreId(theatreId)).thenReturn(dummyScreens);
        when(theatreService.getTheatreById(theatreId)).thenReturn(dummyTheatre);

        List<Screen> screens = screenService.getScreensByTheatreId(theatreId);

        verify(screenRepository, times(1)).findByTheatreId(theatreId);
        assertEquals(2, screens.size());
        assertEquals("Screen 1", screens.get(0).getName());
        assertEquals("Screen 2", screens.get(1).getName());
    }

    @Test
    public void addScreensByTheatreId_ValidTheatreIdAndScreens_ReturnsSavedScreensIdAndCity() {
        Long theatreId = dummyTheatre.getId();
        Long cityId = dummyTheatre.getCity().getId();
        List<Screen> screensToAdd = ScreenTestData.createScreens();

        when(theatreService.getTheatreByCityAndId(cityId, theatreId)).thenReturn(dummyTheatre);
        when(screenRepository.saveAll(anyList())).thenReturn(screensToAdd);

        List<Screen> savedScreens = screenService.addScreensByTheatreIdAndCityId(cityId, theatreId, screensToAdd);

        verify(screenRepository, times(1)).saveAll(screensToAdd);
        assertEquals(2, savedScreens.size());
        assertEquals("Screen 1", savedScreens.get(0).getName());
        assertEquals("Screen 2", savedScreens.get(1).getName());
    }
}