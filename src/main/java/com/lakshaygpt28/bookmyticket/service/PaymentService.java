package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.model.BookingStatus;
import com.lakshaygpt28.bookmyticket.dto.request.PaymentRequestDTO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Service
public class PaymentService {
    private final Logger LOG = LoggerFactory.getLogger(PaymentService.class);

    private final BookingService bookingService;
    private final RedissonClient redissonClient;

    public PaymentService(BookingService bookingService, RedissonClient redissonClient) {
        this.bookingService = bookingService;
        this.redissonClient = redissonClient;
    }

    @Transactional
    public Boolean processPayment(PaymentRequestDTO paymentRequestDTO) {
        String lockKey = "payment_lock_bookingId:" + paymentRequestDTO.getBookingId();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(10, TimeUnit.MINUTES)) {
                return processPaymentInternal(paymentRequestDTO);
            } else {
                throw new RuntimeException("Failed to acquire lock for processing payment for Booking Id: " + paymentRequestDTO.getBookingId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while acquiring lock", e);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    private Boolean processPaymentInternal(PaymentRequestDTO paymentRequestDTO) {
        LOG.info("Processing payment for Booking Id: {}", paymentRequestDTO.getBookingId());
        Long bookingId = paymentRequestDTO.getBookingId();
        Booking booking = bookingService.getBookingById(bookingId);

        if (booking.getBookingStatus() != BookingStatus.RESERVED) {
            LOG.warn("Booking status is not reserved for Booking Id: {}, Skipping payment", bookingId);
            return false;
        }

        boolean paymentSuccess = simulatePaymentProcessing(paymentRequestDTO, booking);

        if (paymentSuccess) {
            bookingService.updateBookingStatus(bookingId, BookingStatus.BOOKED);
            LOG.info("Payment processed successfully for Booking Id: {}", bookingId);
            return true;
        }
        bookingService.cancelBooking(bookingId);
        LOG.info("Payment failed for Booking Id: {}", bookingId);
        return false;
    }

    private boolean simulatePaymentProcessing(PaymentRequestDTO paymentRequestDTO, Booking booking) {
        return booking.getTotalAmount().compareTo(paymentRequestDTO.getPaymentAmount()) <= 0;
    }
}