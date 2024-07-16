package com.lakshaygpt28.bookmyticket.service;
import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.model.BookingStatus;
import com.lakshaygpt28.bookmyticket.dto.request.PaymentRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private BookingService bookingService;

    @Mock
    private RedissonClient redissonClient;

    @Test
    void processPayment_ShouldProcessPaymentSuccessfully() throws InterruptedException {
        Long bookingId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(100);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(bookingId, paymentAmount);
        Booking mockBooking = new Booking(bookingId, null, null, null, null, BookingStatus.RESERVED, paymentAmount);

        RLock mockLock = mock(RLock.class);
        when(redissonClient.getLock(anyString())).thenReturn(mockLock);
        when(mockLock.tryLock(10, TimeUnit.MINUTES)).thenReturn(true);
        when(bookingService.getBookingById(bookingId)).thenReturn(mockBooking);
        when(mockLock.isLocked()).thenReturn(true);

        boolean result = paymentService.processPayment(paymentRequestDTO);

        assertTrue(result);
        verify(redissonClient, times(1)).getLock(anyString());
        verify(mockLock, times(1)).tryLock(10, TimeUnit.MINUTES);
        verify(bookingService, times(1)).getBookingById(bookingId);
        verify(bookingService, times(1)).updateBookingStatus(bookingId, BookingStatus.BOOKED);
        verify(mockLock, times(1)).unlock();
    }

    @Test
    void processPayment_ShouldReturnFalseWhenBookingStatusIsNotReserved() throws InterruptedException {
        Long bookingId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(100);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(bookingId, paymentAmount);
        Booking mockBooking = new Booking(bookingId, null, null, null, null, BookingStatus.BOOKED, paymentAmount);

        RLock mockLock = mock(RLock.class);
        when(redissonClient.getLock(anyString())).thenReturn(mockLock);
        when(mockLock.tryLock(10, TimeUnit.MINUTES)).thenReturn(true);
        when(bookingService.getBookingById(bookingId)).thenReturn(mockBooking);
        when(mockLock.isLocked()).thenReturn(true);

        boolean result = paymentService.processPayment(paymentRequestDTO);

        assertFalse(result);
        verify(redissonClient, times(1)).getLock(anyString());
        verify(mockLock, times(1)).tryLock(10, TimeUnit.MINUTES);
        verify(bookingService, times(1)).getBookingById(bookingId);
        verify(mockLock, times(1)).unlock();
    }

    @Test
    void processPayment_ShouldCancelBookingWhenPaymentFails() throws InterruptedException {
        Long bookingId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(50); // Payment amount less than total amount
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(bookingId, paymentAmount);
        Booking mockBooking = new Booking(bookingId, null, null, null, null, BookingStatus.RESERVED, BigDecimal.valueOf(100));

        RLock mockLock = mock(RLock.class);
        when(redissonClient.getLock(anyString())).thenReturn(mockLock);
        when(mockLock.tryLock(10, TimeUnit.MINUTES)).thenReturn(true);
        when(bookingService.getBookingById(bookingId)).thenReturn(mockBooking);
        when(mockLock.isLocked()).thenReturn(true);

        boolean result = paymentService.processPayment(paymentRequestDTO);

        assertFalse(result);
        verify(redissonClient, times(1)).getLock(anyString());
        verify(mockLock, times(1)).tryLock(10, TimeUnit.MINUTES);
        verify(bookingService, times(1)).getBookingById(bookingId);
        verify(bookingService, times(1)).cancelBooking(bookingId);
        verify(mockLock, times(1)).unlock();
    }

    @Test
    void processPayment_ShouldThrowRuntimeExceptionWhenLockAcquisitionFails() throws InterruptedException {
        Long bookingId = 1L;
        BigDecimal paymentAmount = BigDecimal.valueOf(100);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(bookingId, paymentAmount);

        RLock mockLock = mock(RLock.class);
        when(redissonClient.getLock(anyString())).thenReturn(mockLock);
        when(mockLock.tryLock(10, TimeUnit.MINUTES)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> paymentService.processPayment(paymentRequestDTO));
        verify(redissonClient, times(1)).getLock(anyString());
        verify(mockLock, times(1)).tryLock(10, TimeUnit.MINUTES);
        verify(mockLock, never()).unlock();
        verifyNoInteractions(bookingService); // Ensure bookingService methods were not called
    }
}