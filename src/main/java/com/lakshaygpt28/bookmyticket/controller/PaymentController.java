package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.model.Booking;
import com.lakshaygpt28.bookmyticket.model.BookingStatus;
import com.lakshaygpt28.bookmyticket.request.PaymentRequest;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import com.lakshaygpt28.bookmyticket.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    @Autowired
    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;
        this.bookingService = bookingService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        Boolean paymentSuccessful = paymentService.processPayment(paymentRequest);
        Long bookingId = paymentRequest.getBookingId();
        if (paymentSuccessful) {
            bookingService.updateBookingStatus(bookingId, BookingStatus.BOOKED);
            ResponseEntity.ok("Payment processed successfully. Tickets are booked for booking id: " + bookingId);
        }
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Payment failed. Tickets are not booked for booking id: " + bookingId);
    }
}
