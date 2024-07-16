package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.TestData.*;
import com.lakshaygpt28.bookmyticket.exception.BookingNotFoundException;
import com.lakshaygpt28.bookmyticket.model.*;
import com.lakshaygpt28.bookmyticket.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ShowSeatService showSeatService;

    @Mock
    private ShowService showService;

    @Mock
    private UserService userService;

    @Mock
    private RedissonClient redissonClient;

    @Test
    void createBooking_ShouldCreateBookingSuccessfully() {
        RLock mockLock = mock(RLock.class);
        when(mockLock.isLocked()).thenReturn(false);
        when(redissonClient.getLock(anyString())).thenReturn(mockLock);
        Long userId = 1L;
        Long showId = 1L;
        List<Long> seatIds = Arrays.asList(1L, 2L);

        User mockUser = UserTestData.getDummyUser1();

        Show mockShow = ShowTestData.getDummyShow1(ScreenTestData.getDummyScreen1(), MovieTestData.getDummyMovie1());
        List<ShowSeat> mockShowSeats = Arrays.asList(
                new ShowSeat(mockShow, SeatTestData.getDummySeat()),
                new ShowSeat(mockShow, SeatTestData.getDummySeat())
        );

        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        when(showService.getShowById(showId)).thenReturn(Optional.of(mockShow));
        when(showSeatService.getShowSeatsBySeatIds(showId, seatIds)).thenReturn(mockShowSeats);

        Booking createdBooking = bookingService.createBooking(userId, showId, seatIds);

        assertNotNull(createdBooking);
        assertEquals(userId, createdBooking.getUser().getId());
        assertEquals(showId, createdBooking.getShow().getId());
        assertEquals(mockShowSeats.size(), createdBooking.getShowSeats().size());
        assertEquals(BookingStatus.RESERVED, createdBooking.getBookingStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(showSeatService, times(1)).saveShowSeats(mockShowSeats);
    }

    @Test
    void cancelBooking_ShouldCancelBookingSuccessfully() {
        Long bookingId = 1L;
        List<ShowSeat> mockShowSeats = Arrays.asList(new ShowSeat(), new ShowSeat());
        Booking mockBooking = new Booking(bookingId, new User(), new Show(), mockShowSeats, LocalDateTime.now(), BookingStatus.RESERVED, BigDecimal.TEN);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        bookingService.cancelBooking(bookingId);

        assertEquals(BookingStatus.CANCELLED, mockBooking.getBookingStatus());
        verify(bookingRepository, times(1)).save(mockBooking);
    }

    @Test
    void getBookingById_ShouldReturnBookingIfExists() {
        Long bookingId = 1L;
        Booking mockBooking = new Booking(bookingId, new User(), new Show(), new ArrayList<>(), LocalDateTime.now(), BookingStatus.RESERVED, BigDecimal.TEN);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        Booking result = bookingService.getBookingById(bookingId);

        assertEquals(bookingId, result.getId());
        verify(bookingRepository, times(1)).findById(bookingId);
    }

    @Test
    void getBookingById_ShouldReturnEmptyOptionalForNonExistingBooking() {
        Long nonExistingBookingId = 999L;
        when(bookingRepository.findById(nonExistingBookingId)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(nonExistingBookingId));

        verify(bookingRepository, times(1)).findById(nonExistingBookingId);
    }

    @Test
    void updateBookingStatus_ShouldUpdateBookingStatusSuccessfully() {
        Long bookingId = 1L;
        BookingStatus newStatus = BookingStatus.BOOKED;

        bookingService.updateBookingStatus(bookingId, newStatus);

        verify(bookingRepository, times(1)).updateBookingStatus(bookingId, newStatus);
    }

    @Test
    void getUnpaidBookings_ShouldReturnUnpaidBookings() {
        LocalDateTime expirationTime = LocalDateTime.now();

        List<Booking> mockUnpaidBookings = Arrays.asList(
                new Booking(1L, new User(), new Show(), new ArrayList<>(), LocalDateTime.now(), BookingStatus.RESERVED, BigDecimal.TEN),
                new Booking(2L, new User(), new Show(), new ArrayList<>(), LocalDateTime.now(), BookingStatus.RESERVED, BigDecimal.TEN)
        );

        when(bookingRepository.getUnpaidBookings(BookingStatus.RESERVED, expirationTime)).thenReturn(mockUnpaidBookings);

        List<Booking> result = bookingService.getUnpaidBookings(expirationTime);

        assertEquals(mockUnpaidBookings.size(), result.size());
        assertEquals(mockUnpaidBookings.get(0).getId(), result.get(0).getId());
        assertEquals(mockUnpaidBookings.get(1).getId(), result.get(1).getId());
        verify(bookingRepository, times(1)).getUnpaidBookings(BookingStatus.RESERVED, expirationTime);
    }
}