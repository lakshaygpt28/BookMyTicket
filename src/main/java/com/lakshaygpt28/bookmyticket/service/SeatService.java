package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Screen;
import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    private static final Logger LOG = LoggerFactory.getLogger(SeatService.class);

    private final SeatRepository seatRepository;
    private final ScreenService screenService;

    @Autowired
    public SeatService(SeatRepository seatRepository, ScreenService screenService) {
        this.seatRepository = seatRepository;
        this.screenService = screenService;
    }
    public List<Seat> getSeatsByIds(List<Long> seatIds) {
        LOG.info("Fetching seats with ids: {}", seatIds);
        List<Seat> seats = seatRepository.findAllById(seatIds);
        LOG.info("Retrieved {} seats with ids: {}", seats.size(), seatIds);
        return seats;
    }

    public List<Seat> getSeatsByScreenId(Long screenId) {
        LOG.info("Fetching seats for screenId: {}", screenId);
        List<Seat> seats = seatRepository.findByScreenId(screenId);
        LOG.info("Retrieved {} seats for screenId: {}", seats.size(), screenId);
        return seats;
    }

    public Seat addSeat(Seat newSeat) {
        LOG.info("Adding seat: {}", newSeat.getId());
        Seat savedSeat = seatRepository.save(newSeat);
        LOG.info("Seat added successfully: {}", savedSeat.getId());
        return savedSeat;
    }

    public List<Seat> addSeatsByScreenId(Long screenId, List<Seat> newSeats) {
        LOG.info("Adding seats for screenId: {}", screenId);
        Screen screen = screenService.getScreenById(screenId);
        newSeats.forEach(seat -> seat.setScreen(screen));
        LOG.info("Adding {} seats for screenId: {}", newSeats.size(), screenId);
        List<Seat> savedSeats = seatRepository.saveAll(newSeats);
        LOG.info("Seats added successfully: {}", savedSeats.size());
        return savedSeats;
    }
}
