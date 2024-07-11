package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.request.PaymentRequest;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import com.lakshaygpt28.bookmyticket.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService, BookingService bookingService) {
        this.paymentService = paymentService;

    }

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        Boolean paymentSuccessful = paymentService.processPayment(paymentRequest);
        Long bookingId = paymentRequest.getBookingId();
        if (paymentSuccessful) {
            return ResponseEntity.ok("Payment processed successfully. Tickets are booked for booking id: " + bookingId);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed booking id: " + bookingId);
    }
}
