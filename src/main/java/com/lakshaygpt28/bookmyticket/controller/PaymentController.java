package com.lakshaygpt28.bookmyticket.controller;

import com.lakshaygpt28.bookmyticket.dto.request.PaymentRequestDTO;
import com.lakshaygpt28.bookmyticket.dto.response.ApiResponse;
import com.lakshaygpt28.bookmyticket.exception.PaymentProcessingException;
import com.lakshaygpt28.bookmyticket.service.BookingService;
import com.lakshaygpt28.bookmyticket.service.PaymentService;
import com.lakshaygpt28.bookmyticket.util.ErrorMessages;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<String>> processPayment(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO) {
        Boolean paymentSuccessful = paymentService.processPayment(paymentRequestDTO);
        Long bookingId = paymentRequestDTO.getBookingId();

        if (!paymentSuccessful) {
            throw new PaymentProcessingException(String.format(ErrorMessages.PAYMENT_PROCESSING_ERROR, bookingId));
        }
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Payment processed successfully. Tickets are booked for booking id: " + bookingId)
                .build();
        return  ResponseEntity.ok(response);
    }
}
