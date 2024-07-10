package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.model.ShowSeat;
import com.lakshaygpt28.bookmyticket.repository.ShowSeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowSeatService {
    private final Logger LOG = LoggerFactory.getLogger(ShowSeatService.class);

    private final ShowSeatRepository showSeatRepository;
    private final SeatService seatService;

    @Autowired
    public ShowSeatService(ShowSeatRepository showSeatRepository, SeatService seatService) {
        this.showSeatRepository = showSeatRepository;
        this.seatService = seatService;
    }


    public List<ShowSeat> getShowSeatsBySeatIds(Long showId, List<Long> seatIds) {
        LOG.info("Request to fetch show seats for show with id: {} for {} seat ids", showId, seatIds.size());

        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIdIn(showId, seatIds);

        if (showSeats.size() != seatIds.size()) {
            throw new RuntimeException("One or more seats not found with ids: " + seatIds);
        }

        LOG.info("Successfully fetched show seats for show with id: {} for {} seat ids", showId, seatIds.size());
        return showSeats;
    }

    public void saveShowSeats(List<ShowSeat> showSeats) {
        LOG.info("Request to save show seats: {}", showSeats);
        showSeatRepository.saveAll(showSeats);
        LOG.info("Successfully saved show seats: {}", showSeats);
    }
    
    public void createShowSeats(List<Show> shows) {
        LOG.debug("Creating show seats for {} shows", shows.size());

        List<ShowSeat> showSeats = new ArrayList<>();
        for (Show show: shows) {
            LOG.debug("Processing show: {}", show.getId());
            List<Seat> seats = seatService.getSeatsByScreenId(show.getScreen().getId());
            for (Seat seat: seats) {
                LOG.debug("Creating show seat for seat: {}", seat.getId());
                ShowSeat showSeat = new ShowSeat(show, seat);
                showSeats.add(showSeat);
            }
        }
        LOG.debug("Saving {} show seats", showSeats.size());
        saveShowSeats(showSeats);
        LOG.debug("Show seats saved successfully");
    }

    public List<ShowSeat> getAvailableShowSeats(Long showId) {
        LOG.info("Request to fetch available show seats for show with id: {}", showId);
        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndIsAvailableTrue(showId);
        LOG.info("Successfully fetched available show seats for show with id: {}", showId);
        return showSeats;
    }

    public List<ShowSeat> getShowSeats(Long showId) {
        LOG.info("Request to fetch all show seats for show with id: {}", showId);
        List<ShowSeat> showSeats = showSeatRepository.findByShowId(showId);
        LOG.info("Successfully fetched all show seats for show with id: {}", showId);
        return showSeats;
    }
}
