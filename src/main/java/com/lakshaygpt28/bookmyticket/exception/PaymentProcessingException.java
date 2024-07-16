package com.lakshaygpt28.bookmyticket.exception;

public class PaymentProcessingException extends RuntimeException {
    public PaymentProcessingException(String message) {
        super(message);
    }
}
