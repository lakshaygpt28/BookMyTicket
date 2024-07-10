package com.lakshaygpt28.bookmyticket.service;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.request.PaymentRequest;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {
    private final Logger LOG = LoggerFactory.getLogger(PaymentService.class);

    private final BookingService bookingService;

    public PaymentService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Boolean processPayment(PaymentRequest paymentRequest) {
        LOG.info("Processing payment for Booking Id: {}", paymentRequest.getBookingId());
        Booking booking = bookingService.getBookingById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + paymentRequest.getBookingId()));
        if (booking.getTotalAmount().compareTo(paymentRequest.getPaymentAmount()) > 0) {
            LOG.info("Payment processed successfully for Booking Id: {}", paymentRequest.getBookingId());
            return true;
        }
        LOG.info("Payment failed for Booking Id: {}", paymentRequest.getBookingId());
        return false;
    }
}
