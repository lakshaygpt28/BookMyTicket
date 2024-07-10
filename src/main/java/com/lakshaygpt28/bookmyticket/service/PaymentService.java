package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.model.BookingStatus;
import com.lakshaygpt28.bookmyticket.request.PaymentRequest;
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
    public Boolean processPayment(PaymentRequest paymentRequest) {
        String lockKey = "payment_lock_bookingId:" + paymentRequest.getBookingId();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(10, TimeUnit.MINUTES)) {
                return processPaymentInternal(paymentRequest);
            } else {
                throw new RuntimeException("Failed to acquire lock for processing payment for Booking Id: " + paymentRequest.getBookingId());
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

    private Boolean processPaymentInternal(PaymentRequest paymentRequest) {
        LOG.info("Processing payment for Booking Id: {}", paymentRequest.getBookingId());
        Long bookingId = paymentRequest.getBookingId();
        Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (booking.getBookingStatus() != BookingStatus.RESERVED) {
            LOG.warn("Booking status is not reserved for Booking Id: {}, Skipping payment", bookingId);
            return false;
        }

        boolean paymentSuccess = simulatePaymentProcessing(paymentRequest, booking);

        if (paymentSuccess) {
            bookingService.updateBookingStatus(bookingId, BookingStatus.BOOKED);
            LOG.info("Payment processed successfully for Booking Id: {}", bookingId);
            return true;
        }
        bookingService.cancelBooking(bookingId);
        LOG.info("Payment failed for Booking Id: {}", bookingId);
        return false;
    }

    private boolean simulatePaymentProcessing(PaymentRequest paymentRequest, Booking booking) {
        return booking.getTotalAmount().compareTo(paymentRequest.getPaymentAmount()) <= 0;
    }
}