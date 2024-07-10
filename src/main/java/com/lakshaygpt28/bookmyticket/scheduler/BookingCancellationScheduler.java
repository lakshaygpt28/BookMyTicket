package com.lakshaygpt28.bookmyticket.scheduler;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class BookingCancellationScheduler {

    private final BookingService bookingService;

    @Autowired
    public BookingCancellationScheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void cancelUnpaidBookings() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(2);
        List<Booking> unpaidBookings = bookingService.getUnpaidBookings(expirationTime);
        for (Booking booking: unpaidBookings) {
            bookingService.cancelBooking(booking.getId());
        }
    }
}
