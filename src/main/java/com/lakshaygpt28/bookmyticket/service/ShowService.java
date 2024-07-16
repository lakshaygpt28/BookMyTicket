package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.repository.ShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private static final Logger LOG = LoggerFactory.getLogger(ShowService.class);

    private final ShowRepository showRepository;
    private final ScreenService screenService;
    private final ShowSeatService showSeatService;

    @Autowired
    public ShowService(ShowRepository showRepository, ScreenService screenService, ShowSeatService showSeatService) {
        this.showRepository = showRepository;
        this.screenService = screenService;
        this.showSeatService = showSeatService;
    }

    public Optional<Show> getShowById(Long id) {
        LOG.info("Fetching show with id: {}", id);
        Optional<Show> show = showRepository.findById(id);

        if (show.isPresent()) {
            LOG.info("Show found: {}", show.get().getId());
        } else {
            LOG.info("Show not found with id: {}", id);
        }
        return show;
    }

    public List<Show> addShows(List<Show> newShows) {
        LOG.info("Adding {} shows", newShows.size());
        List<Show> savedShows = showRepository.saveAll(newShows);
        showSeatService.createShowSeats(savedShows);
        LOG.info("{} shows added successfully", savedShows.size());
        return savedShows;
    }

    public List<Show> getAllShows() {
        LOG.info("Fetching all shows");
        List<Show> shows = showRepository.findAll();
        LOG.info("All shows fetched successfully");
        return shows;
    }

    public List<Show> getShowsByTheatreIdAndCityId(Long cityId, Long theatreId) {
        LOG.info("Fetching shows for theatreId: {}", theatreId);
        List<Screen> screens = screenService.getScreensByTheatreIdAndCityId(cityId, theatreId);
        List<Show> shows = new ArrayList<>();
        for (Screen screen: screens) {
            shows.addAll(showRepository.findByScreenId(screen.getId()));
        }
        LOG.info("Retrieved {} shows for theatreId: {}", shows.size(), theatreId);
        return shows;
    }

    public List<Show> getShowsByScreenId(Long screenId) {
        LOG.info("Fetching shows for screenId: {}", screenId);
        List<Show> shows = showRepository.findByScreenId(screenId);
        LOG.info("Retrieved {} shows for screenId: {}", shows.size(), screenId);
        return shows;
    }
}
