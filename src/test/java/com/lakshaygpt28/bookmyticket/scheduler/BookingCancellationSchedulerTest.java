package com.lakshaygpt28.bookmyticket.scheduler;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingCancellationSchedulerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingCancellationScheduler bookingCancellationScheduler;

    @Test
    public void CancelUnpaidBookings_whenUnpaidBookingsFound_shouldCancel() {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        Booking booking2 = new Booking();
        booking2.setId(2L);
        List<Booking> unpaidBookings = Arrays.asList(booking1, booking2);

        when(bookingService.getUnpaidBookings(ArgumentMatchers.any(LocalDateTime.class))).thenReturn(unpaidBookings);

        bookingCancellationScheduler.cancelUnpaidBookings();

        verify(bookingService, times(1)).getUnpaidBookings(ArgumentMatchers.any(LocalDateTime.class));
        verify(bookingService, times(1)).cancelBooking(1L);
        verify(bookingService, times(1)).cancelBooking(2L);
    }
}