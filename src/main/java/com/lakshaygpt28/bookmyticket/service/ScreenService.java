package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Theatre;
import com.lakshaygpt28.bookmyticket.repository.ScreenRepository;
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

    public Optional<Screen> getScreenById(Long id) {
        LOG.info("Fetching screen with id: {}", id);
        Optional<Screen> screen = screenRepository.findById(id);

        if (screen.isPresent()) {
            LOG.info("Screen found: {}", screen.get().getId());
        } else {
            LOG.info("Screen not found with id: {}", id);
        }
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
        List<Screen> screens = screenRepository.findByTheatreId(theatreId);
        LOG.info("Retrieved {} screens for theatreId: {}", screens.size(), theatreId);
        return screens;
    }


    public void deleteScreen(Long id) {
        LOG.info("Deleting screen with id: {}", id);
        screenRepository.deleteById(id);
        LOG.info("Screen deleted successfully: {}", id);
    }

    public Screen updateScreen(Long id, Screen screen) {
        LOG.info("Updating screen with id: {}", id);
        Screen updatedScreen = screenRepository.save(screen);
        LOG.info("Screen updated successfully: {}", updatedScreen.getId());
        return updatedScreen;
    }

    public List<Screen> addScreensByTheatreId(Long theatreId, List<Screen> screens) {
        LOG.info("Adding screens for theatreId: {}", theatreId);
        Theatre theatre = theatreService.getTheatreById(theatreId).orElseThrow(
                () -> new RuntimeException("Theatre not found with id: " + theatreId)
        );
        screens.forEach(screen -> screen.setTheatre(theatre));
        LOG.info("Theatre found. Saving screens for theatreId: {}", theatreId);
        List<Screen> savedScreens = screenRepository.saveAll(screens);
        LOG.info("Screens added successfully: {}", savedScreens.size());
        return savedScreens;
    }
}
