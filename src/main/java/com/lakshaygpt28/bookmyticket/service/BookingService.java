package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.*;
import com.lakshaygpt28.bookmyticket.repository.BookingRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService {
    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final ShowSeatService showSeatService;
    private final ShowService showService;
    private final UserService userService;
    private final RedissonClient redissonClient;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ShowSeatService showSeatService, ShowService showService, UserService userService, RedissonClient redissonClient) {
        this.bookingRepository = bookingRepository;
        this.showSeatService = showSeatService;
        this.showService = showService;
        this.userService = userService;
        this.redissonClient = redissonClient;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Booking createBooking(Long userId, Long showId, List<Long> seatIds) {
        List<RLock> locks = new ArrayList<>();
        try {
            LOG.info("Attempting to acquire locks for {} seats", seatIds.size());
            for (Long seatId: seatIds) {
                String seatLockKey = "showId:" + showId + "_seatId:" + seatId;
                RLock lock = redissonClient.getLock(seatLockKey);
                lock.lock(2, TimeUnit.SECONDS);
                locks.add(lock);
            }
            LOG.info("Locks acquired for {} seats successfully", seatIds.size());
            return createBookingInternal(userId, showId, seatIds);
        } finally {
            LOG.info("Releasing locks for {} seats", seatIds.size());
            for (RLock lock: locks) {
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
            LOG.info("Locks released for {} seats successfully", seatIds.size());
        }
    }

    private Booking createBookingInternal(Long userId, Long showId, List<Long> seatIds) {
        LOG.info("Request to create booking for user with id: {} for show with id: {} for {} seat ids", userId, showId, seatIds.size());
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Show show = showService.getShowById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));
        List<ShowSeat> showSeats = showSeatService.getShowSeatsBySeatIds(showId, seatIds);
        if (showSeats.size() != seatIds.size()) {
            throw new RuntimeException("One or more seats not found with ids: " + seatIds);
        }

        for (ShowSeat showSeat: showSeats) {
            if (!showSeat.isAvailable()) {
                throw new RuntimeException("One or more seats are not available for booking.");
            }
            showSeat.setAvailable(false);
        }
        BigDecimal totalAmount = show.getTicketPrice().multiply(BigDecimal.valueOf(showSeats.size()));
        Booking booking = new Booking(user, show, showSeats, totalAmount);
        bookingRepository.save(booking);
        showSeats.forEach(showSeat -> showSeat.setBooking(booking));
        showSeatService.saveShowSeats(showSeats);
        LOG.info("Successfully reserved seats for booking with id: {}", booking.getId());
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateBookingStatus(Long bookingId, BookingStatus bookingStatus) {
        LOG.info("Request to update booking status for booking with id: {} to: {}", bookingId, bookingStatus);
        bookingRepository.updateBookingStatus(bookingId, bookingStatus);
        LOG.info("Successfully updated booking status for booking with id: {} to: {}", bookingId, bookingStatus);
    }

    public List<Booking> getUnpaidBookings(LocalDateTime expirationTime) {
        LOG.info("Fetching unpaid bookings with expiration time: {}", expirationTime);
        List<Booking> unpaidBookings = bookingRepository.getUnpaidBookings(BookingStatus.RESERVED, expirationTime);
        LOG.info("{} Unpaid bookings fetched successfully", unpaidBookings.size());
        return unpaidBookings;
    }

    public void cancelBooking(Long bookingId) {
        LOG.info("Cancelling booking with id: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        List<ShowSeat> showSeats = booking.getShowSeats();
        for (ShowSeat showSeat : showSeats) {
            showSeat.setAvailable(true);
            showSeat.setBooking(null);
        }
        booking.setShowSeats(null);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        showSeatService.saveShowSeats(showSeats);
        LOG.info("Booking cancelled successfully for booking with id: {}", booking.getId());
    }
}
