package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.model.Seat;
import com.lakshaygpt28.bookmyticket.model.Show;
import com.lakshaygpt28.bookmyticket.model.User;
import com.lakshaygpt28.bookmyticket.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final ShowService showService;
    private final UserService userService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, SeatService seatService, ShowService showService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.showService = showService;
        this.userService = userService;
    }

    public Booking createBooking(Long userId, Long showId, List<Long> seatIds) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Show show = showService.getShowById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));
        List<Seat> seats = seatService.getSeatsByIds(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("One or more seats not found with ids: " + seatIds);
        }

        for (Seat seat: seats) {
            if (!seat.getScreen().equals(show.getScreen())) {
                throw new RuntimeException("One or more seats are not from the show's screen.");
            }
            if (!seat.isAvailable()) {
                throw new RuntimeException("One or more seats are not available for booking.");
            }
            seat.setAvailable(false);
        }
        Booking booking = new Booking(user, show, seats, LocalDateTime.now());
        bookingRepository.save(booking);
        return booking;
    }

    public Optional<Booking> getBookingById(Long id) {
        LOG.info("Fetching booking with id: {}", id);
        Optional<Booking> booking = bookingRepository.findById(id);

        if (booking.isPresent()) {
            LOG.info("Booking found: {}", booking.get().getId());
        } else {
            LOG.info("Booking not found with id: {}", id);
        }

        return booking;
    }
}
