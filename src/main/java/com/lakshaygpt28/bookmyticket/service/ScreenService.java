package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.exception.ScreenNotFoundException;
import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.repository.ScreenRepository;
import com.lakshaygpt28.bookmyticket.util.ErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {
    private final Logger LOG = LoggerFactory.getLogger(ScreenService.class);

    private final ScreenRepository screenRepository;
    private final TheatreService theatreService;

    @Autowired
    public ScreenService(ScreenRepository screenRepository, TheatreService theatreService) {
        this.screenRepository = screenRepository;
        this.theatreService = theatreService;
    }

    public Screen addScreen(Screen newScreen) {
        LOG.info("Adding screen: {}", newScreen.getId());
        newScreen.getSeats().forEach(seat -> seat.setScreen(newScreen));
        Screen savedScreen = screenRepository.save(newScreen);
        LOG.info("Screen added successfully: {}", savedScreen.getId());
        return savedScreen;
    }

    public Screen getScreenById(Long id) {
        LOG.info("Fetching screen with id: {}", id);
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ScreenNotFoundException(String.format(ErrorMessages.SCREEN_NOT_FOUND, id)));
        LOG.info("Screen fetched successfully: {}", screen.getId());
        return screen;
    }

    public List<Screen> getAllScreens() {
        LOG.info("Fetching all screens");
        List<Screen> screens = screenRepository.findAll();
        LOG.info("All screens fetched successfully");
        return screens;
    }

    public List<Screen> getScreensByTheatreId(Long theatreId) {
        LOG.info("Fetching screens for theatreId: {}", theatreId);
        Theatre theatre = theatreService.getTheatreById(theatreId);
        List<Screen> screens = screenRepository.findByTheatreId(theatre.getId());
        LOG.info("Retrieved {} screens for theatreId: {}", screens.size(), theatreId);
        return screens;
    }

    public List<Screen> addScreensByTheatreIdAndCityId(Long cityId, Long theatreId, List<Screen> screens) {
        LOG.info("Adding screens for theatreId: {}", theatreId);
        Theatre theatre = theatreService.getTheatreByCityAndId(cityId, theatreId);
        screens.forEach(screen -> screen.setTheatre(theatre));
        LOG.info("Theatre found. Saving screens for theatreId: {}", theatreId);
        List<Screen> savedScreens = screenRepository.saveAll(screens);
        LOG.info("Screens added successfully: {}", savedScreens.size());
        return savedScreens;
    }

    public List<Screen> getScreensByTheatreIdAndCityId(Long cityId, Long theatreId) {
        LOG.info("Fetching screens for theatreId: {} and cityId: {}", theatreId, cityId);
        Theatre theatre = theatreService.getTheatreByCityAndId(cityId, theatreId);
        LOG.info("Theatre found for cityId: {}. Fetching screens for theatreId: {}", cityId, theatreId);
        return getScreensByTheatreId(theatre.getId());
    }

    public Screen getScreenByIdAndTheatreIdAndCityId(Long cityId, Long theatreId, Long id) {
        LOG.info("Fetching screen with id: {} for theatreId: {} and cityId: {}", id, theatreId, cityId);
        Theatre theatre = theatreService.getTheatreByCityAndId(cityId, theatreId);
        LOG.info("Theatre found for cityId: {}. Fetching screen with id: {} for theatreId: {}", cityId, id, theatreId);

        Screen screen = screenRepository.findByTheatreIdAndId(theatre.getId(), id);
        if (screen == null) {
            throw new ScreenNotFoundException(String.format(ErrorMessages.SCREEN_NOT_FOUND, id));
        }
        LOG.info("Screen found: {}", screen.getId());
        return screen;
    }
}
